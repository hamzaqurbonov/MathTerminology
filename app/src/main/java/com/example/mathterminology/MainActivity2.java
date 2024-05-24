package com.example.mathterminology;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {
    String translate, word;
    TextView wordId, translateId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        wordId = findViewById(R.id.keyTextId);
        translateId = findViewById(R.id.TextId);


        translate = getIntent().getExtras().getString("translate");
        word = getIntent().getExtras().getString("word");

        wordId.setText(word);
        translateId.setText(translate);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void back() {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }

}