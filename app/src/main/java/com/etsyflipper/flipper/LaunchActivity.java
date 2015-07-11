package com.etsyflipper.flipper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.etsyflipper.estyflipper.R;

/**
 * Created by paddy on 7/11/15.
 */
public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_layout);

        View button = findViewById(R.id.launch_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EtsyViewerActivity.startMe(LaunchActivity.this);
            }
        });
    }
}
