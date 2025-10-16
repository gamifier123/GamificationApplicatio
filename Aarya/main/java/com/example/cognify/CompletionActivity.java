package com.example.cognify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CompletionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completion);

        Button startAppButton = findViewById(R.id.startAppButton);
        
        startAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to main app (MainActivity)
                Intent intent = new Intent(CompletionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
