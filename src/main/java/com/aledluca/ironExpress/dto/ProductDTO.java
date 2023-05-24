package com.aledluca.ironExpress.dto;

public class ProductDTO {
    private String prodName;
    private String manufacturer;
    private Double price;
    private Integer quantity;

    
    public ProductDTO() {
    }

    public ProductDTO(String prodName, String manufacturer, Double price, Integer quantity) {
        this.prodName = prodName;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
