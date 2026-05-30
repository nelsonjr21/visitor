package com.manage.visitor.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.manage.visitor.model.dto.admin.FeatureDto;
import com.manage.visitor.model.dto.form.FeatureFormDto;
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
  public ResponseEntity<FeatureDto> save(@RequestBody FeatureFormDto dto) {
    log.info("HTTP POST /features body={}", dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
  }

  @GetMapping
  public ResponseEntity<List<FeatureDto>> getAll() {
    log.info("HTTP GET /features");
    return ResponseEntity.ok().body(service.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<FeatureDto> getById(@PathVariable Integer id) {
    log.info("HTTP GET /features/{}", id);
    return ResponseEntity.ok().body(service.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<FeatureDto> update(
      @PathVariable Integer id, @RequestBody FeatureFormDto dto) {
    log.info("HTTP PUT /features body={}", dto);
    return ResponseEntity.ok().body(service.update(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    log.info("HTTP DELETE /features/{}", id);
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
