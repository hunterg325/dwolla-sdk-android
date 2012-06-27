package com.client;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: nschulze
 * Date: 6/19/12
 * Time: 9:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class DwollaFundingSources
{
    ArrayList<DwollaFundingSource> sources;
    Boolean success;

    public DwollaFundingSources()
    {
        this.success = false;
        this.sources = new ArrayList<DwollaFundingSource>();
    }

    public void add(DwollaFundingSource source)
    {
        this.success = true;
        sources.add(source);
    }

    public DwollaFundingSource getFundingSource(int i)
    {
        return sources.get(i);
    }

    public Boolean equals(DwollaFundingSources sources)
    {
        for(int i = 0; i < this.sources.size(); i++)
        {
            if(this.sources.get(i).equals(sources.sources.get(i)))
            {
                return false;
            }
        }
        return true;
    }
}
