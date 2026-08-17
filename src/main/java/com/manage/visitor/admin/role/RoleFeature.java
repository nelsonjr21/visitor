package com.manage.visitor.admin.role;

import com.manage.visitor.admin.feature.Feature;
import com.manage.visitor.admin.role.entity.Role;
import jakarta.persistence.*;
import lombok.*;

@Builder
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

  private Boolean state;

  @ManyToOne
  @JoinColumn(name = "roleId")
  private Role role;

  @ManyToOne
  @JoinColumn(name = "featureId")
  private Feature feature;
}
