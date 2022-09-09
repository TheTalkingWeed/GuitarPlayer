package com.example.firstapp.ui.chords;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChordsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ChordsViewModel() {
        mText = new MutableLiveData<>();

    }



    public LiveData<String> getText() {
        return mText;
    }


}