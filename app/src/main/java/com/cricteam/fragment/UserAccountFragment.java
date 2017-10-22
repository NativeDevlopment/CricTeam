package com.cricteam.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cricteam.CreateTeamActivity;
import com.cricteam.CricTeamApplication;
import com.cricteam.OtpVerifyActivity;
import com.cricteam.R;
import com.cricteam.adapter.FindTeamAdapter;
import com.cricteam.imagepicker.ImagePicker;
import com.cricteam.imagepicker.utils.CropImage;
import com.cricteam.imagepicker.utils.TakePictureUtils;
import com.cricteam.listner.OnApiResponse;
import com.cricteam.listner.OnFragmentInteractionListener;
import com.cricteam.model.ResultCancel;
import com.cricteam.netwokmodel.APIExecutor;
import com.cricteam.netwokmodel.NetWorkApiCall;
import com.cricteam.netwokmodel.Response;
import com.cricteam.netwokmodel.UserDetails;
import com.cricteam.netwokmodel.UserDetailsRequest;
import com.cricteam.utils.AppConstants;
import com.cricteam.utils.CommonUtils;
import com.cricteam.utils.DelayedProgressDialog;
import com.cricteam.utils.TextDrawable;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.games.event.Event;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAccountFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mView;
    DelayedProgressDialog progressDialog;
    final int PLACE_PICKER_REQUEST = 1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private Context mContext;
    private FloatingActionButton fbSaveUser;
    private ImagePicker ivUserPics;
    private EditText et_name;
    private TextView tv_mobile_no;
    private TextView tv_user_address;
    private EditText et_Email;
    private TextView titleHeader;
    StorageReference mountainsRef;
    StorageReference storageRef;
    private Uri image;
    Realm realm;
    UserDetails userDetails;
    public TextView textLabel;
    private String userLat;

    private String userLong;
    public UserAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instanc
     * <p>
     * <p>
     * e of
     * this fragment using the provided parameters.
     *
     * @param fromWhereAccount Parameter 1.
     * @param param2           Parameter 2.
     * @return A new instance of fragment CreateTeamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAccountFragment newInstance(String fromWhereAccount, String param2) {
        UserAccountFragment fragment = new UserAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, fromWhereAccount);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() !=null){
            if (getActivity() instanceof CreateTeamActivity){
                ((CreateTeamActivity) getActivity()).getAddressAndEnableLocation();
            }
        }
        CricTeamApplication cricTeamApplication= (CricTeamApplication)getActivity(). getApplication();
         realm= Realm.getInstance(cricTeamApplication.config);

        RealmQuery<UserDetails> result2 = realm.where(UserDetails.class)
                .equalTo("userId", Integer.valueOf(CommonUtils.getPreferences(mContext,AppConstants.USER_ID)));
        RealmResults<UserDetails> results1 =
                result2.findAll();
        for(UserDetails c:results1) {
            Log.e("results1>>>",""+ c.getUserId());
            userDetails=c;
        }
        setUi();
    }

    private void setUi() {
        if(userDetails!=null){
            et_name.setText(userDetails.getName());
            et_Email.setText(userDetails.getUserEmail());
            tv_mobile_no.setText(userDetails.getMobileNo());
            if(userDetails.getUserImageUrl()!=null&&!userDetails.getUserImageUrl().equalsIgnoreCase("")){
                ivUserPics.setwebImage(userDetails.getUserImageUrl());
            }else{
                if(userDetails.getName()!=null&&!userDetails.getName().equalsIgnoreCase("")) {
                    TextDrawable drawable = TextDrawable.builder()
                            .beginConfig()
                            .withBorder(4) /* thickness in px */
                            .endConfig()
                            .buildRoundRect("" + userDetails.getName().charAt(0), ContextCompat.getColor(mContext, R.color.colorPrimary), 10);
                    ivUserPics.getImageView().setImageDrawable(drawable);
                }
            }
            tv_user_address.setText(userDetails.getUserAddress());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView = inflater.inflate(R.layout.fragment_user_account, container, false);
        initializedId();
        fbSaveUser.setOnClickListener(this);
        tv_user_address.setOnClickListener(this);
        tv_user_address.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(mContext, R.drawable.ic_place_black_24dp), null, null, null);
        ivUserPics.setOnClickListener(this);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("user");

        return mView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PLACE_PICKER_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    Place place = PlacePicker.getPlace(mContext, data);
                    if (place != null) {
                        tv_user_address.setText("" + place.getAddress());
                        userLat= String.valueOf(place.getLatLng().latitude);
                        userLong= String.valueOf(place.getLatLng().longitude);
                    }
                }
                break;
            case ImagePicker.REQUEST_CAMERA:
                if (resultCode == Activity.RESULT_OK) {

                    image = ivUserPics.setImage(data);
                }
                break;
            case ImagePicker.REQUEST_GALLERY:
                if (resultCode == Activity.RESULT_OK) {
                    image = ivUserPics.setImage(data);
                }
                break;

            case TakePictureUtils.CROP_FROM_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    String path = null;
                    if (data != null) {
                        path = data.getStringExtra(CropImage.IMAGE_PATH);
                        image = ivUserPics.setCropImage(path);
                    }
                    if (path == null) {
                        Log.e("Path null ", path);
                        return;
                    }
                }
        }
    }

    private void initializedId() {
        fbSaveUser = (FloatingActionButton) mView.findViewById(R.id.fbSaveUser);
        ivUserPics = (ImagePicker) mView.findViewById(R.id.ivUserPics);
        ivUserPics.setFragment(this);
        et_name = (EditText) mView.findViewById(R.id.et_name);
        tv_mobile_no = (TextView) mView.findViewById(R.id.tv_mobile_no);
        textLabel = (TextView) mView.findViewById(R.id.textLabel);
        tv_user_address = (TextView) mView.findViewById(R.id.tv_user_address);
        et_Email = (EditText) mView.findViewById(R.id.et_Email);
        titleHeader = (TextView) mView.findViewById(R.id.titleHeader);
        titleHeader.setText("USER PROFILE");

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fbSaveUser:
                if(et_name.getText().toString().trim().isEmpty()){
                    et_name.requestFocus();
                    Snackbar.make(et_Email,"Please enter the name input field.",Snackbar.LENGTH_SHORT).show();
                }else if( et_Email.getText().toString().trim().isEmpty()){
                    et_Email.requestFocus();
                    Snackbar.make(et_Email,"Please enter the email id",Snackbar.LENGTH_SHORT).show();
                }else if(!CommonUtils.isValidEmail(et_Email.getText().toString().trim())){
                    et_Email.requestFocus();
                    Snackbar.make(et_Email,"Please enter the correct email id",Snackbar.LENGTH_SHORT).show();
                }else {
                   if( CommonUtils.isOnline(mContext)){
                         progressDialog = new DelayedProgressDialog();
                       progressDialog.show(getChildFragmentManager(), "tag");

                    saveImageToCloud();}else{
                        CommonUtils.showToast(mContext,"Please Check Network Connection");

                    }
                }
                break;
            case R.id.tv_user_address:
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ivUserPics:
                break;
        }
    }
    private void saveImageToCloud() {
        if(CommonUtils.isOnline(mContext)){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    userDetails.setName(et_name.getText().toString());
                    userDetails.setUserAddress(tv_user_address.getText().toString());
                    userDetails.setUserEmail(et_Email.getText().toString());
                    userDetails.setUserLat(userLat);
                    userDetails.setUserLong(userLong);
                }
            });

        if (image != null) {

            StorageReference riversRef = storageRef.child("cricUser"+userDetails.getUserId()+".jpg");
            UploadTask uploadTask = riversRef.putFile(image);

// Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
             final       Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.e("url", "" + downloadUrl);
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            userDetails.setUserImageUrl(downloadUrl.toString());
                        }
                    });

                    updateUserProfile(userDetails);
                }
            });
        }else {
            updateUserProfile(userDetails);

        }
        }
    }
  void   updateUserProfile(UserDetails userDetails){
      UserDetailsRequest userDetailsRequest= new UserDetailsRequest();
      userDetailsRequest.userId=userDetails.getUserId();
      userDetailsRequest.userEmail=userDetails.getUserEmail();
      userDetailsRequest.userAddress=userDetails.getUserAddress();
      userDetailsRequest.userImageUrl=userDetails.getUserImageUrl();
      userDetailsRequest.userLat=userDetails.getUserLat();
      userDetailsRequest.userLong=userDetails.getUserLong();
      userDetailsRequest.name=userDetails.getName();
      userDetailsRequest.deviceId=userDetails.getDeviceId();
      userDetailsRequest.deviceToken=userDetails.getDeviceToken();
      userDetailsRequest.deviceType=userDetails.getDeviceType();
      userDetailsRequest.mobileNo=userDetails.getMobileNo();

      NetWorkApiCall.getInstance().getApiResponse(mContext, APIExecutor.getApiService().updateUser(userDetailsRequest), new OnApiResponse() {
          @Override
          public void onResponse(Response response) {

               progressDialog.cancel();
              if(response!=null){
                  final  UserDetails userDetails= new Gson().fromJson(new Gson().toJsonTree(response.data).toString(),UserDetails.class);

                  realm.executeTransaction(new Realm.Transaction() {
                      @Override
                      public void execute(Realm realm) {
                          // Add a person

                          realm.copyToRealmOrUpdate(userDetails);
                          CreateTeamFragment createTeamFragment=      CreateTeamFragment.newInstance(mParam1, "");
                          mListener.onFragmentInteractions(createTeamFragment);

                          getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llContainer, createTeamFragment).addToBackStack(null).commit();
                            CommonUtils.savePreferencesBoolean(mContext,AppConstants.IS_COMPLETE_USER,true);
                      }
                  });
              }
          }
      });
    }
    @Subscribe
    public void onEventMainThread( Place place){
        tv_user_address.setText(place.getAddress());
        textLabel.setVisibility(View.INVISIBLE);
        userLat= String.valueOf(place.getLatLng().latitude);
        userLong= String.valueOf(place.getLatLng().longitude);
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
