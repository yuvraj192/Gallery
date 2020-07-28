package com.yuvraj.gallery;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class ImagesGallery {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static ArrayList<String> listOfImages(Context context){
        Uri uri;
        Cursor cursor;
        int column_index_data,column_index_folder_name;
        ArrayList<String> lisOfAllImages = new ArrayList<>();
        String ablosutePathOfImage;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
        MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        String orderBy = MediaStore.Video.Media.DATE_TAKEN;
        cursor = context.getContentResolver().query(uri, projection, null,
                null, orderBy+" DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        //column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        while(cursor.moveToNext()){
            ablosutePathOfImage = cursor.getString(column_index_data);
            lisOfAllImages.add(ablosutePathOfImage);

        }

        return lisOfAllImages;
    }
}
