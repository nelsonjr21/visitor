package com.manage.visitor.model.entity;

import java.util.Set;

import com.manage.visitor.model.dto.JobDto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
