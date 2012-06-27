package com.client;

import junit.framework.Assert;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: nschulze
 * Date: 6/26/12
 * Time: 10:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class Main
{
    public static void main(String[] args)
    {
        DwollaAPI api = new DwollaAPI("test", "test");
        try
        {
            testSendMoney(api);
            testRequestMoney(api);
            testGetBalance(api);
            testGetContacts(api);
            testGetNearby(api);
            testGetFundingSources(api);
            testGetFundingSource(api);
            testGetAccountInfo(api);
            testGetBasicInfo(api);
            testRegisterUser(api);
            testGetTransactions(api);
            testGetTransaction(api);
            testGetTransactionStats(api);
        }
        catch(Throwable t)
        {

        }
    }

    public static void testSendMoney(DwollaAPI api) throws Throwable
    {
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");
        result.put("Response", "12345");

        api.setJSONResult(result);

        String balance = api.sendMoneyWithPIN("", "", "", "", "", "", "", "");
        Assert.assertTrue(balance.equals("12345"));
    }

    public static void testRequestMoney(DwollaAPI api) throws Throwable
    {
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");
        result.put("Response", "23456");

        api.setJSONResult(result);

        String balance = api.requestMoneyWithPIN("","","","","","");
        Assert.assertTrue(balance.equals("23456"));
    }

    public static void testGetBalance(DwollaAPI api) throws Throwable
    {
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");
        result.put("Response", "55.76");

        api.setJSONResult(result);

        String balance = api.getBalance();
        Assert.assertTrue(balance.equals("55.76"));
    }

    public static void testGetContacts(DwollaAPI api) throws Throwable
    {
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");
        result.put("Response", "[\n" +
                "        {\n" +
                "            \"Name\": \"Ben Facebook Test\",\n" +
                "            \"Id\": \"12345\",\n" +
                "            \"Type\": \"Facebook\",\n" +
                "            \"Image\": \"https://graph.facebook.com/12345/picture?type=square\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Name\": \"Ben Dwolla Test\",\n" +
                "            \"Id\": \"812-111-1111\",\n" +
                "            \"Type\": \"Dwolla\",\n" +
                "            \"Image\": \"https://www.dwolla.com/avatar.aspx?u=812-111-1111\"\n" +
                "        }\n" +
                "    ]");

        api.setJSONResult(result);

        DwollaContacts contacts = api.getContactsByName("","","");
        DwollaContacts contacts2 = new DwollaContacts();

        contacts2.add(new DwollaContact("Ben Facebook Test", "12345", "", "", "Facebook", "https://graph.facebook.com/12345/picture?type=square"));
        contacts2.add(new DwollaContact("Ben Dwolla Test", "812-111-1111", "", "", "Dwolla", "https://www.dwolla.com/avatar.aspx?u=812-111-1111"));

        Assert.assertTrue(contacts.equals(contacts2));
    }

    public static void testGetNearby(DwollaAPI api) throws Throwable
    {
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");
        result.put("Response", "[\n" +
                "        {\n" +
                "            \"Address\": \"111 West 5th St. Suite A\",\n" +
                "            \"City\": \"Des Moines\",\n" +
                "            \"Group\": \"812-111-2222\",\n" +
                "            \"Id\": \"812-111-1111\",\n" +
                "            \"Image\": \"https://www.dwolla.com/avatar.aspx?u=812-111-1111\",\n" +
                "            \"Latitude\": 41.5852257,\n" +
                "            \"Longitude\": -93.6245059,\n" +
                "            \"Name\": \"Test Spot 1\",\n" +
                "            \"PostalCode\": \"50309\",\n" +
                "            \"State\": \"IA\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Address\": \"111 West 5th St. Suite B\",\n" +
                "            \"City\": \"Des Moines\",\n" +
                "            \"Group\": \"812-111-1111\",\n" +
                "            \"Id\": \"812-111-2222\",\n" +
                "            \"Image\": \"https://www.dwolla.com/avatar.aspx?u=812-111-2222\",\n" +
                "            \"Latitude\": 41.5852257,\n" +
                "            \"Longitude\": -93.6245059,\n" +
                "            \"Name\": \"Test Spot 2\",\n" +
                "            \"PostalCode\": \"50309\",\n" +
                "            \"State\": \"IA\"\n" +
                "        }\n" +
                "    ]");

        api.setJSONResult(result);

        DwollaContacts contacts = api.getNearbyWithLatitude("","","","");
        DwollaContacts contacts2 = new DwollaContacts();

        contacts2.add(new DwollaContact("Test Spot 1", "812-111-1111", "111 West 5th St. Suite A", "Des Moines", "IA", "50309", "",
                "https://www.dwolla.com/avatar.aspx?u=812-111-1111", "812-111-2222", "41.5852257", "-93.6245059"));

        contacts2.add(new DwollaContact("Test Spot 2", "812-111-2222", "111 West 5th St. Suite B", "Des Moines", "IA", "50309", "",
                "https://www.dwolla.com/avatar.aspx?u=812-111-2222", "812-111-1111", "41.5852257", "-93.6245059"));

        Assert.assertTrue(contacts.equals(contacts2));
    }

    public static void testGetFundingSources(DwollaAPI api) throws Throwable
    {
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");
        result.put("Response", "[\n" +
                "        {\n" +
                "            \"Id\": \"TVmMwlKz1z6HmOK1np8NFA==\",\n" +
                "            \"Name\": \"Donations Collection Fund - Savings\",\n" +
                "            \"Type\": \"Savings\",\n" +
                "            \"Verified\": \"true\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Id\": \"TqmKw8Kz1z6HmOK1np8Npq==\",\n" +
                "            \"Name\": \"Donations Payout Account - Checking\",\n" +
                "            \"Type\": \"Checking\",\n" +
                "            \"Verified\": \"true\"\n" +
                "        }\n" +
                "    ]");

        api.setJSONResult(result);

        DwollaFundingSources sources = api.getFundingSources();
        DwollaFundingSources sources2 = new DwollaFundingSources();

        sources2.add(new DwollaFundingSource("true", "Donations Collection Fund - Savings", "Savings", "TVmMwlKz1z6HmOK1np8NFA=="));
        sources2.add(new DwollaFundingSource("true", "Donations Payout Account - Checking", "Checking", "TqmKw8Kz1z6HmOK1np8Npq=="));

        Assert.assertTrue(sources.equals(sources2));
    }

    public static void testGetFundingSource(DwollaAPI api) throws Throwable
    {
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");
        result.put("Response", "{\n" +
                "        \"Id\": \"TqmKw8Kz1z6HmOK1np8Npq==\",\n" +
                "        \"Name\": \"Donations Payout Account - Checking\",\n" +
                "        \"Type\": \"Checking\",\n" +
                "        \"Verified\": \"true\"\n" +
                "    }");

        api.setJSONResult(result);

        DwollaFundingSource source = api.getFundingSource("");
        DwollaFundingSource source2 = new DwollaFundingSource("true", "Donations Payout Account - Checking", "Checking", "TqmKw8Kz1z6HmOK1np8Npq==");

        Assert.assertTrue(source.equals(source2));
    }

    public static void testGetAccountInfo(DwollaAPI api) throws Throwable
    {
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");
        result.put("Response", "{\n" +
                "        \"City\": \"Des Moines\",\n" +
                "        \"Id\": \"\",\n" +
                "        \"Latitude\": 41.584546,\n" +
                "        \"Longitude\": -93.634167,\n" +
                "        \"Name\": \"Test User\",\n" +
                "        \"State\": \"IA\",\n" +
                "        \"Type\": \"Personal\"\n" +
                "    }");

        api.setJSONResult(result);

        DwollaUser user = api.getAccountInfo();
        DwollaUser user2 = new DwollaUser("true", "Test User", "41.584546", "-93.634167", "812-111-1111", "IA", "Personal", "Des Moines");

        Assert.assertTrue(user.equals(user2));
    }

    public static void testGetBasicInfo(DwollaAPI api) throws Throwable
    {
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");
        result.put("Response", "{\n" +
                "        \"Id\": \"812-111-1111\",\n" +
                "        \"Latitude\": 41.584546,\n" +
                "        \"Longitude\": -93.634167,\n" +
                "        \"Name\": \"Test User\"\n" +
                "    }");

        api.setJSONResult(result);

        DwollaUser user = api.getBasicInfoWithAccountID("");
        DwollaUser user2 = new DwollaUser("true", "Test User", "41.584546", "-93.634167", "812-111-1111", "", "", "");

        Assert.assertTrue(user.equals(user2));
    }

    public static void testRegisterUser(DwollaAPI api) throws Throwable
    {
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");
        result.put("Response", "{\n" +
                "        \"City\": \"Des Moines\",\n" +
                "        \"Id\": \"812-111-1111\",\n" +
                "        \"Latitude\": 41.584546,\n" +
                "        \"Longitude\": -93.634167,\n" +
                "        \"Name\": \"Test User\",\n" +
                "        \"State\": \"IA\",\n" +
                "        \"Type\": \"Personal\"\n" +
                "    }");

        api.setJSONResult(result);

        DwollaUser user = api.registerUserWithEmail("1","1","1","1","1","1","1","1","1","1","1","1","1","1","1",true);
        DwollaUser user2 = new DwollaUser("true", "Test User", "41.584546", "-93.634167", "812-111-1111", "IA", "Personal", "Des Moines");

        Assert.assertTrue(user.equals(user2));
    }

    public static void testGetTransactions(DwollaAPI api) throws Throwable
    {
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");
        result.put("Response", "[\n" +
                "        {\n" +
                "            \"Amount\": 1.25,\n" +
                "            \"Date\": \"8/29/2011 1:42:48 PM\",\n" +
                "            \"DestinationId\": \"812-111-1111\",\n" +
                "            \"DestinationName\": \"Test User\",\n" +
                "            \"Id\": 2,\n" +
                "            \"SourceId\": \"\",\n" +
                "            \"SourceName\": \"\",\n" +
                "            \"Type\": \"money_sent\",\n" +
                "            \"UserType\": \"Dwolla\",\n" +
                "            \"Status\": \"processed\",\n" +
                "            \"ClearingDate\": \"\",\n" +
                "            \"Notes\": \"\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"Amount\": 1.25,\n" +
                "            \"Date\": \"8/29/2011 1:17:35 PM\",\n" +
                "            \"DestinationId\": \"\",\n" +
                "            \"DestinationName\": \"\",\n" +
                "            \"Id\": 1,\n" +
                "            \"SourceId\": \"812-111-1111\",\n" +
                "            \"SourceName\": \"Test User\",\n" +
                "            \"Type\": \"money_received\",\n" +
                "            \"UserType\": \"Dwolla\",\n" +
                "            \"Status\": \"processed\",\n" +
                "            \"ClearingDate\": \"\",\n" +
                "            \"Notes\": \"Thank you for lunch!\"\n" +
                "        }\n" +
                "    ]");

        api.setJSONResult(result);

        DwollaTransactions transactions = api.getTransactionsSince("", "", "");
        DwollaTransactions trasactions2 = new DwollaTransactions();

        trasactions2.add(new DwollaTransaction("", "Dwolla", "processed", "1.25", "", "", "812-111-1111",
                "8/29/2011 1:42:48 PM", "money_sent", "2", "", ""));

        trasactions2.add(new DwollaTransaction("Thank you for lunch!", "Dwolla", "processed", "1.25", "", "", "812-111-1111",
                "8/29/2011 1:17:35 PM", "money_received", "1", "", ""));

        Assert.assertTrue(transactions.equals(trasactions2));
    }

    public static void testGetTransaction(DwollaAPI api) throws Throwable
    {
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");
        result.put("Response", "{\n" +
                "        \"Amount\": 1,\n" +
                "        \"Date\": \"8/31/2011 10:19:09 AM\",\n" +
                "        \"DestinationId\": \"812-111-1111\",\n" +
                "        \"DestinationName\": \"Bob\",\n" +
                "        \"Id\": 12345,\n" +
                "        \"SourceId\": \"812-111-2222\",\n" +
                "        \"SourceName\": \"Alice\",\n" +
                "        \"Type\": \"money_sent\",\n" +
                "        \"UserType\": \"Dwolla\",\n" +
                "        \"Status\": \"processed\",\n" +
                "        \"ClearingDate\": \"\",\n" +
                "        \"Notes\": \"Thank you for lunch!\"\n" +
                "    }");

        api.setJSONResult(result);

        DwollaTransaction transaction = api.getTransaction("id");
        DwollaTransaction trasaction2 = new DwollaTransaction("Thank you for lunch!", "Dwolla", "processed", "1", "", "Alice", "812-111-1111",
                "8/29/2011 1:17:35 PM", "money_sent", "1", "812-111-2222", "Bob");

        Assert.assertTrue(transaction.equals(trasaction2));
    }

    public static void testGetTransactionStats(DwollaAPI api) throws Throwable
    {
        JSONObject result = new JSONObject();

        result.put("Success", "true");
        result.put("Message", "Success");
        result.put("Response", "{\n" +
                "        \"TransactionsCount\": 5,\n" +
                "        \"TransactionsTotal\": 116.92\n" +
                "    }");

        api.setJSONResult(result);

        DwollaTransactionStats stats = api.getTransactionStats("","");
        DwollaTransactionStats stats2 = new DwollaTransactionStats("5", "116.92");

        Assert.assertTrue(stats.equals(stats2));
    }
}

