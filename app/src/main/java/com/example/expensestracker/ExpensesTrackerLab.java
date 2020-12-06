package com.example.expensestracker;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExpensesTrackerLab {
    private static ExpensesTrackerLab ExpensesTrackerLab;

    private List<ExpensesTracker> mExpensesTrackers;

    public static ExpensesTrackerLab get(Context context){
        if (ExpensesTrackerLab == null){
            ExpensesTrackerLab = new ExpensesTrackerLab(context);
        }
        return ExpensesTrackerLab;
    }

    private ExpensesTrackerLab(Context context){

        mExpensesTrackers = new ArrayList<>();
        for (int i=0; i<100; i++){
            ExpensesTracker expensesTracker = new ExpensesTracker();
            expensesTracker.setTitle("Expense #" + i);
            expensesTracker.setPaid(i % 2 == 0); // Every other one
            mExpensesTrackers.add(expensesTracker);

        }

    }

    public List<ExpensesTracker> getExpensesTrackers(){

        return mExpensesTrackers;
    }

    public ExpensesTracker getExpensesTracker(UUID id) {
        for (ExpensesTracker expensesTracker : mExpensesTrackers) {
            if (expensesTracker.getId().equals(id)) {
                return expensesTracker;
            }
        }
        return null;

    }

}
