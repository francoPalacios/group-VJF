//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package JVF.Finances;

import java.time.LocalDate;

import JVF.Data.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class BudgetController {
    @FXML
    private ChoiceBox<String> budgetTypeChoiceBox;
    @FXML
    private ChoiceBox<String> recurrenceChoiceBox;
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
        String budgetType = budgetTypeChoiceBox.getValue();
        String recurrence = recurrenceChoiceBox.getValue();
        String incomestr = incomeField.getText();
        LocalDate budgetStartDate = startDatePicker.getValue();
        LocalDate budgetEndDate = endDatePicker.getValue();

        if (budgetType == null || recurrence == null || incomestr.isEmpty() || budgetStartDate == null || budgetEndDate == null) {
            Alert alExiAlert = new Alert(Alert.AlertType.ERROR);
            alExiAlert.setTitle("Error");
            alExiAlert.setHeaderText(null);
            alExiAlert.setContentText("Error: Please Fill In All Fields");
            alExiAlert.showAndWait();
            return;
        }

        try {
            double value = Double.parseDouble(incomestr);
            if (value <= 0) {
                Alert alExiAlert = new Alert(Alert.AlertType.ERROR);
                alExiAlert.setTitle("Error");
                alExiAlert.setHeaderText(null);
                alExiAlert.setContentText("Error: Please enter a positive number");
                alExiAlert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            Alert alExiAlert = new Alert(Alert.AlertType.ERROR);
            alExiAlert.setTitle("Error");
            alExiAlert.setHeaderText(null);
            alExiAlert.setContentText("Error: Invalid input. Please enter a valid number for income.");
            alExiAlert.showAndWait();
            return;
        }

        double income = Double.parseDouble(incomestr);
        Budget newbudget = new Budget(userId, budgetType, income, budgetStartDate, budgetEndDate, recurrence);

        if (databaseManager.addbudget(newbudget)) {
            Alert addedAlert = new Alert(Alert.AlertType.INFORMATION);
            addedAlert.setTitle("Budget Added");
            addedAlert.setHeaderText(null);
            addedAlert.setContentText("Budget Added Successfully");
            addedAlert.showAndWait();
            recurrenceChoiceBox.setValue(null);
            budgetTypeChoiceBox.setValue(null);
            incomeField.setText("");
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
        } else {
            System.out.println("Failed to add Budget");
        }
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}