package com.irc_corporation.ircmanager.view.adapters;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.model.GroupTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<GroupTask> tasks = new ArrayList<>();
    private static final String LOG_TAG = "TaskAdapter";

    public void setTasks(List<GroupTask> tasks) {
        Log.d(LOG_TAG, "setTasks");
        this.tasks = tasks;
        Collections.sort(this.tasks);
        Log.d(LOG_TAG, "В адаптере: " + this.tasks.size() + " элементов");
    }

    public List<GroupTask> getTasks() {
        return tasks;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        CardView cv;
        cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_tasks, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        //ты тут хочешь просто после viewHolder написать getBinding и тогда дублировать для каждого действия и все?
        GroupTask task = tasks.get(position);
        viewHolder.textViewTaskName.setText(task.getName());
        if (task.getDescription()!=null){
            viewHolder.textViewDescription.setVisibility(View.VISIBLE);
            viewHolder.textViewDescription.setText(task.getDescription());
        } else{
            viewHolder.textViewDescription.setVisibility(View.GONE);
        }
        viewHolder.textViewGroupName.setText(task.getGroup().getName());
        if (task.getDueDate() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(task.getDueDate());
            int diffDays = (int) ((task.getDueDate().getTime()-(new Date().getTime())) / (24 * 60 * 60 * 1000));
            System.out.println("разница в днях"+diffDays);
            viewHolder.textViewData.setText(String.format(Locale.getDefault(), "%02d.%02d.%d", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR)));
            if (diffDays<7){
                int c1 =  viewHolder.itemView.getContext().getResources().getColor(R.color.warning);
                viewHolder.imageViewWarningHand.setColorFilter(c1);
            }
            if (diffDays<3){
                int c1 =  viewHolder.itemView.getContext().getResources().getColor(R.color.soon_deprecate);
                viewHolder.imageViewWarningHand.setColorFilter(c1);
            }
            if (diffDays>7){
                int c1 =  viewHolder.itemView.getContext().getResources().getColor(R.color.green);
                viewHolder.imageViewWarningHand.setColorFilter(c1);
            }
            if (diffDays<0){
                viewHolder.textViewDeprecated.setVisibility(View.VISIBLE);
            } else
                viewHolder.textViewDeprecated.setVisibility(View.GONE);
        } else {
            viewHolder.textViewData.setText("-");
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewTaskName;
        private final TextView textViewDescription;
        private final TextView textViewGroupName;
        private final TextView textViewData;
        private final ImageView imageViewWarningHand;
        private final TextView textViewDeprecated;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            textViewTaskName = itemView.findViewById(R.id.task_name_in_card_tasks);
            textViewDescription = itemView.findViewById(R.id.task_description_in_card_tasks);
            textViewGroupName = itemView.findViewById(R.id.group_name_in_card_tasks);
            textViewData = itemView.findViewById(R.id.task_date_in_card);
            imageViewWarningHand = itemView.findViewById(R.id.warning_hand);
            textViewDeprecated = itemView.findViewById(R.id.deprecated);
        }
    }
}
