package com.oniesoft.dto;

import java.util.List;

public class ProjectUserReq {
private int registerId;
private List<Long> projectIds;
public ProjectUserReq(){

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

}
