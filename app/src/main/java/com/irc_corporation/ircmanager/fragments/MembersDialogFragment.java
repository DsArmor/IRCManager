package com.irc_corporation.ircmanager.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.irc_corporation.ircmanager.R;

public class MembersDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_members_dialog, container, false);

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, new String[]{"few", "few", "ewrwe"});
        ListView listMembers = (ListView) rootView.findViewById(R.id.list_members);
        listMembers.setAdapter(listAdapter);

        return rootView;
    }
}