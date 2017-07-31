package com.identos.android.id100.demo.data;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.identos.android.id100.demo.R;

import java.util.ArrayList;

public class DataFragment extends Fragment {
    private static final String EXTRA_DATA = "extra:data";

    public DataFragment() {
        // default empty constructor
    }

    public static DataFragment newInstance(ArrayList<DataItem> dataItems) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_DATA, dataItems);

        DataFragment fragment = new DataFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_data, container, false);

        ListView listView = (ListView) rootView.findViewById(android.R.id.list);

        ArrayList<DataItem> list = getArguments().getParcelableArrayList(EXTRA_DATA);

        listView.setAdapter(new DataListAdapter(getContext(), list));

        return rootView;
    }
}