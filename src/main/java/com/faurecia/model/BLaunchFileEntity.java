package com.faurecia.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/11/20.
 */
@Entity(name = "b_launch_file")
@Table(name = "b_launch_file", schema = "launch")
public class BLaunchFileEntity {
    private int id;
    private String name;
    private String dis;
    private Integer programId;
    private String launchType;
    private String isDisplay;
    private Long createTime;
    private String isDel;
    private Long delTime;
    private String fileUrl;
    private String fileType;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "dis", nullable = true, length = 50)
    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    @Basic
    @Column(name = "programId", nullable = true)
    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    @Basic
    @Column(name = "launchType", nullable = true, length = 10)
    public String getLaunchType() {
        return launchType;
    }

    public void setLaunchType(String launchType) {
        this.launchType = launchType;
    }

    @Basic
    @Column(name = "isDisplay", nullable = true, length = 1)
    public String getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay;
    }

    @Basic
    @Column(name = "createTime", nullable = true)
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "isDel", nullable = true, length = 1)
    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    @Basic
    @Column(name = "delTime", nullable = true)
    public Long getDelTime() {
        return delTime;
    }

    public void setDelTime(Long delTime) {
        this.delTime = delTime;
    }

    @Basic
    @Column(name = "fileUrl", nullable = true, length = 500)
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Basic
    @Column(name = "fileType", nullable = true, length = 10)
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BLaunchFileEntity that = (BLaunchFileEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (dis != null ? !dis.equals(that.dis) : that.dis != null) return false;
        if (programId != null ? !programId.equals(that.programId) : that.programId != null) return false;
        if (launchType != null ? !launchType.equals(that.launchType) : that.launchType != null) return false;
        if (isDisplay != null ? !isDisplay.equals(that.isDisplay) : that.isDisplay != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (isDel != null ? !isDel.equals(that.isDel) : that.isDel != null) return false;
        if (delTime != null ? !delTime.equals(that.delTime) : that.delTime != null) return false;
        if (fileUrl != null ? !fileUrl.equals(that.fileUrl) : that.fileUrl != null) return false;
        if (fileType != null ? !fileType.equals(that.fileType) : that.fileType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dis != null ? dis.hashCode() : 0);
        result = 31 * result + (programId != null ? programId.hashCode() : 0);
        result = 31 * result + (launchType != null ? launchType.hashCode() : 0);
        result = 31 * result + (isDisplay != null ? isDisplay.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (isDel != null ? isDel.hashCode() : 0);
        result = 31 * result + (delTime != null ? delTime.hashCode() : 0);
        result = 31 * result + (fileUrl != null ? fileUrl.hashCode() : 0);
        result = 31 * result + (fileType != null ? fileType.hashCode() : 0);
        return result;
    }
}
