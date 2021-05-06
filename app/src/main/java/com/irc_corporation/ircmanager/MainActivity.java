package com.irc_corporation.ircmanager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.irc_corporation.ircmanager.fragments.AddGroupFragment;
import com.irc_corporation.ircmanager.fragments.AddTaskFragment;
import com.irc_corporation.ircmanager.fragments.GroupFragment;
import com.irc_corporation.ircmanager.fragments.TaskFragment;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Listener, DismissListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = new TaskFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Fragment fragment = null;

        //вызывается метод onCreate для каждого фрагмента при переключении по навигационным кнопкам, можно и так
        //либо нужно каждый фрагмент сделать одиночкой)
        switch (id){
            case R.id.nav_group:
                fragment = new GroupFragment();
                break;
            case R.id.nav_tasks:
                fragment = new TaskFragment();
                break;
                //допиши навигацию к календарному виду, когда найдешь ему иконку
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        assert fragment != null;
        ft.replace(R.id.content_container, fragment).commit();
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMyClick(int id) {
        if (id == 1){
            Fragment fragment = new AddTaskFragment();
            FragmentTransaction ft =  getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.content_container, fragment).commit();
        } else if (id==2){
            DialogFragment fragment = new AddGroupFragment();
            fragment.show(getSupportFragmentManager(), "AddGroup");
        }
    }


    @Override
    public void onDismiss() {
        Fragment fragment = new GroupFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_container, fragment).commit();
    }
}