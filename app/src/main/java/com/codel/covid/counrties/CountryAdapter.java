package com.codel.covid.counrties;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codel.covid.R;
import com.codel.covid.databinding.ItemCountryBinding;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

import at.grabner.circleprogress.AnimationState;
import at.grabner.circleprogress.AnimationStateChangedListener;
import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> implements Filterable {


    public interface onClickListener{
        void onClick(String name);
    }


    onClickListener listener;

    public void setListener(onClickListener listener){
        this.listener=listener;
    }

    Context context;
    List<CountryModel> countries,filteredList;
    CountryAdapter(Context context, List<CountryModel> countryModels){
        this.context=context;
        this.countries=countryModels;
        this.filteredList=countryModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCountryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_country, parent, false);
        return new CountryAdapter.ViewHolder(binding);
    }

    private void setProgress(final CircleProgressView view,float value){
        view.setValue(0);
        view.setValueAnimated(value);
        view.setOnAnimationStateChangedListener(
                new AnimationStateChangedListener() {
                    @Override
                    public void onAnimationStateChanged(AnimationState _animationState) {
                        switch (_animationState) {
                            case IDLE:
                            case ANIMATING:
                            case START_ANIMATING_AFTER_SPINNING:
                                view.setTextMode(TextMode.PERCENT); // show percent if not spinning
                                view.setUnitVisible(true);
                                break;
                            case SPINNING:
                                view.setTextMode(TextMode.TEXT); // show text while spinning
                                view.setUnitVisible(false);
                            case END_SPINNING:
                                break;
                            case END_SPINNING_START_ANIMATING:
                                break;

                        }
                    }
                }
        );
    }

    private float getValue(float total,float sub){
        return (sub/total)*100;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final FoldingCell fc = holder.binding.foldingCell;
        Animator animator= AnimatorInflater.loadAnimator(context,R.animator.scale);
        animator.setTarget(fc);
        animator.start();
        final CountryModel item=filteredList.get(position);
        fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fc.toggle(false);
                setProgress(holder.binding.death,getValue(Float.parseFloat(item.getCases()),Float.parseFloat(item.getDeaths())));
                setProgress(holder.binding.recovered,getValue(Float.parseFloat(item.getCases()),Float.parseFloat(item.getRecovered())));
                setProgress(holder.binding.critical,getValue(Float.parseFloat(item.getCases()),Float.parseFloat(item.getCritical())));
                setProgress(holder.binding.active,getValue(Float.parseFloat(item.getCases()),Float.parseFloat(item.getActive())));
                holder.binding.timeline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(item.getCountry());
                    }
                });
            }
        });

        holder.bind(filteredList.get(position));
    }



    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if(constraint.toString().equals("")){
                    filteredList=countries;
                }else{
                    try {
                        ArrayList<CountryModel> result = new ArrayList<>();
                        for (CountryModel row : countries) {
                            if (row.getCountry().toLowerCase().contains(constraint.toString().toLowerCase())) {
                                result.add(row);
                            }
                        }
                        filteredList=result;
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCountryBinding binding;

        ViewHolder(@NonNull ItemCountryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(CountryModel s) {
            binding.setItem(s);
            binding.hasPendingBindings();
        }
    }

}
