package com.codel.covid.localCountry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codel.covid.R;
import com.codel.covid.databinding.ActivityLocalCountryBinding;
import com.codel.covid.main.MyResponse;

public class LocalCountryActivity extends AppCompatActivity {

    ActivityLocalCountryBinding binding;
    TimelineAdapter adapter;
    TimelineViewModel timelineViewModel;
    private static String myName;
    public static void newInstance(Context context,String name) {

        Intent in = new Intent(context, LocalCountryActivity.class);
        context.startActivity(in);
        myName=name;
    }

    private boolean startWithNumber(String s) {
        return Character.isDigit(s.charAt(0));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_local_country);
        binding.setName(myName);
        setupViewModel(myName);
    }


    private void setupViewModel(String name){
        binding.progressIndicator.setVisibility(View.VISIBLE);
        timelineViewModel = new ViewModelProvider(this).get(TimelineViewModel.class);
        timelineViewModel.init(name);
        timelineViewModel.getTimelineRepository().observe(this, new Observer<MyResponse>() {
                    @Override
                    public void onChanged(MyResponse myResponse) {
                        if (myResponse == null) {
                            Log.d("LoginError", "null");
                            return;
                        }
                        if (myResponse.getError() == null) {
                            adapter=new TimelineAdapter(LocalCountryActivity.this,(HistoricalModel) myResponse.getPosts());
                            if(adapter.getItemCount()==0){
                                binding.errorText.setVisibility(View.VISIBLE);
                                binding.errorText.setText("لا يوجد جدول زمني لهذه الدولة " + myName);
                            }
                            binding.recy.setAdapter(adapter);
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
}
