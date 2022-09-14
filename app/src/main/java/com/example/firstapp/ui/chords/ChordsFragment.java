package com.example.firstapp.ui.chords;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.firstapp.databinding.FragmentChordsBinding;


public class ChordsFragment extends Fragment {


    private FragmentChordsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChordsViewModel homeViewModel =
                new ViewModelProvider(this).get(ChordsViewModel.class);

        binding = FragmentChordsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}