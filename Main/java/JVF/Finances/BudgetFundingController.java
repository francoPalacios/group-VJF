package JVF.Finances;

import JVF.Data.DataSingleton;
import JVF.Data.DatabaseManager;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BudgetFundingController implements Initializable {

    @FXML
    private ChoiceBox<String> budgetTypeField;
    @FXML
    private ChoiceBox<String> FundingNameField;
    @FXML
    private TextField amountField;
    private int UserID;

    private final DatabaseManager databaseManager;
    private ObservableMap<String, Integer> budgetTypes;
    private ObservableMap<String, Integer> fgNames;

    /*
    public BudgetFundingController(int userId) {
        this.user_id = userId;
        databaseManager = new DatabaseManager();
    }

     */

    public BudgetFundingController(int userId) {
        this.UserID = userId;
        databaseManager = new DatabaseManager();
    }
    public BudgetFundingController() {
        databaseManager = new DatabaseManager();
    }

    @FXML
    public void saveBudgetFunding() {

        String budgetType = budgetTypeField.getValue();
        String FundingName = FundingNameField.getValue();
        String amountStr = amountField.getText();
        DataSingleton data = DataSingleton.getInstance();

        int usrID = data.getUserId();
        budgetTypes = databaseManager.getbudgetType(usrID);


       // System.out.printf("Getting budgetType ID for %s and it is %d%n",
       //         budgetType, budgetTypes.get(budgetType));
        //System.out.printf("Getting Funding ID for %s and it is %d%n",
        //        FundingName,fgNames.get(FundingName));

        if (budgetType==null || FundingName==null || amountStr.isEmpty()) {
            showError("Error", "Please fill in all fields");
            return;
        }


            if (databaseManager.isBudgetFunding(UserID,budgetTypes.get(budgetType), fgNames.get(FundingName) )) {
                Alert alExiAlert = new Alert(Alert.AlertType.ERROR);
                alExiAlert.setTitle("Error");
                alExiAlert.setHeaderText(null);
                alExiAlert.setContentText("budgetname Is Already In Use");
                alExiAlert.showAndWait();
            } else {
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount <= 0) {
                        showError("Error", "Please enter a positive number for amount");
                        return;
                    }
                } catch (NumberFormatException e) {
                    showError("Error", "Invalid input. Please enter a valid number for amount.");
                    return;

                }
                    double amount = Double.parseDouble(amountStr);
                    BudgetFunding newBudgetFunding = new BudgetFunding(amount, UserID, budgetTypes.get(budgetType), fgNames.get(FundingName));

                    if (databaseManager.addBudgetFunding(newBudgetFunding)) {
                        showAlert("Budget Funding Added", "Budget Funding added successfully");
                        clearFields();
                    } else {
                        showError("Error", "Failed to add budget funding");

                }

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
        budgetTypeField.setValue(null);
        FundingNameField.setValue(null);
        amountField.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataSingleton data = DataSingleton.getInstance();

        int usrID = data.getUserId();
        budgetTypes = databaseManager.getbudgetType(usrID);
        budgetTypeField.getItems().setAll(budgetTypes.keySet());

        fgNames = databaseManager.getFundingGroupName();
        FundingNameField.getItems().setAll(fgNames.keySet());
    }
    public void setUserId(int userId) {
        this.UserID = userId;
    }
}
