package com.example.mahmoudsamy.task.view.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mahmoudsamy.task.R;
import com.example.mahmoudsamy.task.model.Cars;
import com.example.mahmoudsamy.task.presenter.IPresenter;
import com.example.mahmoudsamy.task.presenter.MainPresenter;
import com.example.mahmoudsamy.task.view.adapter.CarsListAdapter;
import com.example.mahmoudsamy.task.view.listeners.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements IMainFragment, OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private ProgressBar mainLoading;
    private RecyclerView mRecyclerView;
    private CarsListAdapter mAdapter;
    private SwipeRefreshLayout swipeRefresh;

    private List<Cars> carsList;

    private int lastRenderedItemIndex;
    private Handler delayLoadMore;
    private IPresenter mainPresenter;


    public MainFragment() {
        // Required empty public constructor
        delayLoadMore = new Handler(Looper.getMainLooper());
        carsList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        configureView(view);
        configurePresenter();
        configureRecycleView();
        swipeRefresh.setOnRefreshListener(this);
        addRecycleViewScrollingListener();
        return view;
    }

    private void configureView(View view) {
        mainLoading = view.findViewById(R.id.mainLoading);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        mRecyclerView = view.findViewById(R.id.cars_list);
    }

    private void configurePresenter() {
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        mainPresenter.getCarsList(true);
    }

    private void configureRecycleView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CarsListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void addRecycleViewScrollingListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (dy > 0 && llManager.findLastCompletelyVisibleItemPosition() == (mAdapter.getItemCount() - 2)) {
                    mAdapter.showLoading();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainPresenter.deAttachView();
    }

    @Override
    public void showLoading() {
        mainLoading.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        mainLoading.setVisibility(View.GONE);
    }

    @Override
    public void handleCarsList(List<Cars> cars) {
        hideLoading();
        swipeRefresh.setRefreshing(false);
        carsList.clear();
        lastRenderedItemIndex = 10;
        for (int i = 0; i < lastRenderedItemIndex; i++) {
            carsList.add(cars.get(i));
        }
        mAdapter.dismissLoading();
        mAdapter.clearAll();
        mAdapter.addAll(carsList);

    }


    @Override
    public void showToast(String message) {
        hideLoading();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore() {
        //this was made to moke the lazy loading in the Ui

        delayLoadMore.postDelayed(new Runnable() {
            public void run() {
                loadMoreLogic();
            }
        }, 3000L);
    }

    private void loadMoreLogic() {
        if (mAdapter.getItemCount() <= mainPresenter.getPresenterCarsList().size()) {
            for (int i = lastRenderedItemIndex; i < lastRenderedItemIndex + 10; i++) {
                carsList.add(mainPresenter.getPresenterCarsList().get(i));
            }
            lastRenderedItemIndex += 10;
            mAdapter.dismissLoading();

            mAdapter.setMore(true);
            mAdapter.addItemMore(carsList);

        } else {
            mAdapter.dismissLoading();

            mAdapter.setMore(false);
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.noItemsLeft), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onRefresh() {

        mainPresenter.getCarsList(false);
    }
}
