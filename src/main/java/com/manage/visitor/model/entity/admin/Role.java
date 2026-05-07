package com.manage.visitor.model.entity.admin;

import com.manage.visitor.model.dto.admin.RoleDto;

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
