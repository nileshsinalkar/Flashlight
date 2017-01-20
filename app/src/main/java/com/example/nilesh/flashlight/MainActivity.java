package com.example.nilesh.flashlight;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton imageButton;
    android.hardware.Camera camera;
    android.hardware.Camera.Parameters parameters;
    boolean ison=false;
    boolean isflash=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{

        imageButton=(ImageButton)findViewById(R.id.imagebutton);
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH));
        {
            camera = android.hardware.Camera.open();
            parameters=camera.getParameters();
            isflash=true;
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isflash){
                    if (!ison){
                        imageButton.setImageResource(R.drawable.on);
                        parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_ON);
                        camera.setParameters(parameters);
                        camera.startPreview();
                        ison=true;
                    }else{

                        imageButton.setImageResource(R.drawable.of);
                        parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        ison=false;

                    }
                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage("Cannot open flashlight");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();

                }
            }
        });}catch (RuntimeException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (camera!=null){
            camera.release();
            camera=null;
        }
    }
}
