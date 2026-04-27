package com.cafe.model;

public class OrderDetail {
    private Product product; // Tham chiếu trực tiếp đến sản phẩm
    private int quantity;
    private double unitPrice; // Snapshot giá tại thời điểm bán

    public OrderDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice(); // Lấy giá hiện tại của sản phẩm
    }

    // GRASP: Tự tính thành tiền cho chính dòng này
    public double calculateSubTotal() {
        return quantity * unitPrice;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    
    public void setQuantity(int quantity) { this.quantity = quantity; }
}