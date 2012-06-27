package com.client;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button login = (Button) findViewById(R.id.LoginButton);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String[] scopes = new String[7];
                scopes[0] = "send";
                scopes[1] = "balance";
                scopes[2] = "accountinfofull";
                scopes[3] = "contacts";
                scopes[4] = "funding";
                scopes[5] = "request";
                scopes[6] = "transactions";
                DwollaOAuth2Client client = new DwollaOAuth2Client("qsWR4NLYZWc7hy84kRdr2TTPKjkUDQpDfMjUUrro6d2uWbw0dU",
                        "r+nWEsy45d+zpB86mUy0QANbrTSas9KswANPUeL7auV0m1MicC", "https://www.dwolla.com", scopes);
                client.login(view, MyActivity.this);
            }
        });
    }
}
