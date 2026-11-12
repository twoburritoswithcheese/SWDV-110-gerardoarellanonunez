package bankaccount;


class SavingsAccount extends BankAccount {

    private double interestRate;

    public SavingsAccount(String name, double openingDeposit, double interestRate) {
        super(name, openingDeposit);
        this.interestRate = interestRate;
    }

    public void addInterest() {
        double interest = balance * interestRate;
        balance += interest;

        System.out.printf("Interest added: $%,.2f%n", interest);
    }
}