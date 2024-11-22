package org.example.entities;

import java.util.Date;

public class Rent {
    private int id;
    private int client_id;
    private int machine_id;
    private Date startDate;
    private Date endDate;

    public Rent(int id, int client_id, int machine_id, Date startDate, Date endDate) {
        this.id = id;
        this.client_id = client_id;
        this.machine_id = machine_id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Rent() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(int machine_id) {
        this.machine_id = machine_id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", machine_id=" + machine_id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
