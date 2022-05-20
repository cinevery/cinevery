package com.cinevery.model;

import com.cinevery.constant.ColumnConstants;
import com.cinevery.constant.NumberConstants;
import com.cinevery.constant.TableConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = TableConstants.USER_INFO)
public class User {

  @Id
  @Column(name = ColumnConstants.ID)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = ColumnConstants.USER_INFO_USERNAME, length = NumberConstants.LENGTH_50, unique = true)
  @NotNull
  @Size(min = NumberConstants.MIN_4, max = NumberConstants.MAX_50)
  private String username;

  @Column(name = ColumnConstants.USER_INFO_PASSWORD, length = NumberConstants.LENGTH_100)
  @NotNull
  @Size(min = NumberConstants.MIN_4, max = NumberConstants.MAX_100)
  private String password;

  @Column(name = ColumnConstants.USER_INFO_FIRST_NAME, length = NumberConstants.LENGTH_50)
  @NotNull
  @Size(min = NumberConstants.MIN_4, max = NumberConstants.MAX_50)
  private String firstName;

  @Column(name = ColumnConstants.USER_INFO_LAST_NAME, length = NumberConstants.LENGTH_50)
  @NotNull
  @Size(min = NumberConstants.MIN_4, max = NumberConstants.MAX_50)
  private String lastName;

  @Column(name = ColumnConstants.USER_INFO_EMAIL, length = NumberConstants.LENGTH_50)
  @NotNull
  @Size(min = NumberConstants.MIN_4, max = NumberConstants.MAX_50)
  private String email;

  @Column(name = ColumnConstants.USER_INFO_ENABLED)
  @NotNull
  private Boolean enabled;

  @Column(name = ColumnConstants.USER_INFO_LAST_PASSWORD_RESET_DATE)
  @NotNull
  private Date lastPasswordResetDate;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = ColumnConstants.USER_INFO_USER_AUTHORITY,
      joinColumns = {
          @JoinColumn(
              name = ColumnConstants.USER_INFO_USER_ID,
              referencedColumnName = ColumnConstants.ID
          )},
      inverseJoinColumns = {
          @JoinColumn(
              name = ColumnConstants.USER_INFO_AUTHORITY_NAME,
              referencedColumnName = ColumnConstants.NAME
          )}
  )
  private Set<Authority> authorities;


  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = ColumnConstants.USER_INFO_USER_ROLE,
      joinColumns = {
          @JoinColumn(
              name = ColumnConstants.USER_INFO_USER_ID,
              referencedColumnName = ColumnConstants.ID
          )},
      inverseJoinColumns = {
          @JoinColumn(
              name = ColumnConstants.USER_INFO_ROLE_ID,
              referencedColumnName = ColumnConstants.ID
          )}
  )
  private Set<Role> roles;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstname() {
    return firstName;
  }

  public void setFirstname(String firstName) {
    this.firstName = firstName;
  }

  public String getLastname() {
    return lastName;
  }

  public void setLastname(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Set<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Date getLastPasswordResetDate() {
    return lastPasswordResetDate;
  }

  public void setLastPasswordResetDate(Date lastPasswordResetDate) {
    this.lastPasswordResetDate = lastPasswordResetDate;
  }
}