package com.example.mathterminology;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {
    String translate, word;
    DbLike dbLike;
    TextView wordId, translateId;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);


        dbLike = new DbLike(MainActivity2.this);



        wordId = findViewById(R.id.keyTextId);
        translateId = findViewById(R.id.TextId);

        Log.d("demo22", String.valueOf(dbLike) +  wordId);

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
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);

            startActivity(intent);
//            finish();
        }


    public void buttonLike(View view) {
        confirmDialog();
//        Toast.makeText(MainActivity2.this,  "Matin saqlandi!", Toast.LENGTH_SHORT).show();
    }

    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Matinni saqlash?");
        builder.setMessage("Matnni saqlash menyusiga saqlashni istaysizmi?");
        builder.setPositiveButton("Saqlayman", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dbLike.addNewCourse(word, translate);
        Toast.makeText(MainActivity2.this,  "Matin saqlandi!", Toast.LENGTH_SHORT).show();
//                DBHandler myDB = new DBHandler(ViewCourses.this);
//                dbHistory.deleteAllData();
                //Refresh Activity
//                Intent intent = new Intent(getContext(), MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
        builder.setNegativeButton("Shart emas", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}