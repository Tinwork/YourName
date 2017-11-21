package com.yellowman.tinwork.yourname.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.yellowman.tinwork.yourname.FilmDetails.FilmDetails;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.model.Token;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.user.UserToken;
import com.yellowman.tinwork.yourname.utils.Utils;

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
        UserToken fetch = new UserToken(this.getApplicationContext());

        fetch.makeToken(new GsonCallback<Token>() {
            @Override
            public void onSuccess(Token response) {
                // Save the token in the sharedPreference
                Utils.saveSharedPreference(HomeActivity.this, "yourname_token", response.getToken());
            }
        });
    }

    /**
     * Launch Activity
     */
    protected void launchActivity() {
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FilmDetails.class);
            startActivity(intent);
        });
    }
}
