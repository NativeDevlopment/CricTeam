package com.cricteam.fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cricteam.DashBordActivity;
import com.cricteam.R;
import com.cricteam.TeamCaptionActivity;
import com.cricteam.adapter.AddPlayerPagerAdapter;
import com.cricteam.adapter.PlayerCreateTeamAdapter;
import com.cricteam.adapter.PlayerPreviewAdapter;
import com.cricteam.listner.OnFragmentInteractionListener;
import com.cricteam.model.Player;
import com.cricteam.utils.AppConstants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddTeamPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTeamPlayerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static int counter=0;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View mView;

    private OnFragmentInteractionListener mListener;
    private FloatingActionButton fbAddPlayer;
    private TextView tvNoOfaddPlayer;
    private TextInputEditText etPlayerName;
    private Context mContext;
    private TabLayout tabLayout;
    private ViewPager view_pager;
    private TextView tvTeamPreview;
    private TextView tvNoOFPlayer;
    private BroadcastReceiver receiveTeamPlayer;
    private BroadcastReceiver teamPreview;
    private TextView titleHeader;
    private AdView adView;
    private TextInputLayout tlPlayerName;
    List<Player> playerList;
    Player player;
    private TextView titleSkip;

    public AddTeamPlayerFragment() {
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
    public static AddTeamPlayerFragment newInstance(String param1, String param2) {
        AddTeamPlayerFragment fragment = new AddTeamPlayerFragment();
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

                mView =inflater.inflate(R.layout.fragment_new_add_team_player, container, false);
        playerList= new ArrayList<Player>();
        initializedId();
        teamPreview= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getStringExtra(AppConstants.TEAM_PREVIEW).equalsIgnoreCase("Yes")){
                bottomDialog();}else {
                    fbAddPlayer.performClick();
                }
            }
        };
             receiveTeamPlayer = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                player= new Player();
               Player  localPlayer=intent.getParcelableExtra(AppConstants.ADD_PLAYER);
                player.name=localPlayer.name;
                player.payerType=localPlayer.payerType;
                player.mobileNo=localPlayer.mobileNo;
                if(intent.getIntExtra(AppConstants.UPDATE_PLAYER,0)==1){

                }else if(intent.getIntExtra(AppConstants.UPDATE_PLAYER,0)==2) {
                    for (int i = 0; i <playerList.size() ; i++) {
                        if(playerList.get(i).payerType.equalsIgnoreCase(player.payerType)){
                            playerList.get(i).name=player.name;
                            playerList.get(i).payerType=player.payerType;
                            playerList.get(i).mobileNo=player.mobileNo;
                            break;
                        }

                    }
                }else{
                    playerList.add(player);
                Log.e("player name",player.name);
                counter++;
                tvNoOFPlayer.setText(""+counter+"/11");}
                if(counter>=11) {
                    fbAddPlayer.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.colorPrimaryDark)));
                }
            }
        };
        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiveTeamPlayer,new IntentFilter(AppConstants.ADD_PLAYER));
        LocalBroadcastManager.getInstance(mContext).registerReceiver(teamPreview,new IntentFilter(AppConstants.TEAM_PREVIEW));
        fbAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(counter>=11) {

                        Intent intent = new Intent(mContext, TeamCaptionActivity.class);
                        intent.putExtra(AppConstants.FROM_ACCOUNT_SCREEN, mParam1);
                        ArrayList<Player>  playerArrayList= (ArrayList<Player>) playerList;
                        intent.putParcelableArrayListExtra(AppConstants.PLAYER_LIST,playerArrayList);

                        startActivity(intent);
                        if (mParam1.equalsIgnoreCase(AppConstants.FROM_ACCOUNT_SCREEN)) {
                            getActivity().finish();
                        }
                    }else {
                        alertDialogMethod();
                    }
            }
        });
        view_pager=(ViewPager)mView.findViewById(R.id.view_pager);
        tabLayout=(TabLayout)mView.findViewById(R.id.tab_layout);

        view_pager.setAdapter(new AddPlayerPagerAdapter(getChildFragmentManager()));

        tabLayout.setupWithViewPager(view_pager);
        view_pager.setOffscreenPageLimit(3);
        tvTeamPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog();
            }
        });
        String ADMOB_APP_ID = "ca-app-pub-6188430950445846~6237245453";
        MobileAds.initialize(mContext, ADMOB_APP_ID);
        adView=(AdView)mView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()

                .build();
        adView.loadAd(adRequest);

        return mView;
    }
    private void alertDialogMethod() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(mContext);
        builder.setCancelable(true);

        builder.setMessage("You are not completed Team Yet \n please completed Team Squad").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        }).show();
    }

    private void initializedId() {
        fbAddPlayer = (FloatingActionButton) mView.findViewById(R.id.fbAddPlayer);
        tvNoOFPlayer = (TextView) mView.findViewById(R.id.tvNoOFPlayer);
        tvTeamPreview = (TextView) mView.findViewById(R.id.tvTeamPreview);
        titleHeader=(TextView) mView. findViewById(R.id.titleHeader);
        titleHeader.setText("Add Player");
        titleSkip=(TextView)mView.findViewById(R.id.titleSkip);
        titleSkip.setVisibility(View.VISIBLE);
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

    private void bottomDialog() {

        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mContext);
        if(getActivity()!=null){
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_player_list, null);
        RecyclerView recyclerView= sheetView.findViewById(R.id.RecylerListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new PlayerPreviewAdapter(mContext,playerList));
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();}
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titleSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent= new Intent(mContext, DashBordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
