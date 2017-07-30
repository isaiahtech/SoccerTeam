package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Person;

public class PersonEditDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField parentNameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailAddressField;

    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPerson(Person person) {
        this.person = person;

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        parentNameField.setText(person.getParentName());
        phoneNumberField.setText(person.getPhoneNumber());
        emailAddressField.setText(person.getEmailAddress());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOK() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setParentName(parentNameField.getText());
            person.setPhoneNumber(phoneNumberField.getText());
            person.setEmailAddress(emailAddressField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if(firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No Valid first name!\n";
        }
        if(lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No Valid last name!\n";
        }
        if(parentNameField.getText() == null || parentNameField.getText().length() == 0) {
            errorMessage += "No Valid parent name!\n";
        }
        if(phoneNumberField.getText() == null || phoneNumberField.getText().length() == 0) {
            errorMessage += "No Valid phone number!\n";
        }
        if(emailAddressField.getText() == null || emailAddressField.getText().length() == 0) {
            errorMessage += "No Valid email address!\n";
        }

        if(errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
