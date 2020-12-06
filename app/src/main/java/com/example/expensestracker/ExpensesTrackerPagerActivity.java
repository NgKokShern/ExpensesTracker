package com.example.expensestracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.List;
import java.util.UUID;

public class ExpensesTrackerPagerActivity extends FragmentActivity {

    private static final String EXTRA_EXPENSESTRACKER_ID = ".expensestracker_id";

    private ViewPager mViewPager;
    private List<ExpensesTracker> mExpensesTrackers;

    public static Intent newIntent(Context packageContext, UUID expensestrackerId){
        Intent intent = new Intent(packageContext, ExpensesTrackerPagerActivity.class);
        intent.putExtra(EXTRA_EXPENSESTRACKER_ID, expensestrackerId);
        return intent;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expensestracker_pager);

        UUID expensestrackerId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_EXPENSESTRACKER_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_expensestracker_pager_view_pager);

        mExpensesTrackers = ExpensesTrackerLab.get(this).getExpensesTrackers();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager){

            @Override
            public Fragment getItem(int position){
                ExpensesTracker expensesTracker = mExpensesTrackers.get(position);
                return ExpensesTrackerFragment.newInstance(expensesTracker.getId());
            }

            @Override
            public int getCount(){
                return mExpensesTrackers.size();
            }

        });

        for(int i = 0; i< mExpensesTrackers.size(); i++){
            if(mExpensesTrackers.get(i).getId().equals(expensestrackerId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
