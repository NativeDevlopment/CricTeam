package com.cricteam.imagepicker;
/**
 * Created by oswaldogh89 on 13/04/17.
 */

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cricteam.imagepicker.utils.CropImage;
import com.cricteam.imagepicker.utils.TakePictureUtils;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;



public class ImagePicker extends LinearLayout implements View.OnClickListener {

    private static final int PIC_CROP = 1;
    private final ImagePicker mContext;
    private  Context context;
    private Activity mainactivity;
    private Fragment fragment;
    private boolean calledFromFragment = false;

    private HashMap<Integer, String> hmap;
    private TextView count;
    private Button BorrarTodas;

    public static final int REQUEST_CAMERA = 8848;
    public static final int REQUEST_GALLERY = 8849;
ImageView addNew;
    String mCurrentPhotoPath;

    public ImagePicker(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        inflate(context, R.layout.template, this);
        mContext = this;
        this.context=context;

         addNew = (ImageView) findViewById(R.id.AddNew);



        addNew.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
              bottomDialog();

            }
        });

    }

    public Uri setCropImage(String croppath) {


            File f = new File(croppath);
        Uri path = Uri.fromFile(f);
            Glide.with(getContext()).load(path).override(150, 150).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(addNew);



        Log.e("path",path.toString());

        return path;
    }
    public Uri setImage(Intent imageReturnedIntent) {
        Uri path;
        if(imageReturnedIntent!=null) {
             path = imageReturnedIntent.getData();
          //  Glide.with(getContext()).load(path).override(150, 150).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(addNew);
            if(calledFromFragment){
                startCropImage(fragment.getActivity(),getRealPathFromURI(path));

            }else{
                startCropImage(fragment.getActivity(),getRealPathFromURI(path));
            }

        }else{
            File f = new File(mCurrentPhotoPath);
             path = Uri.fromFile(f);
           // Glide.with(getContext()).load(path).override(150, 150).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(addNew);

            if(calledFromFragment){
                startCropImage(fragment.getActivity(),mCurrentPhotoPath);

            }else{
                startCropImage(fragment.getActivity(),mCurrentPhotoPath);
            }
        }
        Log.e("path",path.toString());

        return path;
    }
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    public void setMainactivity(Activity mainactivity) {
        this.mainactivity = mainactivity;
        this.calledFromFragment = false;
    }

    public void setFragment(Fragment fragment){
        this.fragment = fragment;
        this.calledFromFragment = true;
        this.mainactivity=fragment.getActivity();
    }





    @Override
    public void onClick(final View view) {


    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mainactivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(mainactivity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(mainactivity,
                        "com.cricteam",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                if(calledFromFragment){
                    fragment.startActivityForResult(takePictureIntent,REQUEST_CAMERA);
                }else{
                    mainactivity.startActivityForResult(takePictureIntent, REQUEST_CAMERA);
                }
            }
        }
    }

    private void bottomDialog() {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog( context);
        View sheetView;
        if(calledFromFragment){
            sheetView  = fragment.getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);

        }else{
            sheetView  = mainactivity.getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
        }


        mBottomSheetDialog.setContentView(sheetView);
        sheetView.findViewById(R.id.tvCamera).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=23){
                    if(calledFromFragment) {
                        if (CommonUtils.checkPermissions(fragment.getActivity())) {
                            dispatchTakePictureIntent();
                        }
                    }
                else {
                        if(CommonUtils.checkPermissions(mainactivity)) {
                            dispatchTakePictureIntent();
                        }

                    }

                }   else
                 {
                dispatchTakePictureIntent();}
                mBottomSheetDialog.dismiss();
            }
        });
        sheetView.findViewById(R.id.tvGallery).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=23){
                    if(calledFromFragment){
                        if(CommonUtils.checkPermissions(fragment.getActivity())) {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            if(calledFromFragment){
                                fragment.startActivityForResult(pickPhoto, REQUEST_GALLERY);
                            }else{
                                mainactivity.startActivityForResult(pickPhoto, REQUEST_GALLERY);
                            }                        }}

                        else {
                            if(CommonUtils.checkPermissions(mainactivity)) {
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                if(calledFromFragment){
                                    fragment.startActivityForResult(pickPhoto, REQUEST_GALLERY);
                                }else{
                                    mainactivity.startActivityForResult(pickPhoto, REQUEST_GALLERY);
                                }                            }

                        }

                }   else
                {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    if(calledFromFragment){
                        fragment.startActivityForResult(pickPhoto, REQUEST_GALLERY);
                    }else{
                        mainactivity.startActivityForResult(pickPhoto, REQUEST_GALLERY);
                    }                }
                mBottomSheetDialog.dismiss();
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();
    }

    public void startCropImage(@NonNull Activity context, @NonNull String filePath) {
        Intent intent = new Intent(context, CropImage.class);
        File storageDir = mainactivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        intent.putExtra(CropImage.IMAGE_PATH, filePath);
        intent.putExtra(CropImage.SCALE, true);
        intent.putExtra(CropImage.ASPECT_X, 0);
        intent.putExtra(CropImage.ASPECT_Y, 0);
        intent.putExtra(CropImage.OUTPUT_X, 200);
        intent.putExtra(CropImage.OUTPUT_Y, 200);
        context.startActivityForResult(intent, TakePictureUtils.CROP_FROM_CAMERA);
    }


}
