package com.client;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: nschulze
 * Date: 6/19/12
 * Time: 9:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class DwollaContacts
{
    ArrayList<DwollaContact> contacts;
    Boolean success;

    public DwollaContacts()
    {
        this.success = false;
        this.contacts = new ArrayList<DwollaContact>();
    }

    public void add(DwollaContact contact)
    {
         this.success = true;
         contacts.add(contact);
    }

    public DwollaContact getContact(int i)
    {
        return contacts.get(i);
    }

    public Boolean equals(DwollaContacts contacts)
    {
        for(int i = 0; i < this.contacts.size(); i++)
        {
            if(!this.contacts.get(i).equals(contacts.getContact(i)))
            {
                return false;
            }
        }
            return true;
    }
}
