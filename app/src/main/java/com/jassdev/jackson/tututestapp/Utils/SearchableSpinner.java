package com.jassdev.jackson.tututestapp.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.jassdev.jackson.tututestapp.NawDrawScreens.MoreInfoActivity;
import com.jassdev.jackson.tututestapp.R;

public class SearchableSpinner extends TextView implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private static final String TAG = "SearchableSpinner";
    private SearchableSpinnerAdapter mAdapter;
    private String item;
    private AlertDialog dialog;
    private ExpandableListView listViewItems;
    private SearchView _searchView;
    private OnSearchableSpinnerClickListener mListener;
    private Activity activity;

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            final LayoutInflater inflater = LayoutInflater.from(getContext());
            View rootView = inflater.inflate(R.layout.dialog_searchable_list, null);
            setData(rootView);

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
            alertDialog.setView(rootView);

            alertDialog.setNegativeButton(R.string.search_clean, (dialog12, which) -> {
                item = "";
                mListener.onItemsSelected(item);
                refreshSpinner(true);
                dialog12.dismiss();
            });

            alertDialog.setTitle("Выберите станцию");

            dialog = alertDialog.create();
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams
                    .SOFT_INPUT_STATE_HIDDEN);
        }
    };

    public SearchableSpinner(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.spinnerStyle);
    }

    public void setAdapter(Activity activity, SearchableSpinnerAdapter adapter, OnSearchableSpinnerClickListener listener) {
        this.activity = activity;
        this.mAdapter = adapter;
        this.mListener = listener;

        if (mAdapter != null) {
            setOnClickListener(onClickListener);
        }
    }

    public interface OnSearchableSpinnerClickListener {
        void onItemsSelected(String items);
    }

    private void refreshSpinner(boolean clean) {
        if (!clean)
            setText(item);
        else
            setText("");
    }

    public SearchableSpinnerAdapter getAdapter() {
        return this.mAdapter;
    }

    private void setData(View rootView) {
        _searchView = (SearchView) rootView.findViewById(R.id.search);

        _searchView.setOnQueryTextListener(this);
        _searchView.setOnCloseListener(this);
        _searchView.clearFocus();
        InputMethodManager mgr = (InputMethodManager) getContext().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(_searchView.getWindowToken(), 0);

        listViewItems = (ExpandableListView) rootView.findViewById(R.id.listItems);

        //attach the adapter to the list
        listViewItems.setAdapter(getAdapter());

        listViewItems.setTextFilterEnabled(true);

        listViewItems.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            //Если что-то фильтруется, то мы получаем текст с группы
            if (!getAdapter().filtered.isEmpty()) {
                item = getAdapter().getGroup(groupPosition);
                refreshSpinner(false);
                //я думаю удобно будет, чтобы запрос чистился после того, как результат считан
                //можно без проблем конечно ставить его, но думаю это не так удобно
                onClose();
                mListener.onItemsSelected(item);
                dialog.dismiss();
            }
            return false;
        });

        listViewItems.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            //Если ничего не фильтруется, то мы получаем текст от чайлд вью
            if (getAdapter().filtered.isEmpty()) {
                item = getAdapter().getChild(groupPosition, childPosition);
                refreshSpinner(false);
                mListener.onItemsSelected(item);
                dialog.dismiss();
            }
            return false;
        });

        listViewItems.setOnItemLongClickListener((parent, view, position, id) -> {

            if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                int childPosition = ExpandableListView.getPackedPositionChild(id);

                Log.d(TAG, "onItemLongClick: positions " + groupPosition + childPosition);

                Intent i = new Intent(activity, MoreInfoActivity.class);
                i.putExtra("COUNTRY", mAdapter.getCountryText(groupPosition));
                i.putExtra("CITY", mAdapter.getGroup(groupPosition));
                i.putExtra("STATION", mAdapter.getChild(groupPosition, childPosition));
                activity.startActivity(i);

            }
            else if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                int groupPosition = ExpandableListView.getPackedPositionGroup(id);

                Log.d(TAG, "onItemLongClick: positions " + groupPosition);

                Intent i = new Intent(activity, MoreInfoActivity.class);
                i.putExtra("COUNTRY", mAdapter.getCountryText(groupPosition));
                i.putExtra("CITY", mAdapter.getGroup(groupPosition));
                i.putExtra("STATION", mAdapter.getChildText());
                activity.startActivity(i);
            }

            return false;
        });

    }

    @Override
    public boolean onClose() {
        getAdapter().filterData("");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        getAdapter().filterData(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        getAdapter().filterData(s);
        return true;
    }
}