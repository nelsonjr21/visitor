package com.manage.visitor.worker.service;

import java.util.List;
import java.util.Optional;

import com.manage.visitor.job.entity.Job;
import com.manage.visitor.job.mapper.JobMapper;
import com.manage.visitor.admin.role.mapper.RoleMapper;
import com.manage.visitor.admin.role.repository.RoleRepository;
import com.manage.visitor.worker.entity.Worker;
import com.manage.visitor.worker.mapper.WorkerMapper;
import com.manage.visitor.worker.dto.WorkerDto;
import com.manage.visitor.worker.dto.WorkerFormDto;
import com.manage.visitor.worker.repository.WorkerRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.manage.visitor.config.exception.AppException;
import com.manage.visitor.config.exception.BadRequestException;
import com.manage.visitor.config.exception.DataNotFoundException;
import com.manage.visitor.config.http.Message;
import com.manage.visitor.config.security.JwtUtils;
import com.manage.visitor.user.UserDto;
import com.manage.visitor.admin.role.entity.Role;
import com.manage.visitor.admin.role.RoleWorker;
import com.manage.visitor.job.repository.JobRepository;
import com.manage.visitor.job.RoleWorkerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkerService {

  private final WorkerRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;
  private final RoleWorkerRepository roleWorkerRepository;
  private final JobRepository jobRepository;
  private final RoleRepository roleRepository;
  private final WorkerMapper workerMapper;
  private final JobMapper jobMapper;
    private final RoleMapper roleMapper;

    @Transactional
  public WorkerDto save(WorkerFormDto dto) {
    log.info("save worker");
    try {
      Worker data = workerMapper.toEntity(dto);
        if (repository.findByIdentifier(data.getIdentifier()) != null) {
          throw new BadRequestException("Username already exists");
        }
        data.setPw(passwordEncoder.encode(data.getPw()));

        // save
        Worker saved = repository.save(data);
        dto.roleList()
            .forEach(
                x -> {
                  roleWorkerRepository.save(
                          RoleWorker.builder()
                                  .role(Role.builder().id(x).build())
                                  .worker(Worker.builder().id(saved.getId()).build())
                                  .build());
                });
        return workerMapperToDto(
                saved,
                saved.getJob().getId(),
                saved.getId()
        );
    } catch (Exception e) {
      log.error("save worker failed: ", e);
      throw new AppException(e.getMessage());
    }
  }

  public WorkerDto login(UserDto user) {
    log.info("login worker");
    try {
      if (authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(user.username(), user.password()))
          .isAuthenticated()) {
        String token = jwtUtils.generateToken(user.username());
        Worker data = repository.findByIdentifier(user.username());
        return workerMapper.toDtoWithToken(
            data,
            roleWorkerRepository.findByWorker_Id(data.getId()).stream()
                .map(y -> {
                    Optional<Role> role = roleRepository.findById(y.getRole().getId());
                    return role.map(roleMapper::toDto).orElse(null);
                })
                .toList(),
                jobMapper.toDto(jobRepository.findJobById(data.getJob().getId())),
            token);
      }
    } catch (HttpClientErrorException.Conflict e) {
      log.error("login worker failed: ", e);
      throw new BadCredentialsException(Message.UNAUTHORIZED.getText());
    } catch (Exception e) {
      log.error("login worker failed: ", e);
      throw new AppException(e.getMessage());
    }
    throw new AppException(Message.GLOBAL_ALERT.getText());
  }

  public List<WorkerDto> getAll() {
    log.info("loading worker");
    try {
      return repository.findAll().stream()
          .map(
              x ->
                      workerMapperToDto(
                              x,
                              x.getJob().getId(),
                              x.getId()
                      )
          ).toList();
    } catch (Exception e) {
      log.error("loading worker failed: ", e);
      throw new AppException(e.getMessage());
    }
  }

  public WorkerDto getById(Integer id) {
    log.info("loading worker for {}", id);
    try {
      Optional<Worker> data = repository.findById(id);
      if (data.isPresent()) {
        return workerMapperToDto(
                data.get(),
                data.get().getJob().getId(),
                data.get().getId()
        );
      }
    } catch (Exception e) {
      log.error("loading Worker failed : ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }

  @Transactional
  public WorkerDto update(Integer id, WorkerFormDto dto) {
    log.info("update worker");
    try {
      Optional<Worker> existing = repository.findById(id);
      if (existing.isPresent()) {
        existing.get().setName(dto.name());
        existing.get().setSurname(dto.surname());
        existing.get().setBirthDate(dto.birthDate());
        existing.get().setIdentifier(dto.identifier());
        existing.get().setJob(Job.builder().id(dto.jobId()).build());
        repository.save(existing.get());
        dto.roleList()
            .forEach(
                x -> {
                  if (roleWorkerRepository.findByRole_IdAndWorker_Id(x, existing.get().getId())) {
                    roleWorkerRepository.save(
                            RoleWorker.builder()
                                    .role(Role.builder().id(x).build())
                                    .worker(Worker.builder().id(existing.get().getId()).build())
                                    .build()
                    );
                  }
                });
          return workerMapperToDto(
                  existing.get(),
                  existing.get().getJob().getId(),
                  existing.get().getId()
          );
      }
    } catch (Exception e) {
      log.error("update worker failed: ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }

  public void delete(Integer id) {
    log.info("delete worker for {}", id);
    try {
      Optional<Worker> data = repository.findById(id);
      if (data.isPresent()) {
        repository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      log.error("delete worker failed: ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }

  /*
  * Charge les sous objets après ajout, mise à jour, getall getbyid
  * */
  private WorkerDto workerMapperToDto(Worker worker, Integer jobId, Integer roleId) {
        return workerMapper.toDtoWithJobDtoAndRoleDtoList(
                worker,
                jobRepository.findJobById(jobId),
                roleRepository.findByRoleWorkersWorkerId(roleId) //  on a la liste des rôles en fonctions de worker id
                        .stream().map(roleMapper::toDto).toList()
        );
  }
}
