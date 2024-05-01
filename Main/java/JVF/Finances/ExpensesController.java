package JVF.Finances;

import JVF.Data.DataSingleton;
import JVF.Data.DatabaseManager;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class ExpensesController {

    private int UserID;

    @FXML
    private TextField amountField;
    @FXML
    private ChoiceBox<String> ExpType;
    @FXML
    private TextField Receipt;
    @FXML
    private DatePicker ExpDate;
    @FXML
    private Label CurrCash;
    @FXML
    private Label CashLeft;
    private double currentcash;
    private ObservableMap<String, Integer> fgNames;

    private final DatabaseManager databaseManager;

    public ExpensesController() {
        databaseManager = new DatabaseManager();
    }

    @FXML
    public void saveExpense() {
        String amountStr = amountField.getText();
        String expType = ExpType.getValue();
        String recpt = Receipt.getText();
        LocalDate expdate = ExpDate.getValue();

        if (amountStr.isEmpty() || expType == null || expdate == null) {
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

        DataSingleton data = DataSingleton.getInstance();
        int usrID = data.getUserId();
        Integer fundingGroupId = fgNames.get(ExpType.getValue());
        double cash = databaseManager.getCash(fundingGroupId.intValue(), usrID);
        double cashLeft = databaseManager.getCashleft(fundingGroupId.intValue(), usrID);
        if (cashLeft > 0) {
            if(amount>cashLeft){
                showError("Error", "Error: amount bigger than current cash you have. Please enter a positive amount.");
                return;
            }else {
                double cashLeftsub = cashLeft - amount;
                databaseManager.setcashleft(fundingGroupId.intValue(), usrID, cashLeftsub);
            }

        } else if(cashLeft==0){
            if(amount>cash){
                showError("Error", "Error: amount bigger than current cash you have. Please enter a positive amount.");
                return;
            }else {
                double cashsub = cash - amount;
                databaseManager.setcashleft(fundingGroupId.intValue(), usrID, cashsub);
            }
        }

        Expense newExpense = new Expense(UserID, fgNames.get(expType), amount, recpt, expdate);

        if (databaseManager.addExpense(newExpense)) {
            showAlert("Expense Added", "Expense added successfully");
            clearFields();
            // databaseManager.setcashleft(fundingGroupId.intValue(), usrID, cashLeft);
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
        ExpType.setValue(null);
        Receipt.clear();
        ExpDate.setValue(null);
        CurrCash.setText(null);
        CashLeft.setText(null);
    }

    public void setUserId(int userId) {
        this.UserID = userId;
    }


    public void initialize() {
        DataSingleton data = DataSingleton.getInstance();

        int usrID = data.getUserId();
        fgNames = databaseManager.getFundingGroupName();
        ExpType.getItems().setAll(fgNames.keySet());

        ExpType.setOnAction(this::handlecurrcash);
        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            handleCashLeft();});
    }

    private void handlecurrcash(ActionEvent e) {
        DataSingleton data = DataSingleton.getInstance();

        int usrID = data.getUserId();
        Integer fundingGroupId = fgNames.get(ExpType.getValue());
        if (fundingGroupId != null) {
            double cashLeft = databaseManager.getCashleft(fundingGroupId.intValue(), usrID);
            if (cashLeft > 0) {
                CurrCash.setText(String.valueOf(cashLeft));
            } else {
                double cash = databaseManager.getCash(fundingGroupId.intValue(), usrID);
                CurrCash.setText(String.valueOf(cash));
                this.currentcash = cash;
            }
        } else {
            // Handle the case when the fundingGroupId is null
            // For example, show an error message or set a default value
            CurrCash.setText("0.0"); // Set a default value
        }
    }


    private void handleCashLeft() {
        DataSingleton data = DataSingleton.getInstance();

        int usrID = data.getUserId();
        String amountStr = amountField.getText();
        if (amountStr.isEmpty()) {
            CashLeft.setText("");
            return; // No need to proceed if the amount is empty
        }

        double cashamt;
        try {
            cashamt = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            // Handle invalid input
            CashLeft.setText("Invalid input");
            return;
        }

        Integer fundingGroupId = fgNames.get(ExpType.getValue());
        if (fundingGroupId != null) {
                double cashleft = databaseManager.getCashleft(fundingGroupId.intValue(), usrID);
                if(cashleft > 0) {
                    double cashleftsub = cashleft - cashamt;
                    CashLeft.setText(String.valueOf(cashleftsub));
                }else{
                    double cash = databaseManager.getCash(fundingGroupId.intValue(), usrID);
                    double currentcash= cash - cashamt;
                    CashLeft.setText(String.valueOf(currentcash));
            }
           // databaseManager.setcashleft(fundingGroupId.intValue(), usrID, cashLeft);
            } else{
            // Handle the case when the fundingGroupId is null
            CashLeft.setText("0.0"); // Set a default value
        }
    }

}
