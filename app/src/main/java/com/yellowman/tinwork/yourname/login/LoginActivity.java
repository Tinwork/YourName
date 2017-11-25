package com.yellowman.tinwork.yourname.login;

import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;


import com.google.gson.Gson;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.utils.Utils;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Prepare the listener
        addSubmitListener();
        // Set the Auto Completion
        prepareAutoCompletion();
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
       // Retrieve the usernames from the sharedPreference saved in Json
       String jsonUsernameTrialStr = Utils.getSharedPreference(this, "username_trial");
       // Gson
       gson = new Gson();
       String[] predicates = gson.fromJson(jsonUsernameTrialStr, String[].class);
       // Create a new ArrayAdapter
       ArrayAdapter<String> adapter  = new ArrayAdapter<String>(
               this,
               android.R.layout.simple_dropdown_item_1line,
               predicates);

       // Get the reference of the auto complete view
       mUsername = findViewById(R.id.username);
       mUsername.setAdapter(adapter);
   }

   /**
    * Attempt Login
    *
    */
   private void attemptLogin() {
       View focusView = null;
       // Retrieve the Username
       mAccountID = findViewById(R.id.accountID);
       // Get the text of the edit text and the auto complete component
       String accountID = mAccountID.getText().toString();
       String username  = mUsername.getText().toString();

       if (accountID.isEmpty()) {
           mAccountID.setError(getString(R.string.error_field_required));
           focusView = mAccountID;
       } else if (username.isEmpty()) {
           mUsername.setError(getString(R.string.error_field_required));
           focusView = mUsername;
       }

       // Attempt to make log to the
       //showProgress(true);
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
}

