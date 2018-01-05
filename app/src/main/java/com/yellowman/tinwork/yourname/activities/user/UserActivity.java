package com.yellowman.tinwork.yourname.activities.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentCommunication;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.UIKit.misc.GradientGenerator;
import com.yellowman.tinwork.yourname.model.Series;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Marc Intha-amnouay on 05/01/2018.
 * Created by Didier Youn on 05/01/2018.
 * Created by Abdel-Atif Mabrouck on 05/01/2018.
 * Created by Antoine Renault on 05/01/2018.
 */

public class UserActivity extends AppCompatActivity implements FragmentCommunication {

    private ImageView badge;
    private RelativeLayout container;

    /**
     * On Create
     *
     * @param savedInstanceBundle Bundle
     */
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.user_layout_activity);
        initFragmentListeners();

        // set the element here
        this.badge      = findViewById(R.id.badge);
        this.container  = findViewById(R.id.containerUser);
        // set the params for the view
        initComponent();
    }


    @Override
    public void setParcelable(List<Series> parcel, String key) {

    }

    @Override
    public void fireFragmentEvent(HashMap<String, List<Series>> parcels) {

    }

    @Override
    public void initFragmentListeners() {
        FragmentListener frag = (FragmentListener) getSupportFragmentManager().findFragmentById(R.id.user_frag);
        frag.notifyData(null);
    }

    /**
     * Set Badge
     *      Set a badge
     */
    private void setBadge() {
        // For the moment we only set it to glide
        Glide.with(this).load(R.drawable.kanahei).apply(RequestOptions.circleCropTransform()).into(badge);
    }

    /**
     * Init Component
     *
     */
    private void initComponent() {
        setBadge();

        // Set the background color
        GradientGenerator gd = new GradientGenerator(this, this.container, null);
        gd.buildBackgroundGradientColor();
    }
}
