package com.manage.visitor.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.manage.visitor.helpers.http.ApiResponse;
import com.manage.visitor.model.dto.admin.RoleDto;
import com.manage.visitor.service.admin.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService service;

  @PostMapping
  public ResponseEntity<ApiResponse<RoleDto>> save(@RequestBody RoleDto dto) {
    log.info("HTTP POST /roles body={}", dto);
    return new ResponseEntity<>(ApiResponse.success(service.save(dto)), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<RoleDto>>> getAll() {
    log.info("HTTP GET /roles");
    return new ResponseEntity<>(ApiResponse.success(service.getAll()), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<RoleDto>> getById(@PathVariable Integer id) {
    log.info("HTTP GET /roles/{}", id);
    return new ResponseEntity<>(ApiResponse.success(service.getById(id)), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<ApiResponse<RoleDto>> update(@RequestBody RoleDto dto) {
    log.info("HTTP PUT /roles body={}", dto);
    return new ResponseEntity<>(ApiResponse.success(service.update(dto)), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<?>> delete(@PathVariable Integer id) {
    log.info("HTTP DELETE /roles/{}", id);
    service.delete(id);
    return new ResponseEntity<>(ApiResponse.success(null), HttpStatus.OK);
  }
}
