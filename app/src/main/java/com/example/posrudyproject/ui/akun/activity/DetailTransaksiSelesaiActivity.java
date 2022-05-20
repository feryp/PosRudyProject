package com.example.posrudyproject.ui.akun.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PelangganEndpoint;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.pembayaran.model.DetailPesanan;
import com.example.posrudyproject.ui.penjualan.activity.StrukPenjualanActivity;
import com.example.posrudyproject.ui.penjualan.adapter.TransaksiSuksesAdapter;
import com.example.posrudyproject.ui.penjualan.async.AsyncBluetoothEscPosPrint;
import com.example.posrudyproject.ui.penjualan.async.AsyncEscPosPrint;
import com.example.posrudyproject.ui.penjualan.async.AsyncEscPosPrinter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTransaksiSelesaiActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar mToolbar;
    MaterialButton btnCetakStruk, btnPenukaranBarang;
    RecyclerView rvDetailTransaksi;
    TransaksiSuksesAdapter adapter;
    List<KeranjangItem> keranjangItems;
    AppCompatTextView tvMetodePembayaran,tvTanggalTransaksi,tvNamaPenjual,tvNamaPelanggan,tvDiskon,tvTotal,tvDibayar,tvKembalian;
    String alamat_store,items,uang_diterima,bank_name,norek,id_transaksi,ongkir,auth_token,point;
    PelangganEndpoint pelangganEndpoint;
    public static final int PERMISSION_BLUETOOTH = 1;
    public static final int PERMISSION_BLUETOOTH_ADMIN = 2;
    public static final int PERMISSION_BLUETOOTH_CONNECT = 3;
    public static final int PERMISSION_BLUETOOTH_SCAN = 4;
    private BluetoothConnection selectedDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi_selesai);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");
        auth_token = ("Bearer ").concat(token);
        alamat_store = preferences.getString("alamat_store","");
        pelangganEndpoint = ApiClient.getClient().create(PelangganEndpoint.class);
        //INIT VIEW
        initComponent();

        initToolbar();
        keranjangItems = new ArrayList<>();

        //SET LISTENER
        btnCetakStruk.setOnClickListener(this);
        btnPenukaranBarang.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        if (bundle != null) {
            id_transaksi = bundle.getString("id_transaksi");
            bank_name = bundle.getString("bank_name");
            norek = bundle.getString("norek");
            tvMetodePembayaran.setText(bundle.getString("metode_bayar"));
            tvTanggalTransaksi.setText(bundle.getString("tanggal_transaksi"));
            tvNamaPenjual.setText(bundle.getString("nama_penjual"));
            tvNamaPelanggan.setText(bundle.getString("nama_pelanggan"));
            tvDiskon.setText(bundle.getString("diskon"));
            tvKembalian.setText( "Rp" + formatter.format(bundle.getDouble("kembalian")));
            tvTotal.setText("Rp" + formatter.format(bundle.getDouble("total")));
            ongkir = bundle.getString("ongkir");
            tvDibayar.setText("Rp" + formatter.format((bundle.getDouble("kembalian") + bundle.getDouble("total"))));

            //Transaksi List
            for (int i = 0; i< ((List<DetailPesanan>) bundle.getSerializable("detailPesanan")).size(); i++){
                keranjangItems.add(new KeranjangItem(
                        "",
                        ((List<DetailPesanan>) bundle.getSerializable("detailPesanan")).get(i).getType_name(),
                        ((List<DetailPesanan>) bundle.getSerializable("detailPesanan")).get(i).getSku_code(),
                        ((List<DetailPesanan>) bundle.getSerializable("detailPesanan")).get(i).getArtikel(),
                        ((List<DetailPesanan>) bundle.getSerializable("detailPesanan")).get(i).getNama_barang(),
                        formatter.format(Double.valueOf(((List<DetailPesanan>) bundle.getSerializable("detailPesanan")).get(i).getHarga())),
                        formatter.format(((List<DetailPesanan>) bundle.getSerializable("detailPesanan")).get(i).getKuantitas()),
                        formatter.format(Double.valueOf(((List<DetailPesanan>) bundle.getSerializable("detailPesanan")).get(i).getHarga()) * Double.valueOf(((List<DetailPesanan>) bundle.getSerializable("detailPesanan")).get(i).getKuantitas())),
                        formatter.format(((List<DetailPesanan>) bundle.getSerializable("detailPesanan")).get(i).getKuantitas())
                ));
            }

            //Setup adapter
            adapter = new TransaksiSuksesAdapter(keranjangItems, this);
            rvDetailTransaksi.setLayoutManager(new LinearLayoutManager(this));
            rvDetailTransaksi.setAdapter(adapter);
            rvDetailTransaksi.setHasFixedSize(true);
        } else {
            keranjangItems = new ArrayList<>();
        }

        Call<Double> callPoint = pelangganEndpoint.totalPoin(auth_token,bundle.getString("namaPelanggan"),bundle.getString("noHpPelanggan"));
        callPoint.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (!response.isSuccessful()){
                    point ="0";
                } else {
                    point = formatter.format(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                point ="0";
            }
        });
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_detail_transaksi);
        btnCetakStruk = findViewById(R.id.btn_cetak_struk);
        btnPenukaranBarang = findViewById(R.id.btn_penukaran_barang);
        rvDetailTransaksi = findViewById(R.id.rv_detail_penjualan_transaksi);
        tvMetodePembayaran = findViewById(R.id.tv_tipe_pembayaran_detail_transaksi);
        tvTanggalTransaksi= findViewById(R.id.tv_tgl_detail_transaksi);
        tvNamaPenjual= findViewById(R.id.tv_nama_penjual_detail_transaksi);
        tvNamaPelanggan= findViewById(R.id.tv_nama_pelanggan_detail_transaksi);
        tvDiskon= findViewById(R.id.tv_diskon_detail_transaksi);
        tvTotal= findViewById(R.id.tv_total_detail_transaksi);
        tvDibayar= findViewById(R.id.tv_dibayar_detail_transaksi);
        tvKembalian= findViewById(R.id.tv_total_kembalian_detail_transaksi);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_cetak_struk:
                printBluetooth();
                break;

            case R.id.btn_penukaran_barang:
                Intent penukaranBarang = new Intent(this, PenukaranBarangActivity.class);
                startActivity(penukaranBarang);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case DetailTransaksiSelesaiActivity.PERMISSION_BLUETOOTH:
                case DetailTransaksiSelesaiActivity.PERMISSION_BLUETOOTH_ADMIN:
                case DetailTransaksiSelesaiActivity.PERMISSION_BLUETOOTH_CONNECT:
                case DetailTransaksiSelesaiActivity.PERMISSION_BLUETOOTH_SCAN:
                    this.printBluetooth();
                    break;
            }
        }
    }

    public void browseBluetoothDevice() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        final BluetoothConnection[] bluetoothDevicesList = (new BluetoothPrintersConnections()).getList();
        System.out.println(bluetoothDevicesList);
        if (bluetoothDevicesList != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            final String[] items = new String[bluetoothDevicesList.length + 1];
            items[0] = "Default printer";
            int i = 0;
            for (BluetoothConnection device : bluetoothDevicesList) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                items[++i] = device.getDevice().getName();
            }

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailTransaksiSelesaiActivity.this);
            alertDialog.setTitle("Bluetooth printer selection");
            alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int index = i - 1;
                    if (index == -1) {
                        selectedDevice = null;
                    } else {
                        selectedDevice = bluetoothDevicesList[index];
                    }
                    Button button = (Button) findViewById(R.id.button_bluetooth_browse);
                    button.setText(items[i]);
                }
            });

            AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();

        }
    }

    public void printBluetooth() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, DetailTransaksiSelesaiActivity.PERMISSION_BLUETOOTH);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, DetailTransaksiSelesaiActivity.PERMISSION_BLUETOOTH_ADMIN);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, DetailTransaksiSelesaiActivity.PERMISSION_BLUETOOTH_CONNECT);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, DetailTransaksiSelesaiActivity.PERMISSION_BLUETOOTH_SCAN);
        } else {
            new AsyncBluetoothEscPosPrint(
                    this,
                    new AsyncEscPosPrint.OnPrintFinished() {
                        @Override
                        public void onError(AsyncEscPosPrinter asyncEscPosPrinter, int codeException) {
                            Log.e("Async.OnPrintFinished", "AsyncEscPosPrint.OnPrintFinished : An error occurred !");
                        }

                        @Override
                        public void onSuccess(AsyncEscPosPrinter asyncEscPosPrinter) {
                            Log.i("Async.OnPrintFinished", "AsyncEscPosPrint.OnPrintFinished : Print is finished !");
                        }
                    }
            )
                    .execute(this.getAsyncEscPosPrinter(selectedDevice));
        }
    }

    /**
     * Asynchronous printing
     */
    @SuppressLint("SimpleDateFormat")
    public AsyncEscPosPrinter getAsyncEscPosPrinter(DeviceConnection printerConnection) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy, HH:mm");
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 203, 48f, 32);
        items = "";
        for (int i=0; i<keranjangItems.size(); i++) {
            items += keranjangItems.get(i).getNamaBarang()+"\n"+keranjangItems.get(i).getHargaBarang()+" x "+keranjangItems.get(i).getKuantitasBarang() + "\n" + "[L]\n";
        }
        return printer.addTextToPrint(
                "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, this.getApplicationContext().getResources().getDrawableForDensity(R.drawable.logo_rp, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n" +
                        "[L]\n" +
                        "[C]<u><font size='normal'>"+ alamat_store +"</font></u>\n" +
                        "[L]\n" +
                        "[C]<u type='double'>" + format.format(new Date()) + "</u>\n" +
                        "[C]\n" +
                        "[C]--------------------------------\n" +
                        "[L]\n" +
                        "[C]<font size='normal'><b>Terimakasih sudh belanja!</b></font>\n" +
                        "[C]\n" +
                        "[C]--------------------------------\n" +
                        "[L]\n" +
                        "[L]Penjual   : "+ tvNamaPenjual.getText().toString() + "\n" +
                        "[L]No. Struk : "+ id_transaksi + "\n" +
                        "[C]\n" +
                        "[C]--------------------------------\n" +
                        "[L]"+ items +
                        "[C]--------------------------------\n" +
                        "[L]\n" +
                        "[C]================================\n" +
                        "[L]\n" +
                        "[L]Pelanggan     : "+ tvNamaPelanggan.getText().toString() +"\n" +
                        "[L]Point         : "+ point +"\n" +
                        "[L]Diskon        : "+ tvDiskon.getText().toString() +"\n" +
                        "[L]Ongkir        : " + ongkir + "\n" +
                        "[L]Total         : " + tvTotal.getText().toString() + "\n" +
                        "[L]Metode Bayar  : "+ tvMetodePembayaran.getText().toString()+ "\n" +
                        "[L]Nama Bank     : "+ bank_name+ "\n" +
                        "[L]No Rekening   : "+ norek+ "\n" +
                        "[L]Uang Diterima : " + uang_diterima + "\n" +
                        "[L]Kembalian     : "+ tvKembalian.getText().toString() +"\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "[C]Powered By GB System\n"
        );
    }
}