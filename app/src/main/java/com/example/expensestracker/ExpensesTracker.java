package com.example.expensestracker;

import java.util.Date;
import java.util.UUID;

public class ExpensesTracker {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mPaid;


    public ExpensesTracker(){
        //Generate unique identifier
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isPaid() {
        return mPaid;
    }

    public void setPaid(boolean paid) {
        mPaid = paid;
    }
}

