package com.rivetlogic.mobile.liferaytodoslibrary.interactor.taskcompleted;

/**
 * Created by ronnyvargas on 05/04/16.
 */

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;

import org.json.JSONObject;

public class TaskCompletedCallback extends InteractorAsyncTaskCallback<JSONObject> {

    public TaskCompletedCallback(int targetScreenletId) {
        super(targetScreenletId);
    }

    @Override
    public JSONObject transform(Object obj) throws Exception {
        return (JSONObject) obj;
    }

    @Override
    protected BasicEvent createEvent(int targetScreenletId, JSONObject result) {
        return new TaskCompletedEvent(targetScreenletId, result);
    }

    @Override
    protected BasicEvent createEvent(int targetScreenletId, Exception e) {
        return new TaskCompletedEvent(targetScreenletId, e);
    }

    @Override
    public JSONObject inBackground(JSONObject jsonObject) throws Exception {
    return transform(jsonObject);
    }
}
