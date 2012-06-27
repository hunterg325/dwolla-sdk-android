package com.client;

/**
 * Created with IntelliJ IDEA.
 * User: nschulze
 * Date: 6/19/12
 * Time: 9:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class DwollaContact
{
    private String name;
    private String id;
    private String city;
    private String state;
    private String type;
    private String image;
    private String postal;
    private String address;
    private String latitude;
    private String longitude;
    private String group;
    private Boolean isBasic;
    private Boolean success;

    public DwollaContact(Boolean success)
    {
        this.success = success;
    }

    public DwollaContact(String name, String id, String city, String state, String type, String image)
    {
        this.name = name;
        this.id = id;
        this.city = city;
        this.state = state;
        this.type = type;
        this.image = image;
        this.isBasic = true;
    }

    public DwollaContact(String name, String id, String address, String city, String state, String postal,
                         String type, String image, String group, String latitude, String longitude)
    {
        this.name = name;
        this.id = id;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postal = postal;
        this.type = type;
        this.image = image;
        this.group = group;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isBasic = false;
    }

    public String getName()
    {
        return name;
    }

    public String getId()
    {
        return id;
    }

    public String getCity()
    {
        return city;
    }

    public String getState()
    {
        return state;
    }

    public String getType()
    {
        return type;
    }

    public String getImage()
    {
        return image;
    }

    public String getPostal()
    {
        if(isBasic)
        {
            return "empty";
        }
        return postal;
    }

    public String getAddress()
    {
        if(isBasic)
        {
            return "empty";
        }
        return address;
    }

    public String getLatitude()
    {
        if(isBasic)
        {
            return "empty";
        }
        return latitude;
    }

    public String getLongitude()
    {
        if(isBasic)
        {
            return "empty";
        }
        return longitude;
    }

    public String getGroup()
    {
        if(isBasic)
        {
            return "empty";
        }
        return group;
    }

    public Boolean isBasic()
    {
        return isBasic;
    }

    public Boolean success()
    {
        return success;
    }

    public Boolean equals(DwollaContact contact)
    {
        return (this.name.equals(contact.name) && this.id.equals(contact.id) && this.type.equals(contact.type));
    }
}
