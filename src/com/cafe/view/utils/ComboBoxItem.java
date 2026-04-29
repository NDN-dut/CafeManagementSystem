package com.cafe.view.utils;

public class ComboBoxItem {
    private int value; // Đây là ID (productId, categoryId...)
    private String text; // Đây là tên hiển thị

    public ComboBoxItem(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() { return value; }
    public String getText() { return text; }

    // CỰC KỲ QUAN TRỌNG: JComboBox sẽ gọi hàm toString() để hiển thị lên giao diện
    @Override
    public String toString() {
        return text;
    }
}