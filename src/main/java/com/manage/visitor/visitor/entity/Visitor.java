package com.manage.visitor.visitor.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
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
