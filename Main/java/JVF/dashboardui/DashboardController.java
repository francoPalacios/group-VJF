package JVF.dashboardui;

import JVF.Finances.SettingsController;
import JVF.Data.DataSingleton;
import JVF.Data.DatabaseManager;
import JVF.Finances.BudgetController;
import JVF.Finances.BudgetFundingController;
import JVF.Finances.ExpensesController;
import JVF.Finances.FundingController;
import JVF.loginui.RegisterController;
import JVF.loginui.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController {

    @FXML
    private Button addBudgetButton;
    @FXML
    private Button addExpenseButton;

    @FXML
    private Button addFundingGroupButton;
    @FXML
    private Button AddBudgetFundingButton;
    @FXML
    private Label WelcomeText;
    @FXML
    public Button settingsButton;
    private final DatabaseManager databaseManager;

    public DashboardController() {
        databaseManager = new DatabaseManager();
    }

    @FXML
    void initialize() {
        DataSingleton data = DataSingleton.getInstance();
        addBudgetButton.setOnAction(event -> openAddBudgetWindow(data.getUserId()));
        addExpenseButton.setOnAction(event -> openAddExpensesWindow(data.getUserId()));
        addFundingGroupButton.setOnAction(event -> openAddFundingGroupWindow());
        AddBudgetFundingButton.setOnAction(event -> openBudgetFundingWindow(data.getUserId()));
        settingsButton.setOnAction(event -> openSettingsWindow(data.getUserId()));
    }

    private void openAddBudgetWindow(int userId) {
        try {
            // Create BudgetController instance with userId
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/JVF.dashboardui/addbudget.fxml"));
            Parent root = loader.load();
            BudgetController controller = loader.getController();
            controller.setUserId(userId);

            // Show the stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Budget");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openAddExpensesWindow(int userId) {
        if (!databaseManager.Budgetcheck(userId) && !databaseManager.BudgetFundingcheck(userId)) {
            showError("Error", "Error: You don't have any budget yet. Please add your budget first" +
                    "\n and llocate the Add Budget_Funding to each specified funding name");
        }else if (databaseManager.Budgetcheck(userId)&& !databaseManager.BudgetFundingcheck(userId)) {
            showError("Error", "Error: Please allocate the budget_funding to each specified funding name");
        } else {
            try {
                // Create ExpenseController instance with userId
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/JVF.dashboardui/addexpense.fxml"));
                Parent root = loader.load();
                ExpensesController controller = loader.getController();
                controller.setUserId(userId);

                // Show the stage
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Add Expense");
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openAddFundingGroupWindow() {
        try {
            // Load the FXML file for the funding group window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/JVF.dashboardui/FundingGroup.fxml"));
            Parent root = loader.load();

            // Create an instance of the FundingGroupController
            FundingController controller = loader.getController();

            // Show the stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Funding Group");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void openBudgetFundingWindow(int userId) {
        if (!databaseManager.Budgetcheck(userId)) {
            showError("Error", "Error: You don't have any budget yet. Please add your budget first");
        } else {
            try {

                // Load the FXML file for the funding group window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/JVF.dashboardui/BudgetFunding.fxml"));
                Parent root = loader.load();

                // Create an instance of the FundingGroupController
                BudgetFundingController controller = loader.getController();
                controller.setUserId(userId);
                // Show the stage
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Add Budget-Funding");
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openSettingsWindow(int userId) {
        try {
            // Create settings instance with userId
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/JVF.dashboardui/Settings.fxml"));
            Parent root = loader.load();
            SettingsController controller = loader.getController();

            // Show the stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Settings");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
