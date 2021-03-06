package com.maksg.mobilebutler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.maksg.mobilebutler.R;
import com.maksg.mobilebutler.adapters.TaskAdapter;
import com.maksg.mobilebutler.schedulables.SettingsChangeTask;

public class TasksTabFragment extends TabFragment {
    private TaskAdapter taskAdapter = new TaskAdapter();
    private TextView textView;
    private String emptyMes;

    public static TasksTabFragment getInstance(Context context, String emptyMes, String title) {
        Bundle args = new Bundle();
        TasksTabFragment fragment = new TasksTabFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(title);
        fragment.emptyMes = emptyMes;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_tab, container, false);
        initTaskTabTextView();
        initRecyclerView();
        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void addTask(SettingsChangeTask settingsChangeTask) {
        taskAdapter.addTask(settingsChangeTask);
    }

    private void initTaskTabTextView() {
        textView = (TextView) view.findViewById(R.id.taskTabTextView);
        textView.setText(emptyMes);
        textView.setAlpha(0.75F);
    }

    private void initRecyclerView() {
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(taskAdapter);

        recyclerView.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                textView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
                if (taskAdapter.getItemCount() == 0)
                    textView.setVisibility(View.VISIBLE);
            }
        });
    }
}