package com.example.expensestracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

import java.util.List;

public class ExpensesTrackerListFragment extends Fragment {

    private RecyclerView mExpensesTrackerRecyclerView;
    private ExpensesTrackerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expensestracker_list, container, false);

        mExpensesTrackerRecyclerView = (RecyclerView) view
                .findViewById(R.id.expensestracker_recycler_view);
        mExpensesTrackerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        ExpensesTrackerLab expensesTrackerLab = ExpensesTrackerLab.get(getActivity());
        List<ExpensesTracker> expensesTrackers = expensesTrackerLab.getExpensesTrackers();

        if (mAdapter == null) {
            mAdapter = new ExpensesTrackerAdapter(expensesTrackers);
            mExpensesTrackerRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.notifyDataSetChanged();
        }

    }

    private class ExpensesTrackerHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

        private ExpensesTracker mExpensesTracker;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mPaidCheckBox;

        public ExpensesTrackerHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_expensestracker_title_text_view);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_expensestracker_date_text_view);
            mPaidCheckBox = (CheckBox)
                    itemView.findViewById(R.id.list_item_expensestracker_paid_check_box);
        }

        public void bindExpensesTracker(ExpensesTracker expensesTracker) {
            mExpensesTracker = expensesTracker;
            mTitleTextView.setText(mExpensesTracker.getTitle());
            mDateTextView.setText(mExpensesTracker.getDate().toString());
            mPaidCheckBox.setChecked(mExpensesTracker.isPaid());
        }

        @Override
        public void onClick(View v){
            Intent intent = ExpensesTrackerPagerActivity.newIntent(getActivity(), mExpensesTracker.getId());
            startActivity(intent);
        }
    }

    private class ExpensesTrackerAdapter extends RecyclerView.Adapter<ExpensesTrackerHolder> {
        private List<ExpensesTracker> mExpensesTrackers;

        public ExpensesTrackerAdapter(List<ExpensesTracker> expensesTrackers) {
            mExpensesTrackers = expensesTrackers;
        }

        @Override
        public ExpensesTrackerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_expensestracker, parent, false);
            return new ExpensesTrackerHolder(view);
        }

        @Override
        public void onBindViewHolder(ExpensesTrackerHolder holder, int position) {
            ExpensesTracker expensesTracker = mExpensesTrackers.get(position);
            holder.bindExpensesTracker(expensesTracker);
        }

        @Override
        public int getItemCount() {
            return mExpensesTrackers.size();
        }
    }

}
