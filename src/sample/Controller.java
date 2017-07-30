package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import sample.Main;
import sample.Person;

import java.io.File;

public class Controller {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label laseNameLabel;
    @FXML
    private Label parentNameLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label emailAddressLabel;

    private Main main;

    public Controller() {
    }

    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        showPersonDetails(null);

        personTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showPersonDetails(newValue)));
    }

    public void setMain(Main main) {
        this.main = main;
        personTable.setItems(main.getPersonData());
    }

    private void showPersonDetails(Person person) {
        if (person != null) {
            firstNameLabel.setText(person.getFirstName());
            laseNameLabel.setText(person.getLastName());
            parentNameLabel.setText(person.getParentName());
            phoneNumberLabel.setText(person.getPhoneNumber());
            emailAddressLabel.setText(person.getEmailAddress());
        } else {
            firstNameLabel.setText("");
            laseNameLabel.setText("");
            parentNameLabel.setText("");
            phoneNumberLabel.setText("");
            emailAddressLabel.setText("");
        }
    }

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = main.showPersonEditDialog(tempPerson);
        if(okClicked) {
            main.getPersonData().add(tempPerson);
        }
    }

    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if(selectedPerson != null) {
            boolean okClicked = main.showPersonEditDialog(selectedPerson);
            if(okClicked) {
                showPersonDetails(selectedPerson);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Player Selected");
            alert.setContentText("Please select a player");

            alert.showAndWait();
        }
    }

    //Create empty address book
    @FXML
    private void handleNew() {
        main.getPersonData().clear();
        main.setPersonFilePath(null);
    }

    //Open FileChooser to select address book to load
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(main.getPrimaryStage());

        if(file != null) {
            main.loadPersonDataFromFile(file);
        }
    }

    //Save file to current open file, or prompt for 'save as'
    @FXML
    private void handleSave() {
        File personFile = main.getPersonFilePath();
        if (personFile != null) {
            main.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    //Open Filechooser to select file to save to
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(main.getPrimaryStage());

        if (file != null) {
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            main.savePersonDataToFile(file);
        }
    }

    //Open about dialog
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Soccer Team");
        alert.setHeaderText("About");
        alert.setContentText("Author: Isaiah Dasen\nWebsite: http://www.isaiah.tech");

        alert.showAndWait();
    }

    //Close App
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
