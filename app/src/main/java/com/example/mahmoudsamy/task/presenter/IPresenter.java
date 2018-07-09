package com.example.mahmoudsamy.task.presenter;

import android.support.v4.app.Fragment;

import com.example.mahmoudsamy.task.model.Cars;
import com.example.mahmoudsamy.task.model.CarsResponse;

import java.util.List;

public interface IPresenter {
    void getCarsList(Boolean showLoading);

    void attachView(Fragment fragment);

    void deAttachView();

    void handleSuccessResponse(CarsResponse body);

    void handelFailureResponce();

     List<Cars> getPresenterCarsList();


}
