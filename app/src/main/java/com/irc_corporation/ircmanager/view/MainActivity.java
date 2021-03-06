package com.irc_corporation.ircmanager.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.view.fragments.AddGroupFragment;
import com.irc_corporation.ircmanager.view.fragments.AddTaskFragment;
import com.irc_corporation.ircmanager.view.fragments.GroupFragment;
import com.irc_corporation.ircmanager.view.fragments.MembersDialogFragment;
import com.irc_corporation.ircmanager.view.fragments.TaskFragment;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;


public class MainActivity extends AppCompatActivity implements Listener {

    private static final String LOG_TAG = "MainActivity";
    public BottomNavigationView bottomNavigationMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        bottomNavigationMenu = findViewById(R.id.nav_view);
        bottomNavigationMenu.setOnNavigationItemSelectedListener(navListener);


//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this,
//                drawer,
//                toolbar,
//                R.string.nav_open_drawer,
//                R.string.nav_close_drawer
//        );
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        View header = navigationView.getHeaderView(0);
//        ImageButton imageButton = header.findViewById(R.id.leave_from);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(LOG_TAG, "User Leave");
//                SharedPreferences prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = prefs.edit();
//                editor.clear();
//                editor.commit();
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Fragment fragment = new TaskFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_container, fragment).commit();
        SharedPreferences prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (!(prefs.contains("email") && prefs.contains("password"))) {
            Log.d(LOG_TAG, "User Not Logged In");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }


    private final BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    Fragment fragment = null;
                    //???????????????????? ?????????? onCreate ?????? ?????????????? ?????????????????? ?????? ???????????????????????? ???? ?????????????????????????? ??????????????, ?????????? ?? ??????
                    //???????? ?????????? ???????????? ???????????????? ?????????????? ??????????????????)
                    switch (id) {
                        case R.id.nav_group:
                            fragment = new GroupFragment();
                            break;
                        case R.id.nav_tasks:
                            fragment = new TaskFragment();
                            break;
                        //???????????? ?????????????????? ?? ???????????????????????? ????????, ?????????? ?????????????? ?????? ????????????
                    }
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    assert fragment != null;
                    ft.replace(R.id.content_container, fragment).commit();
                    return true;
                }
            };
//    @SuppressLint("NonConstantResourceId")
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        int id = menuItem.getItemId();
//        Fragment fragment = null;
//        //???????????????????? ?????????? onCreate ?????? ?????????????? ?????????????????? ?????? ???????????????????????? ???? ?????????????????????????? ??????????????, ?????????? ?? ??????
//        //???????? ?????????? ???????????? ???????????????? ?????????????? ??????????????????)
//        switch (id) {
//            case R.id.nav_group:
//                fragment = new GroupFragment();
//                break;
//            case R.id.nav_tasks:
//                fragment = new TaskFragment();
//                break;
//            //???????????? ?????????????????? ?? ???????????????????????? ????????, ?????????? ?????????????? ?????? ????????????
//        }
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        assert fragment != null;
//        ft.replace(R.id.content_container, fragment).commit();
//        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.exit_from_activity);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(LOG_TAG, "User Leave");
                SharedPreferences prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onMyClick(int id) {
        //???????????????? ?????????? ???????????? show
        if (id == 1) {
            Fragment fragment = new AddTaskFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.content_container, fragment).commit();
            bottomNavigationMenu.setVisibility(View.GONE);
        } else if (id == 2) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(null);
            DialogFragment fragment = new AddGroupFragment();
            fragment.show(ft, "AddGroup");
        }
    }

}