package com.example.mithilesh.routeonmap.di;

import android.content.Context;

import com.example.mithilesh.routeonmap.data.Repository;
import com.example.mithilesh.routeonmap.data.local.LocalDataSource;
import com.example.mithilesh.routeonmap.data.remote.RemoteDataSource;

public class RepositoryInjector {

    public static Repository provideRepository(Context context) {
        return Repository.getInstance(LocalDataSource.getInstance(context), RemoteDataSource.getInstance(context));
    }
}
