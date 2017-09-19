package com.cricteam.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import com.cricteam.R;
import com.cricteam.adapter.FindTeamAdapter;
import com.cricteam.imagepicker.ImagePicker;
import com.cricteam.listner.OnFragmentInteractionListener;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView = inflater.inflate(R.layout.fragment_user_account, container, false);
        initializedId();
        fbSaveUser.setOnClickListener(this);
        tv_user_address.setOnClickListener(this);
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
                        String toastMsg = String.format("Place: %s", place.getName());
                        Toast.makeText(mContext, toastMsg, Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case ImagePicker.REQUEST_CAMERA:
                if (resultCode == Activity.RESULT_OK) {

               image=      ivUserPics.setImage(data);
                }
                break;
            case ImagePicker.REQUEST_GALLERY:
                if (resultCode == Activity.RESULT_OK) {
                    image=  ivUserPics.setImage(data);
                }
                break;
        }
    }

    private void initializedId() {
        fbSaveUser = (FloatingActionButton) mView.findViewById(R.id.fbSaveUser);
        ivUserPics = (ImagePicker) mView.findViewById(R.id.ivUserPics);
        ivUserPics.setFragment(this);
        et_name = (EditText) mView.findViewById(R.id.et_name);
        tv_mobile_no = (TextView) mView.findViewById(R.id.tv_mobile_no);
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
                saveImageToCloud();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llContainer, CreateTeamFragment.newInstance(mParam1, "")).addToBackStack(null).commit();

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
        if (image != null) {
            StorageReference riversRef = storageRef.child("myPics.jpg");
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
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.e("url", "" + downloadUrl);

                }
            });
        }
    }
    @Subscribe
    public void onEventMainThread( Place place){
        tv_user_address.setText(place.getAddress());
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
