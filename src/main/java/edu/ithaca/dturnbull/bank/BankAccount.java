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

            if(!isAmountValid(startingBalance)){
                throw new IllegalArgumentException("Starting balance: " + startingBalance + " is invalid, cannot create account");
            }
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    /*
     * @return true if amount is positive and has no more than two decimal places
     */
    public static boolean isAmountValid(double amount){
        String strAmount = Double.toString(amount);

        if (amount < 0.01){
            return false;
        }
        
        if(strAmount.contains(".")){
            String[] decimalSplit = strAmount.split("\\.");
            if (decimalSplit[1].length() > 2){
                return false;
            }
        }

        if(strAmount.contains("E")){
            return false;
        }
        
        return true;
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
        if (!isAmountValid(amount)){
            throw new IllegalArgumentException("Invalid withdrawal: Enter positive amount with no more than two decimal places.");
        } else if (amount <= balance){
            balance -= amount;
        }else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

    /**
     * @post increases the balance by amount if amount is non-negative and valid
     */
    public void deposit (double amount) throws InsufficientFundsException{
        ;
    }

    
    

    public static boolean isEmailValid(String email){
        String specChars = "!#$%^&*() ";
        int count = email.length() - email.replace("@", "").length();
        for (int i = 0; i < specChars.length(); i++){
            if (email.contains(String.valueOf(specChars.charAt(i)))){
                return false;
            }
        }
        if (email.length() >= 6){
            if (email.indexOf('@') >= 1){
                if (count == 1){
                    if ((email.charAt(email.length() - 3) == '.') || (email.charAt(email.length() - 4) == '.')){
                        if ((email.charAt(email.length() - 2) != '.') || (email.charAt(email.length() - 1) != '.')){
                            if (email.charAt(email.indexOf('@')+1) != '.'){
                                return true;
                            }
                        }   
                    }
                }
            }
        }
        return false;
    }
}