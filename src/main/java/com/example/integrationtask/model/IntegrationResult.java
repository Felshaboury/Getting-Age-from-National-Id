package com.example.integrationtask.model;

public class IntegrationResult {

    private int age;
    private String status;
    private String address;
    private String university;

    public IntegrationResult(){

    }

    public IntegrationResult(int age, String status, String address, String university) {
        this.age = age;
        this.status = status;
        this.address = address;
        this.university = university;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
