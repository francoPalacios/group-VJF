package JVF.Finances;

import JVF.Data.DataSingleton;
import JVF.Data.DatabaseManager;
import javafx.application.Platform;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class BudgetFundingController implements Initializable {

    @FXML
    private ChoiceBox<String> budgetTypeField;
    @FXML
    private ChoiceBox<String> FundingNameField;
    @FXML
    private TextField amountField;
    @FXML
    private Label CurrentBudget;
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

        if (budgetType==null || FundingName==null || amountStr.isEmpty()) {
            showError("Error", "Please fill in all fields");
            return;
        }


            if (databaseManager.isBudgetFunding(UserID,budgetTypes.get(budgetType), fgNames.get(FundingName) )) {
                Alert alExiAlert = new Alert(Alert.AlertType.ERROR);
                alExiAlert.setTitle("Error");
                alExiAlert.setHeaderText(null);
                alExiAlert.setContentText("The budget has been allocated to the funding name specified.");
                alExiAlert.showAndWait();
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

                Integer BudgetID = budgetTypes.get(budgetTypeField.getValue());
                double currentcash = databaseManager.getBudgetamount(usrID,BudgetID.intValue());
                double cashasignforeachbudget = databaseManager.getCashforbudgetcheck(usrID, BudgetID.intValue());
                double cashplus = amount+cashasignforeachbudget;
                if (cashplus > currentcash) {
                    showError("Error", "Error: your assignment to each Funding is over than your current cash that you have.");
                    return;
                }


                BudgetFunding newBudgetFunding = new BudgetFunding(amount,UserID, budgetTypes.get(budgetType), fgNames.get(FundingName));

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
        budgetTypeField.setValue(null);
        FundingNameField.setValue(null);
        Platform.runLater(() -> amountField.clear());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataSingleton data = DataSingleton.getInstance();

        int usrID = data.getUserId();
        budgetTypes = databaseManager.getbudgetType(usrID);
        budgetTypeField.getItems().setAll(budgetTypes.keySet());

        budgetTypeField.setOnAction(event -> {
            handleCurrentbudget();
        });
        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
        handlesubbudget();
        });

        fgNames = databaseManager.getFundingGroupName();
        FundingNameField.getItems().setAll(fgNames.keySet());
    }
    public void handleCurrentbudget() {
        DataSingleton data = DataSingleton.getInstance();
        int usrID = data.getUserId();
        String amountStr = amountField.getText();
        Integer BudgetTypeId = budgetTypes.get(budgetTypeField.getValue());

        if (BudgetTypeId != null) {
            double currentcash = databaseManager.getBudgetamount(usrID, BudgetTypeId.intValue());
            double cash = databaseManager.getCashforbudgetcheck(usrID, BudgetTypeId.intValue());
            //CurrentBudget.setText(String.valueOf(currentcash));
            // } else {
            // Handle the case when the fundingGroupId is null
            //    CurrentBudget.setText("0.0"); // Set a default value

            //if(cash==null && cashLeft == 0) {
            if (cash == 0.0) {
                CurrentBudget.setText(String.valueOf(currentcash));

            } else if (cash > 0) {
                double Budgetcash = currentcash - cash;
                CurrentBudget.setText(String.valueOf(Budgetcash));
            }
        }else{
            CurrentBudget.setText("");
        }
        // if (!amountStr.isEmpty()) {
        //   double amtinput;
        //   amtinput = Double.parseDouble(amountStr);
        //  double budgetsub = currentcash - amtinput;
        //  CurrentBudget.setText(String.valueOf(budgetsub));
        // }
    }

    public void setUserId(int userId) {
        this.UserID = userId;
    }
    public void handlesubbudget() {
        DataSingleton data = DataSingleton.getInstance();
        int usrID = data.getUserId();
        String amountStr = amountField.getText();
        String selectedBudgetType = budgetTypeField.getValue();
      double amtinput = 0;

        try {
            amtinput = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            // Handle invalid input
            //CurrentBudget.setText("Invalid input");
        }


        // Check if a budget type is selected
        if (selectedBudgetType != null) {
            Integer BudgetTypeId = budgetTypes.get(selectedBudgetType);
            if (BudgetTypeId != null) {
                double currentcash = databaseManager.getBudgetamount(usrID, BudgetTypeId.intValue());
                double cashasigntoFnG = databaseManager.getCashforbudgetcheck(usrID, BudgetTypeId.intValue());
                if (!amountStr.isEmpty()) {
                   // double amtinput = Double.parseDouble(amountStr);
                    if (cashasigntoFnG == 0) {
                        double budgetsub = currentcash - amtinput;
                        CurrentBudget.setText(String.valueOf(budgetsub));
                    } else {
                        double budgetsub = (currentcash - cashasigntoFnG) - amtinput;
                        CurrentBudget.setText(String.valueOf(budgetsub));
                    }
                } else {
                    if (cashasigntoFnG == 0) {
                        CurrentBudget.setText(String.valueOf(currentcash));
                    } else {
                        double budgetsub = currentcash - cashasigntoFnG;
                        CurrentBudget.setText(String.valueOf(budgetsub));
                    }
                }
            } else {
                // Handle case when BudgetTypeId is null
                CurrentBudget.setText("");
            }
        } else {
            // Handle case when no budget type is selected
            CurrentBudget.setText("");
        }
    }

    public void handlesubbudget1() {
        DataSingleton data = DataSingleton.getInstance();
        int usrID = data.getUserId();
        String amountStr = amountField.getText();
        Integer BudgetTypeId = budgetTypes.get(budgetTypeField.getValue());
        if (!amountStr.isEmpty()) {
            double currentcash = databaseManager.getBudgetamount(usrID, BudgetTypeId.intValue());
            double cashasigntoFnG = databaseManager.getCashforbudgetcheck(usrID, BudgetTypeId.intValue());
            double amtinput = Double.parseDouble(amountStr);
            if(cashasigntoFnG==0) {
                double budgetsub = currentcash - amtinput;
                CurrentBudget.setText(String.valueOf(budgetsub));
            }else{
                double budgetsub = (currentcash-cashasigntoFnG) - amtinput;
                CurrentBudget.setText(String.valueOf(budgetsub));
            }
        }else{
            double currentcash = databaseManager.getBudgetamount(usrID, BudgetTypeId.intValue());
            double cashasigntoFnG = databaseManager.getCashforbudgetcheck(usrID, BudgetTypeId.intValue());
            if(cashasigntoFnG==0) {
                CurrentBudget.setText(String.valueOf(currentcash));
            }else{
                double budgetsub = currentcash-cashasigntoFnG;
                CurrentBudget.setText(String.valueOf(budgetsub));
            }
        }

    }



}
