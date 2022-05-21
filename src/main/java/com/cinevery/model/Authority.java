package com.cinevery.model;

import com.cinevery.constant.ColumnConstants;
import com.cinevery.constant.NumberConstants;
import com.cinevery.constant.TableConstants;
import com.cinevery.enumeration.AuthorityName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = TableConstants.AUTHORITY)
public class Authority implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = ColumnConstants.ID)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  @Column(name = ColumnConstants.NAME, length = NumberConstants.LENGTH_50)
  @Enumerated(EnumType.STRING)
  private AuthorityName name;

  @JsonIgnore
  @ManyToMany(mappedBy = ColumnConstants.AUTHORITY_AUTHORITIES, cascade = CascadeType.ALL)
  private List<User> users;

  public AuthorityName getName() {
    return name;
  }

  public void setName(AuthorityName name) {
    this.name = name;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }
}
