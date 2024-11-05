package com.example.contentprovider;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class ShowCallLogActivity extends Activity {
    private static final int REQUEST_CODE_CALL_LOG = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_call_log);

        // Kiểm tra quyền, nếu chưa được cấp quyền thì yêu cầu quyền
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, REQUEST_CODE_CALL_LOG);
        } else {
            showCallLog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_CALL_LOG) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showCallLog();
            } else {
                // Xử lý khi quyền bị từ chối
            }
        }
    }

    public void showCallLog() {
        ArrayList<String> callLogs = new ArrayList<>();

        // Truy vấn dữ liệu từ Call Log
        Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
            int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);

            while (cursor.moveToNext()) {
                String number = cursor.getString(numberIndex);
                String type = cursor.getString(typeIndex);
                String date = cursor.getString(dateIndex);
                String duration = cursor.getString(durationIndex);

                // Xác định loại cuộc gọi (gọi đến, gọi đi, cuộc gọi nhỡ)
                String callType;
                switch (Integer.parseInt(type)) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        callType = "Gọi đi";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        callType = "Gọi đến";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        callType = "Cuộc gọi nhỡ";
                        break;
                    default:
                        callType = "Khác";
                        break;
                }

                callLogs.add("Số: " + number + "\nLoại: " + callType + "\nNgày: " + date + "\nThời lượng: " + duration + " giây");
            }
            cursor.close();
        }

        // Liên kết ArrayAdapter với ListView để hiển thị danh sách nhật ký cuộc gọi
        ListView lv = findViewById(R.id.listViewCallLog);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, callLogs);
        lv.setAdapter(adapter);
    }
}
