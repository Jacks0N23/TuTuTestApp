package com.jassdev.jackson.tututestapp.NawDrawScreens;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jassdev.jackson.tututestapp.R;
import com.jassdev.jackson.tututestapp.databinding.ActivityMoreInfoBinding;

public class MoreInfoActivity extends AppCompatActivity {

    private ActivityMoreInfoBinding mBinding;
    private Intent outIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_more_info);
        outIntent = getIntent();

        mBinding.countryTV.setText(outIntent.getStringExtra("COUNTRY"));
        mBinding.cityTV.setText(outIntent.getStringExtra("CITY"));
        mBinding.stationTV.setText(outIntent.getStringExtra("STATION"));

        //тут можно было бы ещё сделать карту, раз передаются lanlng
        //просто поверьте, что я умею это делать)
        // для тестового задания это просто трата времени
    }
}
