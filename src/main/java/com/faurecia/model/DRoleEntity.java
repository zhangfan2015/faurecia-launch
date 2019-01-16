package com.faurecia.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/9/18.
 */
@Entity(name = "d_role")
@Table(name = "d_role", schema = "launch")
public class DRoleEntity {
    private int id;
    private String name;
    private String dis;
    private String scope;
    private Long delTime;
    private Integer isDel;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "dis")
    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    @Basic
    @Column(name = "scope")
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Basic
    @Column(name = "delTime")
    public Long getDelTime() {
        return delTime;
    }

    public void setDelTime(Long delTime) {
        this.delTime = delTime;
    }

    @Basic
    @Column(name = "isDel")
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DRoleEntity that = (DRoleEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (dis != null ? !dis.equals(that.dis) : that.dis != null) return false;
        if (scope != null ? !scope.equals(that.scope) : that.scope != null) return false;
        if (delTime != null ? !delTime.equals(that.delTime) : that.delTime != null) return false;
        if (isDel != null ? !isDel.equals(that.isDel) : that.isDel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dis != null ? dis.hashCode() : 0);
        result = 31 * result + (scope != null ? scope.hashCode() : 0);
        result = 31 * result + (delTime != null ? delTime.hashCode() : 0);
        result = 31 * result + (isDel != null ? isDel.hashCode() : 0);
        return result;
    }
}
