package com.cafe.model;

public class CafeTable {
    private int tableId;
    private String tableName;
    private boolean isOccupied;

    public CafeTable(int tableId, String tableName) {
        this.tableId = tableId;
        this.tableName = tableName;
        this.isOccupied = false;
    }

    // GRASP: Chuyên gia thay đổi trạng thái bàn
    public void changeStatus(boolean status) {
        this.isOccupied = status;
    }

    public int getTableId() { return tableId; }
    public String getTableName() { return tableName; }
    public boolean isOccupied() { return isOccupied; }
}