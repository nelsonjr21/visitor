package com.manage.visitor.model.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

  @OneToMany(mappedBy = "worker")
  private Set<RoleWorker> roles;

  @OneToMany(mappedBy = "worker")
  private List<WorkerVisitor> visitors;

  public Worker(
      Integer id,
      String name,
      String surname,
      LocalDate birthDate,
      String identifier,
      String pw,
      Job job) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.birthDate = birthDate;
    this.identifier = identifier;
    this.pw = pw;
    this.job = job;
  }
}
