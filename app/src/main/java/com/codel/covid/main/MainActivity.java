package com.codel.covid.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codel.covid.R;
import com.codel.covid.counrties.CountriesActivity;
import com.codel.covid.databinding.ActivityMainBinding;
import com.robinhood.ticker.TickerUtils;

public class MainActivity extends AppCompatActivity {

    TotalCountryViewModel totalCountryViewModel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.death.setCharacterLists(TickerUtils.provideNumberList());
        binding.recovered.setCharacterLists(TickerUtils.provideNumberList());
        binding.total.setCharacterLists(TickerUtils.provideNumberList());

        setupViewModel();
    }


    private void setupViewModel(){
        binding.progressIndicator.setVisibility(View.VISIBLE);
            totalCountryViewModel = new ViewModelProvider(this).get(TotalCountryViewModel.class);
            totalCountryViewModel.init();
            totalCountryViewModel.getTotalRepository().observe(this, new Observer<MyResponse>() {
                        @Override
                        public void onChanged(MyResponse myResponse) {
                            if(binding.death.getText()==null){
                                binding.death.setText("00,000",false);
                                binding.recovered.setText("000,000",false);
                                binding.total.setText("000,000",false);
                            }

                            if (myResponse == null) {
                                Log.d("LoginError", "null");
                                binding.date.setText("null");
                                return;
                            }
                            if (myResponse.getError() == null) {
                                binding.setItem((TotalModel) myResponse.getPosts());
                            } else {
                                String s = myResponse.getError();
                                if(startWithNumber(s)){
                                    binding.date.setText(s);
                                }else{
                                    binding.date.setText("خطأ في اتصال");
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
    public void goToCountries(View view) {
        CountriesActivity.newInstance(this);
    }

    public void update(View view) {
        Log.d("update","Clicked");
        totalCountryViewModel.setMutableLiveDataNull();
        binding.date.setText("");
        setupViewModel();
        binding.hasPendingBindings();
    }

}
