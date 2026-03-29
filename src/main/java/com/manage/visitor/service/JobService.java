package com.manage.visitor.service;

import java.util.List;
import java.util.Optional;

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

  public JobDto save(JobDto dto) {
    log.info("save job");
    try {
      Job data = JobDto.toEntity(dto);
      if (data.getId() == null) {
        return JobDto.convertToDto(repository.save(data));
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
      return repository.findAll().stream().map(JobDto::convertToDto).toList();
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
        return JobDto.convertToDto(data.get());
      }
    } catch (Exception e) {
      log.error("loading Job failed : ", e);
      throw new AppException(e.getMessage());
    }
    throw new DataNotFoundException(Message.DATA_NOT_FOUND.getText());
  }

  public JobDto update(JobDto dto) {
    log.info("update job");
    Job data = JobDto.toEntity(dto);
    if (data.getId() == null) {
      throw new BadRequestException(Message.ID_REQUIRED.getText());
    }
    try {
      Optional<Job> d = repository.findById(data.getId());
      if (d.isPresent()) {
        Job existing = d.get();
        existing.setLabel(data.getLabel());
        existing.setKeyJobId(data.getKeyJobId());
        return JobDto.convertToDto(repository.save(existing));
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
