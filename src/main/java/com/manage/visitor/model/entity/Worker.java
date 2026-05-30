package com.manage.visitor.model.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Builder
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
