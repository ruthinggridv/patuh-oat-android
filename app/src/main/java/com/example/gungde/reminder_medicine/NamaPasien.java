package com.example.gungde.reminder_medicine;

import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gungde.reminder_medicine.model.Friends;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class NamaPasien extends Fragment {
    @BindView(R.id.listObat)
    RecyclerView listObat;
    @BindView(R.id.prograss)
    ProgressBar prograss;
    @BindView(R.id.main_frame)
    FrameLayout mainFrame;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    private Unbinder unbinder;

    private DatabaseReference mFriendsDatabase;
    private DatabaseReference mUsersDatabase;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;
    private ProgressDialog mProgressDialog;
    private DatabaseReference mUserRef;
    private FirebaseRecyclerAdapter<Friends, FriendsViewHolder> adapter;

    public NamaPasien() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.activity_list_nama_pasien, container, false);
        ButterKnife.bind(this, mView);

        recyclerView = mView.findViewById(R.id.listObat);
        mAuth = FirebaseAuth.getInstance();

        mCurrent_user_id = mAuth.getCurrentUser().getUid();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrent_user_id);
        mFriendsDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        /* Query query = FirebaseDatabase.getInstance().getReference().child("Users").limitToLast(50);*/
        Query query2 = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrent_user_id)
                .orderByChild("kategori_pengguna").equalTo("Pasien")
                .limitToLast(50);
        FirebaseRecyclerOptions<Friends> options = new FirebaseRecyclerOptions.Builder<Friends>()
                .setQuery(query2, Friends.class)
                .setLifecycleOwner((LifecycleOwner) this)
                .build();

        adapter = new FirebaseRecyclerAdapter<Friends, FriendsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FriendsViewHolder holder, final int position, @NonNull Friends model) {

                holder.setDate(model.getDate());
                final String list_user_id = getRef(position).getKey();
                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                String kategori = dataSnapshot.child("kategori_pengguna").getValue().toString();
                        final String username = dataSnapshot.child("name").getValue().toString();
                        String image_thumb = dataSnapshot.child("thumb_image").getValue().toString();
                        final String email = dataSnapshot.child("email").getValue().toString();

                        holder.setName(username);
                        holder.setMcCircleImageView(image_thumb);
                        holder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ReportArtikelPasien fragment = new ReportArtikelPasien();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
                                transaction.replace(R.id.main_frame, ReportArtikelPasien.newInstance(email));
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        });
//                        if (dataSnapshot.child("kategori_pengguna").getValue().toString().equals("Keluarga")) {
//
//                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_singgle_layout, parent, false);
                return new FriendsViewHolder(mView);
            }
        }

        ;


        FirebaseDatabase.getInstance().

                getReference().

                child("Friends").

                child(mCurrent_user_id).

                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long jumlah = dataSnapshot.getChildrenCount();
                        if (jumlah > 0) {
                            recyclerView.setAdapter(adapter);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        mUserRef.child("online").setValue("true");

        progressDoalog.hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView mdisplayname, mstatus, userStatusView;
        CircleImageView mcCircleImageView;
        ImageView userOnlineView;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            mdisplayname = (TextView) mView.findViewById(R.id.user_singgle_name);
            mstatus = (TextView) mView.findViewById(R.id.user_single_status);
            mcCircleImageView = (CircleImageView) mView.findViewById(R.id.profil_single_layout);

            userStatusView = (TextView) mView.findViewById(R.id.user_single_status);
            userOnlineView = (ImageView) mView.findViewById(R.id.user_single_online_icon);

        }

        public void setDate(String date) {


            userStatusView.setText(date);

        }

        public void setName(String name) {
            mdisplayname.setText(name);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void setMcCircleImageView(final String img_uri) {
            //Picasso.with(UsersActivity.this).load(img_uri).placeholder(R.drawable.user).into(mcCircleImageView);
            if (!img_uri.equals("default")) {
                Picasso.with(getContext()).load(img_uri).networkPolicy(NetworkPolicy.OFFLINE)
                        .placeholder(R.drawable.default_avatar).into(mcCircleImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getContext()).load(img_uri).placeholder(R.drawable.default_avatar).into(mcCircleImageView);

                    }
                });

            } else {
                Picasso.with(getContext()).load(R.drawable.default_avatar).into(mcCircleImageView);

            }
        }

        public void setStatusOnline(String online) {
            if (online.equals("true")) {
                userOnlineView.setVisibility(View.VISIBLE);
            } else {
                userOnlineView.setVisibility(View.INVISIBLE);
            }

        }
    }
}
