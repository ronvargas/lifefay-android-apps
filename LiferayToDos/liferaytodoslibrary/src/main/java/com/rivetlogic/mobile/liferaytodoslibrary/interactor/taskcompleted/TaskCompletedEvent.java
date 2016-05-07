package com.rivetlogic.mobile.liferaytodoslibrary.interactor.taskcompleted;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;

import org.json.JSONObject;

public class TaskCompletedEvent extends JSONObjectEvent {

    public TaskCompletedEvent(int targetScreenletId, Exception e) {
        super(targetScreenletId, e);
    }

    public TaskCompletedEvent(int targetScreenletId, JSONObject jsonObject) {
        super(targetScreenletId, jsonObject);
    }
}