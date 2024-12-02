package com.example.mhealth.appointment_diary.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mhealth.appointment_diary.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;



public class ErrorMessage extends BottomSheetDialogFragment {


    private String error;
    private Context context;
   // private Unbinder unbinder;
    private String title = null;
    TextView errorMessage;
    TextView titleTextView;

    public ErrorMessage() {
        // Required empty public constructor
    }



    public static ErrorMessage newInstance(String error, Context context) {
        ErrorMessage fragment = new ErrorMessage();
        fragment.error = error;
        fragment.context = context;
        return fragment;
    }

    public static ErrorMessage newInstance(String title, String error, Context context) {
        ErrorMessage fragment = new ErrorMessage();
        fragment.error = error;
        fragment.title = title;
        fragment.context = context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.error_bottom_sheet, container, false);

        errorMessage=view.findViewById(R.id.error_message);
        titleTextView=view.findViewById(R.id.title);
        //unbinder = ButterKnife.bind(this, view);

        errorMessage.setText(error);
        if (title != null)
            titleTextView.setText(title);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // unbinder.unbind();
    }
}
