package com.example.klaus.facialrecognition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Result extends Activity{

    private ImageView myImageView;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        myImageView = (ImageView) findViewById(R.id.imageView);
        myImageView.setImageResource(R.drawable.cat);

        Button btnMain = (Button)findViewById((R.id.buttonGoToMain));
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
