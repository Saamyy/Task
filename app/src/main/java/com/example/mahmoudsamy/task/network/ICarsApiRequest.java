package com.example.mahmoudsamy.task.network;

import com.example.mahmoudsamy.task.presenter.IPresenter;

public interface ICarsApiRequest {
    void getCarsFromNetwork();
    void attachViewModel(IPresenter viewModel);
    void deAttachViewModel();
}
