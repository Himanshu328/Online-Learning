package com.sgnr.sgnrclasses.ui.Help;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sgnr.sgnrclasses.R;
import com.sgnr.sgnrclasses.ui.FeedbackFragment;

public class HelpFragment extends Fragment {

    private TextView report;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        report = view.findViewById(R.id.textReport);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FeedbackFragment fragment = new FeedbackFragment();
               getActivity().getSupportFragmentManager().beginTransaction()
                       .replace(R.id.help,fragment,"feedback")
                       .addToBackStack(null)
                       .commit();
            }
        });

        return view;
    }
}