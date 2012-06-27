package com.client;

import android.test.ActivityInstrumentationTestCase2;
import junit.framework.Assert;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.client.MyActivityTest \
 * com.client.tests/android.test.InstrumentationTestRunner
 */
public class MyActivityTest extends ActivityInstrumentationTestCase2<MyActivity>
{

    DwollaAPI api;
    public MyActivityTest()
    {
        super("com.client", MyActivity.class);
    }

    public void testSendMoney() throws Throwable
    {
        api = new DwollaAPI("test","test");
        JSONObject result = new JSONObject();

        result.put("Response", "12345");
        result.put("Message", "Success");
        result.put("Success", "true");

        api.setJSONResult(result);

        String balance = api.sendMoneyWithPIN("", "", "", "", "", "", "", "");
        Assert.assertTrue(balance.equals("12345"));
    }

    public void testRequestMoney() throws Throwable
    {
        api = new DwollaAPI("test","test");
        JSONObject result = new JSONObject();

        result.put("Response", "23456");
        result.put("Message", "Success");
        result.put("Success", "true");

        api.setJSONResult(result);

        String balance = api.requestMoneyWithPIN("","","","","","");
        Assert.assertTrue(balance.equals("23456"));
    }

    public void testGetBalance() throws Throwable
    {
        api = new DwollaAPI("test","test");
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");
        result.put("Response", "55.76");

        api.setJSONResult(result);

        String balance = api.getBalance();
        Assert.assertTrue(balance.equals("55.76"));
    }

    public void testGetContacts() throws Throwable
    {
        api = new DwollaAPI("test","test");
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");

        JSONObject data = new JSONObject();
        data.put("Name", "Ben Facebook Test");
        data.put("Id", "12345");
        data.put("Type", "Facebook");
        data.put("Image", "https://graph.facebook.com/12345/picture?type=square");

        JSONObject data2 = new JSONObject();
        data2.put("Name", "Ben Dwolla Test");
        data2.put("Id", "812-111-1111");
        data2.put("Type", "Dwolla");
        data2.put("Image", "https://www.dwolla.com/avatar.aspx?u=812-111-1111");

        JSONArray array = new JSONArray();

        array.put(data);
        array.put(data2);

        result.put("Response", array);

        api.setJSONResult(result);

        DwollaContacts contacts = api.getContactsByName("","","");
        DwollaContacts contacts2 = new DwollaContacts();

        contacts2.add(new DwollaContact("Ben Facebook Test", "12345", "", "", "Facebook", "https://graph.facebook.com/12345/picture?type=square"));
        contacts2.add(new DwollaContact("Ben Dwolla Test", "812-111-1111", "", "", "Dwolla", "https://www.dwolla.com/avatar.aspx?u=812-111-1111"));

        Assert.assertTrue(contacts.equals(contacts2));
    }

    public void testGetNearby() throws Throwable
    {
        api = new DwollaAPI("test","test");
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");

        JSONObject data = new JSONObject();
        data.put("Name", "Test Spot 1");
        data.put("Id", "812-111-1111");
        data.put("Address", "111 West 5th St. Suite A");
        data.put("City", "Des Moines");
        data.put("State", "IA");
        data.put("Postal", "50309");
        data.put("Type", "");
        data.put("Image", "https://graph.facebook.com/12345/picture?type=square");
        data.put("Group", "812-111-2222");
        data.put("Latitude", 41.5852257);
        data.put("Longitude", -93.6245059);

        JSONObject data2 = new JSONObject();
        data2.put("Name", "Test Spot 2");
        data2.put("Id", "812-111-2222");
        data2.put("Address", "111 West 5th St. Suite b");
        data2.put("City", "Des Moines");
        data2.put("State", "IA");
        data2.put("Postal", "50309");
        data2.put("Type", "");
        data2.put("Image", "https://graph.facebook.com/12345/picture?type=square");
        data2.put("Group", "812-111-1111");
        data2.put("Latitude", 41.5852257);
        data2.put("Longitude", -93.6245059);

        JSONArray array = new JSONArray();

        array.put(data);
        array.put(data2);

        result.put("Response", array);

        api.setJSONResult(result);

        DwollaContacts contacts = api.getNearbyWithLatitude("","","","");
        DwollaContacts contacts2 = new DwollaContacts();

        contacts2.add(new DwollaContact("Test Spot 1", "812-111-1111", "111 West 5th St. Suite A", "Des Moines", "IA", "50309", "",
                "https://www.dwolla.com/avatar.aspx?u=812-111-1111", "812-111-2222", "41.5852257", "-93.6245059"));

        contacts2.add(new DwollaContact("Test Spot 2", "812-111-2222", "111 West 5th St. Suite B", "Des Moines", "IA", "50309", "",
                "https://www.dwolla.com/avatar.aspx?u=812-111-2222", "812-111-1111", "41.5852257", "-93.6245059"));

        Assert.assertTrue(contacts.equals(contacts2));
    }

    public void testGetFundingSources() throws Throwable
    {
        api = new DwollaAPI("test","test");
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");

        JSONObject data = new JSONObject();
        data.put("Name", "Donations Collection Fund - Savings");
        data.put("Id", "TVmMwlKz1z6HmOK1np8NFA==");
        data.put("Type", "Savings");
        data.put("Verified", true);

        JSONObject data2 = new JSONObject();
        data2.put("Name", "Donations Payout Account - Checking");
        data2.put("Id", "TqmKw8Kz1z6HmOK1np8Npq==");
        data2.put("Type", "Checking");
        data2.put("Verified", true);


        JSONArray array = new JSONArray();

        array.put(data2);
        array.put(data);

        result.put("Response", array);

        api.setJSONResult(result);

        DwollaFundingSources sources = api.getFundingSources();
        DwollaFundingSources sources2 = new DwollaFundingSources();

        sources2.add(new DwollaFundingSource("true", "Donations Collection Fund - Savings", "Savings", "TVmMwlKz1z6HmOK1np8NFA=="));
        sources2.add(new DwollaFundingSource("true", "Donations Payout Account - Checking", "Checking", "TqmKw8Kz1z6HmOK1np8Npq=="));

        Assert.assertTrue(sources.equals(sources2));
    }

    public void testGetFundingSource() throws Throwable
    {
        api = new DwollaAPI("test","test");
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");

        JSONObject data2 = new JSONObject();
        data2.put("Name", "Donations Payout Account - Checking");
        data2.put("Id", "TqmKw8Kz1z6HmOK1np8Npq==");
        data2.put("Type", "Checking");
        data2.put("Verified", true);

        result.put("Response", data2);

        api.setJSONResult(result);

        DwollaFundingSource source = api.getFundingSource("");
        DwollaFundingSource source2 = new DwollaFundingSource("true", "Donations Payout Account - Checking", "Checking", "TqmKw8Kz1z6HmOK1np8Npq==");

        Assert.assertTrue(source.equals(source2));
    }

    public void testGetAccountInfo() throws Throwable
    {
        api = new DwollaAPI("test","test");
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");

        JSONObject data = new JSONObject();
        data.put("Name", "Test User");
        data.put("Id", "812-111-1111");
        data.put("City", "Des Moines");
        data.put("State", "IA");
        data.put("Type", "Personal");
        data.put("Latitude", 41.5852257);
        data.put("Longitude", -93.6245059);

        result.put("Response", data);

        api.setJSONResult(result);

        DwollaUser user = api.getAccountInfo();
        DwollaUser user2 = new DwollaUser("true", "Test User", "41.584546", "-93.634167", "812-111-1111", "IA", "Personal", "Des Moines");

        Assert.assertTrue(user.equals(user2));
    }

    public void testGetBasicInfo() throws Throwable
    {
        api = new DwollaAPI("test","test");
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");

        JSONObject data = new JSONObject();
        data.put("Name", "Test User");
        data.put("Latitude", 41.584546);
        data.put("Longitude", -93.634167);
        data.put("Id", "812-111-1111");
        result.put("Response", data);

        api.setJSONResult(result);

        DwollaUser user = api.getBasicInfoWithAccountID("");
        DwollaUser user2 = new DwollaUser("true", "Test User", "41.584546", "-93.634167", "812-111-1111", "", "", "");

        Assert.assertTrue(user.equals(user2));
    }

    public void testRegisterUser() throws Throwable
    {
        api = new DwollaAPI("test","test");
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");

        JSONObject data = new JSONObject();

        data.put("Name", "Test User");
        data.put("Latitude", 41.584546);
        data.put("Longitude", -93.634167);
        data.put("State", "IA");
        data.put("City", "Des Moines");
        data.put("Type", "Personal");
        data.put("Id", "812-111-1111");

        result.put("Response", data);


        api.setJSONResult(result);

        DwollaUser user = api.registerUserWithEmail("1","1","1","1","1","1","1","1","1","1","1","1","1","1","1",true);
        DwollaUser user2 = new DwollaUser("true", "Test User", "41.584546", "-93.634167", "812-111-1111", "IA", "Personal", "Des Moines");

        Assert.assertTrue(user.equals(user2));
    }

    public void testGetTransactions() throws Throwable
    {
        api = new DwollaAPI("test","test");
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");

        JSONObject data = new JSONObject();
        data.put("Notes", "");
        data.put("UserType", "Dwolla");
        data.put("Amount", "1.25");
        data.put("Date", "8/29/2011 1:42:48 PM");
        data.put("DestinationName", "");
        data.put("DestinationId", "812-111-1111");
        data.put("Id", "2");
        data.put("SourceId", "");
        data.put("SourceName", "");
        data.put("Type", "money_sent");
        data.put("Status", "processed");
        data.put("ClearingDate", "");

        JSONObject data2 = new JSONObject();
        data2.put("Notes", "");
        data2.put("UserType", "Dwolla");
        data2.put("Amount", "1.25");
        data2.put("Date", "8/29/2011 1:17:35 PM");
        data2.put("DestinationName", "");
        data2.put("DestinationId", "812-111-1111");
        data2.put("Id", "1");
        data2.put("SourceId", "");
        data2.put("SourceName", "");
        data2.put("Type", "money_received");
        data2.put("Status", "processed");
        data2.put("ClearingDate", "");


        JSONArray array = new JSONArray();

        array.put(data);
        array.put(data2);

        result.put("Response", array);
        api.setJSONResult(result);

        DwollaTransactions transactions = api.getTransactionsSince("", "", "");
        DwollaTransactions trasactions2 = new DwollaTransactions();

        trasactions2.add(new DwollaTransaction("", "Dwolla", "processed", "1.25", "", "", "812-111-1111",
                "8/29/2011 1:42:48 PM", "money_sent", "2", "", ""));

        trasactions2.add(new DwollaTransaction("Thank you for lunch!", "Dwolla", "processed", "1.25", "", "", "812-111-1111",
                "8/29/2011 1:17:35 PM", "money_received", "1", "", ""));

        Assert.assertTrue(transactions.equals(trasactions2));
    }

    public void testGetTransaction() throws Throwable
    {
        api = new DwollaAPI("test","test");
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");

        JSONObject data2 = new JSONObject();
        data2.put("Notes", "Thank you for lunch!");
        data2.put("UserType", "Dwolla");
        data2.put("Amount", "1.25");
        data2.put("Date", "8/29/2011 1:17:35 PM");
        data2.put("DestinationName", "Bob");
        data2.put("DestinationId", "812-111-1111");
        data2.put("Id", "1");
        data2.put("SourceId", "812-111-222");
        data2.put("SourceName", "Alice");
        data2.put("Type", "money_sent");
        data2.put("Status", "processed");
        data2.put("ClearingDate", "");

        JSONArray array = new JSONArray();

        array.put(data2);

        result.put("Response", data2);

        api.setJSONResult(result);

        DwollaTransaction transaction = api.getTransaction("id");
        DwollaTransaction trasaction2 = new DwollaTransaction("Thank you for lunch!", "Dwolla", "processed", "1.25", "", "Alice", "812-111-1111",
            "8/29/2011 1:17:35 PM", "money_sent", "1", "812-111-2222", "Bob");

        Assert.assertTrue(transaction.equals(trasaction2));
    }

    public void testGetTransactionStats() throws Throwable
    {
        api = new DwollaAPI("test","test");
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");

        JSONObject data = new JSONObject();
        data.put("TransactionsTotal", 116.92);
        data.put("TransactionsCount", 5);

        result.put("Response", data);

        api.setJSONResult(result);

        DwollaTransactionStats stats = api.getTransactionStats("","");
        DwollaTransactionStats stats2 = new DwollaTransactionStats("116.92", "5");

        Assert.assertTrue(stats.equals(stats2));
    }
}
