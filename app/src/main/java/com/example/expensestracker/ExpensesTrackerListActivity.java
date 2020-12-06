package com.example.expensestracker;
import androidx.fragment.app.Fragment;


public class ExpensesTrackerListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new ExpensesTrackerListFragment();
    }
}
