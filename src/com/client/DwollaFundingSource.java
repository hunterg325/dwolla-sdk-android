package com.client;

/**
 * Created with IntelliJ IDEA.
 * User: nschulze
 * Date: 6/19/12
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class DwollaFundingSource
{
    private String name;
    private String type;
    private String id;
    private Boolean verified;
    private Boolean success;

    DwollaFundingSource()
    {
        this.name = "empty";
        this.type = "empty";
        this.id = "empty";
        this.verified = false;
    }

    DwollaFundingSource(Boolean success)
    {
        this.success = success;
    }

    DwollaFundingSource(String verified, String name, String type, String id)
    {
         if(verified.equals("true"))
         {
             this.verified = true;
         }
        else
         {
             this.verified = false;
         }
        this.name = name;
        this.type = type;
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    public String getId()
    {
        return id;
    }

    public Boolean verified()
    {
        return verified;
    }

    public Boolean success()
    {
        return success;
    }

    public Boolean equals(DwollaFundingSource source)
    {
        return (this.name.equals(source.name) && this.type.equals(source.type) && this.id.equals(source.id) && this.verified.equals(source.verified));
    }
}
