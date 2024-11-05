package com.example.contentprovider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {

    Button btnshowallcontact, btnshowcalllog, btnshowallcontact2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnshowallcontact = findViewById(R.id.btnshowallcontact);
        btnshowcalllog = findViewById(R.id.btnshowcalllog);
        btnshowallcontact2 = findViewById(R.id.btnshowallcontact2);

        // Chuyển sang ShowAllContactActivity khi nhấn nút Show All Contact
        btnshowallcontact.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShowAllContactActivity.class);
            startActivity(intent);
        });

        // Chuyển sang ShowCallLogActivity khi nhấn nút Show Call Log
        btnshowcalllog.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShowCallLogActivity.class);
            startActivity(intent);
        });

        // Chuyển sang ShowAllContactActivity2 khi nhấn nút Show All Contact 2
        btnshowallcontact2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShowAllContactActivity2.class);
            startActivity(intent);
        });
    }
}
