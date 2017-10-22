package com.cricteam.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cricteam.CreateTeamActivity;
import com.cricteam.CricTeamApplication;
import com.cricteam.R;
import com.cricteam.adapter.FindTeamAdapter;
import com.cricteam.adapter.MyTeamAdapter;
import com.cricteam.adapter.PlayerAdapter;
import com.cricteam.listner.OnApiResponse;
import com.cricteam.listner.OnFragmentInteractionListener;
import com.cricteam.model.MyTeam;
import com.cricteam.netwokmodel.APIExecutor;
import com.cricteam.netwokmodel.NetWorkApiCall;
import com.cricteam.netwokmodel.Response;
import com.cricteam.netwokmodel.UserCompleteDetails;
import com.cricteam.netwokmodel.UserDetails;
import com.cricteam.utils.AppConstants;
import com.cricteam.utils.CommonUtils;
import com.cricteam.utils.TextDrawable;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static com.cricteam.R.id.rvPlayerList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int ADD_TEAM_REQUEST = 100;
    private View mView;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private RecyclerView rvTeamList;
    private TextView tvMyTeam;
    private List<MyTeam> myTeamList;
    private CardView cvCreateTeam;
    private AdView adView;
    private Realm realm;
    private UserDetails userDetails;
    private TextView TvOwenerName;
    private TextView tvOwenerLocation;
    private ImageView ivOwenerImage;
    private TextView tvHelp;
    private TextView tvCreateTeam;
    private TextView tvShareApp;
    private SwipeRefreshLayout swipeRefreshLayout;

    public AccountFragment() {
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
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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

        mView= inflater.inflate(R.layout.fragment_account, container, false);
        myTeamList = new ArrayList<>();
        initializedId();



        cvCreateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTeamPlayerFragment.counter=0;
                Intent intent= new Intent(mContext, CreateTeamActivity.class);
                intent.putExtra(AppConstants.FROM_ACCOUNT_SCREEN,AppConstants.FROM_ACCOUNT_SCREEN);
                startActivityForResult(intent,ADD_TEAM_REQUEST);
            }
        });
                return mView;
    }





    private void initializedId() {
        rvTeamList=(RecyclerView)mView.findViewById(R.id.rvTeamList);
        rvTeamList.setNestedScrollingEnabled(false);
        cvCreateTeam=(CardView) mView.findViewById(R.id.cvCreateTeam);
        swipeRefreshLayout=(SwipeRefreshLayout) mView.findViewById(R.id.swipeRefreshLayout);


        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(mContext,R.color.grey));
        tvMyTeam=(TextView) mView.findViewById(R.id.tvMyTeam);
        tvHelp=(TextView) mView.findViewById(R.id.tvHelp);
        tvCreateTeam=(TextView) mView.findViewById(R.id.tvCreateTeam);
        tvShareApp=(TextView) mView.findViewById(R.id.tvShareApp);
        TvOwenerName=(TextView) mView.findViewById(R.id.TvOwenerName);
        tvOwenerLocation=(TextView) mView.findViewById(R.id.tvOwenerLocation);
        ivOwenerImage=(ImageView) mView.findViewById(R.id.ivOwenerImage);
        String ADMOB_APP_ID = "ca-app-pub-6188430950445846~6237245453";
        MobileAds.initialize(mContext, ADMOB_APP_ID);
        adView=(AdView)mView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()

                .build();
        adView.loadAd(adRequest);
        tvHelp.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(mContext, R.drawable.ic_help_outline_black_24dp), null, null, null);
        tvCreateTeam.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(mContext, R.drawable.ic_add_black_24dp), null, null, null);
        tvShareApp.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(mContext, R.drawable.ic_share_black_24dp), null, null, null);

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_TEAM_REQUEST&& resultCode== Activity.RESULT_OK){
            Snackbar.make(cvCreateTeam,"Team Added SuccessFully",Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        getTeamList();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getTeamList();
            }
        });
    }

    private void getTeamList() {
        swipeRefreshLayout.setRefreshing(true);
        NetWorkApiCall.getInstance().getApiResponse(mContext, APIExecutor.getApiService().getUser(userDetails.getUserId()), new OnApiResponse() {
            @Override
            public void onResponse(Response response) {
                swipeRefreshLayout.setRefreshing(false);
                if(response!=null) {
                    UserCompleteDetails userCompleteDetails = new Gson().fromJson(new Gson().toJsonTree(response.data).toString(), UserCompleteDetails.class);
                  if(userCompleteDetails!=null) {
                      if (userCompleteDetails.getUserTeamList().size() > 0) {
                          rvTeamList.setVisibility(View.VISIBLE);
                          tvMyTeam.setVisibility(View.VISIBLE);
                          cvCreateTeam.setVisibility(View.GONE);
                          LinearLayoutManager linearLayoutManager= new LinearLayoutManager(mContext);
                          linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                          rvTeamList.setLayoutManager(linearLayoutManager);
                          rvTeamList.setAdapter(new MyTeamAdapter(mContext,userCompleteDetails.getUserTeamList()));
                      } else {
                          rvTeamList.setVisibility(View.GONE);
                          tvMyTeam.setVisibility(View.GONE);
                          cvCreateTeam.setVisibility(View.VISIBLE);
                      }
                  }
                }
            }
        });
    }

    private void setUi() {
        if(userDetails!=null){
            TvOwenerName.setText(userDetails.getName());
            if(userDetails.getUserImageUrl()!=null&&!userDetails.getUserImageUrl().equalsIgnoreCase("")){
                Glide.with(mContext).load(userDetails.getUserImageUrl()).into(ivOwenerImage);

            }else{
                if(userDetails.getName()!=null&&!userDetails.getName().equalsIgnoreCase("")) {
                    TextDrawable drawable = TextDrawable.builder()
                            .beginConfig()
                            .withBorder(4) /* thickness in px */
                            .endConfig()
                            .buildRoundRect("" + userDetails.getName().charAt(0), ContextCompat.getColor(mContext, R.color.colorPrimary), 10);
                    ivOwenerImage.setImageDrawable(drawable);
                }
            }
            tvOwenerLocation.setText(userDetails.getUserAddress());
        }
    }
}
