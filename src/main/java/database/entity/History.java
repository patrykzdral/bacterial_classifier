package database.entity;

import database.Entity;

import java.sql.Date;


public class History extends Entity {
    private Date date;
    private Integer examinedId;

    public History() {
    }

    public History(Integer examinedId) {
        java.util.Date utilDate = new java.util.Date();
        this.date = new Date(utilDate.getTime());
        this.examinedId = examinedId;
    }
    public History(Integer id, Date date, Integer examinedId) {
        super(id);
        this.date = date;
        this.examinedId = examinedId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getExaminedId() {
        return examinedId;
    }

    public void setExaminedId(Integer examinedId) {
        this.examinedId = examinedId;
    }
}
