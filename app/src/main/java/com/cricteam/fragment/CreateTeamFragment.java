package com.cricteam.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.content.res.AppCompatResources;
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
import com.cricteam.listner.OnFragmentInteractionListener;
import com.cricteam.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
       //  ivTeamLogo.setOnClickListener(this);
                return mView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(mContext,data);
               if(place!=null){
                  tvlocation.setText(""+ place.getAddress());
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(mContext, toastMsg, Toast.LENGTH_LONG).show();}
            }
        }
        switch (requestCode) {
            case PLACE_PICKER_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    Place place = PlacePicker.getPlace(mContext,data);
                    if(place!=null){
                        tvlocation.setText(""+ place.getAddress());
                        String toastMsg = String.format("Place: %s", place.getName());
                        Toast.makeText(mContext, toastMsg, Toast.LENGTH_LONG).show();}
                }
                break;
            case ImagePicker.REQUEST_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    ivTeamLogo.setImage(data);
                }
                break;
            case ImagePicker.REQUEST_GALLERY:
                if (resultCode == Activity.RESULT_OK) {
                    ivTeamLogo.setImage(data);
                }
                break;
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
        titleSkip.setVisibility(View.VISIBLE);
        ivTeamLogo.setFragment(this);
        titleHeader=(TextView) mView. findViewById(R.id.titleHeader);
        titleHeader.setText("Create Team");
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
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llContainer, AddTeamPlayerFragment.newInstance(mParam1, "")).addToBackStack(null).commit();
                }else {
                    Snackbar.make(etTeamName,"Please Enter Team name.",Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.titleSkip:
                Intent  intent= new Intent(mContext, DashBordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
               break;

        }
    }
    @Subscribe
    public void onEventMainThread( Place place){
        tvlocation.setText(place.getAddress());
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
    }
}
