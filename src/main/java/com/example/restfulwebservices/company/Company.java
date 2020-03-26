package com.example.restfulwebservices.company;

import java.util.Date;


public class Company {
    private Integer id;
    private String name;
    private String sector;
    private String country;
    private int nEmployees;
    private Date createdDate;

    public Company(Integer id, String name, String sector, String country, int nEmployees, Date createdDate) {
        this.id = id;
        this.name = name;
        this.sector = sector;
        this.country = country;
        this.nEmployees = nEmployees;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getnEmployees() {
        return nEmployees;
    }

    public void setnEmployees(int nEmployees) {
        this.nEmployees = nEmployees;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
