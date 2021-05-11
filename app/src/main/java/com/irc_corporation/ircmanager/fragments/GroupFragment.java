package com.irc_corporation.ircmanager.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.adapters.GroupAdapter;
import com.irc_corporation.ircmanager.databinding.FragmentGroupBinding;
import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;
import com.irc_corporation.ircmanager.viewmodels.GroupViewModel;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class GroupFragment extends Fragment implements View.OnClickListener{

    private Listener listener;

    private static final String LOG_TAG = "GroupFragment";
    private FragmentGroupBinding binding;

    SharedPreferences prefs;
    SwipeRefreshLayout swipeRefresh;
    TextView textViewForAdmin;
    TextView textViewForMember;
    RecyclerView recyclerViewForAdmin;
    RecyclerView recyclerViewForMember;
    RelativeLayout relativeLayoutForAdmin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_group, container, false);
        prefs = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        FloatingActionButton button = binding.getRoot().findViewById(R.id.add_new_group);
        button.setOnClickListener(this);

        relativeLayoutForAdmin = binding.relativeGroupsWhereYouAdmin;
        recyclerViewForMember = binding.recyclerGroupsWhereYouMember;
        recyclerViewForAdmin = binding.recyclerGroupsWhereYouAdmin;
        swipeRefresh = binding.swipeRefreshGroupWhereYouMember;


        int c1 = getResources().getColor(R.color.light_green);
        int c2 = getResources().getColor(R.color.white);
        int c3 = getResources().getColor(R.color.light_green);
        swipeRefresh.setColorSchemeColors(c1, c2, c3);


        //получение данных с сервера

        GroupAdapter adapterForAdmin = new GroupAdapter();
        recyclerViewForAdmin.setAdapter(adapterForAdmin);

        GroupAdapter adapterForMember = new GroupAdapter();
        recyclerViewForAdmin.setAdapter(adapterForAdmin);

        LinearLayoutManager layoutManagerForAdmin = new LinearLayoutManager(getActivity());
        LinearLayoutManager layoutManagerForMember = new LinearLayoutManager(getActivity());
        recyclerViewForAdmin.setLayoutManager(layoutManagerForAdmin);
        recyclerViewForMember.setLayoutManager(layoutManagerForMember);


        ItemTouchHelper.SimpleCallback callbackForAdmin = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT){
                    //--------перенести логику в вьюМодель
                    Group group =((GroupAdapter)recyclerViewForAdmin.getAdapter()).getGroups().get(viewHolder.getAdapterPosition());
                    Repository repository = IRCRepository.getInstance();
                    repository.delete(
                            prefs.getString("email", ""),
                            prefs.getString("password", ""),
                            group.getName()
                    );
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c,
                                    @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX,
                                    float dY,
                                    int actionState,
                                    boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightActionIcon(R.drawable.baseline_group_off_24)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(getActivity(), R.color.light_green))
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        GroupViewModel groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        groupViewModel.setSharedPreferences(getActivity().getSharedPreferences("settings",Context.MODE_PRIVATE));
        new ItemTouchHelper(callbackForAdmin).attachToRecyclerView(recyclerViewForAdmin);

        groupViewModel.getGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                Log.d(LOG_TAG, "OnChanged");
                adapterForAdmin.setGroups(groupViewModel.getGroupAdmin());
                adapterForAdmin.notifyDataSetChanged();
                adapterForMember.setGroups(groupViewModel.getGroupsMember());
                adapterForMember.notifyDataSetChanged();
            }
        });


        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                groupViewModel.refresh();
                swipeRefresh.setRefreshing(false);
            }
        });

        return binding.getRoot();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onMyClick(2);
        }
    }

}