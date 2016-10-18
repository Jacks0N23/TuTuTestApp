package com.jassdev.jackson.tututestapp.NawDrawScreens;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jassdev.jackson.tututestapp.R;
import com.jassdev.jackson.tututestapp.TutuJsonApi;
import com.jassdev.jackson.tututestapp.Utils.Models.CitiesFromTo;
import com.jassdev.jackson.tututestapp.Utils.Models.Stations;
import com.jassdev.jackson.tututestapp.Utils.Models.TutuModel;
import com.jassdev.jackson.tututestapp.Utils.SearchableSpinnerAdapter;
import com.jassdev.jackson.tututestapp.Utils.Utils;
import com.jassdev.jackson.tututestapp.databinding.FragmentScheduleBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Locale;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jackson on 18/10/2016.
 */

public class ScheduleFragment extends Fragment {

    private static final String TAG = "ScheduleFragment";
    private final String DOWNLOADED_JSON = "DOWNLOADED_JSON";
    private final String url = "https://raw.githubusercontent.com/tutu-ru/hire_android_test/master/allStations.json";

    private TutuJsonApi mApi;
    private FragmentScheduleBinding mBinding;
    private DatePickerDialog.OnDateSetListener datePicker;
    private SearchableSpinnerAdapter stationAdapterFrom;
    private SearchableSpinnerAdapter stationAdapterTo;
    public static TutuModel model;
    private int i = -1;
    private Calendar calendarStart;
    private ProgressDialog mLoadingProgressDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mApi = Utils.createRxService(TutuJsonApi.class, false);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule, container, false);

        if (savedInstanceState == null && (getArguments() == null || getArguments().isEmpty()))
            getStationFromTo();
        else if (getArguments() != null && getArguments().getParcelable(DOWNLOADED_JSON) != null)
        {
            model = getArguments().getParcelable(DOWNLOADED_JSON);
            //Если данные криво сохранились или не успели сохраниться, а мы перешли в другой фрагмент
            try {
                fillAdapters(model);
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                getStationFromTo();
            }
        }
        else if (savedInstanceState != null){

            model = savedInstanceState.getParcelable(DOWNLOADED_JSON);

            //Если данные криво сохранились или не успели сохраниться, а мы перешли в другой фрагмент
            try {
                fillAdapters(savedInstanceState.getParcelable(DOWNLOADED_JSON));
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                getStationFromTo();
            }
        }

        setupDateDialog();

        return mBinding.getRoot();
    }

    void setupDateDialog() {
        calendarStart = Calendar.getInstance();

        mBinding.date.setOnClickListener(v -> {
            DatePickerDialog datePickerDialogF = new DatePickerDialog(getActivity(), datePicker, calendarStart.get(Calendar.YEAR),
                    calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH));
            datePickerDialogF.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialogF.setCanceledOnTouchOutside(true);
            datePickerDialogF.show();
        });

        datePicker = (view, year, monthOfYear, dayOfMonth) -> {
            calendarStart.set(Calendar.YEAR, year);
            calendarStart.set(Calendar.MONTH, monthOfYear);
            calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateText();
            fillInfoTV();
        };
    }

    private void updateDateText(){
        String format = "dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        mBinding.date.setText(simpleDateFormat.format(calendarStart.getTime()));
    }

    private void getStationFromTo() {
        mLoadingProgressDialog = new ProgressDialog(getActivity());
        mLoadingProgressDialog.setProgressStyle(android.R.attr.progressBarStyleHorizontal);
        mLoadingProgressDialog.setMessage("Загрузка...");
        mLoadingProgressDialog.show();

        mApi.getTutuJson(url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<TutuModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e);
                    }

                    @Override
                    public void onNext(Response<TutuModel> response) {
                        if (response.isSuccessful()) {
                            model = response.body();
                            fillAdapters(model);
                            mLoadingProgressDialog.dismiss();
                        }
                    }
                });
    }

    private void fillAdapters(TutuModel model) {
        ArrayList<String> countries = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();
        LinkedHashMap<String, ArrayList<String>> stationFromTo = new LinkedHashMap<>();

        for (CitiesFromTo country : model.getCitiesFrom())
            countries.add(country.getCountryTitle());

        for (CitiesFromTo city : model.getCitiesFrom())
            cities.add(city.getCityTitle());

        for (CitiesFromTo cityId : model.getCitiesFrom()) {
            i++;
            stationFromTo.put(cityId.getCityTitle(), new ArrayList<String>() {{
                for (Stations stations : model.getCitiesFrom().get(i).getStations())
                    add(stations.getStationTitle());
            }});
        }

        Log.d(TAG, "onNext: cities " + cities.get(0));
        Log.d(TAG, "onNext: stationFromTo " + stationFromTo.get(cities.get(0)));
        Log.d(TAG, "onNext: after size " + stationFromTo.size());

        stationAdapterFrom = new SearchableSpinnerAdapter(getActivity(), countries, cities, stationFromTo);
        mBinding.stationFrom.setAdapter(getActivity(), stationAdapterFrom, v -> {
            //менять текст внизу
            fillInfoTV();
        });

        countries.clear();
        cities.clear();
        stationFromTo.clear();

        i = -1;

        for (CitiesFromTo country : model.getCitiesTo())
            countries.add(country.getCountryTitle());

        for (CitiesFromTo city : model.getCitiesTo())
            cities.add(city.getCityTitle());

        for (CitiesFromTo cityId : model.getCitiesTo()) {
            i++;
            stationFromTo.put(cityId.getCityTitle(), new ArrayList<String>() {{
                for (Stations stations : model.getCitiesTo().get(i).getStations())
                    add(stations.getStationTitle());
            }});
        }

        stationAdapterTo = new SearchableSpinnerAdapter(getActivity(), countries, cities, stationFromTo);
        mBinding.stationTo.setAdapter(getActivity(), stationAdapterTo, v -> {
            //менять текст внизу
            fillInfoTV();
        });
    }

    void fillInfoTV() {
        if (!mBinding.stationFrom.getText().toString().isEmpty() && mBinding.stationTo.getText().toString().isEmpty()
                && mBinding.date.getText().toString().isEmpty())
            mBinding.infoText.setText(getString(R.string.info_tv_from, mBinding.stationFrom.getText().toString()));

        else if (!mBinding.stationTo.getText().toString().isEmpty() && mBinding.stationFrom.getText().toString().isEmpty()
                && mBinding.date.getText().toString().isEmpty())
            mBinding.infoText.setText(getString(R.string.info_tv_to, mBinding.stationTo.getText().toString()));

        else if (!mBinding.date.getText().toString().isEmpty() &&
                mBinding.stationFrom.getText().toString().isEmpty() && mBinding.stationTo.getText().toString().isEmpty() )
            mBinding.infoText.setText(getString(R.string.info_tv_date, mBinding.date.getText().toString()));

        else if (!mBinding.stationFrom.getText().toString().isEmpty() && !mBinding.stationTo.getText().toString().isEmpty()
                && mBinding.date.getText().toString().isEmpty())
            mBinding.infoText.setText(getString(R.string.info_tv_full, mBinding.stationFrom.getText().toString(),
                    mBinding.stationTo.getText().toString(), ""));

        else if (!mBinding.stationFrom.getText().toString().isEmpty() && !mBinding.date.getText().toString().isEmpty()
                && mBinding.stationTo.getText().toString().isEmpty())
            mBinding.infoText.setText(getString(R.string.info_tv_from_date, mBinding.stationFrom.getText().toString(),
                    mBinding.date.getText().toString()));

        else if (!mBinding.stationTo.getText().toString().isEmpty() && !mBinding.date.getText().toString().isEmpty()
                && mBinding.stationFrom.getText().toString().isEmpty() )
            mBinding.infoText.setText(getString(R.string.info_tv_to_date, mBinding.stationTo.getText().toString(),
                    mBinding.date.getText().toString()));

        else if (!mBinding.stationTo.getText().toString().isEmpty() && !mBinding.date.getText().toString().isEmpty()
                && !mBinding.stationFrom.getText().toString().isEmpty() )
            mBinding.infoText.setText(getString(R.string.info_tv_full, mBinding.stationFrom.getText().toString(),
                mBinding.stationTo.getText().toString(), mBinding.date.getText().toString()));

        else
            mBinding.infoText.setText(""); //можно было бы и видимость менять, но тут это не решает(без разницы)
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(DOWNLOADED_JSON, model);
    }
}
