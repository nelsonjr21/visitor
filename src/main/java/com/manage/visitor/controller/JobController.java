package com.manage.visitor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.manage.visitor.model.dto.JobDto;
import com.manage.visitor.model.dto.form.JobFormDto;
import com.manage.visitor.service.JobService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

  private final JobService service;

  @PostMapping
  public ResponseEntity<JobDto> save(@RequestBody JobFormDto dto) {
    log.info("HTTP POST /jobs body={}", dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
  }

  @GetMapping
  public ResponseEntity<List<JobDto>> getAll() {
    log.info("HTTP GET /jobs");
    return ResponseEntity.ok().body(service.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<JobDto> getById(@PathVariable Integer id) {
    log.info("HTTP GET /jobs/{}", id);
    return ResponseEntity.ok().body(service.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<JobDto> update(@PathVariable Integer id, @RequestBody JobFormDto dto) {
    log.info("HTTP PUT /jobs body={}", dto);
    return ResponseEntity.ok(service.update(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    log.info("HTTP DELETE /jobs/{}", id);
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
