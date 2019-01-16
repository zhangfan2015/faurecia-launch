package com.faurecia.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/11.
 */
@Entity(name = "b_launch_program")
@Table(name = "b_launch_program", schema = "launch")
public class BLaunchProgramEntity {
    private int id;
    private String name;
    private String isDisplay;
    private String isDel;
    private Long delTime;

    @Transient
    private List<BLaunchProgramPicEntity> piclist = new ArrayList<>();
    private Long createTime;
    private int indexNo;

    @Transient
    public  List<BLaunchProgramPicEntity> getPiclist(){
        return piclist;
    }

    public void setPiclist(List<BLaunchProgramPicEntity> piclist){
        this.piclist=piclist;
    }

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
    @Column(name = "isDisplay")
    public String getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay;
    }

    @Basic
    @Column(name = "isDel")
    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BLaunchProgramEntity that = (BLaunchProgramEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (isDisplay != null ? !isDisplay.equals(that.isDisplay) : that.isDisplay != null) return false;
        if (isDel != null ? !isDel.equals(that.isDel) : that.isDel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isDisplay != null ? isDisplay.hashCode() : 0);
        result = 31 * result + (isDel != null ? isDel.hashCode() : 0);
        return result;
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
    @Column(name = "createTime")
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "indexNo")
    public int getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(int indexNo) {
        this.indexNo = indexNo;
    }
}
