package com.jassdev.jackson.tututestapp.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.jassdev.jackson.tututestapp.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@SuppressLint("LongLogTag")
public class SearchableSpinnerAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "SearchableSpinnerAdapter";
    public ArrayList<String> filtered = new ArrayList<>();
    private ArrayList<String> country, cities = new ArrayList<>();
    private LinkedHashMap<String, ArrayList<String>> stations = new LinkedHashMap<>();
    private Context context;
    private String childText;

    public SearchableSpinnerAdapter(Context context, ArrayList<String> country, ArrayList<String> cities, LinkedHashMap<String, ArrayList<String>> stations) {
        this.context = context;
        this.country = country;
        this.cities = cities;
        this.stations = stations;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_group, parent, false);
        }
        Log.d(TAG, "getGroupView: ");

        TextView textGroup = (TextView) convertView.findViewById(R.id.groupTitle);
        if (!filtered.isEmpty())
            textGroup.setText(filtered.get(groupPosition));
        else
            textGroup.setText(context.getString(R.string.country_city, country.get(groupPosition), cities.get(groupPosition)));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_child, parent, false);
        }

        TextView textChild = (TextView) convertView.findViewById(R.id.childTitle);

        if (filtered.isEmpty())
            textChild.setText(stations.get(cities.get(groupPosition)).get(childPosition));

        return convertView;
    }

    void filterData(String query) {
        query = query.toLowerCase();

        filtered.clear();

        if (!query.isEmpty()) {
            for (int i = 0; i < cities.size(); i++) {
                for (String s : stations.get(cities.get(i))) {
                    if (s.toLowerCase().contains(query)) {
                        filtered.add(context.getString(R.string.country_city_station, country.get(i), cities.get(i), s));
                        setChildText(s);
                    }
                }

            }
        }
        notifyDataSetChanged();
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    @Override
    public int getGroupCount() {
        return cities.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (filtered.isEmpty())
            return stations.get(cities.get(groupPosition)).size();
        else
            return 0;
    }

    public String getCountryText(int groupPosition) {
        return country.get(groupPosition);
    }

    public void setChildText(String childText) {
        this.childText = childText;
    }

    public String getChildText() {
        return childText;
    }

    @Override
    public String getGroup(int groupPosition) {
        if (filtered.isEmpty())
            return cities.get(groupPosition);
        else
            return filtered.get(groupPosition);
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return stations.get(cities.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}