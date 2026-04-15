package com.manage.visitor.model.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.manage.visitor.model.dto.WorkerDto;
import com.manage.visitor.model.entity.admin.RoleWorker;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "worker")
@AllArgsConstructor
@NoArgsConstructor
public class Worker {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String surname;
  private LocalDate birthDate;
  private String identifier;
  private String pw;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "jobId")
  private Job job;

  public Worker(Integer id) {
    this.id = id;
  }
}
