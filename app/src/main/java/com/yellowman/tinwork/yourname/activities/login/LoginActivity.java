package com.yellowman.tinwork.yourname.activities.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;


import com.google.gson.Gson;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.errors.UIErrorManager;
import com.yellowman.tinwork.yourname.UIKit.misc.GradientGenerator;
import com.yellowman.tinwork.yourname.UIKit.misc.ProgressSpinner;
import com.yellowman.tinwork.yourname.activities.home.HomeActivity;
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
    private UserToken tokenReq;
    private UIErrorManager uiErrorManager;


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
        this.tokenReq = new UserToken(this);
        this.uiErrorManager = new UIErrorManager(this);
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
     *
     * @void
     */
    protected void initOtherComponent() {
        mProgressView  = findViewById(R.id.spinner_container);
        mLoginFormView = findViewById(R.id.email_login_form);
    }

   /**
    * Add Submit Listener
    *
    * @void
    */
   private void addSubmitListener() {
       Button signIn = findViewById(R.id.sign_in_button);
       Button skip   = findViewById(R.id.skip);

       // Add the onClickListener
       signIn.setOnClickListener(ev -> LoginActivity.this.attemptLogin());
       skip.setOnClickListener(ev -> LoginActivity.this.getRequestToken(null));
   }

   /**
    * Prepare Auto Completion
    *
    * @void
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
    * @void
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
       getRequestToken(userInfo);
   }


   /**
    * Get Request Token
    *   Get the Token of the App
    * @param payload
    */
   private void getRequestToken(HashMap<String, String> payload) {
       ProgressSpinner.setVisible(mProgressView);
       tokenReq.get(payload, new GsonCallback<Token>() {
           @Override
           public void onSuccess(Token response) {
               Utils.saveSharedPreference(LoginActivity.this, "yourname_token", response.getToken());
               // Save the user data
               if (payload != null) {
                   if (payload.containsKey("username") && payload.containsKey("accountID")){
                       Utils.saveSharedPreference(LoginActivity.this, "username", payload.get("username"));
                       Utils.saveSharedPreference(LoginActivity.this, "accountID", payload.get("account_id"));
                   }
               }
               // Start the new home activity
               LoginActivity.this.dispatchHome();
               ProgressSpinner.setHidden(mProgressView);
           }

           @Override
           public void onError(String err) {
               Log.d("Debug", "CATTTCHHH ERRRROOORRR");
               ProgressSpinner.setHidden(mProgressView);
               uiErrorManager
                       .setError("", err)
                       .setOptsMode(UIErrorManager.RETRY)
                       .setErrorStrategy(UIErrorManager.SNACKBAR);
           }
       });
   }

   /**
    * Shows the progress UI and hides the login form.
    *
    * @param show
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
     *
     * @void
     */
    private void dispatchHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * Set Background
     * @void
     */
    private void setBackground() {
        RelativeLayout layout = findViewById(R.id.loginLayout);
        GradientGenerator gd = new GradientGenerator(this, layout, null);
        gd.buildBackgroundGradientColor();

        // Set the nav bar to translucent
        Utils.makeNavBarTranslucent(getWindow());
    }
}

