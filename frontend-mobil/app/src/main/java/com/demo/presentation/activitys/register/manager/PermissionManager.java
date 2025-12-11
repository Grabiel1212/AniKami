package com.demo.presentation.activitys.register.manager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class PermissionManager {

    private static final int REQUEST_GALLERY = 1001;
    private static final int REQUEST_PERMISSION = 2001;

    private Activity activity;
    private ImageView imgPreview;

    public PermissionManager(Activity activity, ImageView imgPreview) {
        this.activity = activity;
        this.imgPreview = imgPreview;
    }

    public void verificarPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (activity.checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {

                activity.requestPermissions(
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        REQUEST_PERMISSION
                );
            } else {
                abrirGaleria();
            }
        } else {

            if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                activity.requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION
                );
            } else {
                abrirGaleria();
            }
        }
    }

    public void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, REQUEST_GALLERY);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imgPreview.setImageURI(imageUri);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirGaleria();
            } else {
                Toast.makeText(activity, "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
