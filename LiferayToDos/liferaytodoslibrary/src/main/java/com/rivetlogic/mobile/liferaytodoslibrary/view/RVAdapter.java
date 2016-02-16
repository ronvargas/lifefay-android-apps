package com.rivetlogic.mobile.liferaytodoslibrary.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.rivetlogic.mobile.liferaytodoslibrary.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ronnyvargas on 2/1/16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TaskViewHolder> {

    private JSONArray tasks;
    private Context context;

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView taskTitle;
        TextView taskDescription;
        CheckBox taskDone;
        TextView taskDate;

        TaskViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            taskTitle = (TextView) itemView.findViewById(R.id.task_title);
            taskDescription = (TextView) itemView.findViewById(R.id.task_description);
            taskDone = (CheckBox) itemView.findViewById(R.id.task_done);
            taskDate = (TextView) itemView.findViewById(R.id.task_date);
        }
    }

    public RVAdapter(JSONArray tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item, viewGroup, false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        return taskViewHolder;
    }


    @Override
    public void onBindViewHolder(TaskViewHolder taskViewHolder, int i) {
        try {
            taskViewHolder.taskTitle.setText(((JSONObject) tasks.get(i)).getString("name"));
            taskViewHolder.taskDescription.setText(((JSONObject) tasks.get(i)).getString("description"));
            taskViewHolder.taskDone.setChecked(((JSONObject) tasks.get(i)).getBoolean("completed"));
            Date taskDate = new Date(((JSONObject) tasks.get(i)).getLong("date"));

            CharSequence dateTimeString = DateUtils.getRelativeDateTimeString(this.context, taskDate.getTime(),
                    DateUtils.DAY_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_WEEKDAY);
            dateTimeString = dateTimeString.subSequence( 0, dateTimeString.toString().indexOf(",") );
            taskViewHolder.taskDate.setText( dateTimeString );

        } catch (JSONException e) {
            // TODO handle exc
            e.printStackTrace();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}