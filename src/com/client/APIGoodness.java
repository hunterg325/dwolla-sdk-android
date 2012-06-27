package com.client;

/**
 * Created with IntelliJ IDEA.
 * User: nschulze
 * Date: 6/19/12
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class APIGoodness extends Activity
{
    private DwollaAPI api;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            String key = extras.getString("key");
            String secret = extras.getString("secret");
            String token = extras.getString("token");

            try
            {
                token = URLEncoder.encode(token, "utf-8");
            }
            catch(UnsupportedEncodingException e)
            {

            }
            api = new DwollaAPI(key, secret);
            api.setAccessToken(token);
        }

        Button login = (Button) findViewById(R.id.getBalance);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Thread thread = new Thread()
                {
                    public void run()
                    {
                           api.getBasicInfoWithAccountID("812-570-5285");


                    }
                };
                thread.start();
            }
        });
    }
}
