package com.example.jafesmartfeeder.ui.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.jafesmartfeeder.databinding.FragmentIntroBinding;

public class IntroFragment extends Fragment {

    private FragmentIntroBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        IntroViewModel introViewModel =
                new ViewModelProvider(this).get(IntroViewModel.class);

        binding = FragmentIntroBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}