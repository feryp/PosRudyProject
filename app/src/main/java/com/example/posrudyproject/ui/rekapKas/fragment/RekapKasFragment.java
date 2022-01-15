package com.example.posrudyproject.ui.rekapKas.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.rekapKas.activity.DetailKasActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class RekapKasFragment extends Fragment implements View.OnClickListener{

    MaterialCardView cardDetailKasToko;
    LineChart chartWeek;
    LineChart chartMonth;
    LineChart chartYear;
    MaterialButton btnUangMasuk, btnUangKeluar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rekap_kas, container, false);

        //INIT VIEW
        cardDetailKasToko = v.findViewById(R.id.card_detail_kas_toko);
        chartWeek = v.findViewById(R.id.bar_chart_week);
        chartMonth = v.findViewById(R.id.line_chart_month);
        chartYear = v.findViewById(R.id.bar_chart_year);
        btnUangMasuk = v.findViewById(R.id.btn_uang_masuk);
        btnUangKeluar = v.findViewById(R.id.btn_uang_keluar);


        //DATA SET CHART WEEK
        ArrayList<Entry> valueWeek = new ArrayList<>();
        valueWeek.add(new Entry(0, 800, "Mon"));
        valueWeek.add(new Entry(1, 1000 ,"Tue"));
        valueWeek.add(new Entry(2, 250, "Wed"));
        valueWeek.add(new Entry(3, 400, "Thu"));
        valueWeek.add(new Entry(4, 120, "Fri"));
        valueWeek.add(new Entry(5, 50, "Sat"));
        valueWeek.add(new Entry(6, 100, "Sun"));

        //DATA SET CHART MONTH
        ArrayList<Entry> valueMonth = new ArrayList<>();
        valueMonth.add(new Entry(0, 600, "Week 1"));
        valueMonth.add(new Entry(1, 1000, "Week 2"));
        valueMonth.add(new Entry(2, 240, "Week 3"));
        valueMonth.add(new Entry(3, 800, "Week 4"));

        //DATA SET CHART YEAR
        ArrayList<Entry> valueYear = new ArrayList<>();
        valueYear.add(new BarEntry(0, 800, "Jan"));
        valueYear.add(new BarEntry(1, 1000 ,"Feb"));
        valueYear.add(new BarEntry(2, 250, "Mar"));
        valueYear.add(new BarEntry(3, 400, "Apr"));
        valueYear.add(new BarEntry(4, 120, "Mei"));
        valueYear.add(new BarEntry(5, 50, "Jun"));
        valueYear.add(new BarEntry(6, 100, "Jul"));
        valueYear.add(new BarEntry(7, 230, "Agt"));
        valueYear.add(new BarEntry(8, 780, "Sep"));
        valueYear.add(new BarEntry(9, 80, "Oct"));
        valueYear.add(new BarEntry(10, 125, "Nov"));
        valueYear.add(new BarEntry(11, 1000, "Dec"));

        //DATA STRING X AXIS LABEL CHART WEEK
        ArrayList<String> xAxisWeek = new ArrayList<>();
        xAxisWeek.add("Mon");
        xAxisWeek.add("Tue");
        xAxisWeek.add("Wed");
        xAxisWeek.add("Thu");
        xAxisWeek.add("Fri");
        xAxisWeek.add("Sat");
        xAxisWeek.add("Sun");

        //DATA STRING X AXIS LABEL CHART MONTH
        ArrayList<String> xAxisMonth = new ArrayList<>();
        xAxisMonth.add("Week 1");
        xAxisMonth.add("Week 2");
        xAxisMonth.add("Week 3");
        xAxisMonth.add("Week 4");

        //DATA STRING X AXIS LABEL CHART MONTH
        ArrayList<String> xAxisYear = new ArrayList<>();
        xAxisYear.add("Jan");
        xAxisYear.add("Feb");
        xAxisYear.add("Mar");
        xAxisYear.add("Apr");
        xAxisYear.add("Mei");
        xAxisYear.add("Jun");
        xAxisYear.add("Jul");
        xAxisYear.add("Agt");
        xAxisYear.add("Sep");
        xAxisYear.add("Oct");
        xAxisYear.add("Nov");
        xAxisYear.add("Dec");


        //INIT DATA SET
        LineDataSet dataWeekSet = new LineDataSet(valueWeek,"Pendapatan");
        LineDataSet dataMonthSet = new LineDataSet(valueMonth, "Pendapatan");
        LineDataSet dataYearSet = new LineDataSet(valueYear, "Pendapatan");

        //SET STYLE DATASET
        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.bg_chart_gradient); //SET FILL GRADIENT

        //Data Week
        dataWeekSet.setColor(Color.parseColor("#5D5FEF"));
        dataWeekSet.setValueTextColor(Color.parseColor("#7C7C7C"));
        dataWeekSet.setLineWidth(2);
        dataWeekSet.setDrawCircles(true);
        dataWeekSet.setDrawCircleHole(false);
        dataWeekSet.setCircleColor(Color.parseColor("#5D5FEF"));
        dataWeekSet.setCircleRadius(4);
        dataWeekSet.setValueTextColor(Color.parseColor("#7C7C7C"));
        dataWeekSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); //to make the smooth line as the graph is adrapt change so smooth curve
        dataWeekSet.setCubicIntensity(0.2f); //to enable the cubic density : if 1 then it will be sharp curve
        dataWeekSet.setDrawFilled(true); //to fill the below of smooth line in graph
        dataWeekSet.setFillDrawable(drawable); //set gradient
        dataWeekSet.setFillAlpha(80); //set the transparency

        //Data Month
        dataMonthSet.setColor(Color.parseColor("#5D5FEF"));
        dataMonthSet.setValueTextColor(Color.parseColor("#7C7C7C"));
        dataMonthSet.setLineWidth(2);
        dataMonthSet.setDrawCircles(true);
        dataMonthSet.setDrawCircleHole(false);
        dataMonthSet.setCircleColor(Color.parseColor("#5D5FEF"));
        dataMonthSet.setCircleRadius(4);
        dataMonthSet.setValueTextColor(Color.parseColor("#7C7C7C"));
        dataMonthSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); //to make the smooth line as the graph is adrapt change so smooth curve
        dataMonthSet.setCubicIntensity(0.2f); //to enable the cubic density : if 1 then it will be sharp curve
        dataMonthSet.setDrawFilled(true); //to fill the below of smooth line in graph
        dataMonthSet.setFillDrawable(drawable); //set gradient
        dataMonthSet.setFillAlpha(80); //set the transparency

        //Data Year
        dataYearSet.setColor(Color.parseColor("#5D5FEF"));
        dataYearSet.setValueTextColor(Color.parseColor("#7C7C7C"));
        dataYearSet.setLineWidth(2);
        dataYearSet.setDrawCircles(true);
        dataYearSet.setDrawCircleHole(false);
        dataYearSet.setCircleColor(Color.parseColor("#5D5FEF"));
        dataYearSet.setCircleRadius(4);
        dataYearSet.setValueTextColor(Color.parseColor("#7C7C7C"));
        dataYearSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); //to make the smooth line as the graph is adrapt change so smooth curve
        dataYearSet.setCubicIntensity(0.2f); //to enable the cubic density : if 1 then it will be sharp curve
        dataYearSet.setDrawFilled(true); //to fill the below of smooth line in graph
        dataYearSet.setFillDrawable(drawable); //set gradient
        dataYearSet.setFillAlpha(80); //set the transparency


        //STYLING CHART WEEK
        chartWeek.setNoDataText("Tidak Ada Data");
        chartWeek.setNoDataTextColor(Color.parseColor("#4F4F4F"));
        chartWeek.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chartWeek.setDrawGridBackground(true);
        chartWeek.setGridBackgroundColor(Color.parseColor("#FFFFFF"));
        chartWeek.setDrawBorders(true);
        chartWeek.setBorderColor(Color.parseColor("#F0F0F0"));
        chartWeek.setBorderWidth(1);
        chartWeek.getDescription().setEnabled(false);

        //STYLING CHART MONTH
        chartMonth.setNoDataText("Tidak Ada Data");
        chartMonth.setNoDataTextColor(Color.parseColor("#4F4F4F"));
        chartMonth.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chartMonth.setDrawGridBackground(true);
        chartMonth.setGridBackgroundColor(Color.parseColor("#FFFFFF"));
        chartMonth.setDrawBorders(true);
        chartMonth.setBorderColor(Color.parseColor("#F0F0F0"));
        chartMonth.setBorderWidth(1);
        chartMonth.getDescription().setEnabled(false);

        //STYLING CHART YEAR
        chartYear.setNoDataText("Tidak Ada Data");
        chartYear.setNoDataTextColor(Color.parseColor("#4F4F4F"));
        chartYear.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chartYear.setDrawGridBackground(true);
        chartYear.setGridBackgroundColor(Color.parseColor("#FFFFFF"));
        chartYear.setDrawBorders(true);
        chartYear.setBorderColor(Color.parseColor("#F0F0F0"));
        chartYear.setBorderWidth(1);
        chartYear.getDescription().setEnabled(false);

        //SET DATA CHART WEEK
        LineData lineDataWeak = new LineData(dataWeekSet);
        chartWeek.setData(lineDataWeak);
        chartWeek.animateXY(2000, 2000, Easing.EaseInOutBounce, Easing.EaseInOutBounce);
        chartWeek.invalidate();

        //SET DATA CHART MONTH
        LineData lineDataMonth = new LineData(dataMonthSet);
        chartMonth.setData(lineDataMonth);
        chartMonth.animateXY(2000, 2000, Easing.EaseInOutBounce, Easing.EaseInOutBounce);
        chartMonth.invalidate();

        //SET DATA CHART YEAR
        LineData lineDataYear = new LineData(dataYearSet);
        chartYear.setData(lineDataYear);
        chartYear.animateXY(2000, 2000, Easing.EaseInOutBounce, Easing.EaseInOutBounce);
        chartYear.invalidate();

        //X AXIS
        XAxis axisWeek = chartWeek.getXAxis(); //X Axis Chart Week
        axisWeek.setPosition(XAxis.XAxisPosition.BOTTOM);
        axisWeek.setValueFormatter(new IndexAxisValueFormatter(xAxisWeek));

        XAxis axisMonth = chartMonth.getXAxis(); // X Axis Chart Month
        axisMonth.setPosition(XAxis.XAxisPosition.BOTTOM);
        axisMonth.setValueFormatter(new IndexAxisValueFormatter(xAxisMonth));

        XAxis axisYear = chartYear.getXAxis(); // X Axis Chart Month
        axisYear.setPosition(XAxis.XAxisPosition.BOTTOM);
        axisYear.setLabelCount(12, true);
        axisYear.setValueFormatter(new IndexAxisValueFormatter(xAxisYear));

        //Y AXIS
        YAxis yAxisWeek = chartWeek.getAxisRight(); //Y Axis Chart Week
        yAxisWeek.setEnabled(false);

        YAxis yAxisMonth = chartMonth.getAxisRight(); //Y Axis Chart Month
        yAxisMonth.setEnabled(false);

        YAxis yAxisYear = chartYear.getAxisRight(); //Y Axis Chart Month
        yAxisYear.setEnabled(false);

        //LEGEND CHART WEEK
        Legend legendWeek = chartWeek.getLegend();
        legendWeek.setEnabled(true);
        legendWeek.setTextColor(Color.parseColor("#4F4F4F"));
        legendWeek.setTextSize(12);
        legendWeek.setForm(Legend.LegendForm.CIRCLE);
        legendWeek.setFormToTextSpace(4);
        legendWeek.setXEntrySpace(10);

        //LEGEND CHART MONTH
        Legend legendMonth = chartMonth.getLegend();
        legendMonth.setEnabled(true);
        legendMonth.setTextColor(Color.parseColor("#4F4F4F"));
        legendMonth.setTextSize(12);
        legendMonth.setForm(Legend.LegendForm.CIRCLE);
        legendMonth.setFormToTextSpace(4);
        legendMonth.setXEntrySpace(10);

        //LEGEND CHART YEAR
        Legend legendYear = chartYear.getLegend();
        legendYear.setEnabled(true);
        legendYear.setTextColor(Color.parseColor("#4F4F4F"));
        legendYear.setTextSize(12);
        legendYear.setForm(Legend.LegendForm.CIRCLE);
        legendYear.setFormToTextSpace(4);
        legendYear.setXEntrySpace(10);

        //SET LISTENER
        cardDetailKasToko.setOnClickListener(RekapKasFragment.this);
        btnUangMasuk.setOnClickListener(RekapKasFragment.this);
        btnUangKeluar.setOnClickListener(RekapKasFragment.this);

        return v;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_detail_kas_toko:
                Intent detailKas = new Intent(getActivity(), DetailKasActivity.class);
                startActivity(detailKas);
                break;
            case R.id.btn_uang_masuk:
                dialogUangMasuk();
                break;

            case R.id.btn_uang_keluar:
                dialogUangKeluar();
                break;
        }
    }

    private void dialogUangMasuk() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.dialog_uang_masuk, null);

        //init view
        final TextInputEditText etJmlUangMasuk = mView.findViewById(R.id.et_jml_uang_masuk);
        final TextInputEditText etCatatan = mView.findViewById(R.id.et_catatan_uang_masuk);
        MaterialButton btnSimpan = mView.findViewById(R.id.btn_simpan_uang_masuk);
        MaterialButton btnCancel = mView.findViewById(R.id.btn_cancel_dialog);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_rounded_white);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Simpan", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void dialogUangKeluar() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.dialog_uang_keluar, null);

        //init view
        final TextInputEditText etJmlUangKeluar = mView.findViewById(R.id.et_jml_uang_keluar);
        final TextInputEditText etCatatan = mView.findViewById(R.id.et_catatan_uang_keluar);
        MaterialButton btnSimpan = mView.findViewById(R.id.btn_simpan_uang_keluar);
        MaterialButton btnCancel = mView.findViewById(R.id.btn_cancel_dialog);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_rounded_white);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Simpan", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}