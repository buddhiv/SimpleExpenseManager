package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Buddhi on 12/6/2015.
 */
public class DatabaseAccess extends SQLiteOpenHelper {
//    Database Name - 130620E
    public static final String DB_NAME = "130620E";

    public DatabaseAccess(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        Queries for creating the tables
        String query1 = "CREATE TABLE Account ("
                + "Account_no TEXT PRIMARY KEY, Bank TEXT, "
                +"Holder TEXT, balance DOUBLE );";
        db.execSQL(query1);

        String query2 = "CREATE TABLE MyTransaction (" +
                "   transaction_date            TEXT     NOT NULL," +
                "   transaction_accountNo       TEXT    NOT NULL," +
                "   transaction_expenseType     INT     NOT NULL," +
                "   transaction_amount    REAL ," +
                "   PRIMARY KEY(transaction_date,transaction_accountNo,transaction_expenseType,transaction_amount)," +
                "   FOREIGN KEY(transaction_accountNo) REFERENCES Account(Account_no)" +
                ");";

        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Account");
        db.execSQL("DROP TABLE IF EXISTS MyTransaction");
        onCreate(db);
    }
}
