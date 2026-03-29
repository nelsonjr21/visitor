package com.manage.visitor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.manage.visitor.helpers.http.ApiResponse;
import com.manage.visitor.model.dto.WorkerDto;
import com.manage.visitor.model.dto.admin.UserDto;
import com.manage.visitor.service.WorkerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/workers")
@RequiredArgsConstructor
public class WorkerController {

  private final WorkerService service;

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<WorkerDto>> registrer(@RequestBody WorkerDto dto) {
    log.info("HTTP POST /workers/register body={}", dto);
    return new ResponseEntity<>(ApiResponse.success(service.save(dto)), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<WorkerDto>> login(@RequestBody UserDto dto) {
    log.info("HTTP POST /workers/login body={}", dto);
    return new ResponseEntity<>(ApiResponse.success(service.login(dto)), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<WorkerDto>>> getAll() {
    log.info("HTTP GET /workers");
    return new ResponseEntity<>(ApiResponse.success(service.getAll()), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<WorkerDto>> getById(@PathVariable Integer id) {
    log.info("HTTP GET /workers/{}", id);
    return new ResponseEntity<>(ApiResponse.success(service.getById(id)), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<ApiResponse<WorkerDto>> update(@RequestBody WorkerDto dto) {
    log.info("HTTP PUT /workers body={}", dto);
    return new ResponseEntity<>(ApiResponse.success(service.update(dto)), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
    log.info("HTTP DELETE /workers/{}", id);
    service.delete(id);
    return new ResponseEntity<>(ApiResponse.success(null), HttpStatus.OK);
  }
}
