package com.manage.visitor.service.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.manage.visitor.helpers.exception.AppException;
import com.manage.visitor.helpers.exception.BadRequestException;
import com.manage.visitor.helpers.exception.DataNotFoundException;
import com.manage.visitor.helpers.http.Message;
import com.manage.visitor.model.dto.admin.FeatureDto;
import com.manage.visitor.model.dto.admin.RoleFeatureDto;
import com.manage.visitor.model.entity.admin.Feature;
import com.manage.visitor.model.entity.admin.Role;
import com.manage.visitor.model.entity.admin.RoleFeature;
import com.manage.visitor.repository.admin.FeatureRepository;
import com.manage.visitor.repository.admin.RoleFeatureRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeatureService {

  private final FeatureRepository featureRepository;
  private final RoleFeatureRepository roleFeatureRepository;

  // 1. on vérifie que l'idKey existe
  // 2. avant dajouter une nouvelle feafture faudrais vérifier sil le groupe de role
  // auquel est associé cette fonctionnalité nest pas déjà associé à un role si oui l'associé au
  // role
  @Transactional
  public FeatureDto save(FeatureDto dto) {
    log.info("save feature");
    if (dto.idKey() != null) {
      // 1.
      if (!featureRepository.existsByIdKey(dto.idKey())) {
        throw new BadRequestException("feature key not existing");
      }
    }

    try {
      if (dto.id() == null) {
        Feature data = new Feature(dto); // convert dto to entity for saving
        // saved
        Feature f = featureRepository.save(data);
        // 2.
        List<RoleFeature> roleWithKeyFeature =
            roleFeatureRepository.findByFeature_IdKey(data.getIdKey());
        if (!roleWithKeyFeature.isEmpty()) {
          roleWithKeyFeature.forEach(
              x -> {
                log.info("add to role={}", x.getId());
                roleFeatureRepository.save(
                    new RoleFeature(
                        null,
                        false,
                        new Role(x.getId(), null, null),
                        new Feature(f.getId(), null, null, null, null)));
              });
        }
        return new FeatureDto(f);
      }
    } catch (Exception e) {
      log.error("save feature failed: ", e);
      throw new AppException(e.getMessage());
    }
    throw new BadRequestException(Message.ID_NOT_REQUIRED.getText());
  }

  public List<FeatureDto> getAll() {
    log.info("loading feature");
    try {
      return featureRepository.findAll().stream().map(FeatureDto::new).toList();
    } catch (Exception e) {
      log.error("loading feature failed: ", e);
      throw new AppException(e.getMessage());
    }
  }

  public List<FeatureDto> getAllKeyFeature() {
    log.info("loading key feature");
    try {
      return featureRepository.findByIdKeyIsNotNull().stream().map(FeatureDto::new).toList();
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
          new RoleFeature(
              null,
              false,
              new Role(roleId, null, null),
              new Feature(idKeyFeature, null, null, null, null)));
      // after add other feature
      featureRepository
          .findByIdKey(idKeyFeature)
          .forEach(
              x -> {
                roleFeatureRepository.save(
                    new RoleFeature(
                        null,
                        false,
                        new Role(roleId, null, null),
                        new Feature(x.getId(), null, null, null, null)));
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
        return new FeatureDto(data.get());
      }
    } catch (Exception e) {
      log.error("loading Feature failed : ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }

  public FeatureDto update(FeatureDto dto) {
    log.info("update feature");
    if (dto.id() == null) {
      throw new BadRequestException(Message.ID_REQUIRED.getText());
    }
    try {
      Optional<Feature> d = featureRepository.findById(dto.id());
      if (d.isPresent()) {
        Feature existing = d.get();
        existing.setLabel(dto.label());
        existing.setCode(dto.code());
        existing.setIdKey(dto.idKey());
        existing.setUrl(dto.url());
        return new FeatureDto(featureRepository.save(existing));
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
                                  new Role(dto.roleId(), null, null),
                                  new Feature(x.id(), null, null, null, null))));
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
