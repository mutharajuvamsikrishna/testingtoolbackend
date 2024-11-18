package com.oniesoft.dto;

import java.util.List;

public class ProjectUserReq {
private long projectId;
private int registerId;
private List<Long> projectIds;
private List<Integer> registerIds;
public ProjectUserReq(){

}

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public int getRegisterId() {
        return registerId;
    }

    public void setRegisterId(int registerId) {
        this.registerId = registerId;
    }

    public List<Long> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Long> projectIds) {
        this.projectIds = projectIds;
    }

    public List<Integer> getRegisterIds() {
        return registerIds;
    }

    public void setRegisterIds(List<Integer> registerIds) {
        this.registerIds = registerIds;
    }

    @Override
    public String toString() {
        return "ProjectUserReq{" +
                "projectId=" + projectId +
                ", registerId=" + registerId +
                ", projectIds=" + projectIds +
                ", registerIds=" + registerIds +
                '}';
    }
}
