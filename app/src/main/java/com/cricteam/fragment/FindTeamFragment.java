package com.cricteam.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cricteam.CreateTeamActivity;
import com.cricteam.R;
import com.cricteam.SearchdLocationActivity;
import com.cricteam.adapter.FindTeamAdapter;
import com.cricteam.listner.EndlessRecyclerViewScrollListener;
import com.cricteam.listner.OnApiResponse;
import com.cricteam.listner.OnFragmentInteractionListener;
import com.cricteam.netwokmodel.APIExecutor;
import com.cricteam.netwokmodel.FindTeamRequest;
import com.cricteam.netwokmodel.NetWorkApiCall;
import com.cricteam.netwokmodel.Response;
import com.cricteam.netwokmodel.SearchTeam;
import com.cricteam.utils.AppConstants;
import com.cricteam.utils.CommonUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.QueryMap;

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
   final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView searchImage;
    private OnFragmentInteractionListener mListener;
    private FloatingActionButton fbNext;
    private RecyclerView recylerFindTeam;
    private Context mContext;
    private LinearLayoutManager linearLayoutMangaer;
    private EndlessRecyclerViewScrollListener scrollListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page=1;
    private String currentLang;
    private String currentLat;
    private String lastAddress;
    private TextView tvHeader;
    private ImageView currentLoction;
    List<SearchTeam> searchTeams;
    private FindTeamAdapter findTeamAdapter;
    private TextView progressLabel;
    private boolean isPage=true;

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

        currentLat=   CommonUtils.getPreferencesString(mContext,AppConstants.CUURENT_LAT);
        currentLang= CommonUtils.getPreferencesString(mContext,AppConstants.CUURENT_LANG);
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initializedId();
        searchTeams= new ArrayList<>();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PLACE_AUTOCOMPLETE_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Place place = PlacePicker.getPlace(mContext, data);
                    if (place != null) {
                        currentLat = String.valueOf(place.getLatLng().latitude);
                        currentLang = String.valueOf(place.getLatLng().longitude);
                        lastAddress = String.valueOf(place.getAddress());
                       // tvHeader.setText(lastAddress);
                        CommonUtils.savePreferencesString(mContext,AppConstants.CUURENT_LAT,currentLat);
                        CommonUtils.savePreferencesString(mContext,AppConstants.CUURENT_LANG,currentLang);
                        CommonUtils.savePreferencesString(mContext,AppConstants.LAST_ADDRESS,lastAddress);
                        page=1;
                        swipeRefreshLayout.setRefreshing(true);
                        progressLabel.setVisibility(View.VISIBLE);
                        // 1. First, clear the array of data
                        //    listOfItems.clear();
// 2. Notify the adapter of the update
                        //   recyclerAdapterOfItems.notifyDataSetChanged(); // or notifyItemRangeRemoved
// 3. Reset endless scroll listener when performing a new search
                        scrollListener.resetState();
                        getTeamList(page);
                    }
                }
                break;
        }
    }

    private void setAdapter() {
      linearLayoutMangaer = new LinearLayoutManager(mContext);
        recylerFindTeam.setLayoutManager(linearLayoutMangaer);
         findTeamAdapter = new FindTeamAdapter(mContext, searchTeams);
        recylerFindTeam.setAdapter(findTeamAdapter);
    }

    private void initializedId() {
        recylerFindTeam=(RecyclerView)mView.findViewById(R.id.recyclerFindTeam);
        progressLabel=(TextView)mView.findViewById(R.id.progressLabel);
         searchImage = (ImageView) getActivity().findViewById(R.id.searchImage);
        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipeRefreshLayout);
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initalizedPlace();
            }
        });
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

        currentLat= String.valueOf(place.getLatLng().latitude);
        currentLang= String.valueOf(place.getLatLng().longitude);
       lastAddress = String.valueOf(place.getAddress());
    //    tvHeader.setText(lastAddress);
        CommonUtils.savePreferencesString(mContext,AppConstants.CUURENT_LAT,currentLat);
        CommonUtils.savePreferencesString(mContext,AppConstants.CUURENT_LANG,currentLang);
        CommonUtils.savePreferencesString(mContext,AppConstants.LAST_ADDRESS,lastAddress);
        page=1;
        swipeRefreshLayout.setRefreshing(true);
        progressLabel.setVisibility(View.VISIBLE);
        // 1. First, clear the array of data
        //    listOfItems.clear();
// 2. Notify the adapter of the update
        //   recyclerAdapterOfItems.notifyDataSetChanged(); // or notifyItemRangeRemoved
// 3. Reset endless scroll listener when performing a new search
        scrollListener.resetState();
        getTeamList(page);
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
            tvHeader = (TextView)getActivity(). findViewById(R.id.tvHeader);
            currentLoction = (ImageView)getActivity(). findViewById(R.id.currentLoction);
            currentLoction.setVisibility(View.VISIBLE);
            mListener.onFragmentInteraction(null);
           /* if (getActivity() instanceof CreateTeamActivity){
                ((CreateTeamActivity) getActivity()).getAddressAndEnableLocation();
            }*/
            swipeRefreshLayout.setRefreshing(true);
            getTeamList(page);

        }
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutMangaer) {
            @Override
            public void onLoadMore(int page1, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                if(isPage){
                getTeamList(page);}
            }
        };
        // Adds the scroll listener to RecyclerView
        recylerFindTeam.addOnScrollListener(scrollListener);
       // swipeRefreshLayout.setNestedScrollingEnabled(false);
        //recylerFindTeam.setNestedScrollingEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                swipeRefreshLayout.setRefreshing(true);
                // 1. First, clear the array of data
               searchTeams.clear();
// 2. Notify the adapter of the update
              findTeamAdapter.notifyDataSetChanged(); // or notifyItemRangeRemoved
// 3. Reset endless scroll listener when performing a new search
                scrollListener.resetState();
                getTeamList(page);
                progressLabel.setVisibility(View.VISIBLE);
            }
        });
        currentLoction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, SearchdLocationActivity.class));
            }
        });

    }

    void    getTeamList(final int pageNo){
        Map<String,Integer> map=new HashMap<>();

        map.put("pageNo",pageNo);
        map.put("pageSize",10);
        FindTeamRequest findTeamRequest= new FindTeamRequest();
      //  findTeamRequest.setUserId();
        //findTeamRequest.setTeamId(1);
      findTeamRequest.setTeamId(Integer.parseInt(CommonUtils.getPreferences(mContext, AppConstants.TEAM_ID)));
        findTeamRequest.setUserId(Integer.parseInt(CommonUtils.getPreferences(mContext, AppConstants.USER_ID)));
        findTeamRequest.setLatitude(currentLat);
        findTeamRequest.setLongitude(currentLang);
        Log.e("Request",new Gson().toJsonTree(findTeamRequest).toString());
        if(currentLat!=null && !currentLat.equalsIgnoreCase("") && currentLang!=null&& !currentLang.equalsIgnoreCase("")){
         NetWorkApiCall.getInstance().getApiResponse(mContext, APIExecutor.getApiService().findTeam(findTeamRequest,  map), new OnApiResponse() {
        @Override
        public void onResponse(Response response) {

            swipeRefreshLayout.setRefreshing(false);
            if(response!=null){
                progressLabel.setVisibility(View.GONE);

                if(pageNo==1){
                    searchTeams.clear();
                }

                Type listType = new TypeToken<ArrayList<SearchTeam>>(){}.getType();
                List<SearchTeam> searchTeam= new Gson().fromJson(new Gson().toJsonTree(response.data).toString(),listType);

                searchTeams.addAll(searchTeam);
                if(pageNo==1&&searchTeam.size()==0){
                    progressLabel.setVisibility(View.VISIBLE);
                    progressLabel.setText("It seems Like No Team Available in your Area. Kindly Share App to Get Team in Your Area");

                }
                if(searchTeam.size()>0){
                    SearchTeam searchTeam1= new SearchTeam();
                    searchTeam1.setAddView(true);
                    searchTeams.add(searchTeam1);
                    isPage=true;

                }else {
                    isPage=false;
                }
                findTeamAdapter.notifyDataSetChanged();
                page=page+1;

            }
        }
    });
}else {
    showMessage(mContext,"Please search location or Enable Gps Location");
}

    }
    void showMessage (Context context , String message){

        AlertDialog.Builder builder= new AlertDialog.Builder(context).setMessage(message).
                setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.show();
    }
}
