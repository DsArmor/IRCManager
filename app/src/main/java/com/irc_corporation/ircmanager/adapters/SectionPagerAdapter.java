package com.irc_corporation.ircmanager.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.irc_corporation.ircmanager.fragments.CalendarViewFragment;
import com.irc_corporation.ircmanager.fragments.TaskFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter{

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new TaskFragment();
            case 1:
                return new CalendarViewFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        //не могу поправить, чтобы обратиться к ресурсу string и вытащить оттуда
        //todo:поправить
        switch (position){
            case 0:
                return "Сегодня";
            case 1:
                return "Другой вид";
        }
        return null;
    }
}
