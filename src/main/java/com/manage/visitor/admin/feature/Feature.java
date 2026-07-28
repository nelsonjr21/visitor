package com.manage.visitor.admin.feature;

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

  public static record FeatureFormDto(String label, String code, Integer idKey, String url) {
  }
}
