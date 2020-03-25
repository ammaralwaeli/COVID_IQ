package com.codel.covid.localCountry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codel.covid.api.ApiResponse;
import com.codel.covid.counrties.CountriesRepository;
import com.codel.covid.main.MyResponse;

public class TimelineViewModel extends ViewModel {


    private MutableLiveData<MyResponse> mutableLiveData;

    public void init(String name) {
        if (mutableLiveData != null) {
            return;
        }
        TimelineRepository timelineRepository = TimelineRepository.getInstance();
        mutableLiveData = timelineRepository.getTimeline(name);
    }

    public void setMutableLiveDataNull(){
        this.mutableLiveData=null;
    }
    LiveData<MyResponse> getTimelineRepository() {
        return mutableLiveData;
    }
}
