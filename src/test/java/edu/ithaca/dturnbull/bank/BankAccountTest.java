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
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse( BankAccount.isEmailValid(""));         // empty string
        assertFalse( BankAccount.isEmailValid("a@bcom"));    // missing "."
        assertFalse( BankAccount.isEmailValid("name.com"));    // missing "@"
        assertFalse( BankAccount.isEmailValid("@b.com"));    // missing text before "@"
        assertFalse( BankAccount.isEmailValid("a@.com"));    // missing text between "@" and "."
        assertFalse( BankAccount.isEmailValid("a@b."));      // missing text after "."
        assertFalse( BankAccount.isEmailValid("a@@b.com"));  // more than one "@"
        assertFalse( BankAccount.isEmailValid("a b@c.com")); // space in email
        assertTrue(BankAccount.isEmailValid("a.b@c.com"));   // valid email with special characters
        assertFalse(BankAccount.isEmailValid("a.b@c"));      // missing "." after "@"
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