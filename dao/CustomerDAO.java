package dao;

import model.Customer;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CustomerDAO {

    public void saveCustomer(Customer customer) {
        String sql = "INSERT OR IGNORE INTO customers (customer_id, first_name, surname, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getCustomerId());
            pstmt.setString(2, customer.getFirstName());
            pstmt.setString(3, customer.getSurname());
            pstmt.setString(4, customer.getAddress());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Customer> getAllCustomers() {
        Map<String, Customer> customers = new HashMap<>();
        String sql = "SELECT * FROM customers";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Customer c = new Customer(
                        rs.getString("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("surname"),
                        rs.getString("address")
                );
                customers.put(c.getCustomerId(), c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
}