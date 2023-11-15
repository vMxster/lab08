package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.unibo.bank.impl.SimpleBankAccount.*;
import static it.unibo.bank.impl.StrictBankAccount.TRANSACTION_FEE;

public class TestStrictBankAccount {

    private final static int INITIAL_AMOUNT = 100;

    // 1. Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    // 2. Test the initial state of the StrictBankAccount
    @Test
    public void testInitialization() {
        Assertions.assertEquals(0.0, bankAccount.getBalance());
        Assertions.assertEquals(0, bankAccount.getTransactionsCount());
        Assertions.assertEquals(mRossi, bankAccount.getAccountHolder());
    }


    // 3. Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
    @Test
    public void testManagementFees() {
        bankAccount.deposit(bankAccount.getAccountHolder().getUserID(), INITIAL_AMOUNT);
        Assertions.assertEquals(INITIAL_AMOUNT, bankAccount.getBalance());
        bankAccount.chargeManagementFees(bankAccount.getAccountHolder().getUserID());
        Assertions.assertEquals(INITIAL_AMOUNT - MANAGEMENT_FEE - TRANSACTION_FEE, bankAccount.getBalance());
    }

    // 4. Test the withdraw of a negative value
    @Test
    public void testNegativeWithdraw() {
        try {
            bankAccount.withdraw(bankAccount.getAccountHolder().getUserID(), -INITIAL_AMOUNT);
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot withdraw a negative amount", e.getMessage());
        }
    }

    // 5. Test withdrawing more money than it is in the account
    @Test
    public void testWithdrawingTooMuch() {
        try {
            bankAccount.withdraw(bankAccount.getAccountHolder().getUserID(), INITIAL_AMOUNT);
        } catch (IllegalArgumentException e) {
            assertEquals("Insufficient balance", e.getMessage());
        }
    }
}
