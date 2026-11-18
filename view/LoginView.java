package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginView {
    private TextField customerIdField;
    private Button loginButton;
    private Label messageLabel;

    public Scene createScene() {
        Label titleLabel = new Label("Banking System Login");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        customerIdField = new TextField();
        customerIdField.setPromptText("Enter Customer ID (e.g., C001)");
        customerIdField.setMaxWidth(250);

        loginButton = new Button("Login");
        messageLabel = new Label();

        VBox layout = new VBox(15, titleLabel, customerIdField, loginButton, messageLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        return new Scene(layout, 400, 300);
    }

    public TextField getCustomerIdField() { return customerIdField; }
    public Button getLoginButton() { return loginButton; }
    public Label getMessageLabel() { return messageLabel; }
}