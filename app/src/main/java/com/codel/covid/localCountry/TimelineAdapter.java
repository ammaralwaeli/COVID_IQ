package com.codel.covid.localCountry;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.codel.covid.R;
import com.codel.covid.databinding.ItemLocalCountryBinding;
import com.github.vipulasri.timelineview.TimelineView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {


    private HistoricalModel historicalModel;

    private ArrayList<String> sdate;
    private ArrayList<Date> dates;
    private Context context;
    public TimelineAdapter(Context context, HistoricalModel model) {
        this.historicalModel = model;
        this.context=context;

        //TreeMap<String, Integer> sorted = new TreeMap<>(historicalModel.getTimeline().getCases());
        //Set<Map.Entry<String, Integer>> mappings = sorted.entrySet();
        sdate = new ArrayList<>(historicalModel.getTimeline().getCases().keySet());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        dates=new ArrayList<>();
        String dd="";
        for (String dateString : sdate) {
            try {
                dates.add(simpleDateFormat.parse(dateString));
                dd+="\""+dateString+"\",";
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Log.d("sorted",dd);
        Collections.sort(dates);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemLocalCountryBinding binding;

        ViewHolder(@NonNull ItemLocalCountryBinding binding, int viewType) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.timeline.initLine(viewType);
        }

        void bind(Timeline s) {
            binding.setItem(s);
            binding.hasPendingBindings();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TimelineView.getTimeLineViewType(position, getItemCount());
        } else if (position == getItemCount() - 1) {
            return 2;
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemLocalCountryBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_local_country, viewGroup, false);
        return new ViewHolder(binding, i);
    }

    private String dateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("M/d/yy");
        String strDate = dateFormat.format(date);
        return strDate;
    }
    private String displayDate(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        String date = dateToString(dates.get(position));
        TimelineModel timelineModel = historicalModel.getTimeline();
        int cases = timelineModel.getCases().get(date);
        int deat = timelineModel.getDeaths().get(date);
        if(position>0) {
            date = dateToString(dates.get(position-1));
            cases-=timelineModel.getCases().get(date);
            deat -= timelineModel.getDeaths().get(date);
        }

        if(cases==deat){
            holder.binding.timeline.setMarker(ContextCompat.getDrawable(context,R.drawable.ic_dot));
            holder.binding.timeline.setLineStyle(TimelineView.LineStyle.NORMAL);
        }
        else {
            holder.binding.timeline.setMarker(ContextCompat.getDrawable(context,R.drawable.ic_dot_red));
            holder.binding.timeline.setLineStyle(TimelineView.LineStyle.DASHED);
        }

        Timeline timeline = new Timeline(displayDate(dates.get(position)), cases, deat);

        holder.bind(timeline);
    }

    @Override
    public int getItemCount() {
        return sdate.size();
    }
}