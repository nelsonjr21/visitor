package com.manage.visitor.service.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.manage.visitor.helpers.exception.AppException;
import com.manage.visitor.helpers.exception.DataNotFoundException;
import com.manage.visitor.helpers.http.Message;
import com.manage.visitor.model.dto.admin.RoleDto;
import com.manage.visitor.model.dto.form.RoleFormDto;
import com.manage.visitor.model.entity.admin.Role;
import com.manage.visitor.model.mapper.admin.RoleMapper;
import com.manage.visitor.repository.admin.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleRepository repository;
  private final RoleMapper roleMapper;

  public RoleDto save(RoleFormDto dto) {
    log.info("save role");
    try {
      return roleMapper.toDto(repository.save(roleMapper.toEntity(dto)));
    } catch (Exception e) {
      log.error("save role failed: ", e);
      throw new AppException(e.getMessage());
    }
  }

  public List<RoleDto> getAll() {
    log.info("loading role");
    try {
      return repository.findAll().stream().map(roleMapper::toDto).toList();
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
        return roleMapper.toDto(data.get());
      }
    } catch (Exception e) {
      log.error("loading role failed : ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }

  public RoleDto update(Integer id, RoleFormDto dto) {
    log.info("update role");
    try {
      Optional<Role> existing = repository.findById(id);
      if (existing.isPresent()) {
        existing.get().setLabel(dto.label());
        existing.get().setCode(dto.code());
        return roleMapper.toDto(repository.save(existing.get()));
      }
    } catch (Exception e) {
      log.error("update role failed: ", e);
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
