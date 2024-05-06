//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package JVF.Finances;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import JVF.Data.DataSingleton;
import JVF.Data.DatabaseManager;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class BudgetController {
    @FXML
    private TextField budgetNameField;
    @FXML
    private TextField incomeField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TableView<Budget> budgetTableView;
    @FXML
    private TableColumn<Budget, String> budgetIdColumn;
    @FXML
    private TableColumn<Budget, String> budgetNameColumn;
    @FXML
    private TableColumn<Budget, Double> amountColumn;
    @FXML
    private TableColumn<Budget, LocalDate> startDateColumn;
    @FXML
    private TableColumn<Budget, LocalDate> endDateColumn;
    private int userId;
    private final DatabaseManager databaseManager;
    private DataSingleton data;

    public BudgetController() {
        databaseManager = new DatabaseManager();
        data = DataSingleton.getInstance();
    }
    @FXML
    public void initialize() {
        // Assuming userId is set somewhere before this method is called
        populateBudgetTableView();
    }
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void openUpdateDialog(Budget selectedBudget) {
        if(databaseManager.BudgetFundingcheck(data.getUserId())){
            showError("Error", "Error: You have allocated this Budget to some specified funding name");
        }else{
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Budget");
        dialog.setHeaderText("Update Budget Information");
        dialog.setContentText("Enter the new amount:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newAmount -> {
            // Perform the update operation using the DatabaseManager
            selectedBudget.setBudgetincome(Double.parseDouble(newAmount));
            if (databaseManager.updateBudget(selectedBudget)) {
                Alert updatedAlert = new Alert(Alert.AlertType.INFORMATION);
                updatedAlert.setTitle("Budget Updated");
                updatedAlert.setHeaderText(null);
                updatedAlert.setContentText("Budget Updated Successfully");
                updatedAlert.showAndWait();
                populateBudgetTableView(); // Refresh TableView after update
            } else {
                showError("Error", "Error: Failed to update Budget");
            }
        });
    }
        }
    // Method to populate the TableView with inserted data
    public void populateBudgetTableView() {

        ObservableList<Budget> budgetList = FXCollections.observableArrayList(databaseManager.getAllBudgets(data.getUserId()));

        /*
        TableColumn<Budget,Integer> budgetId = new TableColumn<>("budget_id");
        TableColumn<Budget,String> budgetType = new TableColumn<>("budget_type");
        TableColumn<Budget,Double> budgetAmt = new TableColumn<>("Amount");
        TableColumn<Budget, Date> budgetStart = new TableColumn<>("budget_startdate");
        TableColumn<Budget,Date> budgetEnd = new TableColumn<>("budget_enddate");
        budgetTableView.getColumns().addAll(budgetId);
        budgetTableView.getColumns().addAll(budgetType);
        budgetTableView.getColumns().addAll(budgetAmt);
        budgetTableView.getColumns().addAll(budgetStart);
        budgetTableView.getColumns().addAll(budgetEnd);

         */


        budgetIdColumn.setCellValueFactory(new PropertyValueFactory<>("budgetId"));

        budgetNameColumn.setCellValueFactory(new PropertyValueFactory<>("budgetName"));

        amountColumn.setCellValueFactory(new PropertyValueFactory<>("budgetincome"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("budgetstartdate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("budgetenddate"));



        budgetTableView.setItems(budgetList);
    }
    @FXML
    public void updateSelectedBudget() {
        // Get the selected budget from the TableView
        Budget selectedBudget = budgetTableView.getSelectionModel().getSelectedItem();
        if (selectedBudget == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a budget to update.");
            alert.showAndWait();
            return;
        }

        // Assuming you have a method to open an update dialog with the selected budget
        openUpdateDialog(selectedBudget);
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
        if (databaseManager.isBudgetName(data.getUserId(), budgetName)) {
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
            Budget newbudget = new Budget(data.getUserId(), budgetName, income, budgetStartDate, budgetEndDate);

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
