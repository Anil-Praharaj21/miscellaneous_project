package com.devildart.miscellaneous.dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.devildart.miscellaneous.R;

import java.lang.reflect.Field;

public class DialogBox extends DialogFragment {

    public static final Integer NORMAL_DIALOG = 0;
    public static final Integer LIST_DIALOG = 1;
    public static final Integer LOADING_DIALOG = 2;
    public static final Integer EDITABLE_DIALOG = 3;
    public static final Integer FULL_SCREEN_DIALOG = 4;

    private static DialogBox dialogBox;
    private static FragmentManager manager;
    private static String tag;
    private static Integer layout;

    private TextView titleView;
    private TextView subTitleView;
    private TextView submitBtnView;
    private ImageView statusImageView;

    private String titleStr, subTitleStr, submitBtnStr;
    private Integer statusImageDrawable;

    private DialogSubmitBtnCallback submitBtnCallback;

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
            case 4:
                layout = R.layout.dialog_full_screen;
                break;
            default:
                layout = 0;
        }
        dialogBox = new DialogBox();
        return dialogBox;
    }

    public DialogBox setTitleStr(String titleStr) {
        this.titleStr = titleStr;
        return this;
    }

    public DialogBox setSubTitleStr(String subTitleStr) {
        this.subTitleStr = subTitleStr;
        return this;
    }

    public DialogBox setSubmitBtnStr(String submitBtnStr) {
        this.submitBtnStr = submitBtnStr;
        return this;
    }

    public DialogBox setStatusImageDrawable(Integer statusImageDrawable) {
        this.statusImageDrawable = statusImageDrawable;
        return this;
    }

    public DialogBox setSubmitBtnCallback(DialogSubmitBtnCallback submitBtnCallback) {
        this.submitBtnCallback = submitBtnCallback;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layout != R.layout.dialog_full_screen) {
            return super.onCreateView(inflater, container, savedInstanceState);
        } else {
            return inflater.inflate(layout, container, false);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (layout != R.layout.dialog_full_screen) {
            AlertDialog.Builder builder = null;
            builder = new AlertDialog.Builder(getActivity());
            View containerView = LayoutInflater.from(getContext()).inflate(layout, null, false);
            builder.setView(containerView);
            return builder.create();
        } else {
            return new Dialog(getActivity(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch (layout) {
            case R.layout.dialog_normal:
                break;
            case R.layout.dialog_loading:
                break;
            case R.layout.dialog_full_screen:
                titleView = view.findViewById(R.id.dialog_title);
                subTitleView = view.findViewById(R.id.dialog_sub_title);
                submitBtnView = view.findViewById(R.id.dialog_submit);
                statusImageView = view.findViewById(R.id.dialog_image_view);
                setTextToTextView(titleView, titleStr);
                setTextToTextView(subTitleView, subTitleStr);
                setTextToTextView(submitBtnView, submitBtnStr);
                setDrawableToImageView(statusImageView, statusImageDrawable);
                if (submitBtnCallback != null) {
                    submitBtnView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            submitBtnCallback.onClick();
                            dismiss();
                        }
                    });
                }
                break;
        }
    }

    private void setTextToTextView(TextView textView, String str) {
        textView.setText(str);
    }

    private void setDrawableToImageView(ImageView textView, Integer drawable) {
        textView.setImageDrawable(getContext().getDrawable(drawable));
    }

    public void showDialog() {
        show(manager, tag != null ? tag : "Dialog_Fragment");
    }
}
