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

    public InputValidator() {
        timer = new Timer();
        tasks = new Stack<>();
    }

    enum ValidatorType {
        EMAIL,
        PASSWORD
    }

    public void createEmailValidator(TextField textField, Label messageLabel) {
        createEventHandler(textField, messageLabel, ValidatorType.EMAIL);
    }

    public void createPasswordValidator(PasswordField passwordField, Label messageLabel) {
        createEventHandler(passwordField, messageLabel, ValidatorType.PASSWORD);
    }

    public void createEventHandler(TextField field, Label messageLabel, ValidatorType validatorType) {
        field.addEventHandler(KeyEvent.KEY_TYPED, keyEvent -> {
            if (tasks.size() > 0) {
                tasks.pop();
                messageLabel.setText("");
                field.setId("");
                timer.cancel();
                timer.purge();
                timer = new Timer();
                if (validatorType == ValidatorType.EMAIL) {
                    timer.schedule(createEmailTask(field, messageLabel), 1000);
                } else {
                    timer.schedule(createPasswordTask(field, messageLabel), 1000);
                }

            } else {
                if (validatorType == ValidatorType.EMAIL) {
                    timer.schedule(createEmailTask(field, messageLabel), 1000);
                } else {
                    timer.schedule(createPasswordTask(field, messageLabel), 1000);
                }
                messageLabel.setText("");
                field.setId("");
            }
        });
    }

    private TimerTask createEmailTask(TextField textField, Label messageLabel) {
        return getTimerTask(textField, messageLabel, ValidatorType.EMAIL);
    }

    private TimerTask createPasswordTask(TextField passwordField, Label messageLabel) {
        return getTimerTask(passwordField, messageLabel, ValidatorType.PASSWORD);
    }

    @NotNull
    private TimerTask getTimerTask(TextField field, Label messageLabel, ValidatorType validatorType) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    String text = field.getText().trim();
                    if (validatorType == ValidatorType.EMAIL) {
                        if (StringValidation.emailValidation(text).equals(StringValidation.RETURN_STRING)) {
                            messageLabel.setId("correctLabel");
                            messageLabel.setText(StringValidation.RETURN_STRING);
                            field.setId("text-field-correct");
                        } else {
                            messageLabel.setId("errorLabel");
                            messageLabel.setText(StringValidation.emailValidation(text));
                            field.setId("text-field-error");
                        }
                    } else {
                        if (StringValidation.passwordValidation(text).equals(StringValidation.RETURN_STRING)) {
                            messageLabel.setId("correctLabel");
                            messageLabel.setText(StringValidation.RETURN_STRING);
                            field.setId("text-field-correct");
                        } else {
                            messageLabel.setId("errorLabel");
                            messageLabel.setText(StringValidation.passwordValidation(text));
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
