package com.example.mahmoudsamy.task.view.adapter;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mahmoudsamy.task.R;
import com.example.mahmoudsamy.task.model.Cars;
import com.example.mahmoudsamy.task.view.listeners.OnLoadMoreListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CarsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final String imageWidth = "300";
    private final String imageHight = "300";
    private ArrayList<Cars> carsList;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isMoreLoading = true;

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.pBar);
        }
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {
        private CardView carCard;
        private ImageView carImage;
        private TextView carsNameYear;
        private TextView carPrice;
        private TextView carCurrency;
        private TextView timeLeftTittle;
        private TextView lotNumberValue;
        private TextView bidsTittle;
        private TextView bidsValue;
        private TextView timeLeftTittleTittle;
        private TextView timeLeftValue;

        public CarViewHolder(View view) {
            super(view);
            carCard = view.findViewById(R.id.car_card);
            carImage = view.findViewById(R.id.car_image);
            carsNameYear = view.findViewById(R.id.cars_name_year);
            carPrice = view.findViewById(R.id.car_price);
            carCurrency = view.findViewById(R.id.car_currency);
            timeLeftTittle = view.findViewById(R.id.time_left_tittle);
            lotNumberValue = view.findViewById(R.id.lot_number_value);
            bidsTittle = view.findViewById(R.id.bids_tittle);
            bidsValue = view.findViewById(R.id.bids_value);
            timeLeftTittleTittle = view.findViewById(R.id.time_left_tittle_tittle);
            timeLeftValue = view.findViewById(R.id.time_left_value);

        }
    }


    public CarsListAdapter(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        carsList = new ArrayList<>();
    }



    @Override
    public int getItemViewType(int position) {
        return carsList.get(position) != null ? VIEW_ITEM : VIEW_PROG;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new CarViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cars_list_item, parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cars_list_progess, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CarViewHolder) {
            Cars singleCar = (Cars) carsList.get(position);
            setCarData(singleCar, holder);
        }
    }

    private void setCarData(Cars singleCar, RecyclerView.ViewHolder holder) {
        CarViewHolder carViewHolder = (CarViewHolder) holder;
        Picasso.get().load(handleImageUrl(singleCar.getImage())).into(carViewHolder.carImage);
        carViewHolder.carsNameYear.setText(singleCar.getMakeEn() + " " + singleCar.getModelEn() + " " + singleCar.getYear());
        carViewHolder.carPrice.setText(singleCar.getAuctionInfo().getCurrentPrice());
        carViewHolder.carCurrency.setText(singleCar.getAuctionInfo().getCurrencyEn());
        carViewHolder.lotNumberValue.setText(singleCar.getAuctionInfo().getLot());
        carViewHolder.bidsValue.setText(singleCar.getAuctionInfo().getBids());
        String timeLeft = formatData(getDaysBetweenTwoDates(new Date(singleCar.getAuctionInfo().getEndDate()),
                Calendar.getInstance().getTime()));
        if (timeLeft.startsWith("00")) {
            carViewHolder.timeLeftValue.setTextColor(Color.RED);
        }
        carViewHolder.timeLeftValue.setText(timeLeft);
    }

    private String handleImageUrl(String url) {
        String finalUrl = url.replace("[w]", imageWidth).replace("[h]", imageHight);;
        return finalUrl;
    }

    private String formatData(long millis) {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return hms;
    }

    private long getDaysBetweenTwoDates(Date startDate, Date endDate) {

        return TimeUnit.MILLISECONDS.toDays(endDate.getTime() - startDate.getTime());

    }


    public void showLoading() {
        if (isMoreLoading && carsList != null && onLoadMoreListener != null) {
            isMoreLoading = false;
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    carsList.add(null);
                    notifyItemInserted(carsList.size() - 1);
                    onLoadMoreListener.onLoadMore();
                }
            });
        }
    }

    public void setMore(boolean isMore) {
        this.isMoreLoading = isMore;
    }

    public void dismissLoading() {
        if (carsList != null && carsList.size() > 0) {
            carsList.remove(carsList.size() - 1);
            notifyItemRemoved(carsList.size());
        }
    }

    public void addAll(List<Cars> lst) {
        carsList.clear();
        carsList.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<Cars> lst) {
        int sizeInit = carsList.size();
        carsList.addAll(lst);
        notifyItemRangeChanged(sizeInit, carsList.size());
    }

    public void clearAll() {
        carsList.clear();
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }
}
