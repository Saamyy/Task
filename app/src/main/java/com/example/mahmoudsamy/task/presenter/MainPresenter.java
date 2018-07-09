package com.example.mahmoudsamy.task.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v4.app.Fragment;

import com.example.mahmoudsamy.task.R;
import com.example.mahmoudsamy.task.model.Cars;
import com.example.mahmoudsamy.task.model.CarsResponse;
import com.example.mahmoudsamy.task.network.CarsApiRequest;
import com.example.mahmoudsamy.task.network.ICarsApiRequest;
import com.example.mahmoudsamy.task.view.applecation.ApplicationClass;
import com.example.mahmoudsamy.task.view.fragment.IMainFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainPresenter implements IPresenter {

    ICarsApiRequest carsApiRequest;
    IMainFragment iMainFragment;
    private Timer timer = new Timer();
    Handler handler;
    private List<Cars> presenterCarsList;


    public MainPresenter() {
        carsApiRequest = new CarsApiRequest();
        carsApiRequest.attachViewModel(this);
        handler = new Handler();
        presenterCarsList = new ArrayList<>();
    }

    @Override
    public void getCarsList(Boolean showLoading) {
        if (showLoading) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    iMainFragment.showLoading();
                }
            });
        }
        if (isNetworkAvailable(ApplicationClass.getContext())) {
            carsApiRequest.getCarsFromNetwork();

        } else {
            handleOfLine();
        }
    }

    private void handleOfLine() {
        iMainFragment.showToast(ApplicationClass.getContext().getResources().getString(R.string.noNetwrok));
    }

    @Override
    public void attachView(Fragment fragment) {
        this.iMainFragment = (IMainFragment) fragment;
    }


    @Override
    public void deAttachView() {
        this.iMainFragment = null;
        carsApiRequest.deAttachViewModel();

    }

    @Override
    public void handleSuccessResponse(CarsResponse body) {
        iMainFragment.handleCarsList(body.getCars());
        presenterCarsList = body.getCars();
        getFreshData();
    }

    private void getFreshData() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getCarsList(false);
            }
        }, 300000, (300000));
    }


    @Override
    public void handelFailureResponce() {
        iMainFragment.showToast(ApplicationClass.getContext().getResources().getString(R.string.netwrokError));
    }

    @Override
    public List<Cars> getPresenterCarsList() {
        return presenterCarsList;
    }

    private boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
