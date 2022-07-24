package com.example.posrudyproject.ui.rekapKas.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.RekapKasEndpoint;
import com.example.posrudyproject.ui.beranda.fragment.BerandaFragment;
import com.example.posrudyproject.ui.main.MainActivity;
import com.example.posrudyproject.ui.pelanggan.activity.TambahPelangganActivity;
import com.example.posrudyproject.ui.rekapKas.activity.DetailKasActivity;
import com.example.posrudyproject.ui.rekapKas.model.KasKeluarItem;
import com.example.posrudyproject.ui.rekapKas.model.KasMasukItem;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekapKasFragment extends Fragment implements View.OnClickListener{

    MaterialCardView cardDetailKasToko;
    LineChart chartWeek;
    LineChart chartMonth;
    LineChart chartYear;
    MaterialButton btnUangMasuk, btnUangKeluar;
    RekapKasEndpoint rekapKasEndpoint;
    AppCompatTextView totalHarian, totalMingguan, totalBulanan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rekap_kas, container, false);
        rekapKasEndpoint = ApiClient.getClient().create(RekapKasEndpoint.class);
        //INIT VIEW
        cardDetailKasToko = v.findViewById(R.id.card_detail_kas_toko);
        chartWeek = v.findViewById(R.id.bar_chart_week);
        chartMonth = v.findViewById(R.id.line_chart_month);
        chartYear = v.findViewById(R.id.bar_chart_year);
        btnUangMasuk = v.findViewById(R.id.btn_uang_masuk);
        btnUangKeluar = v.findViewById(R.id.btn_uang_keluar);
        totalHarian = v.findViewById(R.id.tv_total_pendapatan_mingguan);
        totalMingguan = v.findViewById(R.id.tv_total_pendapatan_bulanan);
        totalBulanan = v.findViewById(R.id.tv_total_pendapatan_tahunan);
        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MM yyyy | HH:mm");
        DecimalFormat decim = new DecimalFormat("#,###.##");
        String dateString = sdf.format(date);

        Bundle bundle = getArguments();
        TextView namaToko = (AppCompatTextView) v.findViewById(R.id.tv_nama_toko_rekap_kas);
        TextView tanggal = (AppCompatTextView) v.findViewById(R.id.tv_waktu_rekap_kas);
        namaToko.setText("Rudy Project " + bundle.getString("nama_toko"));
        tanggal.setText(dateString);
        Call<Map> call = rekapKasEndpoint.allChart(bundle.getString("auth_token"),bundle.getInt("id_toko"));
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if (!response.isSuccessful()){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    Map<String, Map<String, Map<String, Object>>> inner = (Map<String, Map<String, Map<String, Object>>>) response.body().get("result");
                    Map<String, Map<String, Object>> harian = inner.get("harian");
                    Map<String, Object> defValue = new HashMap<>();
                    defValue.put("id","1");
                    defValue.put("total","0");
                    defValue.put("date","default");
                    Map<String, Object> Mon = harian.get("Mon") == null ? defValue : harian.get("Mon");
                    Map<String, Object> Tue = harian.get("Tue") == null ? defValue : harian.get("Tue");
                    Map<String, Object> Wed = harian.get("Wed") == null ? defValue : harian.get("Wed");
                    Map<String, Object> Thu = harian.get("Thu") == null ? defValue : harian.get("Thu");
                    Map<String, Object> Fri = harian.get("Fri") == null ? defValue : harian.get("Fri");
                    Map<String, Object> Sat = harian.get("Sat") == null ? defValue : harian.get("Sat");
                    Map<String, Object> Sun = harian.get("Sun") == null ? defValue : harian.get("Sun");
                    //DATA SET CHART WEEK
                    ArrayList<Entry> valueWeek = new ArrayList<>();
                    valueWeek.add(new Entry(0, Float.parseFloat(Mon.get("total").toString()), "Mon"));
                    valueWeek.add(new Entry(1, Float.parseFloat(Tue.get("total").toString()) ,"Tue"));
                    valueWeek.add(new Entry(2, Float.parseFloat(Wed.get("total").toString()), "Wed"));
                    valueWeek.add(new Entry(3, Float.parseFloat(Thu.get("total").toString()), "Thu"));
                    valueWeek.add(new Entry(4, Float.parseFloat(Fri.get("total").toString()), "Fri"));
                    valueWeek.add(new Entry(5, Float.parseFloat(Sat.get("total").toString()), "Sat"));
                    valueWeek.add(new Entry(6, Float.parseFloat(Sun.get("total").toString()), "Sun"));

                    totalHarian.setText("Rp"+ decim.format(Float.parseFloat(Mon.get("total").toString()) + Float.parseFloat(Tue.get("total").toString()) + Float.parseFloat(Wed.get("total").toString()) + Float.parseFloat(Thu.get("total").toString()) + Float.parseFloat(Fri.get("total").toString()) + Float.parseFloat(Sat.get("total").toString()) + Float.parseFloat(Sun.get("total").toString())));

                    Map<String, Map<String, Object>> mingguan = inner.get("mingguan");
                    Map<String, Object> week1 = mingguan.get("Week 1") == null ? defValue : mingguan.get("Week 1");
                    Map<String, Object> week2 = mingguan.get("Week 2") == null ? defValue : mingguan.get("Week 2");
                    Map<String, Object> week3 = mingguan.get("Week 3") == null ? defValue : mingguan.get("Week 3");
                    Map<String, Object> week4 = mingguan.get("Week 4") == null ? defValue : mingguan.get("Week 4");
                    //DATA SET CHART MONTH
                    ArrayList<Entry> valueMonth = new ArrayList<>();
                    valueMonth.add(new Entry(0, Float.parseFloat(week1.get("total").toString()), "Week 1"));
                    valueMonth.add(new Entry(1, Float.parseFloat(week2.get("total").toString()), "Week 2"));
                    valueMonth.add(new Entry(2, Float.parseFloat(week3.get("total").toString()), "Week 3"));
                    valueMonth.add(new Entry(3, Float.parseFloat(week4.get("total").toString()), "Week 4"));

                    totalMingguan.setText("Rp"+ decim.format(Float.parseFloat(week1.get("total").toString()) + Float.parseFloat(week2.get("total").toString()) + Float.parseFloat(week3.get("total").toString()) + Float.parseFloat(week4.get("total").toString())));

                    Map<String, Map<String, Object>> bulanan = inner.get("bulanan");
                    Map<String, Object> Jan = bulanan.get("Jan") == null ? defValue : bulanan.get("Jan");
                    Map<String, Object> Feb = bulanan.get("Feb") == null ? defValue : bulanan.get("Feb");
                    Map<String, Object> Mar = bulanan.get("Mar") == null ? defValue : bulanan.get("Mar");
                    Map<String, Object> Apr = bulanan.get("Apr") == null ? defValue : bulanan.get("Apr");

                    Map<String, Object> Mei = bulanan.get("Mei") == null ? defValue : bulanan.get("Mei");
                    Map<String, Object> Jun = bulanan.get("Jun") == null ? defValue : bulanan.get("Jun");
                    Map<String, Object> Jul = bulanan.get("Jul") == null ? defValue : bulanan.get("Jul");
                    Map<String, Object> Agt = bulanan.get("Agt") == null ? defValue : bulanan.get("Agt");

                    Map<String, Object> Sep = bulanan.get("Sep") == null ? defValue : bulanan.get("Sep");
                    Map<String, Object> Oct = bulanan.get("Oct") == null ? defValue : bulanan.get("Oct");
                    Map<String, Object> Nov = bulanan.get("Nov") == null ? defValue : bulanan.get("Nov");
                    Map<String, Object> Dec = bulanan.get("Dec") == null ? defValue : bulanan.get("Dec");

                    //DATA SET CHART YEAR
                    ArrayList<Entry> valueYear = new ArrayList<>();
                    valueYear.add(new BarEntry(0, Float.parseFloat(Jan.get("total").toString()), "Jan"));
                    valueYear.add(new BarEntry(1, Float.parseFloat(Feb.get("total").toString()) ,"Feb"));
                    valueYear.add(new BarEntry(2, Float.parseFloat(Mar.get("total").toString()), "Mar"));
                    valueYear.add(new BarEntry(3, Float.parseFloat(Apr.get("total").toString()), "Apr"));
                    valueYear.add(new BarEntry(4, Float.parseFloat(Mei.get("total").toString()), "Mei"));
                    valueYear.add(new BarEntry(5, Float.parseFloat(Jun.get("total").toString()), "Jun"));
                    valueYear.add(new BarEntry(6, Float.parseFloat(Jul.get("total").toString()), "Jul"));
                    valueYear.add(new BarEntry(7, Float.parseFloat(Agt.get("total").toString()), "Agt"));
                    valueYear.add(new BarEntry(8, Float.parseFloat(Sep.get("total").toString()), "Sep"));
                    valueYear.add(new BarEntry(9, Float.parseFloat(Oct.get("total").toString()), "Oct"));
                    valueYear.add(new BarEntry(10, Float.parseFloat(Nov.get("total").toString()), "Nov"));
                    valueYear.add(new BarEntry(11, Float.parseFloat(Dec.get("total").toString()), "Dec"));

                    totalBulanan.setText("Rp"+ decim.format(Float.parseFloat(Jan.get("total").toString()) +Float.parseFloat(Feb.get("total").toString())+Float.parseFloat(Mar.get("total").toString())+Float.parseFloat(Apr.get("total").toString())+Float.parseFloat(Mei.get("total").toString())+Float.parseFloat(Jun.get("total").toString())+Float.parseFloat(Jul.get("total").toString())+Float.parseFloat(Agt.get("total").toString())+Float.parseFloat(Sep.get("total").toString())+Float.parseFloat(Oct.get("total").toString())+Float.parseFloat(Nov.get("total").toString())+Float.parseFloat(Dec.get("total").toString())));

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
                }

            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        //SET LISTENER
        cardDetailKasToko.setOnClickListener(RekapKasFragment.this);
        btnUangMasuk.setOnClickListener(RekapKasFragment.this);
        btnUangKeluar.setOnClickListener(RekapKasFragment.this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    getActivity().finish();
                    getActivity().overridePendingTransition(0, 0);
                    startActivity(getActivity().getIntent());
                    getActivity().overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
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
        rekapKasEndpoint = ApiClient.getClient().create(RekapKasEndpoint.class);
        Bundle bundle = getArguments();
        String token = bundle.getString("auth_token");
        Integer id_toko = bundle.getInt("id_toko");
        String nama_toko = bundle.getString("nama_toko");
        Integer id_pengguna = bundle.getInt("id_pengguna");
        String nama_pengguna = bundle.getString("nama_pengguna");

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
                KasMasukItem item = new KasMasukItem();
                item.setNominalKasMasuk(etJmlUangMasuk.getText().toString());
                item.setIdToko(id_toko);
                item.setNamaToko(nama_toko);
                item.setIdPenjual(id_pengguna);
                item.setPenjualKasMasuk(nama_pengguna);
                item.setCatatanKasMasuk(etCatatan.getText().toString());
                SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(false);
                pDialog.show();
                Call<Map> call = rekapKasEndpoint.saveKasMasuk(token,item);
                call.enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {
                        pDialog.dismiss();
                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText(response.body().get("message").toString());
                        sweetAlertDialog.show();
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });
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
        rekapKasEndpoint = ApiClient.getClient().create(RekapKasEndpoint.class);
        Bundle bundle = getArguments();
        String token = bundle.getString("auth_token");
        Integer id_toko = bundle.getInt("id_toko");
        String nama_toko = bundle.getString("nama_toko");
        Integer id_pengguna = bundle.getInt("id_pengguna");
        String nama_pengguna = bundle.getString("nama_pengguna");

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
                KasKeluarItem item = new KasKeluarItem();
                item.setNominalKasKeluar(etJmlUangKeluar.getText().toString());
                item.setIdToko(id_toko);
                item.setNamaToko(nama_toko);
                item.setIdPenjual(id_pengguna);
                item.setPenjualKasKeluar(nama_pengguna);
                item.setCatatanKasKeluar(etCatatan.getText().toString());
                SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(false);
                pDialog.show();
                Call<Map> call = rekapKasEndpoint.saveKasKeluar(token,item);
                call.enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {
                        pDialog.dismiss();
                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText(response.body().get("message").toString());
                        sweetAlertDialog.show();
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });
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