package com.example.firebasestudy.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.firebasestudy.databinding.FragmentProfilBinding;
import com.example.firebasestudy.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    private FragmentProfilBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefrence;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfilBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFireBase();
        getData();
        mDatabase.getReference().child("user").child(mAuth.getCurrentUser().getUid()).child("type").setValue(1);

        binding.btnupdate.setOnClickListener(v -> mDatabase.getReference().child("user").child(mAuth.getCurrentUser().getUid()).child("type").setValue(1));
    }

    private void getData() {
        mRefrence.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User mUser = dataSnapshot.getValue(User.class);
                Glide.with(getActivity()).load(mUser.getUrl()).into(binding.addimage);
                binding.mailInscri.setText(mUser.getMail());
                binding.nomInscri.setText(mUser.getNom());
                binding.prenomInscri.setText(mUser.getPrenom());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("eror", databaseError.getMessage());

            }
        });

   /*     mRefrence.child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User mUser = dataSnapshot.getValue(User.class);
                Glide.with(getActivity()).load(mUser.getUrl()).into(binding.addimage);
                binding.mailInscri.setText(mUser.getMail());
                binding.nomInscri.setText(mUser.getNom());
                binding.prenomInscri.setText(mUser.getPrenom());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("eror", databaseError.getMessage());
            }
        });

*/

    }

    private void initFireBase() {
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();

        mRefrence = mDatabase.getReference("user");

    }
}
