package com.example.databases;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DBAdapter db = new DBAdapter(this);

        //---Mở database và thêm dữ liệu---
        db.open();
        db.insertContact("Jennifer Ann", "jenniferann@jfdimarzio.com");
        db.insertContact("Oscar Diggs", "oscar@oscardiggs.com");
        db.close();

        // --- Mở database và đọc dữ liệu ---
        db.open();
        Cursor c = db.getAllContacts();
        if (c != null && c.moveToFirst()) {
            do {
                Log.d("DB_DEBUG", "ID: " + c.getString(0) +
                        ", Name: " + c.getString(1) +
                        ", Email: " + c.getString(2));

                DisplayContact(c);
            } while (c.moveToNext());
        } else {
            Log.e("DB_DEBUG", "Không có dữ liệu trong database");
        }
        c.close(); // Đóng Cursor sau khi sử dụng
        db.close();

        //---get a contact---
        db.open();
        Cursor contactCursor = db.getContact(2); // Đổi tên biến tránh trùng lặp
        if (contactCursor.moveToFirst())
            DisplayContact(contactCursor);
        else
            Toast.makeText(this, "No contact found", Toast.LENGTH_LONG).show();
        contactCursor.close(); // Đóng Cursor sau khi sử dụng
        db.close();

        //---update contact---
        db.open();
        if (db.updateContact(1, "Oscar Diggs", "oscar@oscardiggs.com"))
            Toast.makeText(this, "Update successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Update failed.", Toast.LENGTH_LONG).show();
        db.close();

        //---delete a contact---
        db.open();
        if (db.deleteContact(1))
            Toast.makeText(this, "Delete successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Delete failed.", Toast.LENGTH_LONG).show();
        db.close();

        //---Đảm bảo View tồn tại trước khi áp dụng insets---
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void DisplayContact(Cursor c) {
        String message = "ID: " + c.getString(0) + "\n" +
                "Name: " + c.getString(1) + "\n" +
                "Email: " + c.getString(2);

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_custom, null); // Sửa lỗi inflate

        if (layout != null) {
            TextView text = layout.findViewById(R.id.toast_text);
            text.setText(message);

            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        } else {
            // Trường hợp lỗi, hiển thị Toast mặc định
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
