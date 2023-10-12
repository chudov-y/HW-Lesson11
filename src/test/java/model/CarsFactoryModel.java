package model;

import java.util.List;

public class CarsFactoryModel {

    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(List<String> carBrand) {
        this.carBrand = carBrand;
    }

    public int getCarAmount() {
        return carAmount;
    }

    public void setCarAmount(int carAmount) {
        this.carAmount = carAmount;
    }

    private List<String> carBrand;
    private int carAmount;

}
