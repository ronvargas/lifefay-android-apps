package com.rivetlogic.mobile.liferaytodoslibrary.interactor.taskbyuserid;

import com.liferay.mobile.screens.base.interactor.JSONArrayEvent;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;

import org.json.JSONArray;
import org.json.JSONObject;

public class TaskByUserIdEvent extends JSONArrayEvent {

    public TaskByUserIdEvent(int targetScreenletId, Exception e) {
        super(targetScreenletId, e);
    }

    public TaskByUserIdEvent(int targetScreenletId, JSONArray jsonArray) {
        super(targetScreenletId, jsonArray);
    }
}