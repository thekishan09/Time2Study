package com.android.time2study;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class DatePickUpFragment extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), c.get(1), c.get(2), c.get(5));
    }
}
