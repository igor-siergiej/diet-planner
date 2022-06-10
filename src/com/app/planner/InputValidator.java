package com.app.planner;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
        RETYPE
    }

    public void createEmailValidator(TextField textField, Label messageLabel) {
        createEventHandler(textField, messageLabel, ValidatorType.EMAIL, null);
    }

    public void createPasswordValidator(PasswordField passwordField, Label messageLabel) {
        createEventHandler(passwordField, messageLabel, ValidatorType.PASSWORD, null);
    }

    public void createRetypePasswordValidator(PasswordField passwordField, PasswordField retypePasswordField, Label messageLabel) {
        createEventHandler(retypePasswordField, messageLabel, ValidatorType.RETYPE, passwordField);
    }

    public void createEventHandler(TextField field, Label messageLabel, ValidatorType validatorType, PasswordField retypePasswordField) {
        field.addEventHandler(KeyEvent.KEY_TYPED, keyEvent -> {
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
                timer.schedule(createPasswordTask(field, messageLabel), VALIDATION_DELAY);
            } else {
                timer.schedule(createRetypePasswordTask(field,messageLabel,retypePasswordField), VALIDATION_DELAY);
            }
        });
    }

    private TimerTask createEmailTask(TextField textField, Label messageLabel) {
        return getTimerTask(textField, messageLabel, ValidatorType.EMAIL, null);
    }

    private TimerTask createPasswordTask(TextField passwordField, Label messageLabel) {
        return getTimerTask(passwordField, messageLabel, ValidatorType.PASSWORD, null);
    }

    private TimerTask createRetypePasswordTask(TextField passwordField, Label messageLabel, PasswordField retypePasswordField) {
        return getTimerTask(passwordField,messageLabel,ValidatorType.RETYPE, retypePasswordField);
    }

    @NotNull
    private TimerTask getTimerTask(TextField field, Label messageLabel, ValidatorType validatorType, PasswordField retypePasswordField) {
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
                        } else {
                            messageLabel.setId("errorLabel");
                            messageLabel.setText(StringValidation.passwordValidation(fieldText));
                            field.setId("text-field-error");
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
