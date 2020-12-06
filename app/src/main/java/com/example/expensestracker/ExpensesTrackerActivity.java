package com.example.expensestracker;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class ExpensesTrackerActivity extends SingleFragmentActivity {

    private static final String EXTRA_EXPENSESTRACKER_ID = ".expensestracker_id";

    public static Intent newIntent(Context packageContext, UUID expensestrackerId){
        Intent intent = new Intent(packageContext, ExpensesTrackerActivity.class);
        intent.putExtra(EXTRA_EXPENSESTRACKER_ID, expensestrackerId);
        return intent;
    }

   @Override
    protected Fragment createFragment(){
       UUID expensestrackerId = (UUID) getIntent()
               .getSerializableExtra(EXTRA_EXPENSESTRACKER_ID);
       return ExpensesTrackerFragment.newInstance(expensestrackerId);
   }

}