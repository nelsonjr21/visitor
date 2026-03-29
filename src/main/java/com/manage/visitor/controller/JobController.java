package com.manage.visitor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.manage.visitor.helpers.http.ApiResponse;
import com.manage.visitor.model.dto.JobDto;
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
  public ResponseEntity<ApiResponse<JobDto>> save(@RequestBody JobDto dto) {
    log.info("HTTP POST /jobs body={}", dto);
    return new ResponseEntity<>(ApiResponse.success(service.save(dto)), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<JobDto>>> getAll() {
    log.info("HTTP GET /jobs");
    return new ResponseEntity<>(ApiResponse.success(service.getAll()), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<JobDto>> getById(@PathVariable Integer id) {
    log.info("HTTP GET /jobs/{}", id);
    return new ResponseEntity<>(ApiResponse.success(service.getById(id)), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<ApiResponse<JobDto>> update(@RequestBody JobDto dto) {
    log.info("HTTP PUT /jobs body={}", dto);
    return new ResponseEntity<>(ApiResponse.success(service.update(dto)), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
    log.info("HTTP DELETE /jobs/{}", id);
    service.delete(id);
    return new ResponseEntity<>(ApiResponse.success(null), HttpStatus.OK);
  }
}
