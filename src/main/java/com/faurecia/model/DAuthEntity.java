package com.faurecia.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/9/18.
 */
@Entity(name = "d_auth")
@Table(name = "d_auth", schema = "launch")
public class DAuthEntity {
    private int id;
    private Integer managerId;
    private Integer pId;
    private Integer subId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "managerId")
    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    @Basic
    @Column(name = "pId")
    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    @Basic
    @Column(name = "subId")
    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DAuthEntity that = (DAuthEntity) o;

        if (id != that.id) return false;
        if (managerId != null ? !managerId.equals(that.managerId) : that.managerId != null) return false;
        if (pId != null ? !pId.equals(that.pId) : that.pId != null) return false;
        if (subId != null ? !subId.equals(that.subId) : that.subId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (managerId != null ? managerId.hashCode() : 0);
        result = 31 * result + (pId != null ? pId.hashCode() : 0);
        result = 31 * result + (subId != null ? subId.hashCode() : 0);
        return result;
    }
}
