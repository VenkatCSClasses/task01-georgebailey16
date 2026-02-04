package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001); // class: typical balance
        bankAccount.withdraw(1);
        assertEquals(199, bankAccount.getBalance(), 0.001); // class: after withdraw -- lower boundary
        bankAccount.withdraw(100);
        assertEquals(99, bankAccount.getBalance(), 0.001); // class: after withdraw -- middle
        bankAccount.withdraw(99);
        assertEquals(0, bankAccount.getBalance(), 0.001); // class: after withdraw -- upper boundary
       
        BankAccount bankAccount2 = new BankAccount("john@smith.com", 500);
        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(-300));
        assertEquals(500, bankAccount2.getBalance(), 0.001); // class: after invalid withdraw -- below lower boundary
        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(0));
        assertEquals(500, bankAccount2.getBalance(), 0.001); // class: after invalid withdraw -- middle
        assertThrows(InsufficientFundsException.class, () -> bankAccount2.withdraw(600));
        assertEquals(500, bankAccount2.getBalance(), 0.001); // class: after invalid withdraw -- above upper boundary
    }

    @Test
    void isAmountValidTest(){
        assertTrue(BankAccount.isAmountValid(10.01)); // class: valid decimal -- lower boundary
        assertTrue(BankAccount.isAmountValid(10.5)); // class: valid decimal -- middle 
        assertTrue(BankAccount.isAmountValid(10)); // class: valid decimal -- upper boundary
        assertTrue(BankAccount.isAmountValid(0.01)); // class: valid amount -- lower boundary
        assertTrue(BankAccount.isAmountValid(199.99)); // class: valid amount -- middle
        assertTrue(BankAccount.isAmountValid(10000)); // class: valid amount -- upper boundary
        assertFalse(BankAccount.isAmountValid(-100)); // class: invalid number -- lower boundary
        assertFalse(BankAccount.isAmountValid(-1)); // class: invalid number -- middle
        assertFalse(BankAccount.isAmountValid(0)); // class: invalid number -- upper boundary
        assertFalse(BankAccount.isAmountValid(0.0000001)); // class: invalid amount -- lower decimal place
        assertFalse(BankAccount.isAmountValid(0.001)); // class: invalid amount -- middle decimal place
        assertFalse(BankAccount.isAmountValid(0.111)); // class: invalid amount -- upper decimal place
        assertFalse(BankAccount.isAmountValid(50.0000001)); // class: invalid amount -- lower decimal place
        assertFalse(BankAccount.isAmountValid(50.001)); // class: invalid amount -- middle decimal place
        assertFalse(BankAccount.isAmountValid(50.111)); // class: invalid amount -- upper decimal place
        assertFalse(BankAccount.isAmountValid(1E10)); // class: invalid amount -- large scientific notation
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance(), 0.001); //class: valid withdraw
       
        BankAccount bankAccount2 = new BankAccount("c@d.com", 200);
        bankAccount2.withdraw(0.01);
        assertEquals(199.99, bankAccount2.getBalance(), 0.001); //class: lower boundary withdraw
        
        BankAccount bankAccount3 = new BankAccount("e@f.com", 200);
        bankAccount3.withdraw(199.99);
        assertEquals(0.01, bankAccount3.getBalance(), 0.001); //class: upper boundary withdraw
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); //class: overdraw -- above upper boundary
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-0.01)); //class: negative withdraw -- below lower boundary
        
        BankAccount bankAccount4 = new BankAccount("g@h.com", 100);
        assertThrows(IllegalArgumentException.class, () -> bankAccount4.withdraw(0.0000000000000001)); //class: invalid small withdraw -- lower invalid boundary
        assertThrows(IllegalArgumentException.class, () -> bankAccount4.withdraw(0.001)); //class: invalid small withdraw -- upper invalid boundary
        assertThrows(IllegalArgumentException.class, () -> bankAccount4.withdraw(0)); //class: non-negative invalid withdraw -- lower invalid boundary
        // 0 throws an error so meaningless withdraws can't clog up withdrawal logs
        assertThrows(InsufficientFundsException.class, () -> bankAccount4.withdraw(100.01)); //class: non-negative invalid withdraw -- upper invalid boundary

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
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("name.com", 100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@@b.com", 100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.c", 100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount(" @.com", 100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("name@place", 100));
        //check for invalid starting balance
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("name@place.com", -100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("name2@place.com", -0.01));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("john@smith.com", 0.001));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("email@address.com", 100.001));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("ab@cd.com", 0.0000005));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("jack@smith.com", -200.001));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("maria@smith.com", 0));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("valid@gmail.com", 1E10));
    }

}