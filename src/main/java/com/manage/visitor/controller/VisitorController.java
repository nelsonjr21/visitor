package com.manage.visitor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.manage.visitor.helpers.http.ApiResponse;
import com.manage.visitor.model.dto.VisitorDto;
import com.manage.visitor.service.VisitorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/visitors")
@RequiredArgsConstructor
public class VisitorController {

  private final VisitorService service;

  @PostMapping
  public ResponseEntity<ApiResponse<VisitorDto>> save(@RequestBody VisitorDto dto) {
    log.info("HTTP POST /visitors body={}", dto);
    return new ResponseEntity<>(ApiResponse.success(service.save(dto)), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<VisitorDto>>> getAll() {
    log.info("HTTP GET /visitors");
    return new ResponseEntity<>(ApiResponse.success(service.getAll()), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<VisitorDto>> getById(@PathVariable Integer id) {
    log.info("HTTP GET /visitors/{}", id);
    return new ResponseEntity<>(ApiResponse.success(service.getById(id)), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<ApiResponse<VisitorDto>> update(@RequestBody VisitorDto dto) {
    log.info("HTTP PUT /visitors body={}", dto);
    return new ResponseEntity<>(ApiResponse.success(service.update(dto)), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
    log.info("HTTP DELETE /visitors/{}", id);
    service.delete(id);
    return new ResponseEntity<>(ApiResponse.success(null), HttpStatus.OK);
  }
}
