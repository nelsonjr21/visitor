package com.manage.visitor.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "worker_visitor")
@AllArgsConstructor
@NoArgsConstructor
public class WorkerVisitor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private LocalDateTime visitDate;

  @ManyToOne
  @JoinColumn(name = "workerId")
  private Worker worker;

  @ManyToOne
  @JoinColumn(name = "visitorId")
  private Visitor visitor;
}
