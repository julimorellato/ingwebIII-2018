package edu.iua.calculator.model;


public class Taxes {

    private int id;
    private String taxName;
    private Float taxPercentage;

    public Taxes() {}

    public Taxes(String taxName, Float taxPercentage) {
        this.taxName = taxName;
        this.taxPercentage = taxPercentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public Float getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(Float taxPercentage) {
        this.taxPercentage = taxPercentage;
    }
}
