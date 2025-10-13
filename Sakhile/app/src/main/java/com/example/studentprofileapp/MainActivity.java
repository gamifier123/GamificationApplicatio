package com.example.studentprofileapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android .view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView, textView2;
    EditText etName;
    Button button, button2, changePassword;
    ImageView imageView;

    boolean editing = false; //Track edit mode


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //bind views
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView4);
        etName = findViewById(R.id.etName);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView);

        //profile button on click event
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editing) {
                    etName.setVisibility(View.VISIBLE);
                    button2.setText("Change Password");
                    editing = true;
                } else {
                    //save updated name
                    String newName = etName.getText().toString().trim();
                    if (!newName.isEmpty()) {
                        textView.setText(newName);
                    }
                    etName.setVisibility(View.GONE);
                    button2.setText("Edit Profile");
                    editing = false;
                }
            }
        });
        //view marks button
        button2.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "View Marks", Toast.LENGTH_SHORT).show()
        );
    }
}