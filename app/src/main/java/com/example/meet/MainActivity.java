package com.example.meet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    private SocialAuthAdapter socialAuth;
    private Button LinkedInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        LinkedInBtn = (Button) findViewById(R.id.LinkedInBtn);

        LinkedInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socialAuth.addProvider(SocialAuthAdapter.Provider.LINKEDIN, R.id.LinkedInBtn);
                try {
                    socialAuth.addConfig(SocialAuthAdapter.Provider.LINKEDIN, "75eo3eisk64ie4", "1RIJxlNmq14TzuIt", null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                socialAuth.authorize(MainActivity.this, SocialAuthAdapter.Provider.LINKEDIN);
            }
        });

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void LoadQrActivity(View view)
    {
        Intent i=new Intent(this,UserQrGeneratorActivity.class);
        startActivity(i);
    }

    public void ScanQrActivity(View view)
    {
        Intent i=new Intent(this,SimpleScannerActivity.class);
        startActivity(i);
    }

    public class ResponseListener implements DialogListener{

        @Override
        public void onComplete(Bundle values) {
            socialAuth.getUserProfileAsync(new ProfileDataListener());
        }

        @Override
        public void onError(SocialAuthError error) {
            Log.i("Script", error.getMessage());
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onBack() {

        }
    }


    public class ProfileDataListener implements SocialAuthListener<Profile> {
        @Override
        public void onExecute(String platform, Profile data) {
            Intent intent = new Intent(MainActivity.this, UserQrGeneratorActivity.class);
            intent.putExtra("data", data);
            startActivity(intent);
        }

        @Override
        public void onError(SocialAuthError error) {
            Log.i("Script", error.getMessage());
        }
    }
}
