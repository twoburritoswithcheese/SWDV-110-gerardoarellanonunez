package bankaccount;


class BankAccount {
    protected String name;
    protected double balance;

    public BankAccount(String name, double openingDeposit) {
        this.name = name;
        this.balance = openingDeposit;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("Deposit successful: $%,.2f%n", amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount)
            throws InsufficientFundsException {

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
        } else if (amount > balance) {
            throw new InsufficientFundsException(
                "Insufficient funds! Your balance is $" + String.format("%,.2f", balance));
        } else {
            balance -= amount;
            System.out.printf("Withdrawal successful: $%,.2f%n", amount);
        }
    }

    public void displayBalance() {
        System.out.printf("Current balance: $%,.2f%n", balance);
    }
}
