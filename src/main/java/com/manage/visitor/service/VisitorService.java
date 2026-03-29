package com.manage.visitor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.manage.visitor.helpers.exception.AppException;
import com.manage.visitor.helpers.exception.BadRequestException;
import com.manage.visitor.helpers.exception.DataNotFoundException;
import com.manage.visitor.helpers.http.Message;
import com.manage.visitor.model.entity.Visitor;
import com.manage.visitor.repository.VisitorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitorService {

  private final VisitorRepository repository;

  public Visitor save(Visitor data) {
    log.info("save visitor");
    try {
      if (data.getId() == null) {
        return repository.save(data);
      }
    } catch (Exception e) {
      log.error("save visitor failed: ", e);
      throw new AppException(e.getMessage());
    }
    throw new BadRequestException(Message.ID_NOT_REQUIRED.getText());
  }

  public Visitor demand(Visitor data) {
    log.info("save demand visitor");
    try {
      if (data.getId() == null) {
        return repository.save(data);
      }
    } catch (Exception e) {
      log.error("save demand visitor failed: ", e);
      throw new AppException(e.getMessage());
    }
    throw new BadRequestException(Message.ID_NOT_REQUIRED.getText());
  }

  public List<Visitor> getAll() {
    log.info("loading visitor");
    try {
      return repository.findAll();
    } catch (Exception e) {
      log.error("loading visitor failed: ", e);
      throw new AppException(e.getMessage());
    }
  }

  public Visitor getById(Integer id) {
    log.info("loading visitor for {}", id);
    try {
      Optional<Visitor> data = repository.findById(id);
      if (data.isPresent()) {
        return data.get();
      }
    } catch (Exception e) {
      log.error("loading Visitor failed : ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }

  public Visitor update(Visitor data) {
    log.info("update visitor");
    if (data.getId() == null) {
      throw new BadRequestException(Message.ID_REQUIRED.getText());
    }
    try {
      Optional<Visitor> d = repository.findById(data.getId());
      if (d.isPresent()) {
        Visitor existing = d.get();
        existing.setName(data.getName());
        existing.setSurname(data.getSurname());
        return repository.save(existing);
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
