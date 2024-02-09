package com.example.myapplication.Util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class DatabaseUtil {
    private static final String URL = "https://clientregister-c1856-default-rtdb.firebaseio.com/";

    public static DatabaseReference connect() {
        try {
            return FirebaseDatabase.getInstance(URL).getReference();
        } catch (Exception e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
        return null;
    }

    public static void uploadImagesToFirebaseStorage(String directoryPath, String filename, Map<TextView, Pair<Uri, BitmapDrawable>> textViewImages) {
        for (Map.Entry<TextView, Pair<Uri, BitmapDrawable>> entry : textViewImages.entrySet()) {
            final TextView textView = entry.getKey();
            Pair<Uri, BitmapDrawable> imageData = entry.getValue();
            final BitmapDrawable drawable = imageData.second;

            try {
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                        .child(directoryPath + "/" + filename);

                storageRef.putBytes(byteArray)
                        .addOnSuccessListener(taskSnapshot -> Log.d("Firebase", "Image uploaded to Firebase Storage for TextView: " + textView.getId()))
                        .addOnFailureListener(Throwable::printStackTrace);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
