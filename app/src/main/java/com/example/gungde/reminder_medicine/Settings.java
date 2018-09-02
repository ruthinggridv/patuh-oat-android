package com.example.gungde.reminder_medicine;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gungde.reminder_medicine.ActivityChats.AcountSettings;
import com.example.gungde.reminder_medicine.ActivityChats.AllUserActivity;
import com.example.gungde.reminder_medicine.ActivityChats.ProfilActivity;
import com.example.gungde.reminder_medicine.model.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.googlecode.mp4parser.authoring.Edit;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {
    private LinearLayout lpindahToHelp,lpindahToAbout;
    private Toolbar mToolbar;
    private RecyclerView mListView;
    private String nambahaja;
    private DatabaseReference mDatabaseReference;
    //private FirebaseListAdapter adapter;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseRecyclerAdapter<Users, Settings.UserviewHolder> adapter;

    private ProgressDialog mProgressDialog;
    private ImageView statusOnline;
    private TextView notifnull;

    //Search User
    private EditText searchField;

    private String searchText;
    private DatabaseReference mUserDatabase;
    private ProgressBar progressBar;


    public Settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       //-----------------------------JANGAN DI GANGGU--------------------------------------//

        // Inflate the layout for this fragment
//        View mview =  inflater.inflate(R.layout.fragment_settings, container, false);
//        lpindahToHelp = mview.findViewById(R.id.pindah_to_bantuan);
//        lpindahToHelp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),Help.class);
//                startActivity(intent);
//            }
//        });
//        lpindahToAbout = mview.findViewById(R.id.pindah_to_about);
//        lpindahToAbout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), About.class);
//                startActivity(intent);
//            }
//        });
//        return mview;

        //-----------------------------JANGAN DI GANGGU--------------------------------------//

        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_settings, container, false);
        progressBar = mView.findViewById(R.id.load_allUser);


        //search inisial
        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");
        searchField = (EditText) mView.findViewById(R.id.search_field);
        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE)){
                    searchText = searchField.getText().toString();


                    firebaseUserSearch(searchText);

                }
                return false;
            }
        });

        //database ref users
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseReference.keepSynced(true);

//        mToolbar = (Toolbar) mView.findViewById(R.id.user_appbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle("All User");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                finish();
//            }
//        });
        statusOnline = (ImageView)  mView.findViewById(R.id.user_single_online_icon);

        notifnull =  mView.findViewById(R.id.notifnulluser);

        //Recycle view
        mListView = (RecyclerView)  mView.findViewById(R.id.user_list);
        mListView.setHasFixedSize(true);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));



        //set query
        Query query = mUserDatabase.orderByChild("name").limitToLast(50);

        //set Options
        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(query, Users.class)
                        .setLifecycleOwner(this)
                        .build();
        //Set adapter from Recycle firebased database ui
        adapter = new FirebaseRecyclerAdapter<Users, Settings.UserviewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Settings.UserviewHolder holder, int position, @NonNull Users model) {

                holder.setNama(model.getName());
                final String nama = model.getName();
                holder.setstatus(model.getAddress());
                holder.setMcCircleImageView(model.getThumb_image());


                final String user_id = getRef(position).getKey();

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (!user_id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                            Intent i = new Intent(getActivity(), ProfilActivity.class);
                            i.putExtra("user_id", user_id);
                            startActivity(i);
                        }else {
                            Intent i = new Intent(getActivity(), AcountSettings.class);
                            i.putExtra("user_id", user_id);
                            startActivity(i);
                        }

                    }
                });
            }
            @NonNull
            @Override
            public Settings.UserviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_singgle_layout, parent, false);
                return new Settings.UserviewHolder(mView);
            }
        };
        //if data != null setadapter to friendlist
        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long jumlah = dataSnapshot.getChildrenCount();
                if (jumlah>0){
                    mListView.setAdapter(adapter);
                }else {
                    notifnull.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return mView;
    }

    private void firebaseUserSearch(String searchText) {

        /*Toast.makeText(AllUserActivity.this, "Started Search", Toast.LENGTH_LONG).show();*/
        progressBar.setVisibility(View.VISIBLE);

        Query firebaseSearchQuery = mUserDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

        //set Options
        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(firebaseSearchQuery, Users.class)
                        .setLifecycleOwner(this)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Users, Settings.UserviewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Settings.UserviewHolder holder, int position, @NonNull Users model) {

                holder.setNama(model.getName());

                holder.setstatus(model.getAddress());
                holder.setMcCircleImageView(model.getThumb_image());

                progressBar.setVisibility(View.GONE);





                final String user_id = getRef(position).getKey();

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (!user_id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                            Intent i = new Intent(getActivity(), ProfilActivity.class);
                            i.putExtra("user_id", user_id);
                            startActivity(i);
                        }else {
                            Intent i = new Intent(getActivity(), AcountSettings.class);
                            i.putExtra("user_id", user_id);
                            startActivity(i);
                        }

                    }
                });
            }

            @NonNull
            @Override
            public Settings.UserviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_singgle_layout, parent, false);
                return new  Settings.UserviewHolder(mView);
            }
        };

        mListView.setAdapter(adapter);

    }

//    private void closeKeyboard(){
//        View view = getActivity().getCurrentFocus();
//        if (view != null){
//            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            if (inputMethodManager != null) {
//                inputMethodManager.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
//            }
//        }
//    }
    @Override
    public void onStart() {
        super.onStart();


        //important for fibrebase database ui
        adapter.startListening();




    }

    @Override
    public void onStop() {
        super.onStop();
        //important for fibrebase database ui
        adapter.stopListening();
    }

    //VIEW HOLDER
    public class UserviewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView mdisplayname,mstatus ;
        CircleImageView mcCircleImageView;
        public UserviewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            mdisplayname = (TextView) mView.findViewById(R.id.user_singgle_name);
            mstatus  = (TextView) mView.findViewById(R.id.user_single_status);
            mcCircleImageView = (CircleImageView) mView.findViewById(R.id.profil_single_layout);



        }
        public void setNama(String display_name){

            mdisplayname.setText(display_name);

        }
        public void setstatus(String status){
            mstatus.setText(status);
        }
        public  void setMcCircleImageView(final String img_uri){
            //Picasso.with(UsersActivity.this).load(img_uri).placeholder(R.drawable.user).into(mcCircleImageView);
            if (img_uri!=null && !img_uri.equals("default")){
                Picasso.with(getActivity()).load(img_uri).networkPolicy(NetworkPolicy.OFFLINE)
                        .placeholder(R.drawable.default_avatar).into(mcCircleImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getActivity()).load(img_uri).placeholder(R.drawable.default_avatar).into(mcCircleImageView);

                    }
                });

            }else{
                Picasso.with(getActivity()).load(R.drawable.default_avatar).into(mcCircleImageView);

            }
        }
    }
    }
