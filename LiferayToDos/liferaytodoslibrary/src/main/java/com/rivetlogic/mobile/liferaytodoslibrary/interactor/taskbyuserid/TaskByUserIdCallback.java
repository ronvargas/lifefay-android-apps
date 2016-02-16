package com.rivetlogic.mobile.liferaytodoslibrary.interactor.taskbyuserid;

/**
 * Created by ronnyvargas on 01/27/16.
 */

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;

import org.json.JSONArray;

public class TaskByUserIdCallback extends InteractorAsyncTaskCallback<JSONArray> {

    public TaskByUserIdCallback(int targetScreenletId) {
        super(targetScreenletId);
    }

    @Override
    public JSONArray transform(Object obj) throws Exception {
        return (JSONArray) obj;
    }

    @Override
    protected BasicEvent createEvent(int targetScreenletId, JSONArray result) {
        return new TaskByUserIdEvent(targetScreenletId, result);
    }

    @Override
    protected BasicEvent createEvent(int targetScreenletId, Exception e) {
        return new TaskByUserIdEvent(targetScreenletId, e);
    }

    @Override
    public JSONArray inBackground(JSONArray jsonArray) throws Exception {
        return transform(jsonArray.get(0));
    }
}
