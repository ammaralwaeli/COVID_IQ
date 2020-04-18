package com.codel.covid.chart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codel.covid.R;
import com.codel.covid.counrties.CountriesActivity;
import com.codel.covid.databinding.ActivityChartBinding;
import com.codel.covid.localCountry.HistoricalModel;
import com.codel.covid.localCountry.TimelineViewModel;
import com.codel.covid.main.MyResponse;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class ChartActivity extends AppCompatActivity {


    ActivityChartBinding binding;
    HistoricalModel historicalModel;
    ArrayList<String> dates;
    ArrayList<Integer> values;
    private static String mName;

    public static void newInstance(Context context, String name) {

        Intent in = new Intent(context, ChartActivity.class);
        context.startActivity(in);
        mName = name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chart);
        setSupportActionBar(binding.toolbar);
        binding.setName(mName);
        setupViewModel();
    }

    private void setupViewModel() {
        TimelineViewModel timelineViewModel = new ViewModelProvider(this).get(TimelineViewModel.class);
        timelineViewModel.init(mName);
        timelineViewModel.getTimelineRepository().observe(this, new Observer<MyResponse>() {
                    @Override
                    public void onChanged(MyResponse myResponse) {

                        if (myResponse == null) {
                            Log.d("LoginError", "null");
                            binding.errText.setText("null");
                            return;
                        }
                        if (myResponse.getError() == null) {
                            historicalModel = (HistoricalModel) myResponse.getPosts();
                            dates = getSortedDates(new ArrayList<>(historicalModel.getTimeline().getCases().keySet()));
                            values = getValues(dates, historicalModel.getTimeline().getCases());
                            createChart(dates, values, "الحالات المصابة");


                        } else {
                            String s = myResponse.getError();

                            binding.errText.setText(s);

                            Log.d("LoginError", s);

                        }
                    }
                }
        );

    }

    private String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("M/d/yy");
        return dateFormat.format(date);
    }

    private ArrayList<String> getSortedDates(ArrayList<String> sdate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        ArrayList<Date> dates = new ArrayList<>();
        for (String dateString : sdate) {
            try {
                dates.add(simpleDateFormat.parse(dateString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(dates);
        ArrayList<String> sortedDates = new ArrayList<>();
        for (Date date : dates) {
            sortedDates.add(dateToString(date));
        }
        return sortedDates;
    }

    private void createChart(ArrayList<String> dates, ArrayList<Integer> arrayList, String lable) {

        ArrayList<Entry> list = new ArrayList<>();
        if (binding.chart.getData() != null) {
            binding.chart.clearValues();
        }
        list.add(new Entry(1, arrayList.get(0)));
        for (int i = 1; i < arrayList.size(); i++) {
            list.add(new Entry(i + 1, arrayList.get(i) - arrayList.get(i - 1)));
        }
        LineDataSet set1;
        if (binding.chart.getData() != null &&
                binding.chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) binding.chart.getData().getDataSetByIndex(0);
            set1.setValues(list);
            binding.chart.getData().notifyDataChanged();
            binding.chart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(list, lable);
            set1.setDrawIcons(true);
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.DKGRAY);
            set1.setCircleColor(Color.DKGRAY);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(true);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            set1.setDrawCircles(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);
            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.DKGRAY);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view, dates);
            mv.setChartView(binding.chart);
            XAxis xAxis = binding.chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setLabelRotationAngle(320);
            xAxis.setLabelCount(dates.size());
            xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
            binding.chart.setMarker(mv);
            binding.chart.setData(data);
            binding.chart.invalidate();
            binding.chart.animateXY(500, 1500);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private ArrayList<Integer> getValues(ArrayList<String> dates, HashMap<String, Integer> hashMap) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (String date : dates) {
            arrayList.add(hashMap.get(date));
        }
        return arrayList;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cases:
                dates = getSortedDates(new ArrayList<>(historicalModel.getTimeline().getCases().keySet()));
                values = getValues(dates,
                        historicalModel.getTimeline().getCases());
                createChart(dates, values, "الحالات المصابة");
                return true;

            case R.id.deaths:
                dates = getSortedDates(new ArrayList<>(historicalModel.getTimeline().getDeaths().keySet()));
                values = getValues(dates,
                        historicalModel.getTimeline().getDeaths());
                createChart(dates, values, "الوفيات");
                return true;

            case R.id.recovered:
                dates = getSortedDates(new ArrayList<>(historicalModel.getTimeline().getRecovered().keySet()));
                values = getValues(dates,
                        historicalModel.getTimeline().getRecovered());
                createChart(dates, values, "حالات الشفاء");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
