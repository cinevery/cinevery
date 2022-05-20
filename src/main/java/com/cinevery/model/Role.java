package com.cinevery.model;

import com.cinevery.constant.ColumnConstants;
import com.cinevery.constant.TableConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = TableConstants.ROLE)
@DynamicInsert
@DynamicUpdate
public class Role {

  @Id
  @Column(name = ColumnConstants.ID)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  @Column(name = ColumnConstants.NAME, nullable = false)
  private String name;

  @Column(name = ColumnConstants.ROLE_DESCRIPTION)
  private String description;

  @JsonIgnore
  @ManyToMany(mappedBy = ColumnConstants.ROLE_ROLES)
  private Set<User> users = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  @Override
  public String toString() {
    return "Role{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + '}';
  }
}
