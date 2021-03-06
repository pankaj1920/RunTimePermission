package com.app.runtimepermission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //   private int STORAGE_PERMISION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      Button  contact_permission = findViewById(R.id.contact_permission);

        contact_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when user click on this buton we want to request the permission
                //but first we have to check if permission is already granted
                if (ContextCompat.checkSelfPermission(MainActivity.this
                        , Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission already Granted", Toast.LENGTH_SHORT).show();
                } else {
                    // if the permission is not granted yet. we want to request this permission
                    requestContactPermission();
                }
            }
        });


      Button  externl_permission = findViewById(R.id.externl_permission);
        externl_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storagePermisiom();
            }
        });
    }

    //This is From Harsha Sir
    private void storagePermisiom() {
        if (askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1)) {
            Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
            startActivity(intent);
        } else {
            displayPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1);
        }
    }

    private boolean askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    void displayPermission(String permission, Integer requestCode) {
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this, permission)) {

            //This is called if user has denied the permission before
            //In this case I am just asking the permission again
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{permission}, requestCode);

        } else {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{permission}, requestCode);
        }
    }

    //Request Call Permission

    // This is From Coding With Flow
    private void requestContactPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
            // This method will show user a dialog which explain why we need this permission
            //this happen when user already denied the permission before and tries to acess it again

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Permision needed")
                    .setMessage("This permision is needed for this and that")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.READ_CONTACTS}, 2);

                        }
                    })
                    .setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //when this cancle butoon is clicke we need to dismiss the dialog
                            dialog.dismiss();
                        }
                    }).create().show();
        } else {
            //Request permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS}, 2);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 1) {
                storagePermisiom();
            } else if (requestCode == 2) {
                requestContactPermission();
            }
        }else {
            Toast.makeText(this, "Need Permission to External Storage Permission", Toast.LENGTH_SHORT).show();
        }
    }



}
