package com.example.expensestracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.content.ContentValues.TAG;


public class ExpensesTrackerFragment extends Fragment {

    private static final String ARG_EXPENSESTRACKER_ID = "expensestracker_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private FirebaseFirestore mFirestore;

    private ExpensesTracker mExpensesTracker;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mSaveButton;
    private CheckBox mPaidCheckBox;


    public static ExpensesTrackerFragment newInstance(UUID expensestrackerId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXPENSESTRACKER_ID, expensestrackerId);

        ExpensesTrackerFragment fragment = new ExpensesTrackerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID expensestrackerId = (UUID) getArguments().getSerializable(ARG_EXPENSESTRACKER_ID);
        mExpensesTracker = ExpensesTrackerLab.get(getActivity()).getExpensesTracker(expensestrackerId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_expensestracker, container, false);


        mTitleField = (EditText)v.findViewById(R.id.expensestracker_title);
        mTitleField.setText(mExpensesTracker.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                //This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mExpensesTracker.setTitle(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button)v.findViewById(R.id.expensestracker_date);
        updateDate();
        mDateButton.setText(mExpensesTracker.getDate().toString());
        mDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mExpensesTracker.getDate());
                dialog.setTargetFragment(ExpensesTrackerFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mSaveButton = (Button)v.findViewById(R.id.Savebutton);

        mSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                // Access a Cloud Firestore instance from your Activity
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                String expenses = mTitleField.getText().toString();
                Boolean paid = mPaidCheckBox.isChecked();
                String dateOfExpense = mDateButton.getText().toString();

                // Create a new user with a first and last name
                Map<String, Object> expense = new HashMap<>();
                expense.put("Expenses", expenses);
                expense.put("Paid", paid);
                expense.put("Date", dateOfExpense);

                // Add a new document with a generated ID
                db.collection("expenses")
                        .add(expense)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getActivity() , "Save successfully!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity() , "Fail to Save: " + e, Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        mPaidCheckBox = (CheckBox)v.findViewById(R.id.expensestracker_solved);
        mPaidCheckBox.setChecked(mExpensesTracker.isPaid());
        mPaidCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){

                mExpensesTracker.setPaid(isChecked);


            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_DATE){
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mExpensesTracker.setDate(date);
            updateDate();
            mDateButton.setText(mExpensesTracker.getDate().toString());
        }
    }

    private void updateDate(){
        mDateButton.setText(mExpensesTracker.getDate().toString());
    }


}
