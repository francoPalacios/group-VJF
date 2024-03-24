package JVF.Finances;

import JVF.Data.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExpensesController {

    private int UserID;

    @FXML
    private TextField amountField;
    @FXML
    private ChoiceBox<String> recurrenceChoiceBox;
    @FXML
    private ChoiceBox<String> budgetTypeChoiceBox;
    @FXML
    private TextArea descriptionArea;

    private final DatabaseManager databaseManager;

    public ExpensesController() {
        databaseManager = new DatabaseManager();
    }

    @FXML
    public void saveExpense() {
        String amountStr = amountField.getText();
        String recurrence = recurrenceChoiceBox.getValue();
        String budgetType = budgetTypeChoiceBox.getValue();
        String description = descriptionArea.getText();

        if (amountStr.isEmpty() || recurrence == null || budgetType == null) {
            showError("Error", "Error: Please Fill In All Fields");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                showError("Error", "Error: Please enter a positive number for amount");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Error", "Error: Invalid input. Please enter a valid number for amount.");
            return;
        }

        Expense newExpense = new Expense(UserID, amount, recurrence, budgetType, description);

        if (databaseManager.addExpense(newExpense)) {
            showAlert("Expense Added", "Expense added successfully");
            clearFields();
        } else {
            showError("Error", "Failed to add expense");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        amountField.clear();
        recurrenceChoiceBox.getSelectionModel().clearSelection();
        budgetTypeChoiceBox.getSelectionModel().clearSelection();
        descriptionArea.clear();
    }

    public void setUserId(int userId) {
        this.UserID = userId;
    }
}
