package com.rivetlogic.mobile.liferaytodoslibrary.interactor;

import org.json.JSONArray;

/**
 * Created by ronnyvargas on 01/27/16.
 */
public interface TodosListener {

    //    for UserLoad Interactor actions
    void onLoadToDosSuccess(JSONArray jsonArray);

    void onLoadToDosFailure(Exception exception);
}
