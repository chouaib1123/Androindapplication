package com.example.myapplication.Model;

import java.util.Arrays;

import com.example.myapplication.Extra.FuelType;

public class Car {
    private int carId;
    private byte[] carImage;
    private String color;
    private FuelType fuelType;
    private boolean isAutomatic;
    private String matricula;
    private String model;
    private double pricePerDay;
    private int seatsNumber;
    private int agencyId;
    
    public Car(int carId, byte[] carImage, String color, FuelType fuelType, boolean isAutomatic, String matricula,
            String model, double pricePerDay, int seatsNumber, int agencyId) {
        this.carId = carId;
        this.carImage = carImage;
        this.color = color;
        this.fuelType = fuelType;
        this.isAutomatic = isAutomatic;
        this.matricula = matricula;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.seatsNumber = seatsNumber;
        this.agencyId = agencyId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public byte[] getCarImage() {
        return carImage;
    }

    public void setCarImage(byte[] carImage) {
        this.carImage = carImage;
    }

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

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    @Override
    public String toString() {
        return "Car [carId=" + carId + ", carImage=" + Arrays.toString(carImage) + ", color=" + color + ", fuelType="
                + fuelType + ", isAutomatic=" + isAutomatic + ", matricula=" + matricula + ", model=" + model
                + ", pricePerDay=" + pricePerDay + ", seatsNumber=" + seatsNumber + ", agencyId=" + agencyId + "]";
    }

    
}
