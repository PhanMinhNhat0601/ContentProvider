package com.example.contentprovider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button btnshowallcontact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnshowallcontact = findViewById(R.id.btnshowallcontact);

        btnshowallcontact.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShowAllContactActivity.class);
            startActivity(intent);
        });
    }
}
