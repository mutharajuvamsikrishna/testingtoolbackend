package com.oniesoft.dto;

import java.util.List;

public class ProjectUserReq {
private long projectId;
private List<Integer> registerIds;
public ProjectUserReq(){

}

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public List<Integer> getRegisterIds() {
        return registerIds;
    }

    public void setRegisterIds(List<Integer> registerIds) {
        this.registerIds = registerIds;
    }
}
