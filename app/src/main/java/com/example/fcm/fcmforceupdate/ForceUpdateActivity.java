package com.example.fcm.fcmforceupdate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class ForceUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force_update);

        findViewById(R.id.btn_update).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Your app store link", Toast.LENGTH_SHORT).show();
    }
}
