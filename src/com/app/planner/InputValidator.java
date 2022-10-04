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
        createEventHandler(textField, messageLabel, ValidatorType.EMAIL, null, null);
    }

    public void createPasswordValidator(PasswordField passwordField, Label messageLabel, TextField showPasswordTextField) {
        createEventHandler(passwordField, messageLabel, ValidatorType.PASSWORD, null, showPasswordTextField);
    }

    public void createPasswordValidator(TextField showPasswordTextField, Label messageLabel, PasswordField passwordField) {
        createEventHandler(showPasswordTextField, messageLabel, ValidatorType.PASSWORD, passwordField, null);
    }

    public void createRetypePasswordValidator(PasswordField passwordField, PasswordField retypePasswordField, Label messageLabel, TextField showRetypePasswordTextField) {
        createEventHandler(retypePasswordField, messageLabel, ValidatorType.RETYPE, passwordField, showRetypePasswordTextField);
    }

    public void createRetypePasswordTextValidator(TextField showRetypePasswordTextField, TextField showPasswordTextField, Label messageLabel, PasswordField retypePasswordField) {
        createEventHandler(showPasswordTextField, messageLabel, ValidatorType.RETYPE, retypePasswordField, showRetypePasswordTextField);
    }

    private void createEventHandler(TextField field, Label messageLabel, ValidatorType validatorType, PasswordField passwordField, TextField showPasswordTextField) {
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
                timer.schedule(createPasswordTask(field, messageLabel, showPasswordTextField), VALIDATION_DELAY);
            } else {
                System.out.println("Task Scheduled");
                timer.schedule(createRetypePasswordTask(field, messageLabel, passwordField, showPasswordTextField), VALIDATION_DELAY);
            }
        });
    }

    private TimerTask createEmailTask(TextField textField, Label messageLabel) {
        return getTimerTask(textField, messageLabel, ValidatorType.EMAIL, null, null);
    }

    private TimerTask createPasswordTask(TextField passwordField, Label messageLabel, TextField showPasswordTextField) {
        return getTimerTask(passwordField, messageLabel, ValidatorType.PASSWORD, null, showPasswordTextField);
    }

    private TimerTask createRetypePasswordTask(TextField passwordField, Label messageLabel, PasswordField retypePasswordField, TextField showRetypePasswordTextField) {
        return getTimerTask(passwordField, messageLabel, ValidatorType.RETYPE, retypePasswordField, showRetypePasswordTextField);
    }

    @NotNull
    private TimerTask getTimerTask(TextField field, Label messageLabel, ValidatorType validatorType, PasswordField retypePasswordField, TextField showPasswordTextField) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    System.out.println("Task executed");
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
                    } else {
                        if (fieldText.equals(retypeFieldText)) {
                            messageLabel.setId("correctLabel");
                            messageLabel.setText("Passwords Match!");
                            field.setId("text-field-correct");
                        } else {
                            messageLabel.setId("errorLabel");
                            messageLabel.setText("Passwords do not match!");
                            field.setId("text-field-error");
                            showPasswordTextField.setId("text-field-error");
                        }
                    }
                });
            }
        };
        tasks.push(task);
        return task;
    }
}
