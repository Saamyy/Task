package com.example.mahmoudsamy.task.model;

public class AuctionInfo {
    private String iVinNumber;

    private String endDateAr;

    private String isModified;

    private String endDateEn;

    private String minIncrement;

    private String bids;

    private Long endDate;

    private String currencyEn;

    private String currentPrice;

    private String itemid;

    private String VATPercent;

    private String priority;

    private String iCarId;

    private String currencyAr;

    private String lot;

    public String getIVinNumber() {
        return iVinNumber;
    }

    public void setIVinNumber(String iVinNumber) {
        this.iVinNumber = iVinNumber;
    }

    public String getEndDateAr() {
        return endDateAr;
    }

    public void setEndDateAr(String endDateAr) {
        this.endDateAr = endDateAr;
    }

    public String getIsModified() {
        return isModified;
    }

    public void setIsModified(String isModified) {
        this.isModified = isModified;
    }

    public String getEndDateEn() {
        return endDateEn;
    }

    public void setEndDateEn(String endDateEn) {
        this.endDateEn = endDateEn;
    }

    public String getMinIncrement() {
        return minIncrement;
    }

    public void setMinIncrement(String minIncrement) {
        this.minIncrement = minIncrement;
    }

    public String getBids() {
        return bids;
    }

    public void setBids(String bids) {
        this.bids = bids;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getCurrencyEn() {
        return currencyEn;
    }

    public void setCurrencyEn(String currencyEn) {
        this.currencyEn = currencyEn;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getVATPercent() {
        return VATPercent;
    }

    public void setVATPercent(String VATPercent) {
        this.VATPercent = VATPercent;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getICarId() {
        return iCarId;
    }

    public void setICarId(String iCarId) {
        this.iCarId = iCarId;
    }

    public String getCurrencyAr() {
        return currencyAr;
    }

    public void setCurrencyAr(String currencyAr) {
        this.currencyAr = currencyAr;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    @Override
    public String toString() {
        return "ClassPojo [iVinNumber = " + iVinNumber + ", endDateAr = " + endDateAr + ", isModified = " + isModified + ", endDateEn = " + endDateEn + ", minIncrement = " + minIncrement + ", bids = " + bids + ", endDate = " + endDate + ", currencyEn = " + currencyEn + ", currentPrice = " + currentPrice + ", itemid = " + itemid + ", VATPercent = " + VATPercent + ", priority = " + priority + ", iCarId = " + iCarId + ", currencyAr = " + currencyAr + ", lot = " + lot + "]";
    }

}
