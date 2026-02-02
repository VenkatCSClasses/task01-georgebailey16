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
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // class: valid email -- lower edge case
        assertTrue(BankAccount.isEmailValid("john.doe@example.com")); // class: valid email -- middle
        assertTrue(BankAccount.isEmailValid("first.middle.last@example.co.uk")); // class: valid email -- upper edge case
        assertTrue(BankAccount.isEmailValid("a.b@c.com"));   // class: valid -- special characters
        assertTrue(BankAccount.isEmailValid("u@x.co")); // class: valid domain -- lower edge case
        assertTrue(BankAccount.isEmailValid("aaa@bbb.com")); // class: valid domain -- middle
        assertTrue(BankAccount.isEmailValid("xyz@abc.co.uk")); // class: valid domain -- upper edge case
        //Invalid email cases
        assertFalse(BankAccount.isEmailValid(""));         // class: empty string
        assertFalse(BankAccount.isEmailValid("@."));    // class: missing characters
        assertFalse(BankAccount.isEmailValid(".@...."));  // class: missing characters
        assertFalse(BankAccount.isEmailValid("name.com"));    // class: invalid "@"s -- lower edge case -- 0
        assertFalse(BankAccount.isEmailValid("a@@b.com"));  // class: invalid "@"s -- upper edge case -- 2
        assertFalse(BankAccount.isEmailValid("a@bcom"));    //class: improper domain 
        assertFalse(BankAccount.isEmailValid("a@.com"));    // class: improper domain
        assertFalse(BankAccount.isEmailValid("a@b."));      // class: improper domain
        assertFalse(BankAccount.isEmailValid("a.b@c"));      // class: improper domain
        assertFalse(BankAccount.isEmailValid("a@b..")); // class: improper domain
        assertFalse(BankAccount.isEmailValid("a@b.c")); // class: improper domain -- "." is only one char from end
        assertFalse(BankAccount.isEmailValid("@b.com"));    //class: improper prefix 
        assertFalse(BankAccount.isEmailValid("a b@c.com")); // class: invalid-special-char
        assertFalse(BankAccount.isEmailValid("a!@b.com")); // class: invalid-special-char
        assertFalse(BankAccount.isEmailValid("a#@b.com")); // class: invalid-special-char
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