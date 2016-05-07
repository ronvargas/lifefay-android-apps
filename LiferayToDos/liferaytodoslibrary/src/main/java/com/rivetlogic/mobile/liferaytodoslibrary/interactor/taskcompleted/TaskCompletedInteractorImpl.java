package com.rivetlogic.mobile.liferaytodoslibrary.interactor.taskcompleted;

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
 * Created by ronnyvargas on 05/04/16.
 */
public class TaskCompletedInteractorImpl
        extends BaseRemoteInteractor<TodosListener>
        implements TaskCompletedInteractor {

    private Context context;

    public TaskCompletedInteractorImpl(int screenletId, Context context) {
        super(screenletId);
        this.context = context;
    }

    @Override
    public void setTaskCompleted(long taskId, boolean isCompleted) throws Exception {
        TaskService taskService = getTaskService();
        taskService.setTaskCompleted(taskId, isCompleted);

    }

    public void onEvent(TaskCompletedEvent event) {
        if (!isValidEvent(event)) {
            return;
        }
        if (event.isFailed()) {
            getListener().onSetTaskCompletedFailure(event.getException());

        } else {
            try {
                getListener().onSetTaskCompletedSuccess(event.getJSONObject());
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
        session.setCallback(new TaskCompletedCallback(getTargetScreenletId()));
        return new TaskService(session);
    }
}
