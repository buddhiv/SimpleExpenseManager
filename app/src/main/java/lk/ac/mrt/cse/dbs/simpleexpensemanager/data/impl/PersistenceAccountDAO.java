package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by Buddhi on 12/6/2015.
 */
public class PersistenceAccountDAO implements AccountDAO {
    public static final String TABLE_NAME = "Account";
    public static final String ACCOUNT_NO = "Account_no";
    public static final String BANK = "Bank";
    public static final String ACCOUNT_HOLDER = "Holder";
    public static final String BALANCE = "balance";

    private SQLiteOpenHelper db = null;

    public PersistenceAccountDAO(SQLiteOpenHelper db) {
        this.db = db;

    }

    //        Get account numbers
    @Override
    public List<String> getAccountNumbersList() {
        List<String> accountList = new ArrayList<String>();
        String selectQuery = "SELECT " + ACCOUNT_NO + " FROM " + TABLE_NAME;
        SQLiteDatabase databaseReadableObject = db.getReadableDatabase();
        Cursor cursor = databaseReadableObject.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                accountList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        databaseReadableObject.close();
        return accountList;

    }

//    Get accounts list
    @Override
    public List<Account> getAccountsList() {
        List<Account> accountList = new ArrayList<Account>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase databaseReadableObject = db.getReadableDatabase();
        Cursor cursor = databaseReadableObject.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String num = cursor.getString(0);
                String bank = cursor.getString(1);
                String name = cursor.getString(2);
                double balance = Double.parseDouble(cursor.getString(3));
                Account account = new Account(num, bank, name, balance);

                accountList.add(account);
            } while (cursor.moveToNext());
        }
        databaseReadableObject.close();
        return accountList;
    }

//    Get account details
    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Account account = null;
        String selectQuery = "SELECT " + ACCOUNT_NO + " FROM " + TABLE_NAME + " WHERE " + ACCOUNT_NO + " = " + accountNo;
        SQLiteDatabase databaseReadableObject = db.getReadableDatabase();
        Cursor cursor = databaseReadableObject.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            account = new Account(cursor.getColumnName(0), cursor.getColumnName(1), cursor.getColumnName(2), Double.parseDouble(cursor.getColumnName(3)));
        }
        if (account == null) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
        databaseReadableObject.close();
        return account;
    }

//    Add account details
    @Override
    public void addAccount(Account account) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NO, account.getAccountNo());
        values.put(BANK, account.getBankName());
        values.put(ACCOUNT_HOLDER, account.getAccountHolderName());
        values.put(BALANCE, account.getBalance());

        database.insert(TABLE_NAME, null, values);
        database.close();
    }

//    Remove account details
    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase database = db.getWritableDatabase();
        int result = database.delete(TABLE_NAME, ACCOUNT_NO + " = ?", new String[]{accountNo});
        if (result == 0) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
        database.close();
    }

//    Update account balance
    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        Account account = null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + ACCOUNT_NO + " = '" + accountNo + "'";
        SQLiteDatabase databaseWritableObject = db.getWritableDatabase();
        Cursor cursor = databaseWritableObject.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            account = new Account(cursor.getString(0), cursor.getString(1), cursor.getString(2), Double.parseDouble(cursor.getString(3)));
        }

        switch (expenseType) {
            case EXPENSE:
                account.setBalance(account.getBalance() - amount);
                break;
            case INCOME:
                account.setBalance(account.getBalance() + amount);
                break;
        }

        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NO, accountNo);
        values.put(BALANCE, account.getBalance());

        databaseWritableObject.update(TABLE_NAME, values, ACCOUNT_NO + " = ?", new String[]{String.valueOf(accountNo)});
        databaseWritableObject.close();
    }
}
