package com.manage.visitor.service;

import java.util.List;
import java.util.Optional;

import com.manage.visitor.model.mapper.JobMapper;
import org.springframework.stereotype.Service;

import com.manage.visitor.helpers.exception.AppException;
import com.manage.visitor.helpers.exception.BadRequestException;
import com.manage.visitor.helpers.exception.DataNotFoundException;
import com.manage.visitor.helpers.http.Message;
import com.manage.visitor.model.dto.JobDto;
import com.manage.visitor.model.entity.Job;
import com.manage.visitor.repository.JobRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

  private final JobRepository repository;
  private final JobMapper jobMapper;

  public JobDto save(JobDto dto) {
    log.info("save job");
    try {
      if (dto.id() == null) {
        return jobMapper.toDto(repository.save(jobMapper.toEntity(dto)));
      }
    } catch (Exception e) {
      log.error("save job failed: ", e);
      throw new AppException(e.getMessage());
    }
    throw new BadRequestException(Message.ID_NOT_REQUIRED.getText());
  }

  public List<JobDto> getAll() {
    log.info("loading job");
    try {
      return repository.findAll().stream().map(jobMapper::toDto).toList();
    } catch (Exception e) {
      log.error("loading job failed: ", e);
      throw new AppException(e.getMessage());
    }
  }

  public JobDto getById(Integer id) {
    log.info("loading job for {}", id);
    try {
      Optional<Job> data = repository.findById(id);
      if (data.isPresent()) {
        return jobMapper.toDto(data.get());
      }
    } catch (Exception e) {
      log.error("loading Job failed : ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }

  public JobDto update(JobDto dto) {
    log.info("update job");
    if (dto.id() == null) {
      throw new BadRequestException(Message.ID_REQUIRED.getText());
    }
    try {
      Optional<Job> d = repository.findById(dto.id());
      if (d.isPresent()) {
        Job existing = d.get();
        existing.setLabel(dto.label());
        existing.setKeyJobId(dto.keyJobId());
        return jobMapper.toDto(repository.save(jobMapper.toEntity(dto)));
      }
    } catch (Exception e) {
      log.error("update job failed: ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }

  public void delete(Integer id) {
    log.info("delete job for {}", id);
    try {
      Optional<Job> data = repository.findById(id);
      if (data.isPresent()) {
        repository.deleteById(id);
        return;
      }
    } catch (Exception e) {
      log.error("delete job failed: ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }
}
