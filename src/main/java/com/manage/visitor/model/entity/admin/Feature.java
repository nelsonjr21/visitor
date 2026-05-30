package com.manage.visitor.model.entity.admin;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "feature")
@AllArgsConstructor
@NoArgsConstructor
public class Feature {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String label;
  private String code;
  private Integer idKey;
  private String url;

  public Feature(Integer id) {
    this.id = id;
  }
}
