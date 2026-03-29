package com.manage.visitor.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.manage.visitor.helpers.http.ApiResponse;
import com.manage.visitor.model.dto.admin.FeatureDto;
import com.manage.visitor.service.admin.FeatureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/features")
@RequiredArgsConstructor
public class FeatureController {

  private final FeatureService service;

  @PostMapping
  public ResponseEntity<ApiResponse<FeatureDto>> save(@RequestBody FeatureDto dto) {
    log.info("HTTP POST /features body={}", dto);
    return new ResponseEntity<>(ApiResponse.success(service.save(dto)), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<FeatureDto>>> getAll() {
    log.info("HTTP GET /features");
    return new ResponseEntity<>(ApiResponse.success(service.getAll()), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<FeatureDto>> getById(@PathVariable Integer id) {
    log.info("HTTP GET /features/{}", id);
    return new ResponseEntity<>(ApiResponse.success(service.getById(id)), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<ApiResponse<FeatureDto>> update(@RequestBody FeatureDto dto) {
    log.info("HTTP PUT /features body={}", dto);
    return new ResponseEntity<>(ApiResponse.success(service.update(dto)), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
    log.info("HTTP DELETE /features/{}", id);
    service.delete(id);
    return new ResponseEntity<>(ApiResponse.success(null), HttpStatus.OK);
  }
}
