package com.example.aplikasiptb.direction;

import java.util.List;

public class Result {

    private List<Route> routes;
    private String status;

    public List<Route> getRoutes() {
        return routes;
    }

    public String getStatus() {
        return status;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
