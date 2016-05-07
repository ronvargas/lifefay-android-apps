package com.rivetlogic.mobile.liferaytodoslibrary.interactor.updatetask;

/**
 * Created by ronnyvargas on 05/04/16.
 */

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;

import org.json.JSONObject;

public class UpdateTaskCallback extends InteractorAsyncTaskCallback<JSONObject> {

    public UpdateTaskCallback(int targetScreenletId) {
        super(targetScreenletId);
    }

    @Override
    public JSONObject transform(Object obj) throws Exception {
        return (JSONObject) obj;
    }

    @Override
    protected BasicEvent createEvent(int targetScreenletId, JSONObject result) {
        return new UpdateTaskEvent(targetScreenletId, result);
    }

    @Override
    protected BasicEvent createEvent(int targetScreenletId, Exception e) {
        return new UpdateTaskEvent(targetScreenletId, e);
    }

    @Override
    public JSONObject inBackground(JSONObject jsonObject) throws Exception {
    return transform(jsonObject);
    }
}
