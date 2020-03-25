package com.codel.covid.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TotalCountryViewModel extends ViewModel {


    private MutableLiveData<MyResponse> mutableLiveData;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        TotalRepository totalRepository = TotalRepository.getInstance();
        mutableLiveData = totalRepository.getTotal();
    }

    public void setMutableLiveDataNull(){
        this.mutableLiveData=null;
    }

    LiveData<MyResponse> getTotalRepository() {
        return mutableLiveData;
    }
}
