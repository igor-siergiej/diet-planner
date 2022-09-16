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
    private final static int VALIDATION_DELAY = 1000;

    public InputValidator() {
        timer = new Timer();
        tasks = new Stack<>();
    }

    enum ValidatorType {
        EMAIL,
        PASSWORD,
        RETYPE,
        AGE,

    }

    public void createEmailValidator(TextField textField, Label messageLabel) {
        createEventHandler(textField, messageLabel, ValidatorType.EMAIL, null, null);
    }

    public void createPasswordValidator(PasswordField passwordField, Label messageLabel, TextField showPasswordTextField) {
        createEventHandler(passwordField, messageLabel, ValidatorType.PASSWORD, null, showPasswordTextField);
    }

    public void createRetypePasswordValidator(PasswordField passwordField, PasswordField retypePasswordField, Label messageLabel) {
        createEventHandler(retypePasswordField, messageLabel, ValidatorType.RETYPE, passwordField, null);
    }

    private void createEventHandler(TextField field, Label messageLabel, ValidatorType validatorType, PasswordField retypePasswordField, TextField showPasswordTextField) {
        field.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.TAB)) {
                return; //when tab was pressed it would run the task on the textfield that the tab press sent you to, this is a workaround
            }
            messageLabel.setText("");
            field.setId("");
            if (tasks.size() > 0) {
                tasks.pop();
                timer.cancel();
                timer.purge();
                timer = new Timer();
            }
            if (validatorType == ValidatorType.EMAIL) {
                timer.schedule(createEmailTask(field, messageLabel), VALIDATION_DELAY);
            } else  if (validatorType == ValidatorType.PASSWORD){
                timer.schedule(createPasswordTask(field, messageLabel, showPasswordTextField), VALIDATION_DELAY);
            } else {
                timer.schedule(createRetypePasswordTask(field,messageLabel,retypePasswordField), VALIDATION_DELAY);
            }
        });
    }

    private TimerTask createEmailTask(TextField textField, Label messageLabel) {
        return getTimerTask(textField, messageLabel, ValidatorType.EMAIL, null,null);
    }

    private TimerTask createPasswordTask(TextField passwordField, Label messageLabel, TextField showPasswordTextField) {
        return getTimerTask(passwordField, messageLabel, ValidatorType.PASSWORD, null, showPasswordTextField);
    }

    private TimerTask createRetypePasswordTask(TextField passwordField, Label messageLabel, PasswordField retypePasswordField) {
        return getTimerTask(passwordField,messageLabel,ValidatorType.RETYPE, retypePasswordField, null);
    }

    @NotNull
    private TimerTask getTimerTask(TextField field, Label messageLabel, ValidatorType validatorType, PasswordField retypePasswordField, TextField showPasswordTextField) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    String fieldText = field.getText().trim();
                    String retypeFieldText = "";
                    if (retypePasswordField != null) {
                        retypeFieldText = retypePasswordField.getText().trim();
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
                    } else if (validatorType == ValidatorType.PASSWORD){
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
                    } else {
                        if (fieldText.equals(retypeFieldText)) {
                            messageLabel.setId("correctLabel");
                            messageLabel.setText("Passwords Match!");
                            field.setId("text-field-correct");
                        } else {
                            messageLabel.setId("errorLabel");
                            messageLabel.setText("Passwords do not match!");
                            field.setId("text-field-error");
                        }
                    }
                });
            }
        };
        tasks.push(task);
        return task;
    }
}
