package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.ApplicationContext;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DatabaseAccess;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistenceAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistenceTransactionDAO;

/**
 * Created by Buddhi on 12/6/2015.
 */
public class PersistenceExpenceManager extends ExpenseManager{

    @Override
    public void setup() throws ExpenseManagerException {
        DatabaseAccess dbAccess = new DatabaseAccess(ApplicationContext.getAppContext());  // Database connecting object

        AccountDAO persistenceAccountDAO = new PersistenceAccountDAO(dbAccess);
        setAccountsDAO(persistenceAccountDAO);

        TransactionDAO persistantTransactionDAO = new PersistenceTransactionDAO(dbAccess);
        setTransactionsDAO(persistantTransactionDAO);
    }
}
