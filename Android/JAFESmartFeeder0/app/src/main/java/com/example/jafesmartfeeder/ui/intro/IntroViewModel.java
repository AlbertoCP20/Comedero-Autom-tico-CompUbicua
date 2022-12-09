package com.example.jafesmartfeeder.ui.intro;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IntroViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public IntroViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}