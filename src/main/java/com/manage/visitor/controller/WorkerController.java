package com.manage.visitor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.manage.visitor.model.dto.WorkerDto;
import com.manage.visitor.model.dto.admin.UserDto;
import com.manage.visitor.model.dto.form.WorkerFormDto;
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
  public ResponseEntity<WorkerDto> register(@RequestBody WorkerFormDto dto) {
    log.info("HTTP POST /workers/register body={}", dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
  }

  @PostMapping("/login")
  public ResponseEntity<WorkerDto> login(@RequestBody UserDto dto) {
    log.info("HTTP POST /workers/login body={}", dto);
    return ResponseEntity.accepted().body(service.login(dto));
  }

  @GetMapping
  public ResponseEntity<List<WorkerDto>> getAll() {
    log.info("HTTP GET /workers");
    return ResponseEntity.ok().body(service.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<WorkerDto> getById(@PathVariable Integer id) {
    log.info("HTTP GET /workers/{}", id);
    return ResponseEntity.ok().body(service.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<WorkerDto> update(
      @PathVariable Integer id, @RequestBody WorkerFormDto dto) {
    log.info("HTTP PUT /workers body={}", dto);
    return ResponseEntity.ok(service.update(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    log.info("HTTP DELETE /workers/{}", id);
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
