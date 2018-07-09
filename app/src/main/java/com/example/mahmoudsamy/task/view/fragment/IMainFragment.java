package com.example.mahmoudsamy.task.view.fragment;

import com.example.mahmoudsamy.task.model.Cars;

import java.util.Calendar;
import java.util.List;

public interface IMainFragment {
    void showLoading();
    void hideLoading();
    void handleCarsList(List<Cars>cars);
    void showToast(String message);
}
