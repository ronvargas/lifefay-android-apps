package com.rivetlogic.mobile.liferaytodoslibrary.interactor;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;

import org.json.JSONObject;

public class TodosBaseEvent extends JSONObjectEvent {

    public TodosBaseEvent(int targetScreenletId, Exception e) {
        super(targetScreenletId, e);
    }

    public TodosBaseEvent(int targetScreenletId, JSONObject jsonObject) {
        super(targetScreenletId, jsonObject);
    }

}