package com.manage.visitor.model.entity.admin;

import com.manage.visitor.model.dto.admin.FeatureDto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
