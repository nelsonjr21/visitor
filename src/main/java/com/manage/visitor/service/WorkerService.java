package com.manage.visitor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.manage.visitor.helpers.exception.AppException;
import com.manage.visitor.helpers.exception.BadRequestException;
import com.manage.visitor.helpers.exception.DataNotFoundException;
import com.manage.visitor.helpers.http.Message;
import com.manage.visitor.helpers.security.JwtUtils;
import com.manage.visitor.model.dto.JobDto;
import com.manage.visitor.model.dto.WorkerDto;
import com.manage.visitor.model.dto.admin.RoleDto;
import com.manage.visitor.model.dto.admin.UserDto;
import com.manage.visitor.model.entity.Worker;
import com.manage.visitor.model.entity.admin.Role;
import com.manage.visitor.model.entity.admin.RoleWorker;
import com.manage.visitor.repository.JobRepository;
import com.manage.visitor.repository.WorkerRepository;
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

  @Transactional
  public WorkerDto save(WorkerDto dto) {
    log.info("save worker");
    try {
      Worker data = WorkerDto.toEntity(dto, JobDto.toEntity(dto.job()));
      if (data.getId() == null) {
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
                      new RoleWorker(
                          null,
                          new Role(x.id(), null, null),
                          new Worker(saved.getId(), null, null, null, null, null, null)));
                });
        return WorkerDto.result(saved, null, null);
      }
    } catch (Exception e) {
      log.error("save worker failed: ", e);
      throw new AppException(e.getMessage());
    }
    throw new BadRequestException(Message.ID_NOT_REQUIRED.getText());
  }

  public WorkerDto login(UserDto user) {
    log.info("login worker");
    try {
      if (authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(user.username(), user.password()))
          .isAuthenticated()) {
        String token = jwtUtils.generateToken(user.username());
        Worker data = repository.findByIdentifier(user.username());
        return WorkerDto.forLogin(
            data,
            roleWorkerRepository.findByWorker_Id(data.getId()).stream()
                .map(y -> RoleDto.entityToDTO(y.getRole()))
                .toList(),
            jobRepository.findById(data.getJob().getId()).map(JobDto::convertToDto).orElse(null),
            token);
      }
    } catch (BadCredentialsException e) {
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
                  WorkerDto.result(
                      x,
                      jobRepository
                          .findById(x.getJob().getId())
                          .map(JobDto::convertToDto)
                          .orElse(null),
                      roleWorkerRepository.findByWorker_Id(x.getId()).stream()
                          .map(y -> RoleDto.entityToDTO(y.getRole()))
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
        return WorkerDto.result(
            data.get(),
            jobRepository
                .findById(data.get().getJob().getId())
                .map(JobDto::convertToDto)
                .orElse(null),
            roleWorkerRepository.findByWorker_Id(data.get().getId()).stream()
                .map(y -> RoleDto.entityToDTO(y.getRole()))
                .toList());
      }
    } catch (Exception e) {
      log.error("loading Worker failed : ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }

  @Transactional
  public WorkerDto update(WorkerDto dto) {
    log.info("update worker");
    Worker data = WorkerDto.toEntity(dto, JobDto.toEntity(dto.job()));
    if (data.getId() == null) {
      throw new BadRequestException(Message.ID_REQUIRED.getText());
    }
    try {
      Optional<Worker> d = repository.findById(data.getId());
      if (d.isPresent()) {
        Worker existing = d.get();
        existing.setName(data.getName());
        existing.setSurname(data.getSurname());
        existing.setBirthDate(data.getBirthDate());
        existing.setIdentifier(data.getIdentifier());
        existing.setJob(data.getJob());
        repository.save(existing);
        dto.roleList()
            .forEach(
                x -> {
                  if (roleWorkerRepository.findByRole_IdAndWorker_Id(x.id(), existing.getId())
                      == null) {
                    roleWorkerRepository.save(
                        new RoleWorker(null, new Role(x.id(), null, null), existing));
                  }
                });
        return WorkerDto.result(existing, null, null);
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
