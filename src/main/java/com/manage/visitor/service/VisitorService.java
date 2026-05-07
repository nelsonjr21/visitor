package com.manage.visitor.service;

import java.util.List;
import java.util.Optional;

import com.manage.visitor.model.dto.form.VisitorFormDto;
import com.manage.visitor.model.mapper.VisitorMapper;
import org.springframework.stereotype.Service;

import com.manage.visitor.helpers.exception.AppException;
import com.manage.visitor.helpers.exception.BadRequestException;
import com.manage.visitor.helpers.exception.DataNotFoundException;
import com.manage.visitor.helpers.http.Message;
import com.manage.visitor.model.dto.VisitorDto;
import com.manage.visitor.model.entity.Visitor;
import com.manage.visitor.repository.VisitorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitorService {

  private final VisitorRepository repository;
  private final VisitorMapper visitorMapper;

  public VisitorDto save(VisitorFormDto dto) {
    log.info("save visitor");
    try {
        return visitorMapper.toDto(repository.save(visitorMapper.toEntity(dto)));
    } catch (Exception e) {
      log.error("save visitor failed: ", e);
      throw new AppException(e.getMessage());
    }
  }

  /*public VisitorDto demand(VisitorDto dto) {
    log.info("save demand visitor");
    try {
      if (dto.id() == null) {
        return visitorMapper.toDto(repository.save(visitorMapper.toEntity(dto)));
      }
    } catch (Exception e) {
      log.error("save demand visitor failed: ", e);
      throw new AppException(e.getMessage());
    }
    throw new BadRequestException(Message.ID_NOT_REQUIRED.getText());
  }*/

  public List<VisitorDto> getAll() {
    log.info("loading visitor");
    try {
      return repository.findAll().stream().map(visitorMapper::toDto).toList();
    } catch (Exception e) {
      log.error("loading visitor failed: ", e);
      throw new AppException(e.getMessage());
    }
  }

  public VisitorDto getById(Integer id) {
    log.info("loading visitor for {}", id);
    try {
      Optional<Visitor> data = repository.findById(id);
      if (data.isPresent()) {
        return visitorMapper.toDto(data.get());
      }
    } catch (Exception e) {
      log.error("loading Visitor failed : ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }

  public VisitorDto update(Integer id, VisitorFormDto dto) {
    log.info("update visitor");
    try {
      Optional<Visitor> existing = repository.findById(id);
      if (existing.isPresent()) {
        existing.get().setName(dto.name());
        existing.get().setSurname(dto.surname());
        return visitorMapper.toDto(repository.save(existing.get()));
      }
    } catch (Exception e) {
      log.error("update visitor failed: ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }

  public void delete(Integer id) {
    log.info("delete visitor for {}", id);
    try {
      Optional<Visitor> data = repository.findById(id);
      if (data.isPresent()) {
        repository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      log.error("delete visitor failed: ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }
}
