package com.example.posrudyproject.ui.filter.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.posrudyproject.R;

public class NothingSelectedSpinnerAdapter implements SpinnerAdapter, ListAdapter {

    protected static final int EXTRA = 1;
    protected SpinnerAdapter adapter;
    protected Context context;
    protected AppCompatTextView tvNothingSelected;
    protected int nothingSelectedLayout;
    protected int nothingSelectedDropdownLayout;
    private String hint;

    public NothingSelectedSpinnerAdapter(SpinnerAdapter adapter, Context context, int nothingSelectedLayout, String hint) {
        this.adapter = adapter;
        this.context = context;
        this.nothingSelectedLayout = nothingSelectedLayout;
        this.hint = hint;
        this.tvNothingSelected = (AppCompatTextView) LayoutInflater.from(context).inflate(R.layout.spinner_text_nothing_selected, null);
        this.tvNothingSelected.setText(hint);
    }

    public void setHint(String hint){
        this.hint = hint;
    }

    public String getHint(){
        return this.hint;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // This provides the View for the Selected Item in the Spinner, not
        // the dropdown (unless dropdownView is not set).
        if (position == 0) {
            return getNothingSelectedView(viewGroup);
        }
        return adapter.getView(position - EXTRA, null, viewGroup); // Could re-use
        // the convertView if possible.
    }

    protected View getNothingSelectedView(ViewGroup parent) {
        return tvNothingSelected;
//                LayoutInflater.from(context).inflate(nothingSelectedLayout, parent, false);
    }

    public View getDropDownView(int position, View view, ViewGroup viewGroup) {
        // Android BUG! http://code.google.com/p/android/issues/detail?id=17128 -
        // Spinner does not support multiple view types
        if (position == 0) {
            return getNothingSelectedView(viewGroup);
        }

        // Could re-use the convertView if possible, use setTag...
        return adapter.getDropDownView(position - EXTRA, null, viewGroup);
    }

    protected View getNothingSelectedDropdownView(ViewGroup parent) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
    }

    @Override
    public int getCount() {
        int count = adapter.getCount();
        return count == 0 ? 0 : count + EXTRA;
    }

    @Override
    public Object getItem(int position) {
        return position == 0 ? null : adapter.getItem(position - EXTRA);
    }


    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position >= EXTRA ? adapter.getItemId(position - EXTRA) : position - EXTRA;
    }

    @Override
    public boolean hasStableIds() {
        return adapter.hasStableIds();
    }

    @Override
    public boolean isEmpty() {
        return adapter.isEmpty();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        adapter.registerDataSetObserver(dataSetObserver);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        adapter.unregisterDataSetObserver(dataSetObserver);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0; // Don't allow the 'nothing selected'
        // item to be picked.
    }
}
