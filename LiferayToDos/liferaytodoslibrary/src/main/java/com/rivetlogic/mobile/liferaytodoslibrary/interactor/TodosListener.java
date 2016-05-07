package com.rivetlogic.mobile.liferaytodoslibrary.interactor;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ronnyvargas on 01/27/16.
 */
public interface TodosListener {

    // events for UserLoad Interactor actions
    void onLoadToDosSuccess(JSONArray jsonArray);

    void onLoadToDosFailure(Exception exception);

    // events for check box click
    void onSetTaskCompletedSuccess(JSONObject jsonObject);

    void onSetTaskCompletedFailure(Exception exception);

    // events for save button on Task update
    void onUpdateTaskSuccess(JSONObject jsonObject);

    void onUpdateTaskFailure(Exception exception);
}
