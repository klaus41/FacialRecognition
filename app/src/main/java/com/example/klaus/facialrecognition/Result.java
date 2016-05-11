package com.example.klaus.facialrecognition;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class Result extends Activity{

    private ImageView myImageView;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        myImageView = (ImageView) findViewById(R.id.imageView);
        myImageView.setImageResource(R.drawable.cat);

    }
}
