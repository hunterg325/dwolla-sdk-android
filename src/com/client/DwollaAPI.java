package com.client;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import java.util.Iterator;
import org.apache.http.entity.StringEntity;

/**
 * Created with IntelliJ IDEA.
 * User: nschulze
 * Date: 6/18/12
 * Time: 5:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class DwollaAPI
{
    private String key;
    private String secret;
    private String token;
    private Boolean test;
    public String result;
    public JSONObject jsonResult;
    public static final String baseURL= "https://www.dwolla.com/oauth/rest";
    /**
     * checks to see if the user has a valid access token
     *
     * @return YES if a valid access token is present, false otherwise
     **/

 public DwollaAPI(String key, String secret)
 {
    test = false;
    if(key.equals("test") && secret.equals("test"))
    {
        test = true;
    }
    this.key = key;
    this.secret = secret;
    this.token = "";
 }
 public boolean hasToken()
 {
     if(this.token.equals(""))
     {
         return false;
     }
     else
     {
         return true;
     }
 }

/**
 * gets the stored access token
 *
 * @return a string representing the access token
 **/
 public String getAccessToken()
 {
     return token;
 }

/**
 * sets the access token
 *
 * @param token: the string representing the token
 **/
 public void setAccessToken(String token)
 {
     this.token = token;
 }

/**
 * clears the access token
 **/
 public void clearAccessToken()
 {
     this.token = null;
 }

/**
 * sends money to the indicated user
 *
 * @param pin: the pin of the user sending the money (required)
 * @param destinationID: the id of the user receiving the money (required)
 * @param type: the type of account receiving the money, may be null
 * @param amount: the amount of money to be sent (required)
 * @param facilitatorAmount: the amount of money the facilitator gets from the transaction
 *                           may be null, can't exceed 25% of the amount
 * @param assumeCosts: values accepted are either YES or NO, indicates if the user wants to
 *                    assume the costs of the transaction, may be null
 * @param notes: notes to be delivered with the transaction, may be null
 * @param fundingSourceID: id of the funding source funds will be sent from, may be null
 *
 * @return string representing the transaction id
 **/
 public String sendMoneyWithPIN(String pin, String destinationID, String type, String amount, String facilitatorAmount,
                         String assumeCosts, String notes, String fundingSourceID)
 {
     JSONObject req = new JSONObject();

     if (!this.hasToken())
     {
         //throw error
     }
     String token = this.getAccessToken();

     String url = baseURL + "/transactions/send?oauth_token=" + token;

     if(pin == null || pin.equals(""))
     {
         //throw error
     }
     if (destinationID == null || destinationID.equals(""))
     {
         //throw error 
     }
     if (amount == null || amount.equals(""))
     {
         //throw error 
     }

     try
     {
         req.put("pin", pin);
         req.put("destinationId", destinationID);
         req.put("amount", amount);

         if (type != null && !type.equals(""))
         {
             req.put("destinationType", type);
         }
         if (facilitatorAmount != null && !facilitatorAmount.equals(""))
         {
             req.put("facilitatorAmount", facilitatorAmount);
         }
         if (assumeCosts != null && !assumeCosts.equals(""))
         {
             req.put("assumeCosts", assumeCosts);
         }
         if (notes != null && !notes.equals(""))
         {
             req.put("notes", notes);
         }
         if (fundingSourceID != null && !fundingSourceID.equals(""))
         {
             req.put("fundsSource", fundingSourceID);
         }
     }
     catch(JSONException j)
     {

     }

     JSONObject json = this.httpPost(req, url);
     try
     {
        return (String) json.get("Response");
     }
     catch (JSONException j)
     {

     }
     return "Error";
  }

/**
 * requests money from the indicated user
 *
 * @param pin: the pin of the user requesting the money (required)
 * @param sourceID: the id of the user money is being requested from (required)
 * @param sourceType: the type of account money is being requested from, may be null
 * @param amount: the amount of money to be sent (required)
 * @param facilitatorAmount: the amount of money the facilitator gets from the transaction
 *                           may be null, can't exceed 25% of the amount
 * @param notes: notes to be delivered with the transaction, may be null
 *
 * @return string representing the request id
 **/
 String requestMoneyWithPIN(String pin, String sourceID,String sourceType,
                            String amount, String facilitatorAmount, String notes)
 {
     JSONObject req = new JSONObject();

     if (!this.hasToken())
     {
         //throw error
     }
     String token = this.getAccessToken();

     String url = baseURL + "/transactions/request?oauth_token=" + token;

     if(pin == null || pin.equals(""))
     {
         //throw error
     }
     if (sourceID == null || sourceID.equals(""))
     {
         //throw error
     }
     if (amount == null || amount.equals(""))
     {
         //throw error
     }

     try
     {
         req.put("pin", pin);
         req.put("sourceId", sourceID);
         req.put("amount", amount);

         if (sourceType != null && !sourceType.equals(""))
         {
             req.put("sourceType", sourceType);
         }
         if (facilitatorAmount != null && !facilitatorAmount.equals(""))
         {
             req.put("facilitatorAmount", facilitatorAmount);
         }
         if (notes != null && !notes.equals(""))
         {
             req.put("notes", notes);
         }
     }
     catch(JSONException j)
     {

     }

     JSONObject json = this.httpPost(req, url);


    try
    {
        return (String) json.get("Response");
    }
    catch (JSONException j)
    {
        return "";
    }
 }

/**
 * shows the balance of the current user
 *
 * @return JSON representation of the request see: https://www.dwolla.com/developers/endpoints/balance/account
 **/
  JSONObject getJSONBalance()
  {
      if (!this.hasToken())
      {
          //throw error
      }

      String url = baseURL + "/balance?oauth_token=" + this.getAccessToken();

      return this.httpGet(url);
  }

/**
 * shows the balance of the current user
 *
 * @return string representation of the balance
 **/
 String getBalance()
 {
     JSONObject response = getJSONBalance();
     try
     {
         return (String) response.get("Response");
     }
     catch(JSONException j)
     {
         return "";
     }
 }

/**
 * gets users contacts
 *
 * @param name: search term to search the contacts, may be null
 * @param types: the types of account to be returned, may be null
 * @param limit: the number of contacts to be returned
 *
 * @return JSON representation of the request see: https://www.dwolla.com/developers/endpoints/contacts/user
 **/
 JSONObject getJSONContactsByName(String name, String types, String limit)
 {
     if (!this.hasToken())
     {
         //throw error
     }
     String url = baseURL + "/contacts?";

     if (name != null && !name.equals(""))
     {
         url += "search=" + name + "&";
     }
     if (types != null && !types.equals(""))
     {
         url += "types=" + types + "&";
     }
     if (limit != null && !limit.equals(""))
     {
         url += "limit=" + limit + "&";
     }
     url += "oauth_token=" + this.getAccessToken();

     return httpGet(url);
 }

/**
 * gets users contacts
 *
 * @param name: search term to search the contacts, may be null
 * @param types: the types of account to be returned, may be null
 * @param limit: the number of contacts to be returned
 *
 * @return a DwollaContacts object containing the results of the request
 **/
 DwollaContacts getContactsByName(String name, String types, String limit)
 {
     JSONObject response = getJSONContactsByName(name, types, limit);
     DwollaContacts dwollaContacts = new DwollaContacts();

     try
     {
        JSONArray contacts = response.getJSONArray("Response");
        for(int i = 0; i < contacts.length(); i++)
        {
           DwollaContact contact = this.generateBasicContact(contacts.getJSONObject(i));
           dwollaContacts.add(contact);
        }
     }
     catch(JSONException j)
     {

     }

     return dwollaContacts;
 }

/**
 * gets nearby contacts
 *
 * @param latitude: the latitude to search from
 * @param longitude: the longitude to search from
 * @param limit: the number of contacts to be returned
 * @param range: the distance a contact may be from the latitude and longitude
 *
 * @return JSON representation of the request see: https://www.dwolla.com/developers/endpoints/contacts/nearby
 **/
 JSONObject getJSONNearbyWithLatitude(String latitude, String longitude, String limit, String range)
 {
     if (key == null || secret == null || key.equals("") || secret.equals(""))
     {
         //error
     }

     String url = baseURL + "/contacts/nearby?client_id=" + key + "&client_secret=" + secret;

     if(latitude == null || latitude.equals("") || longitude == null || longitude.equals(""))
     {
         //error
     }
     url += "&latitude=" + latitude + "&longitude=" + longitude;

     if (range != null && !range.equals(""))
     {
         url += "&range=" + range;
     }
     if (limit != null && !limit.equals(""))
     {
         url += "&limit=" + limit;
     }
     
     return this.httpGet(url);
 }

/**
 * gets nearby contacts
 *
 * @param latitude: the latitude to search from
 * @param longitude: the longitude to search from
 * @param limit: the number of contacts to be returned
 * @param range: the distance a contact may be from the latitude and longitude
 *
 * @return a DwollaContacts object containing the results of the request
 **/
 DwollaContacts getNearbyWithLatitude(String latitude, String longitude, String limit, String range)
 {
     JSONObject response = getJSONNearbyWithLatitude(latitude, longitude, limit, range);
     DwollaContacts dwollaContacts = new DwollaContacts();

     try
     {
         JSONArray contacts = response.getJSONArray("Response");
         for(int i = 0; i < contacts.length(); i++)
         {
             DwollaContact contact = this.generateContact(contacts.getJSONObject(i));
             dwollaContacts.add(contact);
         }
     }
     catch(JSONException j)
     {

     }

     return dwollaContacts;
 }

/**
 * gets the funding sources of the current user
 *
 * @return JSON representation of the request see https://www.dwolla.com/developers/endpoints/fundingsources/list
 **/
    JSONObject getJSONFundingSources()
    {
        if (!this.hasToken())
        {
            //error
        }

        String url = baseURL + "/fundingsources?oauth_token=" + getAccessToken();

        return  httpGet(url);
    }

/**
 * gets the funding sources of the current user
 *
 * @return a DwollaFundingSources object containing the results of the request
 **/
    DwollaFundingSources getFundingSources()
    {
        JSONObject response = getJSONFundingSources();
        DwollaFundingSources dwollaSources = new DwollaFundingSources();

        try
        {
            JSONArray sources = response.getJSONArray("Response");
            for(int i = 0; i < sources.length(); i++)
            {
                DwollaFundingSource source = this.generateSource(sources.getJSONObject(i));
                dwollaSources.add(source);
            }
        }
        catch(JSONException j)
        {

        }

        return dwollaSources;
    }

/**
 * gets the funding source details of the provided funding source
 *
 * @param sourceID: a string representation of the id of the funding source
 *
 * @return JSON representation of the request see https://www.dwolla.com/developers/endpoints/fundingsources/details
 **/
    JSONObject getJSONFundingSource(String sourceID)
    {
        if (!this.hasToken())
        {
           //error
        }

        if (sourceID.equals("") || sourceID == null)
        {
            //error
        }

        String encodedID = sourceID;
        try
        {
        encodedID = URLEncoder.encode(sourceID, "utf-8");
        }
        catch(UnsupportedEncodingException e)
        {

        }
        String url = baseURL + "/fundingsources?fundingid=" + encodedID + "&oauth_token=" + getAccessToken();

        return httpGet(url);
    }

/**
 * gets the funding source details of the provided funding source
 *
 * @param sourceID: a string representation of the id of the funding source
 *
 * @return a DwollaFundingSource object containing the result of the request
 **/
    DwollaFundingSource getFundingSource(String sourceID)
    {
        JSONObject response = getJSONFundingSource(sourceID);

        try
        {
        return this.generateIndividualSource(response.getJSONObject("Response"));
        }
        catch (JSONException j)
        {

        }
        return new DwollaFundingSource(false);
    }

/**
 * gets the account information of the user
 *
 * @return JSON representation of the request see https://www.dwolla.com/developers/endpoints/users/accountinformation
 **/
    JSONObject getJSONAccountInfo()
    {
        if (this.hasToken())
        {
            //error
        }

        String url = baseURL + "/users?oauth_token=" +getAccessToken();

        return httpGet(url);
    }

/**
 * gets the account information of the user
 *
 * @return a DwollaUser object containing the result of the request
 **/
    DwollaUser getAccountInfo()
    {
        JSONObject response = getJSONAccountInfo();

        try
        {
         return this.generateDwollaUser(response.getJSONObject("Response"));
        }
        catch(JSONException j)
        {

        }
        return new DwollaUser(false);
    }

/**
 * gets the account information of the user with the given id
 *
 * @param accountID: a string representation of the id of the account
 *
 * @return JSON representation of the request see https://www.dwolla.com/developers/endpoints/users/basicinformation
 **/
    JSONObject getJSONBasicInfoWithAccountID(String accountID)
    {
        if (key == null || secret == null || key.equals("") || secret.equals(""))
        {
           //error
        }
        if (accountID == null || accountID.equals(""))
        {
            //error
        }

        String url = baseURL + "/users/" + accountID + "?client_id=" + key + "&client_secret=" + secret;

        return  httpGet(url);
    }

/**
 * gets the account information of the user with the given id
 *
 * @param accountID: a string representation of the id of the account
 *
 * @return a DwollaUser object containing the result of the request
 **/
    DwollaUser getBasicInfoWithAccountID(String accountID)
    {
        JSONObject response = getJSONBasicInfoWithAccountID(accountID);

        try
        {
        return this.generateBasicDwollaUser(response.getJSONObject("Response"));
        }
        catch(JSONException j)
        {

        }
        return new DwollaUser(false);
    }

/**
 * registers a new user
 *
 * @param email: the email of the new user (required)
 * @param password: the password of the new user (required)
 * @param pin: the pin of the new user (required)
 * @param first: the first name of the new user (required)
 * @param last:  the last name of the new user (required)n
 * @param address:  the street address of the new user (required) 
 * @param address2: the secondary street address of the new user, may be null
 * @param city:  the city of the new user (required)
 * @param state: the state of the new user (required) -> uses two character identifier ex: IA == Iowa
 * @param zip:  the zip code of the new user (required)
 * @param phone: the phone number of the new user (required) -> formatted like **********
 * @param birthDate: the birthday of the new user (required) -> formatted like **-**-**** month-day-year
 * @param type:  the account type of the new user, may be null
 * @param organization:  the organization of the new user (required for commercial and nonprofit) 
 * @param ein: the ein of the new user (required for commercial and nonprofit)
 * @param accept: if the user accepts the terms, must be YES
 *
 * @return a DwollaUser object containing the result of the request
 **/
    DwollaUser registerUserWithEmail(String email, String password, String pin, String first, String last,
                                     String address, String address2, String city, String state, String zip,
                                     String phone, String birthDate, String type, String organization,
                                     String ein, Boolean accept)
    {


        if (key == null || secret == null)
        {
            //error
        }

        String acceptTerms = "false";

        String url = baseURL + "/register/?client_id=" + key + "&client_secret=" + secret;

        if(email == null || password == null || pin == null || first == null || last == null || address == null ||
           city == null || state == null || zip == null || phone == null || birthDate == null || email.equals("") ||
           password.equals("") || pin.equals("") || first.equals("") || last.equals("") || address.equals("") ||
           city.equals("") || state.equals("") || zip.equals("") || phone.equals("") ||birthDate.equals(""))
        {
            //error
        }

        JSONObject json = new JSONObject();

        try
        {
            json.put("email", email);
            json.put("password", password);
            json.put("pin", pin);
            json.put("firstName", first);
            json.put("lastName", last);
            json.put("address", address);
            json.put("city", city);
            json.put("state", state);
            json.put("zip", zip);
            json.put("phone", phone);
            json.put("dateOfBirth", birthDate);

        if(type != null && !type.equals("") && !type.equals("Personal") &&
        !type.equals("Commercial") && !type.equals("NonProfit"))
        {
            //error
        }
        if((type.equals("Commercial") || type.equals("NonProfit")) &&
        (organization == null || organization.equals("") ||
        ein == null || ein.equals("")))
        {
            //error
        }
        if (!accept)
        {
           //error
        }
        else
        {
            acceptTerms = "true";
        }

            json.put("acceptTerms", acceptTerms);


            if (type != null  && !type.equals(""))
        {
            json.put("type", type);
        }
        if (address2 != null  && !address2.equals(""))
        {
            json.put("address2", address2);
        }
        if (organization != null  && !organization.equals(""))
        {
            json.put("organization", organization);
        }
        if (ein != null  && !ein.equals(""))
        {
            json.put("ein", ein);
        }
        }
        catch(JSONException j)
        {

        }

        JSONObject response = this.httpPost(json, url);
        try
        {
            JSONObject jsonResponse = response.getJSONObject("Response");
            Double longitude = (Double) jsonResponse.getDouble("Longitude");
            Double latitude = (Double) jsonResponse.getDouble("Latitude");
            return new DwollaUser("true", (String)jsonResponse.get("Name"), latitude.toString(),longitude.toString(), (String)jsonResponse.get("Id"), (String)jsonResponse.get("State"), (String)jsonResponse.get("Type"), (String)jsonResponse.get("City"));

        }
        catch (JSONException j)
        {
            j.getMessage();
        }
        return  new DwollaUser(false);
    }

/**
 * gets recent transactions
 *
 * @param date: the date to begin pulling transactions from 
 * @param limit: the number of transactions to be returned
 * @param skip: the number of transactions to skip
 *
 * @return JSON representation of the request see: https://www.dwolla.com/developers/endpoints/transactions/list
 **/
    JSONObject getJSONTransactionsSince(String date, String limit, String skip)
    {
        if (!this.hasToken())
        {
            //error
        }

        String url = baseURL + "/transactions?";
        
        if (date != null  && !date.equals(""))
        {
            url += "sincedate=" + date;
        }
        if (limit != null  && !limit.equals(""))
        {
            url += "limit=" + limit;
        }
        if (skip != null  && !skip.equals(""))
        {
            url += "skip=" + skip;
        }
        url += "oauth_token=" + getAccessToken();

        return  this.httpGet(url);
    }

/**
 * gets recent transactions
 *
 * @param date: the date to begin pulling transactions from 
 * @param limit: the number of transactions to be returned
 * @param skip: the number of transactions to skip
 *
 * @return a DwollaTransactions object contiaining the results of the request
 **/
    DwollaTransactions getTransactionsSince(String date, String limit, String skip)
    {
        JSONObject response = getJSONTransactionsSince(date, limit, skip);
        DwollaTransactions dwollaTransactions = new DwollaTransactions();

        try
        {
            JSONArray transactions = response.getJSONArray("Response");
            for(int i = 0; i < transactions.length(); i++)
            {
                DwollaTransaction transaction = this.generateTransaction(transactions.getJSONObject(i));
                dwollaTransactions.add(transaction);
            }
        }
        catch(JSONException j)
        {

        }

        return dwollaTransactions;
    }



/**
 * gets the transaction details of the provided transactionID
 *
 * @param transactionID: a string representation of the id of the transaction
 *
 * @return JSON representation of the request see https://www.dwolla.com/developers/endpoints/transactions/details
 **/
    JSONObject getJSONTransaction(String transactionID)
    {
        if(this.hasToken())
        {
            //error
        }
        if(transactionID == null || transactionID.equals(""))
        {
            //error
        }

        String url = baseURL + "/transactions/" + transactionID + "?oauth_token=" + getAccessToken();
        return httpGet(url);
    }

/**
 * gets the transaction details of the provided transactionID
 *
 * @param transactionID: a string representation of the id of the transaction
 *
 * @return a DwollaTransaction object containing the result of the request
 **/
    DwollaTransaction getTransaction(String transactionID)
    {
        JSONObject response = getJSONTransaction(transactionID);

        try
        {
            return this.generateBasicTransaction(response.getJSONObject("Response"));
        }
        catch (JSONException j)
        {

        }
        return new DwollaTransaction(false);
    }

/**
 * gets the transaction stats for the user
 *
 * @param start: the date to begin pulling the stats from
 * @param end: the date to stop pulling the stats from
 *
 * @return JSON representation of the request see https://www.dwolla.com/developers/endpoints/transactions/stats
 **/
    JSONObject getJSONTransactionStats(String start, String end)
    {
        if (!this.hasToken())
        {
            //error
        }

        String url = baseURL + "/transactions/stats?oauth_token=" + getAccessToken();    
        
        if (start != null  && !start.equals(""))
        {
            url += "&startdate=" + start;
        }
        if (end != null  && !end.equals(""))
        {
            url += "&enddate=" + end;
        }

        return  httpGet(url);
    }

/**
 * gets the transaction stats for the user
 *
 * @param start: the date to begin pulling the stats from
 * @param end: the date to stop pulling the stats from
 *
 * @return a DwollaTransactionStats object containing the results of the request
 * //throw errors NSException if parameters are invalid, no access token is available, or request fails
 **/
    DwollaTransactionStats getTransactionStats(String start, String end)
    {
        JSONObject response = getJSONTransactionStats(start, end);

        try
        {
            return this.generateTransactionStats(response.getJSONObject("Response"));
        }
        catch(JSONException j)
        {

        }
        return new DwollaTransactionStats(false);
    }

/**
 * helper class used to generate the url to request an oauth token
 *
 * @param redirect: the url to redirect the user to
 * @param redirect: the url to redirect the user to
 *
 * @return url request to be run by the client
 **/
    String generateURLWithKey(String redirect, String[] scopes)
    {
        if (key == null || key.equals(""))
        {
            //error
        }
        if(redirect == null || redirect.equals("") || scopes == null || scopes.length == 0)
        {
            //error
        }
        String url = "https://www.dwolla.com/oauth/v2/authenticate?client_id=" + key + "&response_type=code&redirect_uri=" +
                redirect + "&scope=";

        for (int i = 0; i < scopes.length; i++)
        {
            url += scopes[i];
            if(scopes.length > 0 && i < scopes.length-1)
            {
                url += "%7C";
            }
        }
        return  url;
    }

/**
 * helper method that generates a DwollaContact from the given string
 *
 * @param contact: the string containing the DwollaContact data
 *
 * @return DwollaContact object containing the contents of the string
 **/
    DwollaContact generateBasicContact(JSONObject contact)
    {
        try
        {
            String name = (String) contact.get("Name");
            String id = (String) contact.get("Id");
            String image = (String) contact.get("Image");
            String type = (String) contact.get("Type");
            return new DwollaContact(name, id, "", "", type, image);
        }
        catch(JSONException j)
        {

        }
        return new DwollaContact(false);
    }

    DwollaContact generateContact(JSONObject contact)
    {
        try
        {
        String name = (String) contact.get("Name");
        String id = (String) contact.get("Id");
        String city = (String) contact.get("City");
        String state = (String) contact.get("State");
        String postal = (String) contact.get("Postal");
        String image = (String) contact.get("Image");
        String type = (String) contact.get("Type");
        String address = (String) contact.get("Address");
        String group = (String) contact.get("Group");
        Double latitude = contact.getDouble("Latitude");
        Double longitude = contact.getDouble("Longitude");

        return new DwollaContact(name, id, address, city, state, postal,
                type, image, group, latitude.toString(), longitude.toString());
        }
        catch(JSONException j)
        {

        }
        return new DwollaContact(false);
    }

    DwollaFundingSource generateSource(JSONObject source)
    {
        try
        {
            Boolean verified = source.getBoolean("Verified");
            String name = (String) source.get("Name");
            String type = (String) source.get("Type");
            String id = (String) source.get("Id");

            return new DwollaFundingSource(verified.toString(), name, type, id);
        }
        catch(JSONException j)
        {

        }
        return new DwollaFundingSource(false);
    }

    DwollaFundingSource generateIndividualSource(JSONObject source)
    {
        try
        {
        Boolean verified = source.getBoolean("Verified");
        String name = (String) source.get("Name");
        String type = (String) source.get("Type");
        String id = (String) source.get("Id");

        return new DwollaFundingSource(verified.toString(), name, type, id);
        }
        catch(JSONException j)
        {

        }
        return new DwollaFundingSource(false);
    }

    DwollaUser generateDwollaUser(JSONObject user)
    {
        try
        {
        String name = (String) user.get("Name");
        Double latitude = user.getDouble("Latitude");
        Double longitude =  user.getDouble("Longitude");
        String id = (String) user.get("Id");
        String city = (String) user.get("City");
        String state = (String) user.get("State");
        String type = (String) user.get("Type");

        return new DwollaUser("true", name, latitude.toString(), longitude.toString(), id, state, type, city);
        }
        catch(JSONException j)
        {

        }
        return new DwollaUser(false);
    }

    DwollaTransaction generateTransaction(JSONObject transaction)
    {
        try
        {
            String notes = (String) transaction.get("Notes");
            String type = (String) transaction.get("UserType");
            String status = (String) transaction.get("Status");
            String amount = (String) transaction.get("Amount");
            String clearingDate = (String) transaction.get("ClearingDate");
            String source = (String) transaction.get("SourceName");
            String destinationID = (String) transaction.get("DestinationId");
            String date = (String) transaction.get("Date");
            String transactionType = (String) transaction.get("Type");
            String id = (String) transaction.get("Id");
            String sourceID = (String) transaction.get("SourceId");
            String name = (String) transaction.get("DestinationName");

            return new DwollaTransaction(notes, type, status, amount, clearingDate, source, destinationID, date,
                    transactionType, id, sourceID, name);
        }
        catch (JSONException j)
        {

        }
        return new DwollaTransaction(false);
    }

    DwollaTransaction generateBasicTransaction(JSONObject transaction)
    {
        try
        {
            String notes = (String) transaction.get("Notes");
            String type = (String) transaction.get("UserType");
            String status = (String) transaction.get("Status");
            String amount = (String) transaction.get("Amount");
            String clearingDate = (String) transaction.get("ClearingDate");
            String source = (String) transaction.get("SourceName");
            String destinationID = (String) transaction.get("DestinationId");
            String date = (String) transaction.get("Date");
            String transactionType = (String) transaction.get("Type");
            String id = (String) transaction.get("Id");
            String sourceID = (String) transaction.get("SourceId");
            String name = (String) transaction.get("DestinationName");

        return new DwollaTransaction(notes, type, status, amount, clearingDate, source, destinationID, date,
                transactionType, id, sourceID, name);
        }
        catch (JSONException j)
        {

        }
        return new DwollaTransaction(false);
    }

    DwollaTransactionStats generateTransactionStats(JSONObject transaction)
    {
        try
        {
        Double total = transaction.getDouble("TransactionsTotal");
        Integer count = transaction.getInt("TransactionsCount");
        return new DwollaTransactionStats(total.toString(), count.toString());

        }
        catch(JSONException j)
        {

        }
        return new DwollaTransactionStats(false);
    }

    DwollaUser generateBasicDwollaUser(JSONObject user)
    {
        try
        {
        String name = (String) user.get("Name");
        String id = (String) user.get("Id");
        Double latitude = user.getDouble("Latitude");
        Double longitude =  user.getDouble("Longitude");
        return new DwollaUser(":true", name, latitude.toString(), longitude.toString(), id, "", "", "");
        }
        catch(JSONException j)
        {

        }
       return new DwollaUser(false);
    }

    public JSONObject httpPost(JSONObject req, String url)
    {

        if(test)
        {
            return jsonResult;
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();

        HttpPost httpost = new HttpPost(url);

        //passes the results to a string builder/entity
        try
        {
        StringEntity se = new StringEntity(req.toString());

        //sets the post request as the resulting string
        httpost.setEntity(se);
        //sets a request header so the page receving the request will know what to do with it
        httpost.setHeader("Accept", "application/json");
        httpost.setHeader("Content-type", "application/json");

        //Handles what is returned from the page
        ResponseHandler responseHandler = new BasicResponseHandler();
        String response = (String) httpclient.execute(httpost, responseHandler);
        return new JSONObject(response);
        }
        catch(UnsupportedEncodingException e)
        {

        }
        catch(ClientProtocolException c)
        {

        }
        catch(IOException i)
        {

        }
        catch (JSONException j)
        {

        }
        return null;
    }

    public JSONObject httpGet(String url)
    {

        if(test)
        {
            return jsonResult;
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(url);

            //sets a request header so the page receving the request will know what to do with it
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");

            //Handles what is returned from the page
            ResponseHandler responseHandler = new BasicResponseHandler();
        try
        {
            String response = (String) httpclient.execute(httpGet, responseHandler);
            try
            {
                return new JSONObject(response);
            }
            catch (JSONException j)
            {

            }
        }
        catch(ClientProtocolException c)
        {

        }
        catch(IOException i)
        {

        }
        return new JSONObject();
    }

    public String clean(String toclean)
    {
       if(toclean.startsWith(":"))
       {
          toclean = toclean.replace(":", "");
       }
       if(toclean.endsWith(","))
       {
          toclean = toclean.replace(",", "");
       }
       if(toclean.contains("//n"))
       {
          toclean = toclean.replace("//n", " ");
       }
       if(toclean.contains("//"))
       {
           toclean = toclean.replace("//", "");
       }
       if(toclean.contains("}}"))
       {
           toclean = toclean.replace("}}", "");
       }
       return toclean;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public void setJSONResult(JSONObject result)
    {
        this.jsonResult = result;
    }
}
