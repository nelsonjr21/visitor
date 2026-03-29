package com.manage.visitor.service.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.manage.visitor.helpers.exception.AppException;
import com.manage.visitor.helpers.exception.BadRequestException;
import com.manage.visitor.helpers.exception.DataNotFoundException;
import com.manage.visitor.helpers.http.Message;
import com.manage.visitor.model.dto.admin.RoleDto;
import com.manage.visitor.model.entity.admin.Role;
import com.manage.visitor.repository.admin.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleRepository repository;

  public RoleDto save(RoleDto dto) {
    log.info("save role");
    try {
      Role data = RoleDto.toEntity(dto);
      if (data.getId() == null) {
        return RoleDto.entityToDTO(repository.save(data));
      }
    } catch (Exception e) {
      log.error("save role failed: ", e);
      throw new AppException(e.getMessage());
    }
    throw new BadRequestException(Message.ID_NOT_REQUIRED.getText());
  }

  public List<RoleDto> getAll() {
    log.info("loading role");
    try {
      return repository.findAll().stream().map(RoleDto::entityToDTO).toList();
    } catch (Exception e) {
      log.error("loading role failed: ", e);
      throw new AppException(e.getMessage());
    }
  }

  public RoleDto getById(Integer id) {
    log.info("loading role for {}", id);
    try {
      Optional<Role> data = repository.findById(id);
      if (data.isPresent()) {
        return RoleDto.entityToDTO(data.get());
      }
    } catch (Exception e) {
      log.error("loading role failed : ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }

  public RoleDto update(RoleDto dto) {
    log.info("update role");
    Role data = RoleDto.toEntity(dto);
    if (data.getId() == null) {
      throw new BadRequestException(Message.ID_REQUIRED.getText());
    }
    try {
      Optional<Role> d = repository.findById(data.getId());
      if (d.isPresent()) {
        Role existing = d.get();
        existing.setLabel(data.getLabel());
        existing.setCode(data.getCode());
        return RoleDto.entityToDTO(repository.save(data));
      }
    } catch (Exception e) {
      log.error("update roleList failed: ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }

  public void delete(Integer id) {
    log.info("delete roleList for {}", id);
    try {
      Optional<Role> data = repository.findById(id);
      if (data.isPresent()) {
        repository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      log.error("delete role failed: ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }
}
