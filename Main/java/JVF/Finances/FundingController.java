package JVF.Finances;

import JVF.Data.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FundingController {

    @FXML
    private TextField FGNameField;
    @FXML
    private TextField FGDescriptionArea;

    private final DatabaseManager databaseManager;

    public FundingController() {
        databaseManager = new DatabaseManager();
    }

    @FXML
    public void saveFundingGroup() {
        String FGName = FGNameField.getText();
        String FGDescription = FGDescriptionArea.getText();

        if (FGName.isEmpty()) {
            showError("Error", "Please Give a name");
            return;
        }

        FundingGroup newFundingGroup = new FundingGroup(FGName, FGDescription);

        if (databaseManager.addFundingGroup(newFundingGroup)) {
            showAlert("Funding Group Added", "Funding Group added successfully");
            clearFields();
        } else {
            showError("Error", "Failed to add funding group");
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
        FGNameField.clear();
        FGDescriptionArea.clear();
    }
}