package com.faurecia.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/9/18.
 */
@Entity(name = "d_menu")
@Table(name = "d_menu", schema = "launch")
public class DMenuEntity {
    private int id;
    private String name;
    private String url;
    private String method;
    private Integer pId;
    private String type;

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
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "method")
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DMenuEntity that = (DMenuEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;
        if (pId != null ? !pId.equals(that.pId) : that.pId != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (pId != null ? pId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
