package com.irc_corporation.ircmanager.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.databinding.FragmentAddTaskBinding;
import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.GroupTask;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;
import com.irc_corporation.ircmanager.viewmodels.AddTaskViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AddTaskFragment extends DialogFragment {

    private Listener listener;
    private FragmentAddTaskBinding binding;
    private static final String LOG_TAG = "AddTaskFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_task, container, false);
        AddTaskViewModel addTaskViewModel = new ViewModelProvider(this).get(AddTaskViewModel.class);
        binding.setAddTaskViewModel(addTaskViewModel);
        addTaskViewModel.setFragmentManager(getActivity().getSupportFragmentManager());
        SharedPreferences prefs = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        addTaskViewModel.setSharedPreferences(prefs);
        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Log.d(LOG_TAG, Integer.toString(year) + " " + Integer.toString(month) + " " + Integer.toString(dayOfMonth));
                addTaskViewModel.setDueDate(new Date(year - 1900, month, dayOfMonth));
            }
        });

        //todo: здесь получение должно быть из viewmodel, а не вызов репозитория
        Repository repository = IRCRepository.getInstance();
        List<Group> groups = repository.getGroups().getValue();

        List<Group> currentGroup = new ArrayList<>();
        for (int i=0; i<groups.size(); i++){
            if (groups.get(i).getAdmin().getEmail().equals(prefs.getString("email", ""))){
                currentGroup.add(groups.get(i));
            }
        }
        String[] temp_groups = new String[currentGroup.size()];
        for (int i=0; i<currentGroup.size(); i++){
            temp_groups[i]=currentGroup.get(i).getName();
        }

        Spinner spinner = binding.spinnerGroups.findViewById(R.id.spinner_groups);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, temp_groups);
        spinner.setAdapter(adapter);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addTaskViewModel.setCheckedGroup((String) parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
    }

}
