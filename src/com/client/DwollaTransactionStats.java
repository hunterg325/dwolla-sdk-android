package com.client;

import android.text.BoringLayout;

/**
 * Created with IntelliJ IDEA.
 * User: nschulze
 * Date: 6/19/12
 * Time: 9:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class DwollaTransactionStats
{
    private String total;
    private String count;
    private Boolean success;

    public DwollaTransactionStats(Boolean success)
    {
        this.success = false;
    }

    public DwollaTransactionStats(String total, String count)
    {
        this.total = total;
        this.count = count;
        this.success = true;
    }

    public Boolean getSuccess()
    {
        return success;
    }

    public String getTotal()
    {
        return total;
    }

    public String getCount()
    {
        return count;
    }

    public boolean equals(DwollaTransactionStats stats)
    {
        return (this.count.equals(stats.count) && this.total.equals(stats.total));
    }
}
