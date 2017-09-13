package com.cricteam.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cricteam.CreateTeamActivity;
import com.cricteam.R;
import com.cricteam.adapter.FindTeamAdapter;
import com.cricteam.listner.OnFragmentInteractionListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FindTeamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindTeamFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mView;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView searchImage;
    private OnFragmentInteractionListener mListener;
    private FloatingActionButton fbNext;
    private RecyclerView recylerFindTeam;
    private Context mContext;

    public FindTeamFragment() {
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
    public static FindTeamFragment newInstance(String param1, String param2) {
        FindTeamFragment fragment = new FindTeamFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView= inflater.inflate(R.layout.fragment_find_team, container, false);
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initializedId();
        setAdapter();

                return mView;
    }

    private void initalizedPlace() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }

    }

    private void setAdapter() {
        recylerFindTeam.setLayoutManager(new LinearLayoutManager(mContext));
        recylerFindTeam.setAdapter(new FindTeamAdapter(mContext));
    }

    private void initializedId() {
        recylerFindTeam=(RecyclerView)mView.findViewById(R.id.recyclerFindTeam);
         searchImage = (ImageView) getActivity().findViewById(R.id.searchImage);
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initalizedPlace();
            }
        });
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
    @Subscribe
    public void onEventMainThread( Place place){
        Log.e("latitude",""+place.getLatLng().latitude);
        Log.e("longitude",""+place.getLatLng().longitude);
        Toast.makeText(mContext,"lat" +place.getLatLng(),Toast.LENGTH_SHORT).show();
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
            mListener.onFragmentInteraction(null);
           /* if (getActivity() instanceof CreateTeamActivity){
                ((CreateTeamActivity) getActivity()).getAddressAndEnableLocation();
            }*/
        }
    }


}
