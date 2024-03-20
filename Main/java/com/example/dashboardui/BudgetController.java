//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.dashboardui;

import java.time.LocalDate;

import com.example.dashboardui.Budget;
import com.example.loginui.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;



public class BudgetController {
    @FXML
    private TextField budgetNameField;
    @FXML
    private TextField incomeField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;

    private int userId;
    private final DatabaseManager databaseManager;

    public BudgetController(int userId) {
        databaseManager = new DatabaseManager();
        this.userId = userId;
    }

    public BudgetController() {
        databaseManager = new DatabaseManager();
    }

    @FXML
    public void budgetadd() {
        String budgetName = budgetNameField.getText();
        String incomestr = incomeField.getText();
        LocalDate budgetStartDate = startDatePicker.getValue();
        LocalDate budgetEndDate = endDatePicker.getValue();

        if (budgetName.isEmpty() || incomestr.isEmpty() || budgetStartDate == null || budgetEndDate == null) {

            Alert alExiAlert = new Alert(Alert.AlertType.ERROR);
            alExiAlert.setTitle("Error");
            alExiAlert.setHeaderText(null);
            alExiAlert.setContentText("Error: Please Fill In All Fields");
            alExiAlert.showAndWait();
            return;

        }

        // Check if the email already exists in the database
        if (databaseManager.isBudgetName(budgetName)) {
            Alert alExiAlert = new Alert(Alert.AlertType.ERROR);
            alExiAlert.setTitle("Error");
            alExiAlert.setHeaderText(null);
            alExiAlert.setContentText("budgetname Is Already In Use");
            alExiAlert.showAndWait();
        } else {
            try {
                double value = Double.parseDouble(incomeField.getText());
                if (value <= 0) {
                    Alert alExiAlert = new Alert(Alert.AlertType.ERROR);
                    alExiAlert.setTitle("Error");
                    alExiAlert.setHeaderText(null);
                    alExiAlert.setContentText("Error: Please enter positive number");
                    alExiAlert.showAndWait();
                    return;
                }
            } catch (NumberFormatException e) {
                Alert alExiAlert = new Alert(Alert.AlertType.ERROR);
                alExiAlert.setTitle("Error");
                alExiAlert.setHeaderText(null);
                alExiAlert.setContentText("error: Invalid input. Please enter a valid number for income.");
                alExiAlert.showAndWait();
                return;

            }

            double income = Double.parseDouble(incomestr);
            Budget newbudget = new Budget(userId, budgetName, income, budgetStartDate, budgetEndDate);

            if (databaseManager.addbudget(newbudget)) {
                Alert addedAlert = new Alert(Alert.AlertType.INFORMATION);
                addedAlert.setTitle("Budget Added");
                addedAlert.setHeaderText(null);
                addedAlert.setContentText("Budget Added Successfully");
                addedAlert.showAndWait();
                budgetNameField.setText("");
                incomeField.setText("");
                startDatePicker.setValue(null);
                endDatePicker.setValue(null);

            } else {
                System.out.println("Failed to add Budget");
            }

        }
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }
}
