package com.codel.covid.counrties;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codel.covid.api.ApiResponse;
import com.codel.covid.main.MyResponse;
import com.codel.covid.main.TotalRepository;

public class CountriesViewModel extends ViewModel {


    private MutableLiveData<ApiResponse> mutableLiveData;

    public void init(SortBy sortBy) {
        if (mutableLiveData != null) {
            return;
        }
        CountriesRepository countriesRepository = CountriesRepository.getInstance();
        mutableLiveData = countriesRepository.getCountries(sortBy.value);
    }

    public void setMutableLiveDataNull(){
        this.mutableLiveData=null;
    }
    LiveData<ApiResponse> getCountriesRepository() {
        return mutableLiveData;
    }
}
