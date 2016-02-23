package com.rivetlogic.mobile.liferaytodoslibrary.interactor.taskbyuserid;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.task.TaskService;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.rivetlogic.mobile.liferaytodos.constants.TodosConstants;
import com.rivetlogic.mobile.liferaytodoslibrary.interactor.TodosListener;

/**
 * Created by ronnyvargas on 01/27/16.
 */
public class TaskByUserIdInteractorImpl
        extends BaseRemoteInteractor<TodosListener>
        implements TaskByUserIdInteractor {

    private Context context;

    public TaskByUserIdInteractorImpl(int screenletId, Context context) {
        super(screenletId);
        this.context = context;
    }

    @Override
    public void loadTasks (final long userId) throws Exception {
        TaskService taskService = getTaskService();
        taskService.getTaskByUserId(userId);

    }

    public void onEvent(TaskByUserIdEvent event) {
        if (!isValidEvent(event)) {
            return;
        }
        if (event.isFailed()) {
            getListener().onLoadToDosFailure(event.getException());

        } else {
            try {
                getListener().onLoadToDosSuccess(event.getJsonArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected TaskService getTaskService() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        LiferayServerContext.setServer(prefs.getString(
                TodosConstants.LIFERAY_SERVER, TodosConstants.LOCAL_LIFERAY_SERVER_ADDRESS));
        LiferayServerContext.setCompanyId(new Long(prefs.getString(
                TodosConstants.COMPANY_ID, TodosConstants.LOCAL_LIFERAY_COMPANY_ID)));
        Session session = SessionContext.createSessionFromCurrentSession();
        session.setCallback(new TaskByUserIdCallback(getTargetScreenletId()));
        return new TaskService(session);
    }
}
