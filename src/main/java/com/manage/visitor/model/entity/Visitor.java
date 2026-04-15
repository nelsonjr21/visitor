package com.manage.visitor.model.entity;

import java.util.List;

import com.manage.visitor.model.dto.VisitorDto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "visitor")
@AllArgsConstructor
@NoArgsConstructor
public class Visitor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String surname;
}
