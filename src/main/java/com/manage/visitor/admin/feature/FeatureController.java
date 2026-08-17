package com.manage.visitor.admin.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.manage.visitor.config.exception.AppException;
import com.manage.visitor.config.exception.BadRequestException;
import com.manage.visitor.config.exception.DataNotFoundException;
import com.manage.visitor.config.http.Message;
import com.manage.visitor.admin.role.RoleFeatureDto;
import com.manage.visitor.admin.role.entity.Role;
import com.manage.visitor.admin.role.RoleFeature;
import com.manage.visitor.job.RoleFeatureRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/features")
@RequiredArgsConstructor
public class FeatureController {

  private final FeatureService service;

  @PostMapping
  public ResponseEntity<FeatureDto> save(@RequestBody Feature.FeatureFormDto dto) {
    log.info("HTTP POST /features body={}", dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
  }

  @GetMapping
  public ResponseEntity<List<FeatureDto>> getAll() {
    log.info("HTTP GET /features");
    return ResponseEntity.ok().body(service.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<FeatureDto> getById(@PathVariable Integer id) {
    log.info("HTTP GET /features/{}", id);
    return ResponseEntity.ok().body(service.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<FeatureDto> update(@PathVariable Integer id, @RequestBody Feature.FeatureFormDto dto) {
    log.info("HTTP PUT /features body={}", dto);
    return ResponseEntity.ok().body(service.update(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    log.info("HTTP DELETE /features/{}", id);
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Component
  public static class FeatureMapper {

    // GETALL OR GETBYID
    public FeatureDto toDto(Feature data) {
      return new FeatureDto(
              data.getId(),
              data.getLabel(),
              data.getCode(),
              data.getIdKey(),
              data.getUrl());
    }

  // ADD
    public Feature toEntity(Feature.FeatureFormDto dto) {
      return Feature.builder()
              .code(dto.code())
              .label(dto.label())
              .idKey(dto.idKey())
              .url(dto.url())
              .build();
    }

  }

  @Repository
  public static interface FeatureRepository extends JpaRepository<Feature, Integer> {
    List<Feature> findByIdKeyIsNotNull();

    List<Feature> findByIdKey(Integer idKey);

    boolean existsByIdKey(Integer idKey);
  }

  @Slf4j
  @Service
  @RequiredArgsConstructor
  public static class FeatureService {

    private final FeatureRepository featureRepository;
    private final FeatureMapper featureMapper;
    private final RoleFeatureRepository roleFeatureRepository;


    // 2. avant dajouter une nouvelle feafture faudrais vérifier sil le groupe de role
    // auquel est associé cette fonctionnalité nest pas déjà associé à un role si oui l'associé au
    // role
    @Transactional
    public FeatureDto save(Feature.FeatureFormDto dto) {
      log.info("save feature");
      if (dto.idKey() != null) {
        // 1.
        if (!featureRepository.existsByIdKey(dto.idKey())) {
          throw new BadRequestException("feature key not existing");
        }
      }

      try {
          // saved
          Feature saved = featureRepository.save(featureMapper.toEntity(dto));
          // 2.
          List<RoleFeature> roleWithKeyFeature =
              roleFeatureRepository.findByFeature_IdKey(saved.getIdKey());
          if (!roleWithKeyFeature.isEmpty()) {
            roleWithKeyFeature.forEach(
                x -> {
                  log.info("add to role={}", x.getId());
                  roleFeatureRepository.save(
                          RoleFeature.builder()
                                  .state(false)
                                  .role(Role.builder().id(x.getId()).build())
                                  .feature(Feature.builder().id(saved.getId()).build()
                                  ).build()
                  );
                });
          }
          return featureMapper.toDto(saved);
      } catch (Exception e) {
        log.error("save feature failed: ", e);
        throw new AppException(e.getMessage());
      }
    }

    public List<FeatureDto> getAll() {
      log.info("loading feature");
      try {
        return featureRepository.findAll().stream().map(featureMapper::toDto).toList();
      } catch (Exception e) {
        log.error("loading feature failed: ", e);
        throw new AppException(e.getMessage());
      }
    }

    public List<FeatureDto> getAllKeyFeature() {
      log.info("loading key feature");
      try {
        return featureRepository.findByIdKeyIsNotNull().stream().map(featureMapper::toDto).toList();
      } catch (Exception e) {
        log.error("loading key feature failed: ", e);
        throw new AppException(e.getMessage());
      }
    }

    @Transactional
    public void addKeyFeatureToRole(Integer idKeyFeature, Integer roleId) {
      log.info("save role with feature");
      try {
        // add begin the key feature
        roleFeatureRepository.save(
             RoleFeature.builder()
                     .state(false)
                     .role(Role.builder().id(roleId).build())
                     .feature(Feature.builder().id(idKeyFeature).build())
                     .build()
        );
        // after add other feature
        featureRepository
            .findByIdKey(idKeyFeature)
            .forEach(
                x -> {
                  roleFeatureRepository.save(
                      new RoleFeature(
                          null,
                          false,
                          new Role(roleId),
                          new Feature(x.getId())));
                });
      } catch (Exception e) {
        log.error("save role with failed: ", e);
        throw new AppException(e.getMessage());
      }
    }

    public FeatureDto getById(Integer id) {
      log.info("loading feature for {}", id);
      try {
        Optional<Feature> data = featureRepository.findById(id);
        if (data.isPresent()) {
          return featureMapper.toDto(data.get());
        }
      } catch (Exception e) {
        log.error("loading Feature failed : ", e);
        throw new AppException(e.getMessage());
      }
      throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
    }

    public FeatureDto update(Integer id, Feature.FeatureFormDto dto) {
      log.info("update feature");
      try {
        Optional<Feature> existing = featureRepository.findById(id);
        if (existing.isPresent()) {
          existing.get().setLabel(dto.label());
          existing.get().setCode(dto.code());
          existing.get().setIdKey(dto.idKey());
          existing.get().setUrl(dto.url());
          return featureMapper.toDto(featureRepository.save(existing.get()));
        }
      } catch (Exception e) {
        log.error("update feature failed: ", e);
        throw new AppException(e.getMessage());
      }
      throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
    }

    @Transactional
    public void changeStateFeatureForRole(RoleFeatureDto dto) {
      log.info("update state feature");
      try {
        List<RoleFeature> toSave = new ArrayList<>();
        log.info("loading all feature by roleid={}", dto.roleId());
        List<RoleFeature> roleFeatureList = roleFeatureRepository.findByRole_Id(dto.roleId());
        if (!dto.features().isEmpty()) {
          log.info(
              "parcourir la liste des features update pour ce role, dans le but de d'avoir les ids roleFeature"
                  + "pour préparer les datas pour la modif. features={}",
              dto.features());
          dto.features()
              .forEach(
                  x -> {
                    Optional<RoleFeature> rf =
                        roleFeatureList.stream()
                            .filter(
                                y ->
                                    Objects.equals(dto.roleId(), y.getRole().getId())
                                        && Objects.equals(x.id(), y.getFeature().getId()))
                            .findFirst();
                    rf.ifPresent(
                        roleFeature ->
                            toSave.add(
                                new RoleFeature(
                                    roleFeature.getId(),
                                    x.state(),
                                    new Role(dto.roleId()),
                                    new Feature(x.id()))));
                  });
          if (!toSave.isEmpty()) {
            roleFeatureRepository.saveAll(toSave);
            log.info("save state ok");
          }
        }
      } catch (Exception e) {
        log.error("update state feature failed: ", e);
        throw new AppException(e.getMessage());
      }
      throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
    }

    public void delete(Integer id) {
      log.info("delete feature for {}", id);
      try {
        Optional<Feature> data = featureRepository.findById(id);
        if (data.isPresent()) {
          featureRepository.deleteById(id);
          return;
        }
      } catch (Exception e) {
        log.error("delete feature failed: ", e);
        throw new AppException(e.getMessage());
      }
      throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
    }
  }
}
