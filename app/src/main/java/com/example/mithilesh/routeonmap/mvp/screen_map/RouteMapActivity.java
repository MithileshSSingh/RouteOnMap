package com.example.mithilesh.routeonmap.mvp.screen_map;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mithilesh.routeonmap.R;
import com.example.mithilesh.routeonmap.mvp.BaseActivity;
import com.example.mithilesh.routeonmap.utils.AppConstants;


public class RouteMapActivity extends BaseActivity {
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void init() {
        initView();
        initMembers();
        initListeners();
        initData();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initMembers() {

        showFragment(AppConstants.Screens.SCREEN_MAP, null);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }
}
