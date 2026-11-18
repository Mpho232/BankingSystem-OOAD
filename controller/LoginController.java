package controller;

import javafx.stage.Stage;
import model.Customer;
import view.AccountView;
import view.LoginView;

import java.util.Map;

public class LoginController {
    private LoginView loginView;
    private Stage primaryStage;
    private Map<String, Customer> customerStore;

    public LoginController(LoginView loginView, Stage primaryStage, Map<String, Customer> customerStore) {
        this.loginView = loginView;
        this.primaryStage = primaryStage;
        this.customerStore = customerStore;
        attachEvents();
    }

    private void attachEvents() {
        loginView.getLoginButton().setOnAction(e -> handleLogin());
    }

    private void handleLogin() {
        String id = loginView.getCustomerIdField().getText().trim();
        if (id.isEmpty()) {
            loginView.getMessageLabel().setText("Please enter a Customer ID.");
            return;
        }

        Customer customer = customerStore.get(id);
        if (customer != null) {
            AccountView accountView = new AccountView();
            primaryStage.setScene(accountView.createScene());
            new AccountController(accountView, customer);
        } else {
            loginView.getMessageLabel().setText("Customer not found. Try C001.");
        }
    }
}