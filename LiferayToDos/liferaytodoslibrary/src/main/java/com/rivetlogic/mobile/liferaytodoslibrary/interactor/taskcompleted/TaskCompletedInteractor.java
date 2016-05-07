package com.rivetlogic.mobile.liferaytodoslibrary.interactor.taskcompleted;

import com.rivetlogic.mobile.liferaytodoslibrary.interactor.TodosBaseInteractor;

/**
 * Created by ronnyvargas on 05/04/16.
 */
public interface TaskCompletedInteractor extends TodosBaseInteractor {

    void setTaskCompleted(long taskId, boolean isCompleted) throws Exception;
}
