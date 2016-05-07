package com.rivetlogic.mobile.liferaytodoslibrary.interactor.updatetask;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.rivetlogic.mobile.liferaytodoslibrary.interactor.TodosBaseInteractor;

/**
 * Created by ronnyvargas on 05/04/16.
 */
public interface UpdateTaskInteractor extends TodosBaseInteractor {

    void updateTask(JSONObjectWrapper task) throws Exception;
}
