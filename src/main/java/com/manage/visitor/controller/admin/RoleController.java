package com.manage.visitor.controller.admin;

import java.util.List;

import com.manage.visitor.model.dto.form.RoleFormDto;
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
  public ResponseEntity<RoleDto> save(@RequestBody RoleFormDto dto) {
    log.info("HTTP POST /roles body={}", dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
  }

  @GetMapping
  public ResponseEntity<List<RoleDto>> getAll() {
    log.info("HTTP GET /roles");
    return ResponseEntity.ok().body(service.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<RoleDto> getById(@PathVariable Integer id) {
    log.info("HTTP GET /roles/{}", id);
    return ResponseEntity.ok().body(service.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<RoleDto> update(@PathVariable Integer id, @RequestBody RoleFormDto dto) {
    log.info("HTTP PUT /roles body={}", dto);
    return ResponseEntity.ok(service.update(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    log.info("HTTP DELETE /roles/{}", id);
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
