package com.faurecia.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/8/24.
 */
@Entity(name = "d_manager")
@Table(name = "d_manager", schema = "launch")
public class DManagerEntity {
    private int id;
    private String userName;
    private String pwd;
    private String role;
    private Long createTime;
    private Integer isDel;
    private Long delTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "pwd")
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Basic
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DManagerEntity that = (DManagerEntity) o;

        if (id != that.id) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (pwd != null ? !pwd.equals(that.pwd) : that.pwd != null) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (pwd != null ? pwd.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "createTime")
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "isDel")
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    @Basic
    @Column(name = "delTime")
    public Long getDelTime() {
        return delTime;
    }

    public void setDelTime(Long delTime) {
        this.delTime = delTime;
    }
}
