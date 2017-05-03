package com.maksg.mobilebutler.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.maksg.mobilebutler.R;
import com.maksg.mobilebutler.SCTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<SCTask> tasks = new ArrayList<>();
    private Handler handler = new Handler();

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_card_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.settings = tasks.get(position).getFormattedSettings();
        holder.toolbar.setTitle(tasks.get(position).getName());
        holder.selectedTime.setText(tasks.get(position).getFormattedStartMoment(holder.itemView.getContext()));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void addTask(final SCTask SCTask) {
        tasks.add(SCTask);
        notifyDataSetChanged();

        SCTask.schedule();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (tasks.contains(SCTask))
                    removeAt(tasks.indexOf(SCTask));
            }
        }, SCTask.getStartMoment().getTimeInMillis() - Calendar.getInstance().getTimeInMillis());
    }

    private void removeAt(int position) {
        tasks.get(position).cancel();
        tasks.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tasks.size());
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        String settings;
        Toolbar toolbar;
        TextView selectedTime;
        Button showSettings;
        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage(settings)
                        .setTitle("Настройки для " + toolbar.getTitle())
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };

        TaskViewHolder(final View itemView) {
            super(itemView);

            selectedTime = (TextView) itemView.findViewById(R.id.selectedTime);

            showSettings = (Button) itemView.findViewById(R.id.showSettings);
            showSettings.setOnClickListener(onClickListener);

            initToolbar(itemView);
        }

        private void initToolbar(View itemView) {
            toolbar = (Toolbar) itemView.findViewById(R.id.cardToolbar);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    removeAt(getAdapterPosition());
                    return true;
                }
            });

            toolbar.inflateMenu(R.menu.card);
        }
    }
}
