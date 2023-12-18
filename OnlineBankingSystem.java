import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class BankAccount {
    private String accountNumber;
    private double balance;
    private List<String> transactionHistory;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactionHistory = new LinkedList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposit: +" + amount);
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add("Withdrawal: -" + amount);
            return true;
        } else {
            System.out.println("Insufficient funds.");
            return false;
        }
    }

    public void transfer(BankAccount recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            transactionHistory.add("Transfer to " + recipient.getAccountNumber() + ": -" + amount);
        }
    }
}

class Bank {
    private Map<String, BankAccount> accounts;

    public Bank() {
        accounts = new HashMap<>();
    }

    public void createAccount(String accountNumber, double initialBalance) {
        accounts.put(accountNumber, new BankAccount(accountNumber, initialBalance));
    }

    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
}

public class OnlineBankingSystem {
    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.createAccount("12345", 1000.0);
        bank.createAccount("67890", 500.0);

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Account Balance Inquiry");
            System.out.println("2. Fund Transfer");
            System.out.println("3. Transaction History");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    String balanceInquiryAccount = scanner.next();
                    BankAccount account = bank.getAccount(balanceInquiryAccount);
                    if (account != null) {
                        System.out.println("Account Balance: " + account.getBalance());
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 2:
                    System.out.print("Enter sender account number: ");
                    String senderAccount = scanner.next();
                    System.out.print("Enter recipient account number: ");
                    String recipientAccount = scanner.next();
                    System.out.print("Enter transfer amount: ");
                    double transferAmount = scanner.nextDouble();

                    BankAccount sender = bank.getAccount(senderAccount);
                    BankAccount recipient = bank.getAccount(recipientAccount);

                    if (sender != null && recipient != null) {
                        sender.transfer(recipient, transferAmount);
                        System.out.println("Transfer successful.");
                    } else {
                        System.out.println("Invalid account numbers.");
                    }
                    break;
                case 3:
                    System.out.print("Enter account number: ");
                    String historyAccount = scanner.next();
                    BankAccount accountWithHistory = bank.getAccount(historyAccount);
                    if (accountWithHistory != null) {
                        List<String> transactionHistory = accountWithHistory.getTransactionHistory();
                        System.out.println("Transaction History:");
                        for (String transaction : transactionHistory) {
                            System.out.println(transaction);
                        }
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 4);

        scanner.close();
    }
}
