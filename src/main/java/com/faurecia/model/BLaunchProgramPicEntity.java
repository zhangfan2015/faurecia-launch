package com.faurecia.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/9/11.
 */
@Entity(name = "b_launch_program_pic")
@Table(name = "b_launch_program_pic", schema = "launch")
public class BLaunchProgramPicEntity {
    private int id;
    private int programId;
    private String picUrl;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "programId")
    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    @Basic
    @Column(name = "picUrl")
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BLaunchProgramPicEntity that = (BLaunchProgramPicEntity) o;

        if (id != that.id) return false;
        if (programId != that.programId) return false;
        if (picUrl != null ? !picUrl.equals(that.picUrl) : that.picUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + programId;
        result = 31 * result + (picUrl != null ? picUrl.hashCode() : 0);
        return result;
    }
}
