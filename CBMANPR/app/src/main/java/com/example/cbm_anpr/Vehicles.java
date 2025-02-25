package com.example.cbm_anpr;

public class Vehicles
{
    String Name,Department,Phone,cStatus,VehicleNo,RollNo;

    public Vehicles(String name, String department, String phone, String cStatus, String vehicleNo, String rollNo) {
        Name = name;
        Department = department;
        Phone = phone;
        this.cStatus = cStatus;
        VehicleNo = vehicleNo;
        RollNo = rollNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getcStatus() {
        return cStatus;
    }

    public void setcStatus(String cStatus) {
        this.cStatus = cStatus;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String rollNo) {
        RollNo = rollNo;
    }

    public Vehicles(){

    }
}

