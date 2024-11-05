package com.example.contentprovider;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class ShowAllContactActivity2 extends Activity {
    private static final int REQUEST_CODE_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_contact2);

        Button btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(v -> finish());

        // Kiểm tra quyền đọc danh bạ
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_CONTACT);
        } else {
            showAllContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_CONTACT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showAllContacts();
            } else {
                // Xử lý trường hợp từ chối quyền truy cập
            }
        }
    }

    private void showAllContacts() {
        ArrayList<String> contacts = new ArrayList<>();

        // Lấy dữ liệu danh bạ
        Cursor cursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null
        );

        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            while (cursor.moveToNext()) {
                if (nameIndex != -1) {
                    String name = cursor.getString(nameIndex);
                    contacts.add(name); // Thêm tên vào danh sách
                }
            }
            cursor.close();
        }


        if (contacts.isEmpty()) {

            contacts.add("Nguyễn Văn A");
            contacts.add("Trần Thị B");
            contacts.add("Lê Văn C");
            contacts.add("Phạm Thị D");
            contacts.add("Ngô Văn E");
            // Thông báo rằng danh bạ trống
            Toast.makeText(this, "Danh bạ trống, hiển thị thông tin mẫu.", Toast.LENGTH_SHORT).show();
        }

        // Hiển thị danh bạ trong ListView
        ListView lvContacts = findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        lvContacts.setAdapter(adapter);
    }
}

