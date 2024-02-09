package com.example.myapplication.Model;

import com.example.myapplication.Extra.FuelType;

public class Car {
    private String color;
    private FuelType fuelType;
    private boolean isAutomatic;
    private String matricula;
    private String model;
    private double pricePerDay;
    private int seatsNumber;
    private String agencyUsername;
    private String agencyCity;
    
    public Car(String color, FuelType fuelType, boolean isAutomatic, String matricula,
               String model, double pricePerDay, int seatsNumber, String agencyUsername) {
        this.color = color;
        this.fuelType = fuelType;
        this.isAutomatic = isAutomatic;
        this.matricula = matricula;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.seatsNumber = seatsNumber;
        this.agencyUsername = agencyUsername;
    }

    public Car(String color, FuelType fuelType, boolean isAutomatic, String matricula,
               String model, double pricePerDay, int seatsNumber, String agencyUsername, String agencyCity) {
        this.color = color;
        this.fuelType = fuelType;
        this.isAutomatic = isAutomatic;
        this.matricula = matricula;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.seatsNumber = seatsNumber;
        this.agencyUsername = agencyUsername;
        this.agencyCity = agencyCity;
    }

    public Car(String color, String fuelType, String isAutomatic, String matricula,
               String model, String pricePerDay, String seatsNumber, String agencyUsername, String agencyCity) {
        this.color = color;
        this.fuelType = FuelType.valueOf(fuelType);
        this.isAutomatic = Boolean.parseBoolean(isAutomatic);
        this.matricula = matricula;
        this.model = model;
        this.pricePerDay = Double.parseDouble(pricePerDay);
        this.seatsNumber = Integer.parseInt(seatsNumber);
        this.agencyUsername = agencyUsername;
        this.agencyCity = agencyCity;
    }

    public Car() {}

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public boolean isAutomatic() {
        return isAutomatic;
    }

    public void setAutomatic(boolean isAutomatic) {
        this.isAutomatic = isAutomatic;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public String getAgencyUsername() {
        return agencyUsername;
    }

    public void setAgencyUsername(String agencyUsername) {
        this.agencyUsername = agencyUsername;
    }
    public String getAgencyCity() {
        return agencyCity;
    }

    public void setAgencyCity(String agencyCity) {
        this.agencyCity = agencyCity;
    }

    @Override
    public String toString() {
        return "Car [color=" + color + ", fuelType="
                + fuelType + ", isAutomatic=" + isAutomatic + ", matricula=" + matricula + ", model=" + model
                + ", pricePerDay=" + pricePerDay + ", seatsNumber=" + seatsNumber + ", agencyUsername=" + agencyUsername +
                ", agencyCity=" + agencyCity + "]";
    }
}
