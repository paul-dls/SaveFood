package com.example.myfridge.ui.home;

import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myfridge.Aliments;
import com.example.myfridge.AlimentsOperations;
import com.example.myfridge.R;

import java.util.Vector;

public class HomeViewModel extends ViewModel {

    private AlimentsOperations alimentsOperations;
    private MutableLiveData<String> mText;

    public HomeViewModel() {

        mText = new MutableLiveData<>();

        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}