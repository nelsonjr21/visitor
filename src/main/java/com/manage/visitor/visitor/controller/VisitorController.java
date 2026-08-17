package com.manage.visitor.visitor.controller;

import java.util.List;

import com.manage.visitor.visitor.dto.VisitorDto;
import com.manage.visitor.visitor.dto.VisitorFormDto;
import com.manage.visitor.visitor.service.VisitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/visitors")
@RequiredArgsConstructor
public class VisitorController {

  private final VisitorService service;

  @PostMapping
  public ResponseEntity<VisitorDto> save(@RequestBody VisitorFormDto dto) {
    log.info("HTTP POST /visitors body={}", dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
  }

  @GetMapping
  public ResponseEntity<List<VisitorDto>> getAll() {
    log.info("HTTP GET /visitors");
    return ResponseEntity.ok().body(service.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<VisitorDto> getById(@PathVariable Integer id) {
    log.info("HTTP GET /visitors/{}", id);
    return ResponseEntity.ok().body(service.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<VisitorDto> update(@PathVariable Integer id, @RequestBody VisitorFormDto dto) {
    log.info("HTTP PUT /visitors body={}", dto);
    return ResponseEntity.ok(service.update(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    log.info("HTTP DELETE /visitors/{}", id);
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
