package com.manage.visitor.model.entity.admin;

import com.manage.visitor.model.entity.Worker;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "role_worker")
@AllArgsConstructor
@NoArgsConstructor
public class RoleWorker {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "roleId")
  private Role role;

  @ManyToOne
  @JoinColumn(name = "workerId")
  private Worker worker;
}
