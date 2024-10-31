package com.example.contentprovider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class ShowAllContactActivity extends Activity {
    private static final int REQUEST_CODE_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_contact);

        // Nút back để quay lại Activity trước đó
        Button btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(v -> finish());

        // Kiểm tra quyền truy cập danh bạ, nếu chưa được cấp quyền thì yêu cầu quyền
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
                // Xử lý trường hợp từ chối quyền truy cập ở đây (ví dụ: hiện thông báo cho người dùng)
            }
        }
    }

    public void showAllContacts() {
        ArrayList<String> list = new ArrayList<>();

        // Thêm các dòng tin nhắn vào danh sách
        list.add("22115053122127_Phan Minh Nhat");
        list.add("Tin nhắn 1: Xin chào");
        list.add("Tin nhắn 2: Hôm nay trời đẹp");
        list.add("Tin nhắn 3: Bạn có khỏe không?");
        list.add("Tin nhắn 4: Đã đến giờ nghỉ trưa");
        list.add("Tin nhắn 5: Cùng nhau cố gắng nhé!");
        list.add("Tin nhắn 6: Cuối tuần bạn có kế hoạch gì?");
        list.add("Tin nhắn 7: Thời tiết hôm nay ra sao?");
        list.add("Tin nhắn 8: Đã đến lúc uống cà phê rồi");
        list.add("Tin nhắn 9: Chúc bạn một ngày tốt lành");
        list.add("Tin nhắn 10: Hãy giữ gìn sức khỏe");

        // Truy vấn danh bạ để lấy thông tin các liên hệ
        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (c != null) {
            int idIndex = c.getColumnIndex(ContactsContract.Contacts._ID);
            int nameIndex = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            while (c.moveToNext()) {
                if (idIndex != -1 && nameIndex != -1) {
                    String id = c.getString(idIndex);
                    String name = c.getString(nameIndex);
                    list.add(id + " - " + name);
                }
            }
            c.close();
        }

        // Liên kết ArrayAdapter với ListView để hiển thị danh sách
        ListView lv = findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

        // Lắng nghe sự kiện click vào từng dòng
        lv.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy nội dung của dòng được chọn
            String message = list.get(position);

            // Chuyển sang MessageDetailActivity và truyền dữ liệu
            Intent intent = new Intent(ShowAllContactActivity.this, MessageDetailActivity.class);
            intent.putExtra("message", message);
            startActivity(intent);
        });
    }
}
