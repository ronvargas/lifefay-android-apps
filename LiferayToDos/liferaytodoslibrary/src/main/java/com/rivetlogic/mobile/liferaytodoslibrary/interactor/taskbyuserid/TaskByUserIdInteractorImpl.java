package com.rivetlogic.mobile.liferaytodoslibrary.interactor.taskbyuserid;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.task.TaskService;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.context.SessionContext;
import com.rivetlogic.mobile.liferaytodoslibrary.interactor.TodosListener;

/**
 * Created by ronnyvargas on 01/27/16.
 */
public class TaskByUserIdInteractorImpl
        extends BaseRemoteInteractor<TodosListener>
        implements TaskByUserIdInteractor {

    public TaskByUserIdInteractorImpl(int screenletId) {
        super(screenletId);
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
        Session session = SessionContext.createSessionFromCurrentSession();
        session.setCallback(new TaskByUserIdCallback(getTargetScreenletId()));
        return new TaskService(session);
    }
}
