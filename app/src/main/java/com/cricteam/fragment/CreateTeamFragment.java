package com.cricteam.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cricteam.CreateTeamActivity;
import com.cricteam.DashBordActivity;
import com.cricteam.imagepicker.ImagePicker;
import com.cricteam.imagepicker.utils.CropImage;
import com.cricteam.imagepicker.utils.TakePictureUtils;
import com.cricteam.listner.OnApiResponse;
import com.cricteam.listner.OnFragmentInteractionListener;
import com.cricteam.R;
import com.cricteam.netwokmodel.APIExecutor;
import com.cricteam.netwokmodel.NetWorkApiCall;
import com.cricteam.netwokmodel.Response;
import com.cricteam.netwokmodel.TeamDetails;
import com.cricteam.netwokmodel.TeamRequest;
import com.cricteam.netwokmodel.UserDetails;
import com.cricteam.netwokmodel.UserDetailsRequest;
import com.cricteam.utils.AppConstants;
import com.cricteam.utils.CommonUtils;
import com.cricteam.utils.DelayedProgressDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateTeamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateTeamFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FloatingActionButton fbNext;
    private Context mContext;
    private TextView tvlocation;
    final int PLACE_PICKER_REQUEST = 1;
    private TextInputEditText etAboutTeam;
    private TextInputEditText etTeamName;
    private ImagePicker ivTeamLogo;
    private TextView titleHeader;
    private TextView titleSkip;
    public TextView textLabel;
    private Uri image;
    private DelayedProgressDialog progressDialog;
    private StorageReference storageRef;
    private double teamLat;
    private double teamLong;

    public CreateTeamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instanc
     *
     *
     * e of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateTeamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateTeamFragment newInstance(String param1, String param2) {
        CreateTeamFragment fragment = new CreateTeamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView= inflater.inflate(R.layout.fragment_create_team, container, false);
        initializedId();
        fbNext.setOnClickListener(this);
         tvlocation.setOnClickListener(this);
        titleSkip.setOnClickListener(this);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("team");
       //  ivTeamLogo.setOnClickListener(this);
                return mView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PLACE_PICKER_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    Place place = PlacePicker.getPlace(mContext,data);
                    if(place!=null) {
                        tvlocation.setText("" + place.getAddress());
                        teamLat = place.getLatLng().latitude;
                        teamLong = place.getLatLng().longitude;
                    }
                }
                break;
            case ImagePicker.REQUEST_CAMERA:
                if (resultCode == Activity.RESULT_OK) {

                    image = ivTeamLogo.setImage(data);
                }
                break;
            case ImagePicker.REQUEST_GALLERY:
                if (resultCode == Activity.RESULT_OK) {
                    image = ivTeamLogo.setImage(data);
                }
                break;

            case TakePictureUtils.CROP_FROM_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    String path = null;
                    if (data != null) {
                        path = data.getStringExtra(CropImage.IMAGE_PATH);
                        image = ivTeamLogo.setCropImage(path);
                    }
                    if (path == null) {
                        Log.e("Path null ", path);
                        return;
                    }
                }
        }
    }

    private void initializedId() {
        fbNext=(FloatingActionButton)mView.findViewById(R.id.fbNext);
        tvlocation=(TextView)mView.findViewById(R.id.tvlocation);
        tvlocation.setCompoundDrawablesWithIntrinsicBounds( null, null,AppCompatResources.getDrawable(mContext, R.drawable.ic_add_location_black_24dp), null);

        etAboutTeam=(TextInputEditText)mView.findViewById(R.id.etAboutTeam);
        etTeamName=(TextInputEditText)mView.findViewById(R.id.etTeamName);
        ivTeamLogo=(ImagePicker)mView.findViewById(R.id.ivTeamLogo);
        titleSkip=(TextView)mView.findViewById(R.id.titleSkip);
        textLabel=(TextView)mView.findViewById(R.id.textLabel);
        titleSkip.setVisibility(View.VISIBLE);
        ivTeamLogo.setFragment(this);
        titleHeader=(TextView) mView. findViewById(R.id.titleHeader);
        titleHeader.setText(R.string.create_team);
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
        this.mContext=context;
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
        switch (view.getId()){
            case R.id.tvlocation:
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
               break;
            case R.id.fbNext:
                if(!etTeamName.getText().toString().equalsIgnoreCase("")) {
                    if( CommonUtils.isOnline(mContext)){
                        progressDialog = new DelayedProgressDialog();
                        progressDialog.show(getChildFragmentManager(), "tag");

                        saveImageToCloud();}else{
                        CommonUtils.showToast(mContext,"Please Check Network Connection");

                    }
                }else {
                    Snackbar.make(etTeamName,"Please Enter Team name.",Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.titleSkip:
                Intent  intent= new Intent(mContext, DashBordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finishAffinity();
               break;

        }
    }

    private void saveImageToCloud() {
        if(CommonUtils.isOnline(mContext)){
        final     TeamRequest request= new TeamRequest();
            request.setTeamName(etTeamName.getText().toString());
            request.setTeamAddress(tvlocation.getText().toString());
            request.setTeamDesc(etAboutTeam.getText().toString());
            request.setUserId(Integer.valueOf(CommonUtils.getPreferences(mContext,AppConstants.USER_ID)));
            request.setTeamLat(String.valueOf(teamLat));
            request.setTeamLong(String.valueOf(teamLong));
            CommonUtils.savePreferencesString(mContext,AppConstants.CUURENT_LAT,String.valueOf(teamLat));
            CommonUtils.savePreferencesString(mContext,AppConstants.CUURENT_LANG,String.valueOf(teamLong));

           /* realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    userDetails.setName(et_name.getText().toString());
                    userDetails.setUserAddress(tv_user_address.getText().toString());
                    userDetails.setUserEmail(et_Email.getText().toString());
                    userDetails.setUserLat(userLat);
                    userDetails.setUserLong(userLong);
                }
            });*/

            if (image != null) {
             String   teamImageName="cricTeam"+etTeamName.getText().toString()+CommonUtils.getPreferences(mContext, AppConstants.USER_ID);
                StorageReference riversRef = storageRef.child("cricTeam"+teamImageName+".jpg");
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
                        request.setTeamLogoUrl(downloadUrl.toString());
                       /* realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                userDetails.setUserImageUrl(downloadUrl.toString());
                            }
                        });*/

                        saveTeam(request);
                    }
                });
            }else {
                saveTeam(request);

            }
        }
    }
    void   saveTeam(TeamRequest request){


        NetWorkApiCall.getInstance().getApiResponse(mContext, APIExecutor.getApiService().saveTeam(request), new OnApiResponse() {
            @Override
            public void onResponse(Response response) {

                progressDialog.cancel();
                if(response!=null){
                   //final TeamDetails userDetails= new Gson().fromJson(new Gson().toJsonTree(response.data).toString(),TeamDetails.class);
                 String teamDetails= new Gson().toJsonTree(response.data).toString();

                   /* realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            // Add a person

                            realm.copyToRealmOrUpdate(userDetails);


                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llContainer, AddTeamPlayerFragment.newInstance(mParam1, "")).addToBackStack(null).commit();

                        }
                    });*/
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llContainer, AddTeamPlayerFragment.newInstance(mParam1, teamDetails)).addToBackStack(null).commit();

                }
            }
        });
    }
    @Subscribe
    public void onEventMainThread( Place place){
        tvlocation.setText(place.getAddress());
        teamLat=place.getLatLng().latitude;
        teamLong=place.getLatLng().longitude;
    }
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() !=null){
            if (getActivity() instanceof CreateTeamActivity){
                ((CreateTeamActivity) getActivity()).getAddressAndEnableLocation();
            }
        }
        if(mParam1.equalsIgnoreCase("")){

        }else {
            titleSkip.setVisibility(View.GONE);
        }
    }
}
