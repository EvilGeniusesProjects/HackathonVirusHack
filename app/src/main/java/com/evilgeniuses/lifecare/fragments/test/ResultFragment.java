package com.evilgeniuses.lifecare.fragments.test;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.interfaces.SwitchFragmentInterface;


public class ResultFragment extends Fragment implements View.OnClickListener {

    private SwitchFragmentInterface switchFragmentInterface;

    private TextView mTextViewPercent;
    private ImageView mImageViewShare;
    private ImageView mImageViewRefresh;
    private ImageView mImageViewEstimate;

    private int mTotalPoints;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_result, container, false);

        mTextViewPercent = rootView.findViewById(R.id.textViewPercent);
        mImageViewShare = rootView.findViewById(R.id.imageViewShare);
        mImageViewRefresh = rootView.findViewById(R.id.imageViewRefresh);
        mImageViewEstimate = rootView.findViewById(R.id.imageViewEstimate);

        mImageViewShare.setOnClickListener(this);
        mImageViewRefresh.setOnClickListener(this);
        mImageViewEstimate.setOnClickListener(this);

        SharedPreferences sRightAnswers = getActivity().getSharedPreferences("percent", Context.MODE_PRIVATE);
        mTotalPoints = sRightAnswers.getInt("percent", Context.MODE_PRIVATE);

        mTextViewPercent.setText(mTotalPoints + "%");
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageViewShare:
                shareResult();
                break;
            case R.id.imageViewRefresh:
                switchFragmentInterface.setFragment(StartFragment.newInstance());
                break;
            case R.id.imageViewEstimate:
                estimateTheApp();
                break;
        }
    }

    private void shareResult(){
        String textToShare = "https://play.google.com/store/apps/details?id=com.evilgeniuses.vulgaritytest";
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plan");
        String textToSend = "Мой результат в приложении \"Тест на пошлость\" равен " + Math.round(mTotalPoints) + "%. Скачать приложение можно тут: " + textToShare;
        intent.putExtra(Intent.EXTRA_TEXT, textToSend);
        try {
            startActivity(Intent.createChooser(intent, "Поделится"));
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "Неизвестная ошибка", Toast.LENGTH_SHORT).show();
        }
    }


    public void estimateTheApp() {
        Uri uri = Uri.parse("market://details?id=com.evilgeniuses.vulgaritytest");
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "Приложение не найдено", Toast.LENGTH_LONG).show();
        }
        startActivity(myAppLinkToMarket);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SwitchFragmentInterface) {
            switchFragmentInterface = (SwitchFragmentInterface) context;
        }
    }

    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
        return fragment;
    }
}
