package com.example.deepu.dogwiteration1;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Marx on 17/08/2017.
 */

public class PicassoClient {
    public static void downloading(Context c, String url, ImageView img) {
        if (url != null && url.length() > 0) {
            Picasso.with(c).load(url).placeholder(R.drawable.white).into(img);
        } else {
            Picasso.with(c).load(R.drawable.white).into(img);
        }
    }
}
