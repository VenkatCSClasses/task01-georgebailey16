package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        String specChars = "!#$%^&*()";
        for (int i = 0; i < specChars.length(); i++){
            if (email.contains(String.valueOf(specChars.charAt(i)))){
                return false;
            }
        }
        if (email.length() >= 6){
            if (email.indexOf('@') >= 2){
                if (email.indexOf('.') == -2 || email.indexOf('.') == -3){
                    return true;
                }
            }
        }
        return false;
    }
}