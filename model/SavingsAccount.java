package model;

public class SavingsAccount extends Account {

    public SavingsAccount(String accountNumber, double initialBalance, String branch, Customer holder) {
        super(accountNumber, initialBalance, branch, holder);
    }

    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Withdrawals are not allowed from a Savings Account.");
    }

    @Override
    public void calculateMonthlyInterest() {
        double interest = balance * 0.0005; // 0.05%
        balance += interest;
    }
}