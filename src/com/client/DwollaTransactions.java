package com.client;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: nschulze
 * Date: 6/19/12
 * Time: 9:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class DwollaTransactions
{
    ArrayList<DwollaTransaction> transactions;
    Boolean success;

    public DwollaTransactions()
    {
        this.success = false;
        transactions = new ArrayList<DwollaTransaction>();
    }

    public void add(DwollaTransaction transaction)
    {
        success = true;
        transactions.add(transaction);
    }

    public DwollaTransaction getTransaction(int i)
    {
        return transactions.get(i);
    }

    public Boolean equals(DwollaTransactions transactions)
    {
        for(int i = 0; i < this.transactions.size(); i++)
        {
            if(!this.transactions.get(i).equals(transactions.transactions.get(i)))
            {
                return false;
            }
        }
        return true;
    }
}
