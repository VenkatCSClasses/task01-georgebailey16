package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        //Valid email cases
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address //class: valid
        assertTrue(BankAccount.isEmailValid("a.b@c.com"));   // valid email with special characters
        //Invalid email cases
        assertFalse( BankAccount.isEmailValid(""));         // class: empty string //edge case
        assertFalse( BankAccount.isEmailValid("a@bcom"));    //class: missing "." 
        assertFalse( BankAccount.isEmailValid("name.com"));    // class: missing "@"
        assertFalse( BankAccount.isEmailValid("a@.com"));    // class: improper domain
        assertFalse( BankAccount.isEmailValid("a@b."));      // class: improper domain
        assertFalse( BankAccount.isEmailValid("a.b@c"));      // class: improper domain
        assertFalse( BankAccount.isEmailValid("@b.com"));    //class: improper prefix 
        assertFalse( BankAccount.isEmailValid("a@@b.com"));  // class: improper prefix
        assertFalse( BankAccount.isEmailValid("a b@c.com")); // class: special characters
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}