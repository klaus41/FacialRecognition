package com.example.klaus.facialrecognition;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
import com.google.android.gms.vision.face.FaceDetector;


public class MainActivity extends Activity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static  int RESULT_LOAD_IMAGE = 1;

    private static FaceDetector detector;
    private static Frame frame;
    private static String picturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonOpenCamera = (Button) findViewById(R.id.buttonCamera);
        buttonOpenCamera.setOnClickListener((new View.OnClickListener() {
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        }));

        ImageView imageView = (ImageView) findViewById(R.id.imgView);
        imageView.setImageResource(R.drawable.shutterstock_151971218);

        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        Button btnMain = (Button)findViewById((R.id.buttonGoToResult));
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Result.class);
                startActivity(intent);
            }
        });

        detector = new FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false).setLandmarkType(FaceDetector.ALL_LANDMARKS).build();
    }
    protected void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }

        frame = new Frame.Builder().setBitmap(BitmapFactory.decodeFile(picturePath)).build();
        SparseArray<Face> faces = detector.detect(frame);
        for(int i = 0; i < faces.size(); i++){
            Face face = faces.valueAt(i);
            for(Landmark landmark : face.getLandmarks()){
                System.out.println(landmark.getType());
                System.out.println(landmark.getPosition().x);
                System.out.println(landmark.getPosition().y);
            }
        }
    }
}