package com.manage.visitor.model.entity.admin;

import com.manage.visitor.model.dto.admin.RoleDto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  public Role(RoleDto dto) {
    this.id = dto.id();
    this.label = dto.label();
    this.code = dto.code();
  }
}
