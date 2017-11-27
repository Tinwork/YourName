package com.yellowman.tinwork.yourname.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.google.gson.Gson;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.GradientGenerator;
import com.yellowman.tinwork.yourname.home.HomeActivity;
import com.yellowman.tinwork.yourname.model.Token;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.user.UserToken;
import com.yellowman.tinwork.yourname.utils.Utils;

import java.util.HashMap;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mUsername;
    private EditText mAccountID;
    private View mProgressView;
    private View mLoginFormView;
    private Gson gson;
    private View focusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Init the other login component view
        initOtherComponent();
        // Prepare the listener
        addSubmitListener();
        // Set the Auto Completion
        prepareAutoCompletion();
        // set the background color
        setBackground();
        // focus view
        this.focusView = null;
    }

    /**
     * On Back Pressed
     *      Overriding the behavior of this method
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**
     * Init Other Component
     */
    protected void initOtherComponent() {
        mProgressView  = findViewById(R.id.login_progress);
        mLoginFormView = findViewById(R.id.email_login_form);
    }

   /**
    * Add Submit Listener
    */
   private void addSubmitListener() {
       Button signIn = (Button) findViewById(R.id.sign_in_button);

       // Add the onClickListener
       signIn.setOnClickListener(ev -> LoginActivity.this.attemptLogin());
   }

   /**
    * Prepare Auto Completion
    */
   private void prepareAutoCompletion() {
       mUsername = findViewById(R.id.username);
       // Retrieve the usernames from the sharedPreference saved in Json
       String jsonUsernameTrialStr = Utils.getSharedPreference(this, "username_trial");
       // Gson
       gson = new Gson();
       String[] predicates = gson.fromJson(jsonUsernameTrialStr, String[].class);

       if (predicates != null) {
           // Create a new ArrayAdapter
           ArrayAdapter<String> adapter  = new ArrayAdapter<String>(
                   this,
                   android.R.layout.simple_dropdown_item_1line,
                   predicates);

           // Set the adatper
           mUsername.setAdapter(adapter);
       }
   }

   /**
    * Attempt Login
    *
    */
   private void attemptLogin() {
       HashMap<String, String> userInfo = new HashMap<>();
       // Retrieve the Username
       mAccountID = findViewById(R.id.accountID);
       // Get the text of the edit text and the auto complete component
       String accountID = mAccountID.getText().toString();
       String username  = mUsername.getText().toString();


       if (accountID.isEmpty()) {
           mAccountID.setError(getString(R.string.error_field_required));
           focusView = mAccountID;
           focusView.requestFocus();
       } else if (username.isEmpty()) {
           mUsername.setError(getString(R.string.error_field_required));
           focusView = mUsername;
           focusView.requestFocus();
       } else {
           userInfo.put("username", username);
           userInfo.put("account_id", accountID);
       }

       showProgress(true);
       // Make a post request to get the token

       UserToken tokenReq = new UserToken(this);
       tokenReq.get(userInfo, new GsonCallback<Token>() {
           @Override
           public void onSuccess(Token response) {
               Utils.saveSharedPreference(LoginActivity.this, "yourname_token", response.getToken());
               // Save the user data
               Utils.saveSharedPreference(LoginActivity.this, "username", username);
               Utils.saveSharedPreference(LoginActivity.this, "accountID", accountID);
               // Start the new home activity
               LoginActivity.this.dispatchHome();
           }

           @Override
           public void onError(String err) {
               showProgress(false);
           }
       });
   }

   /**
    * Shows the progress UI and hides the login form.
    */
    private void showProgress(final boolean show) {
        // The ViewPropertyAnimator APIs are not available, so simply show
        // and hide the relevant UI components.
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    /**
     * Dispatch Home
     *      Call the Home Activity
     */
    private void dispatchHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * Set Background
     */
    private void setBackground() {
        LinearLayout layout = findViewById(R.id.loginLayout);
        GradientGenerator gd = new GradientGenerator(this, layout);
        gd.buildBackgroundGradientColor();

        // Set the nav bar to translucent
        Utils.makeNavBarTranslucent(getWindow());
    }
}

