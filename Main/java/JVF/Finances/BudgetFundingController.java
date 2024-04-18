package JVF.Finances;

import JVF.Data.DataSingleton;
import JVF.Data.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;

import java.util.List;

public class BudgetFundingController {

    @FXML
    private ChoiceBox budgetTypeField;
    @FXML
    private ChoiceBox FundingNameField;
    @FXML
    private TextField amountField;
    private final int user_id;

    private final DatabaseManager databaseManager;

    public BudgetFundingController(int userId) {
        this.user_id = userId;
        databaseManager = new DatabaseManager();
    }

    public void initialize() {
        DataSingleton data = DataSingleton.getInstance();

        int usrID = data.getUserId();
        List<String> budgetTypes = databaseManager.getbudgetType(usrID);
        budgetTypeField.getItems().addAll(budgetTypes);

        List<String> fgNames = databaseManager.getFundingGroupName();
        FundingNameField.getItems().addAll(fgNames);
    }

    @FXML
    public void saveBudgetFunding() {
        String budgetType = budgetTypeField.getTypeSelector();
        String FundingName = FundingNameField.getTypeSelector();
        String amountStr = amountField.getText();

        if (budgetType.isEmpty() || FundingName.isEmpty() || amountStr.isEmpty()) {
            showError("Error", "Please fill in all fields");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                showError("Error", "Please enter a positive number for amount");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Error", "Invalid input. Please enter a valid number for amount.");
            return;
        }

        BudgetFunding newBudgetFunding = new BudgetFunding(amount, user_id, budgetType, FundingName);

        if (databaseManager.addBudgetFunding(newBudgetFunding)) {
            showAlert("Budget Funding Added", "Budget Funding added successfully");
            clearFields();
        } else {
            showError("Error", "Failed to add budget funding");
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
        SingleSelectionModel<String> originalSelectionModel = budgetTypeField.getSelectionModel();
        budgetTypeField.setSelectionModel(null);
        budgetTypeField.setSelectionModel(originalSelectionModel);

        SingleSelectionModel<String> originalSelectionModelFN = FundingNameField.getSelectionModel();
        FundingNameField.setSelectionModel(null);
        FundingNameField.setSelectionModel(originalSelectionModelFN);
        amountField.clear();
    }
}
