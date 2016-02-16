package com.rivetlogic.mobile.liferaytodoslibrary.interactor;

/**
 * Created by ronnyvargas on 01/27/16.
 */

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;

import org.json.JSONObject;

public class TodosBaseCallback extends InteractorAsyncTaskCallback<JSONObject> {

    public TodosBaseCallback(int targetScreenletId) {
        super(targetScreenletId);
    }

    @Override
    public JSONObject transform(Object obj) throws Exception {
        return (JSONObject) obj;
    }

    @Override
    protected BasicEvent createEvent(int targetScreenletId, JSONObject result) {
        return new TodosBaseEvent(targetScreenletId, result);
    }

    @Override
    protected BasicEvent createEvent(int targetScreenletId, Exception e) {
        return new TodosBaseEvent(targetScreenletId, e);
    }


}
