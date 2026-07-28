package com.manage.visitor.admin.role.entity;

import com.manage.visitor.admin.role.RoleWorker;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@Entity
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String label;
  private String code;

  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
  private List<RoleWorker> roleWorkers;

  public Role(Integer id) {
    this.id = id;
  }
}
