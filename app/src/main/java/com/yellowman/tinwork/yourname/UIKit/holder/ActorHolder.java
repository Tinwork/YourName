package com.yellowman.tinwork.yourname.UIKit.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.helpers.Helper;
import com.yellowman.tinwork.yourname.activities.search.ActorWebViewActivity;
import com.yellowman.tinwork.yourname.entity.Actor;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.utils.AppUtils;

/**
 * MERRY CHRISTMAS !!!!! ✨ L~~~~~~~~~MM~~~~~~~~~L ✨
 *
 * Created by Marc Intha-amnouay on 24/12/2017.
 * Created by Didier Youn on 24/12/2017.
 * Created by Abdel-Atif Mabrouck on 24/12/2017.
 * Created by Antoine Renault on 24/12/2017.
 */
public class ActorHolder extends RecyclerView.ViewHolder {

    private ImageView actorImg;
    private TextView actorTxt;
    protected final View view;
    private Helper redirect;

    /**
     * Actor Holder::Constructor
     * @param viewItem View
     */
    public ActorHolder(final View viewItem) {
        super(viewItem);
        this.view = viewItem;
        this.redirect = new Helper();

        prepareElements();
    }

    /**
     * Prepare Elements
     */
    public void prepareElements() {
        this.actorImg  = view.findViewById(R.id.actor_image);
        this.actorTxt  = view.findViewById(R.id.actor_name);
    }

    /**
     * Bind Data
     *
     * @param actor Actor
     */
    public void bindData(final Actor actor) {
        if (actor.getImage().isEmpty()) {
            Glide.with(view.getContext().getApplicationContext()).load(R.drawable.ic_account_circle_white_24dp).apply(RequestOptions.circleCropTransform()).into(actorImg);
        } else {
            Glide.with(view.getContext().getApplicationContext()).load(AppUtils.buildMiscURI(Routes.IMG_PATH, actor.getImage())).apply(RequestOptions.circleCropTransform()).into(actorImg);
        }

        actorTxt.setText(actor.getName());
        view.setOnClickListener(event -> {
            redirect.launchNewViewWithModel(actor, view.getContext(), ActorWebViewActivity.class);
        });
    }
}
