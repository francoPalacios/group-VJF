package JVF.dashboardui;

import JVF.Data.DataSingleton;
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
    void initialize() {
        DataSingleton data = DataSingleton.getInstance();
        addBudgetButton.setOnAction(event -> openAddBudgetWindow(data.getUserId()));
        addExpenseButton.setOnAction(event -> openAddExpensesWindow(data.getUserId()));
        addFundingGroupButton.setOnAction(event -> openAddFundingGroupWindow());
        AddBudgetFundingButton.setOnAction(event -> openBudgetFundingWindow(data.getUserId()));
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

    private void openBudgetFundingWindow(int userId) {
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
