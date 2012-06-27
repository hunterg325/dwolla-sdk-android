package com.client;

/**
 * Created with IntelliJ IDEA.
 * User: nschulze
 * Date: 6/18/12
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import android.webkit.WebView;
import android.view.View;
import android.webkit.WebViewClient;
import android.content.Intent;

public class DwollaOAuth2Client
{
    private String redirect;
    private String key;
    private String secret;
    private String code;
    private String[] scopes;
    private DwollaAPI api;

    /**
     * initializes an instance of the DwollaO2AuthClient allowing the user to 
     * login and logout of their Dwolla account
     *
     * @param key: the application key provided by the Dwolla website
     * @param secret: the application secret provided by the Dwolla website
     * @param redirect: the website the user will be redirected to 
     * @param scopes: scopes the developer wants to use in the application
     *
     * @return an instance of DwollaOAuth2Client
     **/
    public DwollaOAuth2Client(String key, String secret, String redirect, String[] scopes)
    {
        try
        {
            this.key = URLEncoder.encode(key, "utf-8");
            this.secret = URLEncoder.encode(secret, "utf-8");
            this.redirect = URLEncoder.encode(redirect, "utf-8");

        }
        catch(UnsupportedEncodingException e)
        {
            this.key = key;
            this.secret = secret;
            this.redirect = redirect;
        }
        this.scopes = scopes;
        api = new DwollaAPI(this.key, this.secret);
    }

    /**
     * checks to see if a valid access token is available
     *
     * @return YES if a valid access token is present, false otherwise
     **/
    public boolean isAuthorized()
    {
        return api.hasToken();
    }

    /**
     * calls a webview and allows the user to login to
     * Dwolla from within the application
     **/
    public void login(View view, Activity _activity)
    {
        final Activity activity;
        activity = _activity;
        String url = api.generateURLWithKey(redirect, scopes);
        WebView webview = new WebView(view.getContext());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setVisibility(View.VISIBLE);
        activity.setContentView(webview);
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView _view, String url, Bitmap favicon)  {

                if (url.startsWith("https://www.dwolla.com/?code")) {

                        if (url.indexOf("code=")!=-1)
                        {
                            String[] urlSplit = url.split("=");
                            String code = urlSplit[1];
                            DwollaOAuth2Client.this.code = code;
                            Thread thread = new Thread()
                            {
                                public void run()
                                {
                                    String token = requestAccessToken(DwollaOAuth2Client.this.code);
                                    Intent apiGoodness = new Intent(activity, APIGoodness.class);
                                    apiGoodness.putExtra("token", token);
                                    apiGoodness.putExtra("key", DwollaOAuth2Client.this.key);
                                    apiGoodness.putExtra("secret", DwollaOAuth2Client.this.secret);
                                    activity.startActivity(apiGoodness);
                                }
                            };
                            thread.start();
                        }
                }
            }
        });

        webview.loadUrl(url);
    }

    /**
     * clears the user's access token, logging them out
     * and disabling the Dwolla integration
     **/
    public void logout()
    {
        api.clearAccessToken();
    }

    public boolean hasCode(String[][] urlValues)
    {

        if (urlValues[0][0].equals("error"))
        {
            //[receiver failedLogin:urlValues];
            return false;
        }
        else if (urlValues[0][0].equals("code"))
        {
            return true;
        }
        return false;
    }

    private String requestAccessToken(String code)
    {

        String url = "https://www.dwolla.com/oauth/v2/token?client_id="+ key +"&client_secret="+ secret
                +"&grant_type=authorization_code&redirect_uri=https://www.dwolla.com&code="+code;

        try
        {
            URL fullURL = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) fullURL.openConnection();
            //HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //conn.setSSLSocketFactory(sslContext.getSocketFactory());
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(20000);
            conn.setDoOutput(true);
            conn.connect();

            if (conn.getResponseCode() != 200)
            {
                // Parse bad request data
                if (conn.getResponseCode() == 400)
                {
                    Log.d("ERROR", "API Response Code is 400):");

                    String errorStream = convertStreamToString(conn.getErrorStream());
                    errorStream = errorStream.substring(errorStream.indexOf("<Detail>"));
                    errorStream = errorStream.replace("<Detail><string xmlns=\"http://schemas.microsoft.com/2003/10/Serialization/\">", "");
                    errorStream = errorStream.replace("</string></Detail></Fault>", "").trim();

                    Log.d("ERROR", "Parse the error):" + errorStream);

                    //throw new DwollaApiException(DwollaApiError.valueOf(errorStream));
                }
                else
                {
                    Log.d("ERROR", "API Response Code is not 200 or 400 - throw system error):");
                    //throw new DwollaApiException(DwollaApiError.SYSTEM_ERROR);
                }
            }

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            BufferedReader buffer = new BufferedReader(reader, 2048);
            StringBuilder sb = new StringBuilder();
            String cur;
            while ((cur = buffer.readLine()) != null)
                sb.append(cur + "\n");

            reader.close();

            JSONObject res = new JSONObject(sb.toString());

            String token = res.getString("access_token");

            return token;
        }
        catch(MalformedURLException e)
        {
            //handle
        }
        catch(IOException i)
        {
           //handle
        }
        catch(JSONException j)
        {
            //handle
        }
        return "";
    }

    public String convertStreamToString(InputStream is) throws IOException {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }


}
