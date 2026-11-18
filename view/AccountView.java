package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AccountView {
    private Label customerInfoLabel;
    private ComboBox<String> accountTypeBox;
    private TextField amountField;
    private TextField employerField;
    private TextField employerAddressField;
    private Button openAccountButton;
    private Button depositButton;
    private Button withdrawButton;
    private Button applyInterestButton;
    private TextArea accountDisplayArea;

    public Scene createScene() {
        customerInfoLabel = new Label("Welcome, Customer");
        customerInfoLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        accountTypeBox = new ComboBox<>();
        accountTypeBox.getItems().addAll("Savings", "Investment", "Cheque");
        accountTypeBox.setPromptText("Select Account Type");

        amountField = new TextField();
        amountField.setPromptText("Amount (BWP)");

        employerField = new TextField();
        employerField.setPromptText("Employer Company (Cheque only)");

        employerAddressField = new TextField();
        employerAddressField.setPromptText("Employer Address (Cheque only)");

        openAccountButton = new Button("Open Account");
        depositButton = new Button("Deposit");
        withdrawButton = new Button("Withdraw");
        applyInterestButton = new Button("Pay Monthly Interest");

        HBox actionButtons = new HBox(10, openAccountButton, depositButton, withdrawButton, applyInterestButton);

        accountDisplayArea = new TextArea();
        accountDisplayArea.setEditable(false);
        accountDisplayArea.setPrefHeight(180);

        VBox layout = new VBox(12,
                customerInfoLabel,
                new HBox(10, accountTypeBox, amountField),
                new HBox(10, employerField, employerAddressField),
                actionButtons,
                new Label("Account Summary & Messages:"),
                accountDisplayArea
        );
        layout.setPadding(new Insets(20));

        return new Scene(layout, 580, 420);
    }

    public Label getCustomerInfoLabel() { return customerInfoLabel; }
    public ComboBox<String> getAccountTypeBox() { return accountTypeBox; }
    public TextField getAmountField() { return amountField; }
    public TextField getEmployerField() { return employerField; }
    public TextField getEmployerAddressField() { return employerAddressField; }
    public Button getOpenAccountButton() { return openAccountButton; }
    public Button getDepositButton() { return depositButton; }
    public Button getWithdrawButton() { return withdrawButton; }
    public Button getApplyInterestButton() { return applyInterestButton; }
    public TextArea getAccountDisplayArea() { return accountDisplayArea; }
}