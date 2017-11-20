package com.yellowman.tinwork.yourname.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yellowman.tinwork.yourname.FilmDetails.FilmDetails;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.entity.TokenEntity;
import com.yellowman.tinwork.yourname.network.fetch.FetchToken;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        testFetch();
        launchActivity();
    }

    /**
     * Test Fetch
     */
    protected void testFetch() {
        FetchToken fetch = FetchToken.getInstance(this.getApplicationContext());
        fetch.makeToken(new GsonCallback<TokenEntity>() {
            @Override
            public void onSuccess(TokenEntity response) {
                // Do the rest here..
                System.out.println(response.getToken());
            }
        });
        //fetch.getToken();
    }

    /**
     * Launch Activity
     */
    protected void launchActivity() {
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, FilmDetails.class);
                startActivity(intent);
            }
        });
    }
}
