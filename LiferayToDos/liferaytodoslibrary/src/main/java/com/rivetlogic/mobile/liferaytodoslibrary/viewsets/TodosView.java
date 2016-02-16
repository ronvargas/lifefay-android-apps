package com.rivetlogic.mobile.liferaytodoslibrary.viewsets;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultTheme;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;
import com.rivetlogic.mobile.liferaytodoslibrary.R;
import com.rivetlogic.mobile.liferaytodoslibrary.TodosScreenlet;
import com.rivetlogic.mobile.liferaytodoslibrary.view.TodosViewModel;

import org.json.JSONArray;

/**
 * Created by ronnyvargas on 01/27/16.
 */
public class TodosView extends FrameLayout implements TodosViewModel, View.OnClickListener{

    public TodosView(Context context) {
        super(context);

        DefaultTheme.initIfThemeNotPresent(context);
    }

    public TodosView(
            Context context, AttributeSet attributes) {
        super(context, attributes);

        DefaultTheme.initIfThemeNotPresent(context);
    }

    public TodosView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);

        DefaultTheme.initIfThemeNotPresent(context);
    }

    @Override
    public void showStartOperation(String actionName) {
        //       todo:
    }

    @Override
    public void showFinishOperation(String actionName) {
        //       todo:
    }

    @Override
    public void onClick(View v) {
        //       todo:
    }

    @Override
    public void showFailedOperation(String actionName, Exception e) {
        if (TodosScreenlet.LOAD_TO_DOS.equals(actionName)) {
            LiferayLogger.e("List of To Dos failed to load", e);
        }
        else {
            LiferayCrouton.error(getContext(), "Portrait failed to upload", e);
            LiferayLogger.e("portrait failed to upload", e);
        }
    }



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

}
