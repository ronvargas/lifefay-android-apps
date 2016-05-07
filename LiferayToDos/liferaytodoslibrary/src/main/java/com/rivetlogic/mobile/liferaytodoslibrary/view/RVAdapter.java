package com.rivetlogic.mobile.liferaytodoslibrary.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.screens.context.SessionContext;
import com.rivetlogic.mobile.liferaytodos.constants.TodosConstants;
import com.rivetlogic.mobile.liferaytodoslibrary.R;
import com.rivetlogic.mobile.liferaytodoslibrary.interactor.taskcompleted.TaskCompletedInteractor;
import com.rivetlogic.mobile.liferaytodoslibrary.interactor.taskcompleted.TaskCompletedInteractorImpl;
import com.rivetlogic.mobile.liferaytodoslibrary.interactor.updatetask.UpdateTaskInteractor;
import com.rivetlogic.mobile.liferaytodoslibrary.interactor.updatetask.UpdateTaskInteractorImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ronnyvargas on 2/1/16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TaskViewHolder> {

    private final int screenletId;
    private JSONArray tasks;
    private Context context;
    private RVAdapter thisAdapter = this;

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView taskId;
        private TextView taskTitle;
        private TextView taskDescription;
        private CheckBox taskDone;
        private TextView taskDate;
        private TextView taskCalendarBooking;
        private Button taskSaveButton;

        TaskViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            taskId = (TextView) itemView.findViewById(R.id.task_id);
            taskTitle = (TextView) itemView.findViewById(R.id.task_title);
            taskDescription = (TextView) itemView.findViewById(R.id.task_description);
            taskCalendarBooking = (TextView) itemView.findViewById(R.id.task_calendarBookingId);
            taskDone = (CheckBox) itemView.findViewById(R.id.task_done);
            taskDate = (TextView) itemView.findViewById(R.id.task_date);
            taskSaveButton = (Button) itemView.findViewById(R.id.button_task_save);
        }
    }

    public RVAdapter(JSONArray tasks, Context context, int screenletId) {
        this.tasks = tasks;
        this.context = context;
        this.screenletId = screenletId;
    }

    public void setTasks(JSONArray tasks) {
        this.tasks = tasks;
    }

    @Override
    public int getItemCount() {
        if (tasks == null) {
            return 0;
        } else {
            return tasks.length();
        }
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_list_item_layout, viewGroup, false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        return taskViewHolder;
    }


    @Override
    public void onBindViewHolder(TaskViewHolder taskViewHolder, int i) {
        try {
            taskViewHolder.taskId.setText(((JSONObject) tasks.get(i)).getString(TodosConstants.TASK_ID_KEY));
            taskViewHolder.taskTitle.setText(((JSONObject) tasks.get(i)).getString(TodosConstants.TASK_TITLE_KEY));
            taskViewHolder.taskDescription.setText(((JSONObject) tasks.get(i)).getString(TodosConstants.TASK_DESCRIPTION_KEY));
            taskViewHolder.taskCalendarBooking.setText(((JSONObject) tasks.get(i)).getString(TodosConstants.TASK_LIFERAY_CALENDAR_ID_KEY));
            Date taskDate = new Date(((JSONObject) tasks.get(i)).getLong(TodosConstants.TASK_DATE_KEY));
            Boolean completed =  ((JSONObject) tasks.get(i)).getBoolean(TodosConstants.TASK_IS_COMPLETED_KEY);
            taskViewHolder.taskDone.setChecked(completed);
            if (completed) {
                taskViewHolder.taskTitle.setPaintFlags(taskViewHolder.taskTitle.getPaintFlags()
                        | Paint.STRIKE_THRU_TEXT_FLAG);
            }

            CharSequence dateTimeString = getPrettyDate(taskDate);
            taskViewHolder.taskDate.setText(dateTimeString);
        } catch (JSONException e) {
            Log.e("LiferayToDos", "Exception loading data for the tasks list, item number:"+ i , e);

        }

        taskViewHolder.taskDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CardView currentCard = (CardView) v.getParent().getParent();
                TextView titleText = (TextView) currentCard.findViewById(R.id.task_title);
                TextView idText = (TextView) currentCard.findViewById(R.id.task_id);
                CheckBox isCompleted = (CheckBox) currentCard.findViewById(R.id.task_done);
                titleText.setPaintFlags(titleText.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
                try{
                    TaskCompletedInteractor taskCompletedInteractor = new TaskCompletedInteractorImpl(screenletId, context);
                    taskCompletedInteractor.setTaskCompleted(Integer.parseInt(idText.getText().toString()), isCompleted.isChecked());
                } catch (Exception e) {
                    Log.e("LiferayToDos", "Exception changing the completed state of the Task ", e);
                }
            }
        });

        taskViewHolder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                RelativeLayout viewLayout = (RelativeLayout) v.findViewById(R.id.task_view_layout);
                LinearLayout editLayout = (LinearLayout) v.findViewById(R.id.task_edit_layout);
                CardView currentCard = (CardView) editLayout.getParent();

                TextView titleText = (TextView) currentCard.findViewById(R.id.task_title);
                TextView descText = (TextView) currentCard.findViewById(R.id.task_description);
                TextView editTitleText = (TextView) currentCard.findViewById(R.id.task_edit_title);
                TextView editDescText = (TextView) currentCard.findViewById(R.id.task_edit_description);

                editTitleText.setText(titleText.getText());
                editDescText.setText(descText.getText());
                //change to edit mode;
                editLayout.setVisibility(View.VISIBLE);
                viewLayout.setVisibility(View.GONE);
                return true;
            }
        });

        taskViewHolder.taskSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    CardView currentCard = (CardView) v.getParent().getParent();
                    RelativeLayout viewLayout = (RelativeLayout) currentCard.findViewById(R.id.task_view_layout);
                    LinearLayout editLayout = (LinearLayout) currentCard.findViewById(R.id.task_edit_layout);

                    TextView idText = (TextView) currentCard.findViewById(R.id.task_id);
                    TextView calendarBookingText = (TextView) currentCard.findViewById(R.id.task_calendarBookingId);
                    CheckBox isCompleted = (CheckBox) currentCard.findViewById(R.id.task_done);
                    TextView titleText = (TextView) currentCard.findViewById(R.id.task_edit_title);
                    TextView descText = (TextView) currentCard.findViewById(R.id.task_edit_description);
                    DatePicker date = (DatePicker) currentCard.findViewById(R.id.task_edit_date);
                    Calendar c = new GregorianCalendar(date.getYear(), date.getMonth(), date.getDayOfMonth(),23,59,59);

                    JSONObject jsonObj = new JSONObject();
                    jsonObj.accumulate(TodosConstants.TASK_ID_KEY, idText.getText());
                    jsonObj.accumulate(TodosConstants.TASK_DATE_KEY, c.getTime().getTime());
                    jsonObj.accumulate(TodosConstants.TASK_TITLE_KEY, titleText.getText());
                    jsonObj.accumulate(TodosConstants.TASK_DESCRIPTION_KEY, descText.getText());
                    jsonObj.accumulate(TodosConstants.TASK_USER_ID_KEY, SessionContext.getLoggedUser().getId());
                    jsonObj.accumulate(TodosConstants.TASK_LIFERAY_CALENDAR_ID_KEY, calendarBookingText.getText());
                    jsonObj.accumulate(TodosConstants.TASK_IS_COMPLETED_KEY, isCompleted.isChecked());

                    JSONObjectWrapper task = new JSONObjectWrapper("com.rivetlogic.portlet.todo.model.impl.TaskImpl",jsonObj);
                    UpdateTaskInteractor updateTaskInteractor = new UpdateTaskInteractorImpl(screenletId, context);
                    updateTaskInteractor.updateTask(task);

                    editLayout.setVisibility(View.GONE);
                    viewLayout.setVisibility(View.VISIBLE);

                    TextView titleDisplayText = (TextView) currentCard.findViewById(R.id.task_title);
                    TextView descDisplayText = (TextView) currentCard.findViewById(R.id.task_description);
                    TextView displayDate = (TextView) currentCard.findViewById(R.id.task_date);
                    titleDisplayText.setText(titleText.getText());
                    descDisplayText.setText(descText.getText());
                    displayDate.setText(getPrettyDate(c.getTime()));

                } catch (Exception e) {
                    Log.e("LiferayToDos", "Exception updating the Task ", e);
                }
            }
        });


    }

    private CharSequence getPrettyDate(Date taskDate) {
        CharSequence dateTimeString = DateUtils.getRelativeDateTimeString(this.context, taskDate.getTime(),
                DateUtils.DAY_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_WEEKDAY);
        dateTimeString = dateTimeString.subSequence( 0, dateTimeString.toString().indexOf(",") );
        return dateTimeString;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}