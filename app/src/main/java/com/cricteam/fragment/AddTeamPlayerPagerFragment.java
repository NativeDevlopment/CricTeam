package com.cricteam.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cricteam.DashBordActivity;
import com.cricteam.R;
import com.cricteam.adapter.AddPlayerPagerAdapter;
import com.cricteam.listner.OnFragmentInteractionListener;
import com.cricteam.model.Player;
import com.cricteam.utils.AppConstants;
import com.cricteam.utils.CommonUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddTeamPlayerPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTeamPlayerPagerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String playerType;
    private String mParam2;
    View mView;
    int counter=0;
    private OnFragmentInteractionListener mListener;
    private FloatingActionButton fbAddPlayer;
    private TextView tvNoOfaddPlayer;
    private TextInputEditText etPlayerName;
    private Context mContext;
    private TabLayout tabLayout;
    private ViewPager view_pager;
    private TextInputEditText etPlayerMobileNo;
    Player player;
    boolean isEdit;
    int sendPreview=0;
    private TextInputLayout tlPlayerName;

    public AddTeamPlayerPagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instanc
     *
     *
     * e of
     * this fragment using the provided parameters.
     *
     * @param playerType Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateTeamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTeamPlayerPagerFragment newInstance(String playerType, String param2) {
        AddTeamPlayerPagerFragment fragment = new AddTeamPlayerPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, playerType);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            playerType = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(etPlayerName!=null && etPlayerMobileNo!=null){
                if(AddTeamPlayerFragment.counter<=10) {
                    etPlayerName.setEnabled(true);
                    etPlayerMobileNo.setEnabled(true);}else {
                    etPlayerName.setEnabled(false);
                    etPlayerMobileNo.setEnabled(false);
                    alertDialogMethod();
                }
            }
        }

    }

    private void alertDialogMethod() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(mContext);
        builder.setCancelable(true);

        builder.setMessage("You Completed Team .Do you Want to See preview or Continue").setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent= new Intent(AppConstants.TEAM_PREVIEW);
                intent.putExtra(AppConstants.TEAM_PREVIEW,"No");
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                dialogInterface.cancel();

            }
        }).setNegativeButton("Preview", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent= new Intent(AppConstants.TEAM_PREVIEW);
                intent.putExtra(AppConstants.TEAM_PREVIEW,"Yes");
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                dialogInterface.cancel();



            }
        }).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(AddTeamPlayerFragment.counter<=11) {
            etPlayerName.setEnabled(true);
            etPlayerMobileNo.setEnabled(true);}else {
            etPlayerName.setEnabled(false);
            etPlayerMobileNo.setEnabled(false);
        }

        player= new Player();
        player.payerType=playerType;

        fbAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etPlayerName.getText().toString().equalsIgnoreCase("")) {
                    if (AddTeamPlayerFragment.counter < 11) {
                        Intent intent = new Intent(AppConstants.ADD_PLAYER);

                        player.name = etPlayerName.getText().toString();
                        player.mobileNo = etPlayerMobileNo.getText().toString();

                        intent.putExtra(AppConstants.ADD_PLAYER, player);

                        intent.putExtra(AppConstants.UPDATE_PLAYER, counter);
                        if (playerType == AppConstants.WK) {

                            isEdit = !isEdit;
                            Toast.makeText(mContext,"You successfully Added A WicketKeeper In Team Squade",Toast.LENGTH_SHORT).show();

                            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);


                            if (isEdit) {
                                tlPlayerName.setErrorEnabled(false);
                                fbAddPlayer.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.white)));
                                counter = 1;
                                fbAddPlayer.setImageResource(R.drawable.ic_edit_black_24dp);
                                etPlayerName.setEnabled(false);
                                etPlayerMobileNo.setEnabled(false);
                            } else {
                                counter = 2;
                                fbAddPlayer.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.colorPrimaryDark)));
                                etPlayerName.setEnabled(true);
                                etPlayerMobileNo.setEnabled(true);
                                fbAddPlayer.setImageResource(R.drawable.ic_save_black_24dp);

                            }
                        } else if (playerType == AppConstants.BAT) {
                            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                            etPlayerName.setText("");
                            etPlayerMobileNo.setText("");
                            etPlayerName.requestFocus();
                            tlPlayerName.setErrorEnabled(false);
                            if (AddTeamPlayerFragment.counter > 10) {
                                etPlayerName.setEnabled(false);
                                etPlayerMobileNo.setEnabled(false);
                            } else {
                                etPlayerName.setText("");
                                etPlayerName.setEnabled(true);
                                etPlayerMobileNo.setEnabled(true);
                                Toast.makeText(mContext,"You successfully Added A Batsman In Team Squade",Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                            etPlayerName.setText("");
                            etPlayerMobileNo.setText("");
                            etPlayerName.requestFocus();
                            tlPlayerName.setErrorEnabled(false);
                            if (AddTeamPlayerFragment.counter < 10) {
                                etPlayerName.setEnabled(true);
                                etPlayerMobileNo.setEnabled(true);
                                Toast.makeText(mContext,"You successfully Added A Boller In Team Squade",Toast.LENGTH_SHORT).show();
                                etPlayerName.setText("");

                            } else {
                                etPlayerName.setEnabled(false);
                                etPlayerMobileNo.setEnabled(false);
                            }

                        }
                    } else {
                        etPlayerName.setEnabled(false);
                        etPlayerMobileNo.setEnabled(false);
                        tlPlayerName.setErrorEnabled(false);
                        alertDialogMethod();
                    }


                }else {
                    tlPlayerName.setError("Please enter player name");
                    etPlayerName.requestFocus();
                    tlPlayerName.setErrorEnabled(false);
                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =inflater.inflate(R.layout.fragment_add_team_pager_player, container, false);
        initializedId();

            return mView;
    }



    private void initializedId() {
        fbAddPlayer=(FloatingActionButton)mView.findViewById(R.id.fbAddPlayer);
        etPlayerName=(TextInputEditText)mView.findViewById(R.id.etPlayerName);
        etPlayerMobileNo=(TextInputEditText)mView.findViewById(R.id.etPlayerMobileNo);
        etPlayerMobileNo=(TextInputEditText)mView.findViewById(R.id.etPlayerMobileNo);
        tlPlayerName=(TextInputLayout)mView.findViewById(R.id.tlPlayerName);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
