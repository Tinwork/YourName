package com.yellowman.tinwork.yourname.UIKit.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.entity.Actor;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.utils.Utils;

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
    private TextView actorRole;
    protected final View view;

    /**
     * Actor Holder::Constructor
     * @param viewItem
     */
    public ActorHolder(final View viewItem) {
        super(viewItem);
        this.view = viewItem;
        prepareElements();
    }

    /**
     * Prepare Elements
     * @void
     */
    public void prepareElements() {
        this.actorImg  = view.findViewById(R.id.actor_image);
        this.actorTxt  = view.findViewById(R.id.actor_name);
        this.actorRole = view.findViewById(R.id.actor_role);
    }

    /**
     * Bind Data
     *
     * @param actor
     * @void
     */
    public void bindData(final Actor actor) {
        if (actor.getImage().isEmpty()) {
            Glide.with(view.getContext()).load(R.drawable.ic_account_circle_white_18dp).apply(RequestOptions.circleCropTransform()).into(actorImg);
        } else {
            Glide.with(view.getContext()).load(Utils.buildMiscURI(Routes.IMG_PATH, actor.getImage())).apply(RequestOptions.circleCropTransform()).into(actorImg);
        }

        actorTxt.setText(actor.getName());
        actorRole.setText(actor.getRole());
    }
}
