package com.yellowman.tinwork.yourname.activities.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.entity.Actor;
import com.yellowman.tinwork.yourname.network.api.Routes;

/**
 * Created by Marc Intha-amnouay on 30/12/2017.
 * Created by Didier Youn on 30/12/2017.s
 * Created by Abdel-Atif Mabrouck on 30/12/2017.
 * Created by Antoine Renault on 30/12/2017.
 */
public class ActorWebViewActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String actorName = getActorName();
        String requestUrl = buildQuery(actorName);

        setContentView(R.layout.actor_webview);

        WebView webView = findViewById(R.id.actor_webview_detail);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(requestUrl);
    }

    /**
     * Extract actor name from intent
     *
     * @return String
     */
    private String getActorName() {
        Intent intent = getIntent();
        Parcelable actorParcel = intent.getParcelableExtra("Entity");

        Actor actor = (Actor) actorParcel;

        return actor.getName();
    }

    /**
     * Build URL Request
     *
     * @param actorName String
     * @return String
     */
    private String buildQuery(String actorName)
    {
        return Routes.IMDB_BASE_URL + Routes.IMDB_URI_SEARCH + actorName;
    }
}
