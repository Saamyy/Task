package com.example.mahmoudsamy.task.view.adapter;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import java.util.List;
import java.util.Locale;

import cn.iwgang.countdownview.CountdownView;

public class CarsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final String imageWidth = "300";
    private final String imageHight = "300";
    private ArrayList<Cars> carsList;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isMoreLoading = true;
    private boolean isLeftToRight;
    private CountDownTimer timeLefCountDownTimer;


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
        private CountdownView timeLeftValue;
        private CountdownView timeLeftValueRed;


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
            timeLeftValueRed = view.findViewById(R.id.time_left_value_red);


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

    private void setCarData(final Cars singleCar, final RecyclerView.ViewHolder holder) {

        final CarViewHolder carViewHolder = (CarViewHolder) holder;
        Picasso.get().load(handleImageUrl(singleCar.getImage())).into(carViewHolder.carImage);
        isLeftToRight = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_LTR;
        if (isLeftToRight) {
            handleLeftToRightViews(singleCar, carViewHolder);

        } else {
            handleRightToLeftViews(singleCar, carViewHolder);

        }
        carViewHolder.carPrice.setText(singleCar.getAuctionInfo().getCurrentPrice());
        carViewHolder.lotNumberValue.setText(singleCar.getAuctionInfo().getLot());
        carViewHolder.bidsValue.setText(singleCar.getAuctionInfo().getBids());
        carViewHolder.timeLeftValue.start(singleCar.getAuctionInfo().getEndDate().intValue()*1000);// Millisecond
        carViewHolder.timeLeftValue.setOnCountdownIntervalListener(singleCar.getAuctionInfo().getEndDate().intValue() * 1000, new CountdownView.OnCountdownIntervalListener() {
            @Override
            public void onInterval(CountdownView cv, long remainTime) {
                int minutes = (int) ((remainTime / (1000 * 60)) % 60);
                int hours = (int) ((remainTime / (1000 * 60 * 60)) % 24);

                if ((hours == 0) && (minutes < 5)) {
                    carViewHolder.timeLeftValueRed.start(singleCar.getAuctionInfo().getEndDate().intValue()*1000);
                    carViewHolder.timeLeftValue.setVisibility(View.GONE);
                    carViewHolder.timeLeftValueRed.setVisibility(View.VISIBLE);

                }else{
                    carViewHolder.timeLeftValue.setVisibility(View.VISIBLE);
                    carViewHolder.timeLeftValueRed.setVisibility(View.GONE);
                }
            }
        });
    }



    private void handleLeftToRightViews(Cars singleCar, CarViewHolder carViewHolder) {
        carViewHolder.carsNameYear.setText(singleCar.getMakeEn() + " " + singleCar.getModelEn() + " " + singleCar.getYear());
        carViewHolder.carCurrency.setText(singleCar.getAuctionInfo().getCurrencyEn());
    }

    private void handleRightToLeftViews(Cars singleCar, CarViewHolder carViewHolder) {
        carViewHolder.carsNameYear.setText(singleCar.getMakeAr() + " " + singleCar.getModelAr() + " " + singleCar.getYear());
        carViewHolder.carCurrency.setText(singleCar.getAuctionInfo().getCurrencyAr());
    }



    private String handleImageUrl(String url) {
        String finalUrl = url.replace("[w]", imageWidth).replace("[h]", imageHight);
        ;
        return finalUrl;
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
    }

    public void addItemMore(List<Cars> lst) {
        int sizeInit = carsList.size();

        carsList.addAll(lst);
        notifyItemRangeChanged(sizeInit, carsList.size()-1);
    }


    @Override
    public int getItemCount() {
        return carsList.size();
    }
}
