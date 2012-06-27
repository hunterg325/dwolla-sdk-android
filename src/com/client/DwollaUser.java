package com.client;

/**
 * Created with IntelliJ IDEA.
 * User: nschulze
 * Date: 6/19/12
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class DwollaUser
{
    private Boolean success;
    private String name;
    private String latitude;
    private String longitude;
    private String id;
    private String state;
    private String type;
    private String city;

    DwollaUser(Boolean success)
    {
        this.success = false;
    }
    DwollaUser(String success, String name, String latitude, String longitude, String id, String state, String type, String city)
    {
        if(success.equals("true"))
        {
            this.success = true;
        }
        else
        {
            this.success = false;
        }
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.state = state;
        this.type = type;
        this.city = city;
    }

    public String getName()
    {
       return name;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public String getId()
    {
        return id;
    }

    public String getType()
    {
        return type;
    }

    public String getCity()
    {
        return city;
    }

    public String getState()
    {
        return state;
    }

    public Boolean getSuccess()
    {
        return success;
    }

    public Boolean equals(DwollaUser user)
    {
        return (this.name.equals(user.name) && this.id.equals(user.id) && this.type.equals(user.type));
    }
}
