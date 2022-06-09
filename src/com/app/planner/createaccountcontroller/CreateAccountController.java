package com.app.planner.createaccountcontroller;

import com.app.planner.*;
import com.app.planner.createprofilecontroller.CreateProfileController;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class CreateAccountController extends BaseScreenController {

    @FXML
    private ProgressBar passwordStrengthProgressBar;

    @FXML
    private TextField registerEmailTextField;

    @FXML
    private PasswordField registerPasswordField;

    @FXML
    private PasswordField registerRetypePasswordField;

    @FXML
    private Label registerEmailMessage;

    @FXML
    private Label registerRetypePasswordMessage;

    @FXML
    private Label registerPasswordMessage;

    @FXML
    private RadioButton charLimitRadioButton;

    @FXML
    private RadioButton upperCaseRadioButton;

    @FXML
    private RadioButton lowerCaseRadioButton;

    @FXML
    private RadioButton numberRadioButton;

    @FXML
    private RadioButton specialCharRadioButton;

    @FXML
    private Pane mainPane;

    @FXML
    private Button registerButton;

    @FXML
    public void initialize() {
        registerButton.disableProperty().bind(Bindings.isEmpty(registerEmailTextField.textProperty()).or(Bindings.isEmpty(registerPasswordField.textProperty())).or(Bindings.isEmpty(registerRetypePasswordField.textProperty())));
        InputValidator inputValidator = new InputValidator();
        inputValidator.createEmailValidator(registerEmailTextField, registerEmailMessage);
        inputValidator.createPasswordValidator(registerPasswordField, registerPasswordMessage);
    }

    public void passwordStrengthHandler() {
        String password = registerPasswordField.getText();
        float passwordStrength = StringValidation.getPasswordStrength(password);
        if (passwordStrength < 0.5) {
            passwordStrengthProgressBar.setId("passwordStrengthProgressBarWeak");
        } else if (passwordStrength < 0.9) {
            passwordStrengthProgressBar.setId("passwordStrengthProgressBarOk");
        } else {
            passwordStrengthProgressBar.setId("passwordStrengthProgressBarStrong");
        }
        passwordStrengthProgressBar.setProgress(passwordStrength);

        if (StringValidation.isStringWithinCharLimit(password)) {
            charLimitRadioButton.setId("greenRadioButton");
            charLimitRadioButton.setSelected(true);
        } else {
            charLimitRadioButton.setSelected(false);
        }

        if (StringValidation.containsUpperCase(password)) {
            upperCaseRadioButton.setSelected(true);
            upperCaseRadioButton.setId("greenRadioButton");
        } else {
            upperCaseRadioButton.setSelected(false);
        }

        if (StringValidation.contrainsLowerCase(password)) {
            lowerCaseRadioButton.setSelected(true);
            lowerCaseRadioButton.setId("greenRadioButton");
        } else {
            lowerCaseRadioButton.setSelected(false);
        }

        if (StringValidation.containsNumber(password)) {
            numberRadioButton.setSelected(true);
            numberRadioButton.setId("greenRadioButton");
        } else {
            numberRadioButton.setSelected(false);
        }

        if (StringValidation.containsSpecialChar(password)) {
            specialCharRadioButton.setSelected(true);
            specialCharRadioButton.setId("greenRadioButton");
        } else {
            specialCharRadioButton.setSelected(false);
        }
    }

    public void register(ActionEvent event) {
        String email = registerEmailTextField.getText();
        String password = registerPasswordField.getText();
        String retypePassword = registerRetypePasswordField.getText();

        registerEmailTextField.setId("");
        registerPasswordField.setId("");
        registerRetypePasswordField.setId("");

        if (StringValidation.emailValidation(email).equals(true)) {
            if (StringValidation.passwordValidation(password).equals("valid")) {
                if (password.equals(retypePassword)) {
                    if (DatabaseConnection.register(email, password)) { // this will register if validation is ok?
                        goToCreateProfileScreenWithLogin(event, email, password);
                    } else {
                        createErrorNotification(mainPane, "An account with this email already exists");
                        registerEmailTextField.setId("text-field-warning");
                        return;
                    }
                } else {
                    createErrorNotification(mainPane, "Passwords not Matching");
                    registerRetypePasswordField.setId("text-field-error");
                    return;
                }
            } else {
                createErrorNotification(mainPane, "Please create a stronger password");
                if (!StringValidation.isStringWithinCharLimit(password)) {
                    charLimitRadioButton.setId("errorRadioButton");
                }
                if (!StringValidation.containsUpperCase(password)) {
                    upperCaseRadioButton.setId("errorRadioButton");
                }
                if (!StringValidation.contrainsLowerCase(password)) {
                    lowerCaseRadioButton.setId("errorRadioButton");
                }
                if (!StringValidation.containsNumber(password)) {
                    numberRadioButton.setId("errorRadioButton");
                }
                if (!StringValidation.containsSpecialChar(password)) {
                    specialCharRadioButton.setId("errorRadioButton");
                }
                registerPasswordField.setId("text-field-error");
                return;
            }
        } else {
            createErrorNotification(mainPane, "Invalid Email Format");
            registerEmailTextField.setId("text-field-error");
            return;
        }
    }

    // TODO this method should be used for testing to bypass having to register a new account for each test.
    public void testRegister(ActionEvent event) {
        CreateProfileController createProfileController = goToScreen(event, "createprofilecontroller/CreateProfileScreen.fxml").getController();
        createProfileController.initialise("test", "test");
    }

    public void goToCreateProfileScreenWithLogin(ActionEvent event, String email, String password) { // this method will open the profile screen window
        CreateProfileController createProfileController = goToScreen(event, "createprofilecontroller/CreateProfileScreen.fxml").getController();
        createProfileController.initialise(email, password);
    }
}
