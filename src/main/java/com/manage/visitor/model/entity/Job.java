package com.manage.visitor.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "job")
@AllArgsConstructor
@NoArgsConstructor
public class Job {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String label;
  private Integer keyJobId;

  public Job(Integer id) {
    this.id = id;
  }
}
