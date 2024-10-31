package com.example.contentprovider;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MessageDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        // Nhận nội dung tin nhắn từ Intent
        String message = getIntent().getStringExtra("message");

        // Hiển thị nội dung tin nhắn lên TextView
        TextView txtMessage = findViewById(R.id.txtMessage);
        txtMessage.setText(message);
    }
}
