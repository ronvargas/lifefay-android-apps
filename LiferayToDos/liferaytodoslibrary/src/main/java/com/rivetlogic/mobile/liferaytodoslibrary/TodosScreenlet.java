package com.rivetlogic.mobile.liferaytodoslibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.rivetlogic.mobile.liferaytodoslibrary.interactor.TodosBaseInteractor;
import com.rivetlogic.mobile.liferaytodoslibrary.interactor.TodosListener;
import com.rivetlogic.mobile.liferaytodoslibrary.interactor.taskbyuserid.TaskByUserIdInteractor;
import com.rivetlogic.mobile.liferaytodoslibrary.interactor.taskbyuserid.TaskByUserIdInteractorImpl;
import com.rivetlogic.mobile.liferaytodoslibrary.view.RVAdapter;
import com.rivetlogic.mobile.liferaytodoslibrary.view.TodosViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ronnyvargas on 01/27/16.
 */
public class TodosScreenlet extends BaseScreenlet<TodosViewModel, TodosBaseInteractor>
        implements TodosListener {

    public static final String LOAD_TO_DOS = "LOAD_TO_DOS";
    public static final String FILTER_PAST_DUE = "FILTER_PAST_DUE";
    public static final String FILTER_DUE_TODAY = "FILTER_DUE_TODAY";
    public static final String FILTER_DUE_TOMORROW = "FILTER_DUE_TOMORROW";
    public static final String FILTER_FUTURE = "FILTER_FUTURE";
    private JSONArray previousTasks;
    private JSONArray todayTasks;
    private JSONArray tomorrowTasks;
    private JSONArray futureTasks;
    private String appliedFilter = FILTER_DUE_TODAY;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView mRecyclerView;
    private TextView emptyView;
    private RVAdapter mAdapter;
    private TodosBaseInteractor singleInteractor;

    private TodosListener _listener;
    private Context context;

    public TodosScreenlet(Context context) {
        super(context);
        this.context = context;
    }

    public TodosScreenlet(Context context, AttributeSet attributes) {
        super(context, attributes);
        this.context = context;
    }

    public TodosScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
        this.context = context;
    }

    public void loadToDos() {
        performUserAction(LOAD_TO_DOS);
    }

    public void loadDueTodayTasks() {
        appliedFilter = FILTER_DUE_TODAY;
        performUserAction(FILTER_DUE_TODAY);
    }

    public void loadDueTomorrowTasks() {
        appliedFilter = FILTER_DUE_TOMORROW;
        performUserAction(FILTER_DUE_TOMORROW);
    }

    public void loadFutureTasks() {
        appliedFilter = FILTER_FUTURE;
        performUserAction(FILTER_FUTURE);
    }

    public void loadPastDueTasks() {
        appliedFilter = FILTER_PAST_DUE;
        performUserAction(FILTER_PAST_DUE);
    }

    //   ------------ BASE SCREENLET: createView, createInteractor, onUserAction ------------
    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attributes, R.styleable.LoginScreenlet, 0, 0);


        int layoutId = typedArray.getResourceId(R.styleable.LoginScreenlet_layoutId, getDefaultLayoutId());
        typedArray.recycle();
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(layoutId, null, false);
    }

    @Override
    protected TodosBaseInteractor createInteractor(String actionName) {
        if (singleInteractor == null) {
            singleInteractor = new TaskByUserIdInteractorImpl(getScreenletId(), this.context);
        }
        return singleInteractor;
    }

    @Override
    protected void onUserAction(String userActionName, TodosBaseInteractor interactor, Object... args) {
        try {
            if (LOAD_TO_DOS.equals(userActionName)) {
                TaskByUserIdInteractor taskByUserIdInteractor = (TaskByUserIdInteractor) getInteractor(userActionName);
                taskByUserIdInteractor.loadTasks(SessionContext.getLoggedUser().getId());
            } else if (FILTER_DUE_TODAY.equals(userActionName)) {
                refreshTasks(todayTasks);
            } else if (FILTER_DUE_TOMORROW.equals(userActionName)) {
                refreshTasks(tomorrowTasks);
            } else if (FILTER_FUTURE.equals(userActionName)) {
                refreshTasks(futureTasks);
            } else if (FILTER_PAST_DUE.equals(userActionName)) {
                refreshTasks(previousTasks);
            }
        } catch (Exception e) {
            onLoadToDosFailure(e);
        }
    }

    @Override
    protected void onScreenletAttached() {
        // actions after Activity's "onCreate" call
        emptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadToDos();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_light,
                android.R.color.background_dark,
                android.R.color.background_light);

        //load the first time
        loadToDos();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable inState) {
        super.onRestoreInstanceState(inState);
//        Bundle bundle = (Bundle) inState;
//        super.onRestoreInstanceState(bundle.getParcelable(_STATE_SUPER));
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(_STATE_SUPER, super.onSaveInstanceState());
//        bundle.putString(_STATE_FILE_PATH, _filePath);
//        return bundle;
    }


    //   ------------------ LISTENER IMPL: action from interactors ------------------------

    //   ------------------ LOAD TASK LIST action ------------------------
    @Override
    public void onLoadToDosSuccess(JSONArray jsonArray) {
        if (_listener != null) {
            _listener.onLoadToDosSuccess(jsonArray);
        }
        groupTasksByDate(jsonArray);

        switch (appliedFilter) {
            case FILTER_DUE_TODAY:
                refreshTasks(todayTasks);
                break;
            case FILTER_DUE_TOMORROW:
                refreshTasks(tomorrowTasks);
                break;
            case FILTER_FUTURE:
                refreshTasks(futureTasks);
                break;
            case FILTER_PAST_DUE:
                refreshTasks(previousTasks);
                break;
        }
    }

    @Override
    public void onLoadToDosFailure(Exception exception) {
        getViewModel().showFailedOperation(null, exception);
        if (_listener != null) {
            _listener.onLoadToDosFailure(exception);
        }
        swipeContainer.setRefreshing(false);
    }


    private void refreshTasks(JSONArray jsonArray) {
        if (mAdapter == null) {
            mAdapter = new RVAdapter(jsonArray, getContext(), getScreenletId());
        } else {
            mAdapter.setTasks(jsonArray);
        }
        if (mAdapter.getItemCount() == 0)
        {
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }else{
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        swipeContainer.setRefreshing(false);
        mRecyclerView.invalidate();
    }

    private void groupTasksByDate(JSONArray allTasks) {
        previousTasks = new JSONArray();
        todayTasks = new JSONArray();
        tomorrowTasks = new JSONArray();
        futureTasks = new JSONArray();

        Date now = new Date();
        Calendar today = getCalendarWithOutTime(now);
        Calendar tomorrow = getCalendarWithOutTime(now);
        tomorrow.add(Calendar.DATE, 1);

        try {
            for (int t = 0; t < allTasks.length(); t++) {
                JSONObject task = allTasks.getJSONObject(t);
                Calendar taskDate = getCalendarWithOutTime(new Date(task.getLong("date")));

                if (taskDate.equals(today)) {
                    todayTasks.put(task);
                } else if (taskDate.before(today)) {
                    previousTasks.put(task);
                } else if (taskDate.equals(tomorrow)) {
                    tomorrowTasks.put(task);
                } else if (taskDate.after(tomorrow)) {
                    futureTasks.put(task);
                }
            }
        } catch (JSONException e) {
            Log.e("LiferayToDos", "Exception, can't get/read JSON object for the list of tasks", e);
        }

    }

    public static Calendar getCalendarWithOutTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        return cal;
    }

    //   ------------------ SET CHECKBOX CHANGED action ------------------------
    @Override
    public void onSetTaskCompletedSuccess(JSONObject jsonObject) {
        if (_listener != null) {
            _listener.onSetTaskCompletedSuccess(jsonObject);
        }
        Toast.makeText(context,"check action saved",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSetTaskCompletedFailure(Exception exception) {
        getViewModel().showFailedOperation(null, exception);
        if (_listener != null) {
            _listener.onSetTaskCompletedFailure(exception);
        }
    }

    //   ------------------ UPDATE TASK action ------------------------
    @Override
    public void onUpdateTaskSuccess(JSONObject jsonObject) {
        if (_listener != null) {
            _listener.onUpdateTaskSuccess(jsonObject);
        }
        Toast.makeText(context,"task changes saved",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateTaskFailure(Exception exception) {
        getViewModel().showFailedOperation(null, exception);
        if (_listener != null) {
            _listener.onUpdateTaskFailure(exception);
        }
    }
}
