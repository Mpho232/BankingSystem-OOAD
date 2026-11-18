package model;

public class ChequeAccount extends Account {
    private String employerCompany;
    private String companyAddress;

    public ChequeAccount(String accountNumber, double initialBalance, String branch, Customer holder, String employerCompany, String companyAddress) {
        super(accountNumber, initialBalance, branch, holder);
        this.employerCompany = employerCompany;
        this.companyAddress = companyAddress;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Invalid withdrawal amount or insufficient balance.");
        }
    }

    @Override
    public void calculateMonthlyInterest() {
        // Cheque accounts typically do not earn monthly interest in this spec
    }

    public String getEmployerCompany() { return employerCompany; }
    public String getCompanyAddress() { return companyAddress; }
}