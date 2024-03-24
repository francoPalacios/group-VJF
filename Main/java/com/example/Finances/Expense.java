package com.example.Finances;

public class Expense {

    private int UserID;
    private double amount;
    private String recurrence;
    private String budgetType;
    private String description;

    public Expense(int UserID, double amount, String recurrence, String budgetType, String description) {
        this.UserID = UserID;
        this.amount = amount;
        this.recurrence = recurrence;
        this.budgetType = budgetType;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  int getuserID(){return UserID;}
}