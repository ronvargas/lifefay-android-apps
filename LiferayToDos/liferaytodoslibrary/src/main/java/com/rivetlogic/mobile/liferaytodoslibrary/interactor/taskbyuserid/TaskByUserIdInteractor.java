package com.rivetlogic.mobile.liferaytodoslibrary.interactor.taskbyuserid;

import com.rivetlogic.mobile.liferaytodoslibrary.interactor.TodosBaseInteractor;

/**
 * Created by ronnyvargas on 01/27/16.
 */
public interface TaskByUserIdInteractor extends TodosBaseInteractor {

    void loadTasks(long userId) throws Exception;
}
