package com.cricteam.imagepicker.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * this class is used for image operation
 */

public class TakePictureUtils {

    public static final int TAKE_PICTURE = 1;
    public static final int PICK_GALLERY = 2;
    public static final int CROP_FROM_CAMERA = 3;
    private static final String TEMP_PHOTO_FILE_NAME = "temp";


    /**
     * this method is used for take picture from camera
     */
    public static void takePicture(@NonNull Activity context, String fileName) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {

            Uri mImageCaptureUri = null;
            Log.e(context + "", "file name " + fileName);
            mImageCaptureUri = Uri.fromFile(new File(context.getExternalFilesDir("temp"), fileName + ".png"));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            intent.putExtra("return-data", true);
            context.startActivityForResult(intent, TAKE_PICTURE);

        } catch (Exception ex) {

            Log.e(context + "", "cannot take picture " + ex);
        }
    }

    /**
     * this method is used for take picture from gallery
     */
    public static void openGallery(@NonNull Activity context) {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        context.startActivityForResult(photoPickerIntent, PICK_GALLERY);
    }

    /**
     * this method is used for copy stream
     */

    public static void copyStream(@NonNull InputStream input, @NonNull OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    /** this method is used for open crop image */
  /*public static void startCropImage(Activity context, String fileName) {

          Intent intent = new Intent(context, CropImage.class);
          intent.putExtra(CropImage.IMAGE_PATH, new File(context.getExternalFilesDir("temp") ,fileName).getPath());
          intent.putExtra(CropImage.SCALE, true);
          intent.putExtra(CropImage.ASPECT_X, 1);
          intent.putExtra(CropImage.ASPECT_Y, 1);
          intent.putExtra(CropImage.OUTPUT_X, 800);
          intent.putExtra(CropImage.OUTPUT_Y, 600);
          context.startActivityForResultOnActivity(intent, CROP_FROM_CAMERA);
      }
*/

    /**
     * this method is used for take picture from camera
     */
    public void takePictureSecond(@NonNull Activity context, @NonNull String fileName) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {

            Uri mImageCaptureUri = null;
            Log.e(context + "", "file name " + fileName);
            mImageCaptureUri = Uri.fromFile(getFileTemp(context, fileName));
            Log.d("CreateEvent", "3");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            intent.putExtra("return-data", true);
            context.startActivityForResult(intent, TAKE_PICTURE);

        } catch (Exception ex) {

            Log.e(context + "", "cannot take picture " + ex);
        }
    }

    @Nullable
    private File getFileTemp(@NonNull Context context, @NonNull String fileName) {
        File mFileTemp = null;


        if (mFileTemp != null && fileExists(mFileTemp.getPath())) {
            return mFileTemp;
        } else {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                System.out.println("Media Mounted ");
                String RootDir = Environment.getExternalStorageDirectory() + File.separator + "Strings/";
                File RootFile = new File(RootDir);
                if (RootFile.isDirectory()) {

                } else {
                    RootFile.mkdir();
                }
                mFileTemp = new File(RootFile, fileName);
                System.out.println("if mFileTemp : " + mFileTemp);
            } else {
                mFileTemp = new File(context.getFilesDir(), fileName);
                System.out.println("media not mounted");
                System.out.println("else mFileTemp : " + mFileTemp);
            }
            return mFileTemp;
        }
    }

    private boolean fileExists(@NonNull String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

}
