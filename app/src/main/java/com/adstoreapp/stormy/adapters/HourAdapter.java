package com.adstoreapp.stormy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adstoreapp.stormy.R;
import com.adstoreapp.stormy.weather.Hour;

import java.util.zip.Inflater;

/**
 * Created by sunita on 10/17/2015.
 */
public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder>
                    implements View.OnClickListener {

    private Hour[] mHours;
    private Context mContext;

    public HourAdapter (Context context, Hour[] hours) {
        mHours = hours;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hourly_list_layout, viewGroup, false);
        HourViewHolder viewHolder = new HourViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(HourViewHolder hourViewHolder, int i) {
        hourViewHolder.BindHour(mHours[i]);
    }

    @Override
    public int getItemCount() {
        return mHours.length;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(mContext, "YOLOOOO", Toast.LENGTH_LONG).show();
    }

    public class HourViewHolder extends RecyclerView.ViewHolder  {

        public TextView mTimeLabel;
        public TextView mTemperatureLabel;
        public TextView mSummaryLabel;
        public ImageView mIconImageView;

        public HourViewHolder(View itemView) {
            super(itemView);

            mTimeLabel = (TextView) itemView.findViewById(R.id.timeTextView);
            mTemperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
            mSummaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);
            mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);

                  }


        public void BindHour(Hour hour) {
            mTimeLabel.setText(hour.getHour());
            mTemperatureLabel.setText(hour.getTemperature()+"");
            mSummaryLabel.setText(hour.getSummary());
            mIconImageView.setImageResource(hour.getIconId());
        }


    }
}