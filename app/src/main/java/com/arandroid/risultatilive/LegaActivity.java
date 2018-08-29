package com.arandroid.risultatilive;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.arandroid.risultatilive.core.GlobalState;
import com.arandroid.risultatilive.fragment.ClassificaFragment;
import com.arandroid.risultatilive.fragment.RisultatiFragment;

public class LegaActivity extends AppCompatActivity {
    private GlobalState gs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lega);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        gs = (GlobalState) getApplication();

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gs.reset();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private static final int RISULTATI = 0;
        private static final int CLASSIFICA = 1;

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case RISULTATI:
                    return new RisultatiFragment();
                case CLASSIFICA:
                    return new ClassificaFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return CLASSIFICA + 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case RISULTATI:
                    return getString(R.string.risultati);
                case CLASSIFICA:
                    return getString(R.string.classifica);
            }
            return null;
        }
    }
}
