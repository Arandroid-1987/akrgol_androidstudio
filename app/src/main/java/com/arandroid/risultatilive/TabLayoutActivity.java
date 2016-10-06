package com.arandroid.risultatilive;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabLayoutActivity extends TabActivity {
	private String url;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        url = getIntent().getStringExtra("url");
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        setContentView(R.layout.main);
 
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
        
        TabHost tabHost = getTabHost();
        
        TabSpec resultspec = tabHost.newTabSpec("Risultati");
        resultspec.setIndicator("Risultati", getResources().getDrawable(R.drawable.icon_results_tab));
        Intent resultsIntent = new Intent(this, RisultatiActivity.class);
        resultsIntent.putExtra("url", url);
        resultsIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        resultspec.setContent(resultsIntent);
        
        TabSpec classificaspec = tabHost.newTabSpec("Classifica");
        // setting Title and Icon for the Tab
        classificaspec.setIndicator("Classifica", getResources().getDrawable(R.drawable.icon_classifica_tab));
        Intent classificaIntent = new Intent(this, ClassificaActivity.class);
        classificaspec.setContent(classificaIntent);
        classificaIntent.putExtra("url", url);
        classificaIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Adding all TabSpec to TabHost
        tabHost.addTab(resultspec);
        tabHost.addTab(classificaspec);
    }
}