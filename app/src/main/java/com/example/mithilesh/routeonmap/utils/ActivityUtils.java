package com.example.mithilesh.routeonmap.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

public class ActivityUtils {
    public static final String TAG = ActivityUtils.class.getSimpleName();


    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId,
                                             String fragmentTag, boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(fragmentTag);
        }

        transaction.replace(frameId, fragment, fragmentTag);
        transaction.commit();
    }


}
