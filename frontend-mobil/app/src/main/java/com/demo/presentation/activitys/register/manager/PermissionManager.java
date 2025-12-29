package com.demo.presentation.activitys.register.manager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.ImageView;
import androidx.annotation.NonNull;

public class PermissionManager {

    private static final int REQUEST_GALLERY = 1001;
    private static final int REQUEST_PERMISSION = 2001;

    private final Activity activity;
    private final ImageView imgPreview;
    private final OnImageSelectedListener listener;

    public interface OnImageSelectedListener {
        void onImageSelected(Uri imageUri);
    }

    public PermissionManager(Activity activity, ImageView imgPreview, OnImageSelectedListener listener) {
        this.activity = activity;
        this.imgPreview = imgPreview;
        this.listener = listener;
    }

    public void verificarPermisos() {
        String permission = (Build.VERSION.SDK_INT >= 33)
                ? Manifest.permission.READ_MEDIA_IMAGES
                : Manifest.permission.READ_EXTERNAL_STORAGE;

        if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{permission}, REQUEST_PERMISSION);
        } else {
            abrirGaleria();
        }
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(intent, REQUEST_GALLERY);
    }

    public void onActivityResult(int r, int c, Intent d) {
        if (r == REQUEST_GALLERY && c == Activity.RESULT_OK && d != null) {
            Uri uri = d.getData();
            imgPreview.setImageURI(uri);
            listener.onImageSelected(uri);
        }
    }

    public void onRequestPermissionsResult(int r, @NonNull int[] g) {
        if (r == REQUEST_PERMISSION && g.length > 0 && g[0] == PackageManager.PERMISSION_GRANTED) {
            abrirGaleria();
        }
    }
}
