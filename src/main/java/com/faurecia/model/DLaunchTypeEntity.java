package com.faurecia.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/8/21.
 */
@Entity(name = "d_launch_type")
@Table(name = "d_launch_type", schema = "launch")
public class DLaunchTypeEntity {
    private int id;
    private int indexNo;
    private String name;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "indexNo")
    public int getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(int indexNo) {
        this.indexNo = indexNo;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DLaunchTypeEntity that = (DLaunchTypeEntity) o;

        if (id != that.id) return false;
        if (indexNo != that.indexNo) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + indexNo;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
