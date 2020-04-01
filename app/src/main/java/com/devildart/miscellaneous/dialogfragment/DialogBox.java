package com.devildart.miscellaneous.dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.devildart.miscellaneous.R;

public class DialogBox extends DialogFragment {

    public static final Integer NORMAL_DIALOG = 0;
    public static final Integer LIST_DIALOG = 1;
    public static final Integer LOADING_DIALOG = 2;
    public static final Integer EDITABLE_DIALOG = 3;

    private static DialogBox dialogBox;
    private static FragmentManager manager;
    private static String tag;
    private static Integer layout;

    public static DialogBox getInstance(FragmentManager manager1, String tag1, Integer layoutType) {
        manager = manager1;
        tag = tag1;
        switch (layoutType) {
            case 0:
                layout = R.layout.dialog_normal;
                break;
            case 1:
                break;
            case 2:
                layout = R.layout.dialog_loading;
                break;
            case 3:
                break;
            default:
                layout = 0;
        }
        dialogBox = new DialogBox();
        return dialogBox;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View containerView = LayoutInflater.from(getContext()).inflate(layout, null, false);
        builder.setView(containerView);
        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void showDialog() {
        show(manager, tag != null ? tag : "Dialog_Fragment");
    }
}
