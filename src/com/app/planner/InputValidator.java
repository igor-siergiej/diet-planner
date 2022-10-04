package com.app.planner;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class InputValidator {
    Timer timer;
    Stack<TimerTask> tasks;
    private final static int VALIDATION_DELAY = 500;

    public InputValidator() {
        timer = new Timer();
        tasks = new Stack<>();
        //SEPARATE TIMERS FOR EACH VALIDATOR
        //CREATE VALIDATOR IN BASE SCREEN??
        // STATIC OBJECT IN BASE CLASS???
    }

    enum ValidatorType {
        EMAIL,
        PASSWORD,
        RETYPE,
        AGE
    }

    public void createEmailValidator(TextField textField, Label messageLabel) {
        createEventHandler(textField, null, messageLabel, null, ValidatorType.EMAIL);
    }

    public void createPasswordValidator(PasswordField passwordField, TextField showPasswordTextField, Label messageLabel) {
        createEventHandler(passwordField, null, messageLabel, showPasswordTextField, ValidatorType.PASSWORD);
    }

    public void createPasswordValidator(TextField showPasswordTextField, PasswordField passwordField, Label messageLabel) {
        createEventHandler(showPasswordTextField, null, messageLabel, passwordField, ValidatorType.PASSWORD);
    }

    public void createRetypePasswordValidator(PasswordField passwordField, PasswordField retypePasswordField, Label messageLabel, TextField showRetypePasswordTextField) {
        createEventHandler(retypePasswordField, passwordField, messageLabel, showRetypePasswordTextField, ValidatorType.RETYPE);
    }

    public void createRetypePasswordValidator(TextField showRetypePasswordTextField, TextField showPasswordTextField, Label messageLabel, PasswordField retypePasswordField) {
        createEventHandler(showRetypePasswordTextField, showPasswordTextField, messageLabel, retypePasswordField, ValidatorType.RETYPE);
    }

    private void createEventHandler(TextField field, TextField passwordField, Label messageLabel, TextField showPasswordTextField, ValidatorType validatorType) {
        field.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            System.out.println(keyEvent.getText());
            if (keyEvent.getCode().equals(KeyCode.TAB)) {
                return; //when tab was pressed it would run the task on the textfield that the tab press sent you to, this is a workaround
            }
            messageLabel.setText("");
            field.setId("");
            if (tasks.size() > 0) {
                System.out.println("Task Cancelled");
                timer.cancel();
                timer.purge();
                timer = new Timer();
            }
            if (validatorType == ValidatorType.EMAIL) {
                System.out.println("Task Scheduled");
                timer.schedule(createEmailTask(field, messageLabel), VALIDATION_DELAY);
            } else if (validatorType == ValidatorType.PASSWORD) {
                System.out.println("Task Scheduled");
                timer.schedule(createPasswordTask(field, showPasswordTextField, messageLabel), VALIDATION_DELAY);
            } else {
                System.out.println("Task Scheduled");
                timer.schedule(createRetypePasswordTask(field, passwordField, messageLabel, showPasswordTextField), VALIDATION_DELAY);
            }
        });
    }

    private TimerTask createEmailTask(TextField textField, Label messageLabel) {
        return getTimerTask(textField, null, messageLabel, null, ValidatorType.EMAIL);
    }

    private TimerTask createPasswordTask(TextField passwordField, TextField showPasswordTextField, Label messageLabel) {
        return getTimerTask(passwordField, null, messageLabel, showPasswordTextField, ValidatorType.PASSWORD);
    }

    private TimerTask createRetypePasswordTask(TextField passwordField, TextField retypePasswordField, Label messageLabel, TextField showRetypePasswordTextField) {
        return getTimerTask(passwordField, retypePasswordField, messageLabel, showRetypePasswordTextField, ValidatorType.RETYPE);
    }

    @NotNull
    private TimerTask getTimerTask(TextField field, TextField compareField, Label messageLabel, TextField showPasswordTextField, ValidatorType validatorType) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    System.out.println("Task executed");
                    String fieldText = field.getText().trim();
                    String comapareFieldText = "";
                    if (compareField != null) {
                        comapareFieldText = compareField.getText().trim();
                    }
                    if (validatorType == ValidatorType.EMAIL) {
                        if (StringValidation.emailValidation(fieldText).equals(StringValidation.RETURN_STRING)) {
                            messageLabel.setId("correctLabel");
                            messageLabel.setText(StringValidation.RETURN_STRING);
                            field.setId("text-field-correct");
                        } else {
                            messageLabel.setId("errorLabel");
                            messageLabel.setText(StringValidation.emailValidation(fieldText));
                            field.setId("text-field-error");
                        }
                    } else if (validatorType == ValidatorType.PASSWORD) {
                        if (StringValidation.passwordValidation(fieldText).equals(StringValidation.RETURN_STRING)) {
                            messageLabel.setId("correctLabel");
                            messageLabel.setText(StringValidation.RETURN_STRING);
                            field.setId("text-field-correct");
                            showPasswordTextField.setId("text-field-correct");
                        } else {
                            messageLabel.setId("errorLabel");
                            messageLabel.setText(StringValidation.passwordValidation(fieldText));
                            field.setId("text-field-error");
                            showPasswordTextField.setId("text-field-error");
                        }
                    } else if (validatorType == ValidatorType.RETYPE) {
                        if (fieldText.equals(comapareFieldText)) {
                            messageLabel.setId("correctLabel");
                            messageLabel.setText("Passwords Match!");
                            field.setId("text-field-correct");
                            showPasswordTextField.setId("text-field-correct");
                        } else {
                            messageLabel.setId("errorLabel");
                            messageLabel.setText("Passwords do not match!");
                            field.setId("text-field-error");
                            showPasswordTextField.setId("text-field-error");
                        }
                    } else {
                        throw new RuntimeException("Unknown ValidatorType provided");
                    }
                });
            }
        };
        tasks.push(task);
        return task;
    }
}
