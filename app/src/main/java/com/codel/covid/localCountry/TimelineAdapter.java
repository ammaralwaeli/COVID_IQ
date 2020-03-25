package com.codel.covid.localCountry;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.codel.covid.R;
import com.codel.covid.databinding.ItemLocalCountryBinding;
import com.github.vipulasri.timelineview.TimelineView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {


    private HistoricalModel historicalModel;

    private ArrayList<String> dates;

    public TimelineAdapter(HistoricalModel model) {
        this.historicalModel = model;

        TreeMap<String, Integer> sorted = new TreeMap<>(historicalModel.getTimeline().getCases());
        //Set<Map.Entry<String, Integer>> mappings = sorted.entrySet();
        dates = new ArrayList<>(sorted.keySet());
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        String date = dates.get(position);
        TimelineModel timelineModel = historicalModel.getTimeline();
        int cases = timelineModel.getCases().get(date);
        int deat = timelineModel.getDeaths().get(date);
        int rec = timelineModel.getRecovered().get(date);

        Timeline timeline = new Timeline(dates.get(position), cases, deat, rec);

        holder.bind(timeline);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }
}