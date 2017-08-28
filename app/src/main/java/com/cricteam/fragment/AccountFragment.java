package com.cricteam.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricteam.CreateTeamActivity;
import com.cricteam.R;
import com.cricteam.adapter.FindTeamAdapter;
import com.cricteam.adapter.MyTeamAdapter;
import com.cricteam.adapter.PlayerAdapter;
import com.cricteam.listner.OnFragmentInteractionListener;
import com.cricteam.utils.AppConstants;

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
    private FloatingActionButton fbAddTeam;

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
        initializedId();
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTeamList.setLayoutManager(linearLayoutManager);
        rvTeamList.setAdapter(new MyTeamAdapter(mContext));
        fbAddTeam.setOnClickListener(new View.OnClickListener() {
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
        fbAddTeam=(FloatingActionButton)mView.findViewById(R.id.fbAddTeam);
        rvTeamList=(RecyclerView)mView.findViewById(R.id.rvTeamList);
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
            Snackbar.make(fbAddTeam,"Team Added SuccessFully",Snackbar.LENGTH_SHORT).show();
        }
    }
}
