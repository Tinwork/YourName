package com.yellowman.tinwork.yourname.activities.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.adapters.SearchAdapter;
import com.yellowman.tinwork.yourname.UIKit.errors.UIErrorManager;
import com.yellowman.tinwork.yourname.UIKit.misc.GradientGenerator;
import com.yellowman.tinwork.yourname.application.YourName;
import com.yellowman.tinwork.yourname.model.Search;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.search.SearchSeries;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Marc Intha-amnouay on 30/12/2017.
 * Created by Didier Youn on 30/12/2017.s
 * Created by Abdel-Atif Mabrouck on 30/12/2017.
 * Created by Antoine Renault on 30/12/2017.
 */

public class SearchResultsActivity extends AppCompatActivity {

    @Inject
    @Named("FetchSearchSeries")
    Fetch searchSeries;

    private RecyclerView recyclerView;
    private UIErrorManager uiErrorManager;
    private TextView title;
    private TextView errRes;
    private View spinner;

    /**
     * On Create
     *
     * @param savedBundleInstance bundle
     */
    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.search_result_activity);
        // Inject NetworkPresenter
        ((YourName) getApplicationContext()).getmNetworkComponent().inject(this);
        // gradient
        GradientGenerator gd = new GradientGenerator(this, findViewById(R.id.result_activity_layout), null);
        int color = gd.buildBackgroundGradientColor();
        AppUtils.colorizeStatusBar(this.getWindow(), this, color);
        // init ui component
        initComponent();
        handleIntent(getIntent());
    }

    /**
     * Handle Intent
     *
     * @param intent intent
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (query.isEmpty()) {
                errRes.setVisibility(View.VISIBLE);
            } else {
                makeRequest(query);
            }
        }
    }

    /**
     * Init Component
     *
     */
    private void initComponent() {
        this.spinner = findViewById(R.id.search_result_spinner);
        this.title   = findViewById(R.id.title_search);
        this.errRes  = findViewById(R.id.result_failed);
        this.recyclerView = findViewById(R.id.result_search_recyclerView);

        this.uiErrorManager = new UIErrorManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        ));
        recyclerView.setHasFixedSize(true);
    }

    /**
     * Make Request
     *
     */
    private void makeRequest(String criteria) {
        HashMap<String, String> payload = new HashMap<>();
        payload.put("name", criteria);
        payload.put("notsave", null);
        title.setText(getString(R.string.result_search, criteria));

        searchSeries.get(payload, new GsonCallback<List<Series>>() {
            @Override
            public void onSuccess(List<Series> response) {
                SearchAdapter adapter = new SearchAdapter(response);
                recyclerView.setAdapter(adapter);
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onError(String err) {
                spinner.setVisibility(View.GONE);
                errRes.setVisibility(View.VISIBLE);
                // one day we should handle error
                uiErrorManager
                        .setError("", err)
                        .setOptsMode(UIErrorManager.RETRY)
                        .setErrorStrategy(UIErrorManager.SNACKBAR);
            }
        });
    }
}
