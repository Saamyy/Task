package com.example.mahmoudsamy.task.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarsResponse {

    private String sortOption;

    private List<Cars> Cars;

    private String count;

    private String alertAr;

    private String endDate;

    private String alertEn;

    private String sortDirection;

    private String Ticks;

    private String RefreshInterval;
    @SerializedName("Error")
    private ServerError Error;

    public String getSortOption() {
        return sortOption;
    }

    public void setSortOption(String sortOption) {
        this.sortOption = sortOption;
    }

    public List<Cars> getCars() {
        return Cars;
    }

    public void setCars(List<Cars> Cars) {
        this.Cars = Cars;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAlertAr() {
        return alertAr;
    }

    public void setAlertAr(String alertAr) {
        this.alertAr = alertAr;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAlertEn() {
        return alertEn;
    }

    public void setAlertEn(String alertEn) {
        this.alertEn = alertEn;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getTicks() {
        return Ticks;
    }

    public void setTicks(String Ticks) {
        this.Ticks = Ticks;
    }

    public String getRefreshInterval() {
        return RefreshInterval;
    }

    public void setRefreshInterval(String RefreshInterval) {
        this.RefreshInterval = RefreshInterval;
    }

    public ServerError getError() {
        return Error;
    }

    public void setError(ServerError Error) {
        this.Error = Error;
    }

    @Override
    public String toString() {
        return "ClassPojo [sortOption = " + sortOption + ", Cars = " + Cars + ", count = " + count + ", alertAr = " + alertAr + ", endDate = " + endDate + ", alertEn = " + alertEn + ", sortDirection = " + sortDirection + ", Ticks = " + Ticks + ", RefreshInterval = " + RefreshInterval + ", Error = " + Error + "]";
    }
}
