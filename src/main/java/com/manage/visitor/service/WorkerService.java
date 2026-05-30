package com.manage.visitor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.manage.visitor.helpers.exception.AppException;
import com.manage.visitor.helpers.exception.BadRequestException;
import com.manage.visitor.helpers.exception.DataNotFoundException;
import com.manage.visitor.helpers.http.Message;
import com.manage.visitor.helpers.security.JwtUtils;
import com.manage.visitor.model.dto.WorkerDto;
import com.manage.visitor.model.dto.admin.UserDto;
import com.manage.visitor.model.dto.form.WorkerFormDto;
import com.manage.visitor.model.entity.Job;
import com.manage.visitor.model.entity.Worker;
import com.manage.visitor.model.entity.admin.Role;
import com.manage.visitor.model.entity.admin.RoleWorker;
import com.manage.visitor.model.mapper.JobMapper;
import com.manage.visitor.model.mapper.WorkerMapper;
import com.manage.visitor.model.mapper.admin.RoleMapper;
import com.manage.visitor.repository.JobRepository;
import com.manage.visitor.repository.WorkerRepository;
import com.manage.visitor.repository.admin.RoleRepository;
import com.manage.visitor.repository.admin.RoleWorkerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
      return workerMapper.toDtoWithJobDtoAndRoleDtoList(
          saved,
          jobRepository.findJobById(saved.getJob().getId()),
          roleRepository
              .findByRoleWorkersWorkerId(
                  saved.getId()) //  on a la liste des rôles en fonctions de worker id
              .stream()
              .map(roleMapper::toDto)
              .toList());
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
                .map(
                    y -> {
                      Optional<Role> role = roleRepository.findById(y.getRole().getId());
                      return role.map(roleMapper::toDto).orElse(null);
                    })
                .toList(),
            jobRepository.findById(data.getJob().getId()).map(jobMapper::toDto).orElse(null),
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
                  workerMapper.toDtoWithJobDtoAndRoleDtoList(
                      x,
                      jobRepository.findJobById(x.getJob().getId()),
                      roleRepository
                          .findByRoleWorkersWorkerId(
                              x.getId()) //  on a la liste des rôles en fonctions de worker id
                          .stream()
                          .map(roleMapper::toDto)
                          .toList()))
          .toList();
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
        return workerMapper.toDtoWithJobDtoAndRoleDtoList(
            data.get(),
            jobRepository.findJobById(data.get().getJob().getId()),
            roleRepository
                .findByRoleWorkersWorkerId(
                    data.get().getId()) //  on a la liste des rôles en fonctions de worker id
                .stream()
                .map(roleMapper::toDto)
                .toList());
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
                            .build());
                  }
                });
        return workerMapper.toDtoWithJobDtoAndRoleDtoList(
            existing.get(),
            jobRepository.findJobById(existing.get().getJob().getId()),
            roleRepository
                .findByRoleWorkersWorkerId(
                    existing.get().getId()) //  on a la liste des rôles en fonctions de worker id
                .stream()
                .map(roleMapper::toDto)
                .toList());
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
}
