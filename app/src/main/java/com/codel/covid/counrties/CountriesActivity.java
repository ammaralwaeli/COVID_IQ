package com.codel.covid.counrties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.codel.covid.R;
import com.codel.covid.api.ApiResponse;
import com.codel.covid.databinding.ActivityCountriesBinding;
import com.codel.covid.localCountry.LocalCountryActivity;

public class CountriesActivity extends AppCompatActivity implements CountryAdapter.onClickListener {




    CountriesViewModel countriesViewModel;
    ActivityCountriesBinding binding;
    CountryAdapter adapter;
    boolean canFilter;
    SortBy sortBy;

    public static void newInstance(Context context) {

        Intent in = new Intent(context, CountriesActivity.class);
        context.startActivity(in);
    }
    private void setupRadioGroupLestiner() {
        binding.toggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                binding.progressIndicator.setVisibility(View.VISIBLE);
                binding.recy.setAdapter(null);
                if (binding.sDeaths.getId() == checkedId) {
                    sortBy=SortBy.DEATHS;
                    binding.sDeaths.setTextColor(Color.BLACK);
                    binding.sCases.setTextColor(Color.WHITE);
                    binding.sRecovered.setTextColor(Color.WHITE);
                } else if (binding.sRecovered.getId() == checkedId) {
                    sortBy=SortBy.RECOVERED;
                    binding.sRecovered.setTextColor(Color.BLACK);
                    binding.sCases.setTextColor(Color.WHITE);
                    binding.sDeaths.setTextColor(Color.WHITE);
                }else if(binding.sCases.getId() == checkedId){
                    binding.sRecovered.setTextColor(Color.WHITE);
                    binding.sDeaths.setTextColor(Color.WHITE);
                    binding.sCases.setTextColor(Color.BLACK);
                    sortBy=SortBy.CASES;
                }
                countriesViewModel.setMutableLiveDataNull();
                setupViewModel(sortBy);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_countries);
        binding.progressIndicator.setVisibility(View.VISIBLE);
        canFilter =false;
        sortBy= SortBy.CASES;
        binding.recy.requestFocus();
        binding.errorText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchBar.setText("");
                binding.errorText.setText("");
                countriesViewModel.setMutableLiveDataNull();
                binding.progressIndicator.setVisibility(View.VISIBLE);
                countriesViewModel.setMutableLiveDataNull();
                setupViewModel(sortBy);
            }
        });
        setupRadioGroupLestiner();
        binding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(canFilter) {
                    adapter.getFilter().filter(s);
                    if(adapter.getItemCount()==0){
                        binding.errorText.setVisibility(View.VISIBLE);
                        binding.errorText.setText("لا توجد دولة بهذا الاسم " + s);
                    }else{
                        binding.errorText.setVisibility(View.GONE);
                    }
                    binding.recy.setAdapter(adapter);
                }
            }
        });
        setupViewModel(sortBy);
    }


    private void setupViewModel(SortBy sortBy){
        countriesViewModel = new ViewModelProvider(this).get(CountriesViewModel.class);
        countriesViewModel.init(sortBy);
        countriesViewModel.getCountriesRepository().observe(this, new Observer<ApiResponse>() {
                    @Override
                    public void onChanged(ApiResponse myResponse) {
                        if (myResponse == null) {
                            Log.d("LoginError", "null");
                            return;
                        }
                        if (myResponse.getError() == null) {
                            adapter=new CountryAdapter(CountriesActivity.this,myResponse.getPosts());
                            adapter.setListener(CountriesActivity.this);
                            binding.recy.setAdapter(adapter);
                            canFilter =true;
                        } else {
                            String s = myResponse.getError();
                            binding.errorText.setVisibility(View.VISIBLE);

                            if(startWithNumber(s)){

                                binding.errorText.setText(s);
                            }else{
                                binding.errorText.setText("خطا في الاتصال");
                            }
                            Log.d("LoginError", s);

                        }
                        binding.progressIndicator.setVisibility(View.GONE);
                    }
                }
        );

    }
    private boolean startWithNumber(String s){
        return (s.charAt(0)>='0'&& s.charAt(0)<='9');
    }

    @Override
    public void onClick(String name) {
        LocalCountryActivity.newInstance(this,name);
    }
}
