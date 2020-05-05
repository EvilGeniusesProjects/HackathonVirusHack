package com.evilgeniuses.lifecare.fragments.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.databases.DatabaseHelper;
import com.evilgeniuses.lifecare.interfaces.SwitchFragmentInterface;

import java.io.IOException;
import java.util.Random;

public class TestFragment extends Fragment implements View.OnClickListener {

    private SwitchFragmentInterface switchFragmentInterface;

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    private Button mButtonAnswer1;
    private Button mButtonAnswer2;
    private Button mButtonAnswer3;
    private TextView mTextQuestion;
    private TextView mTextQuestionCount;

    private int mPointQuestion = 0;
    private int mCountQuestion = 0;
    private int mPositionQuestions[];

    private int mCountAd = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_test, container, false);

        mButtonAnswer1 = rootView.findViewById(R.id.mButtonAnswer1);
        mButtonAnswer2 = rootView.findViewById(R.id.mButtonAnswer2);
        mButtonAnswer3 = rootView.findViewById(R.id.mButtonAnswer3);
        mTextQuestion = rootView.findViewById(R.id.mTextQuestion);
        mTextQuestionCount = rootView.findViewById(R.id.mTextQuestionCount);

        mButtonAnswer1.setOnClickListener(this);
        mButtonAnswer2.setOnClickListener(this);
        mButtonAnswer3.setOnClickListener(this);

        readDatabase();
        getQuestionCount();
        shuffleArray(mPositionQuestions);
        setQuestion();

        return rootView;
    }

    private void readDatabase() {
        mDBHelper = new DatabaseHelper(getActivity());
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }

    private void getQuestionCount() {
        Cursor cursor = mDb.rawQuery("SELECT * FROM QandA", null);
        mPositionQuestions = new int[cursor.getCount()];

        for (int i = 0; i < cursor.getCount(); i++) {
            mPositionQuestions[i] = i;
        }
        cursor.close();
    }

    private static void shuffleArray(int[] a) {
        int n = a.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }

    private static void swap(int[] a, int i, int change) {
        int temp = a[i];
        a[i] = a[change];
        a[change] = temp;
    }

    private void addPoints(int point) {
        mPointQuestion = mPointQuestion + point;
    }

    private void setQuestion() {
        Cursor cursor = mDb.rawQuery("SELECT * FROM QandA", null);
        if (mCountQuestion != cursor.getCount()) {
            cursor.moveToPosition(mPositionQuestions[mCountQuestion]);
            mTextQuestion.setText(cursor.getString(1));
            mButtonAnswer1.setText(cursor.getString(2));
            mButtonAnswer2.setText(cursor.getString(3));
            mButtonAnswer3.setText(cursor.getString(4));
            mTextQuestionCount.setText("Вопрос " + (mCountQuestion + 1) + " из " + cursor.getCount());
        } else {
            float percent = (mPointQuestion * 100.0f) / (cursor.getCount() * 2);
            SharedPreferences sPoints = getActivity().getSharedPreferences("percent", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sPoints.edit();
            editor.putInt("percent", Math.round(percent));
            editor.apply();
            switchFragmentInterface.setFragment(ResultFragment.newInstance());
        }
        cursor.close();
        mCountQuestion++;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mButtonAnswer1:
                addPoints(2);
                break;
            case R.id.mButtonAnswer2:
                addPoints(1);
                break;
            case R.id.mButtonAnswer3:
                addPoints(0);
                break;
        }
        setQuestion();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SwitchFragmentInterface) {
            switchFragmentInterface = (SwitchFragmentInterface) context;
        }
    }

    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
        return fragment;
    }
}
