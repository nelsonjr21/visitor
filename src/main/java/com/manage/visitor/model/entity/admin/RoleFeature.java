package com.manage.visitor.model.entity.admin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role_feature")
@AllArgsConstructor
@NoArgsConstructor
public class RoleFeature {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String urlFeature;
  private Boolean stateFeature;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "roleId")
  private Role role;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "featureId")
  private Feature feature;
}
