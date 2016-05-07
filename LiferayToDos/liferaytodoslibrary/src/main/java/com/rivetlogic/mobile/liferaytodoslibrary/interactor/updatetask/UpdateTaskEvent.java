package com.rivetlogic.mobile.liferaytodoslibrary.interactor.updatetask;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;

import org.json.JSONObject;

public class UpdateTaskEvent extends JSONObjectEvent {

    public UpdateTaskEvent(int targetScreenletId, Exception e) {
        super(targetScreenletId, e);
    }

    public UpdateTaskEvent(int targetScreenletId, JSONObject jsonObject) {
        super(targetScreenletId, jsonObject);
    }
}