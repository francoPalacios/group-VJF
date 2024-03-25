package com.example;

import com.example.dashboardui.DashboardController;

public class DataSingleton {

    private int userId;
    private boolean hasBudget;
    private boolean hasExpense;
    private DashboardController controller;
    private DataSingleton(){}

    public int getUserId() {
        return userId;
    }

    public int setUserId(int userId) {
        this.userId = userId;
        return userId;
    }

    public boolean isHasBudget() {
        return hasBudget;
    }
    public boolean isHasExpense() {
        return hasExpense;
    }

    public void setHasBudget(boolean hasBudget) {
        this.hasBudget = hasBudget;
    }
    public void setHasExpense(boolean hasExpense) {
        this.hasExpense = hasExpense;
    }

    public DashboardController getController() {
        return controller;
    }

    public void setController(DashboardController controller) {
        this.controller = controller;
    }

    private static class SingletonHelper {
        private static final DataSingleton INSTANCE = new DataSingleton();
    }

    public static DataSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
