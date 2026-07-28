package com.manage.visitor.woker_visitor;

import java.time.LocalDateTime;

import com.manage.visitor.visitor.entity.Visitor;
import com.manage.visitor.worker.entity.Worker;
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
