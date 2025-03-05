package org.example.serviceforcouriers.entity;

public class Order {

    private Long id;

    private String product;

    private String customerName;

    private String executorName;

    private String address;

    private Character purchasesPrice;

    private Character purchasesSell;

    private boolean soldStatus;

    public Order(String product, String customerName, String executorName, String address, Character purchasesPrice, Character purchasesSell, boolean soldStatus) {
        this.product = product;
        this.customerName = customerName;
        this.executorName = executorName;
        this.address = address;
        this.purchasesPrice = purchasesPrice;
        this.purchasesSell = purchasesSell;
        this.soldStatus = soldStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Character getPurchasesPrice() {
        return purchasesPrice;
    }

    public void setPurchasesPrice(Character purchasesPrice) {
        this.purchasesPrice = purchasesPrice;
    }

    public Character getPurchasesSell() {
        return purchasesSell;
    }

    public void setPurchasesSell(Character purchasesSell) {
        this.purchasesSell = purchasesSell;
    }

    public boolean isSoldStatus() {
        return soldStatus;
    }

    public void setSoldStatus(boolean soldStatus) {
        this.soldStatus = soldStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", product='" + product + '\'' +
                ", customerName='" + customerName + '\'' +
                ", executorName='" + executorName + '\'' +
                ", address='" + address + '\'' +
                ", purchasesPrice=" + purchasesPrice +
                ", purchasesSell=" + purchasesSell +
                ", soldStatus=" + soldStatus +
                '}';
    }
}


