package com.irc_corporation.ircmanager.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.CalendarView;
import android.widget.Spinner;

import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.databinding.FragmentAddTaskBinding;
import com.irc_corporation.ircmanager.viewmodels.AddTaskViewModel;

import java.util.Date;

public class AddTaskFragment extends DialogFragment {

    private Listener listener;
    private FragmentAddTaskBinding binding;
    private static final String LOG_TAG = "AddTaskFragment";
    private AddTaskViewModel addTaskViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_task, container, false);
        addTaskViewModel = new ViewModelProvider(this).get(AddTaskViewModel.class);
        binding.setAddTaskViewModel(addTaskViewModel);

        //этого тут быть не должно
        addTaskViewModel.setFragmentManager(getActivity().getSupportFragmentManager());

        SharedPreferences prefs = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        addTaskViewModel.setSharedPreferences(prefs);

        //работа с календарем
        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Log.d(LOG_TAG, Integer.toString(year) + " " + Integer.toString(month) + " " + Integer.toString(dayOfMonth));
                addTaskViewModel.setDueDate(new Date(year - 1900, month, dayOfMonth));
            }
        });

        //работа с спиннером для групп
        //todo:почему при биндинге, здесь до сих пор findViewById, а не просто binding.
        Spinner spinner = binding.spinnerGroups.findViewById(R.id.spinner_groups);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, addTaskViewModel.getGroups());
        spinner.setAdapter(adapter);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //установка группы, выбранной пользователем
                addTaskViewModel.setCheckedGroup((String) parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        //работа с кнопками отмена/добавление на xml фрагмента
        binding.addTaskComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //тут нужны в будущем проверки на корректность введенных данных
                //их можно сделать в viewModel, но тут нужна вывести реакцию на это
                //например не закрывать фрагмент, вывести тост
                addTaskViewModel.addTask();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        binding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
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
