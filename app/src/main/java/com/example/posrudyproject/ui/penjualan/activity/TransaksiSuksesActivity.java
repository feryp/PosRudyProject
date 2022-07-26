package com.example.posrudyproject.ui.penjualan.activity;

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

import com.example.posrudyproject.ui.main.MainActivity;
import com.example.posrudyproject.ui.pembayaran.model.DetailPesanan;
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

public class TransaksiSuksesActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar mToolbar;
    MaterialButton btnCetakStruk, btnBuatPesananBaru;
    AppCompatTextView tvNoInvoiceDetailPesanan, tvJumlahNominalTransaksi, tvKembalianTransaksiSukses, tvMetodePembayaranTransaksiSukses,
            tvPelangganTransaksiSukses, tvDiskonTransaksiSukses, tvPenjualTransaksiSukses, tvOngkirTransaksiSukses, tvPointTransaksiSukses;
    RecyclerView rvTransaksi;
    TransaksiSuksesAdapter adapter;
    List<KeranjangItem> keranjangItems;
    PelangganEndpoint pelangganEndpoint;
    String auth_token;
    String alamat_store,items,uang_diterima,bank_name,norek;

    public static final int PERMISSION_BLUETOOTH = 1;
    public static final int PERMISSION_BLUETOOTH_ADMIN = 2;
    public static final int PERMISSION_BLUETOOTH_CONNECT = 3;
    public static final int PERMISSION_BLUETOOTH_SCAN = 4;
    private BluetoothConnection selectedDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_sukses);
        keranjangItems = new ArrayList<>();
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        pelangganEndpoint = ApiClient.getClient().create(PelangganEndpoint.class);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");
        auth_token = ("Bearer ").concat(token);
        alamat_store = preferences.getString("alamat_store","");
        //INIT VIEW
        initComponent();

        initToolbar();

        Button button = (Button) this.findViewById(R.id.button_bluetooth_browse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browseBluetoothDevice();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvJumlahNominalTransaksi.setText("Rp" + formatter.format(bundle.getDouble("total")));
            tvKembalianTransaksiSukses.setText("Rp" + formatter.format(bundle.getDouble("kembalian")));
            tvMetodePembayaranTransaksiSukses.setText(bundle.getString("metode_bayar"));
            bank_name = bundle.getString("bank_name");
            norek = bundle.getString("no_rek");
            tvNoInvoiceDetailPesanan.setText(bundle.getString("id_transaksi"));
            tvPelangganTransaksiSukses.setText(bundle.getString("namaPelanggan"));
            uang_diterima = "Rp" + formatter.format(bundle.getDouble("total") + bundle.getDouble("kembalian"));
            if (bundle.getDouble("diskon") < 100.0 ) {
                tvDiskonTransaksiSukses.setText(formatter.format(bundle.getDouble("diskon")) + "%");
            } else if (bundle.getDouble("diskon") >= 100.0 ) {
                tvDiskonTransaksiSukses.setText("Rp" + formatter.format(bundle.getDouble("diskon")));
            } else {
                tvDiskonTransaksiSukses.setText("-");
            }

            tvPenjualTransaksiSukses.setText(bundle.getString("namaPenjual"));
            tvOngkirTransaksiSukses.setText("Rp" + formatter.format(bundle.getDouble("ongkir")));

            //Transaksi List
            if ((List<DetailPesanan>) bundle.getSerializable("items") != null) {
                for (int i = 0; i < ((List<DetailPesanan>) bundle.getSerializable("items")).size(); i++) {
                    keranjangItems.add(new KeranjangItem(
                            "",
                            ((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getType_name(),
                            ((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getSku_code(),
                            ((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getArtikel(),
                            ((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getNama_barang(),
                            "Rp" + formatter.format(((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getHarga()),
                            "Rp" + formatter.format(((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getHarga_baru()),
                            ((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getHarga_baru_remark(),
                            formatter.format(((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getKuantitas()),
                            "Rp" + formatter.format(((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getHarga() * ((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getKuantitas()),
                            formatter.format(((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getKuantitas())
                    ));
                }
            }

            Call<Double> callPoint = pelangganEndpoint.totalPoin(auth_token,bundle.getString("namaPelanggan"),bundle.getString("noHpPelanggan"));
            callPoint.enqueue(new Callback<Double>() {
                @Override
                public void onResponse(Call<Double> call, Response<Double> response) {
                    if (!response.isSuccessful()){
                        tvPointTransaksiSukses.setText("0");
                    } else {
                        tvPointTransaksiSukses.setText(response.body().toString());
                    }
                }

                @Override
                public void onFailure(Call<Double> call, Throwable t) {
                    tvPointTransaksiSukses.setText("0");
                }
            });
        }
        //SET LISTENER
        btnCetakStruk.setOnClickListener(this);
        btnBuatPesananBaru.setOnClickListener(this);

        //Setup adapter
        adapter = new TransaksiSuksesAdapter(keranjangItems, this);
        rvTransaksi.setLayoutManager(new LinearLayoutManager(this));
        rvTransaksi.setAdapter(adapter);
        rvTransaksi.setHasFixedSize(true);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransaksiSuksesActivity.this, KategoriActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_transaksi_sukses);
        btnCetakStruk = findViewById(R.id.btn_cetak_struk);
        btnBuatPesananBaru = findViewById(R.id.btn_buat_pesanan_baru);
        rvTransaksi = findViewById(R.id.rv_item_pesanan_transaksi_selesai);
        tvJumlahNominalTransaksi = findViewById(R.id.tv_jumlah_nominal_transaksi);
        tvKembalianTransaksiSukses = findViewById(R.id.tv_kembalian_transaksi_sukses);
        tvMetodePembayaranTransaksiSukses = findViewById(R.id.tv_metode_pembayaran_transaksi_sukses);
        tvPelangganTransaksiSukses = findViewById(R.id.tv_pelanggan_transaksi_sukses);
        tvDiskonTransaksiSukses = findViewById(R.id.tv_diskon_transaksi_sukses);
        tvPenjualTransaksiSukses = findViewById(R.id.tv_penjual_transaksi_sukses);
        tvOngkirTransaksiSukses = findViewById(R.id.tv_ongkir_transaksi_sukses);
        tvNoInvoiceDetailPesanan = findViewById(R.id.tv_no_invoice_detail_pesanan);
        tvPointTransaksiSukses = findViewById(R.id.tv_point_transaksi_sukses);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cetak_struk:
                try {
                    browseBluetoothDevice();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;

            case R.id.btn_buat_pesanan_baru:
                Intent buatPesananBaru = new Intent(this, KategoriActivity.class);
                startActivity(buatPesananBaru);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case TransaksiSuksesActivity.PERMISSION_BLUETOOTH:
                case TransaksiSuksesActivity.PERMISSION_BLUETOOTH_ADMIN:
                case TransaksiSuksesActivity.PERMISSION_BLUETOOTH_CONNECT:
                case TransaksiSuksesActivity.PERMISSION_BLUETOOTH_SCAN:
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

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(TransaksiSuksesActivity.this);
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
                    printBluetooth();
                }
            });

            AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();

        }
    }

    public void printBluetooth() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, TransaksiSuksesActivity.PERMISSION_BLUETOOTH);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, TransaksiSuksesActivity.PERMISSION_BLUETOOTH_ADMIN);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, TransaksiSuksesActivity.PERMISSION_BLUETOOTH_CONNECT);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, TransaksiSuksesActivity.PERMISSION_BLUETOOTH_SCAN);
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
            ).execute(this.getAsyncEscPosPrinter(selectedDevice));
        }
    }

    /**
     * Asynchronous printing
     */
    @SuppressLint("SimpleDateFormat")
    public AsyncEscPosPrinter getAsyncEscPosPrinter(DeviceConnection printerConnection) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy, HH:mm");
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 203, 48f, 32);
        items = "";
        for (int i=0; i<keranjangItems.size(); i++) {
            items += keranjangItems.get(i).getNamaBarang()+"\n"+ keranjangItems.get(i).getHargaBarang()+" - Rp"+formatter.format(Double.valueOf(((keranjangItems.get(i).getHargaBarang().replace(",", "")).replace("Rp","")).replace(".","")) - Double.valueOf(((keranjangItems.get(i).getHarga_baru().replace(",", "")).replace("Rp","")).replace(".","")))+"\n"+"Rp"+keranjangItems.get(i).getHarga_baru().replace("Rp","")+" x "+keranjangItems.get(i).getKuantitasBarang() + "\n" + "[L]\n";
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
                        "[L]Penjual   : "+ tvPenjualTransaksiSukses.getText().toString() + "\n" +
                        "[L]No. Struk : "+ tvNoInvoiceDetailPesanan.getText().toString() + "\n" +
                        "[C]\n" +
                        "[C]--------------------------------\n" +
                        "[L]"+ items +
                        "[C]--------------------------------\n" +
                        "[L]\n" +
                        "[C]================================\n" +
                        "[L]\n" +
                        "[L]Pelanggan     : "+ tvPelangganTransaksiSukses.getText().toString() +"\n" +
                        "[L]Point         : "+ tvPointTransaksiSukses.getText().toString() +"\n" +
                        "[L]Diskon        : "+ tvDiskonTransaksiSukses.getText().toString() +"\n" +
                        "[L]Ongkir        : " + tvOngkirTransaksiSukses.getText().toString() + "\n" +
                        "[L]Total         : " + tvJumlahNominalTransaksi.getText().toString() + "\n" +
                        "[L]Metode Bayar  : "+ tvMetodePembayaranTransaksiSukses.getText().toString()+ "\n" +
                        "[L]Nama Bank     : "+ bank_name+ "\n" +
                        "[L]No Rekening   : "+ norek+ "\n" +
                        "[L]Uang Diterima : " + uang_diterima + "\n" +
                        "[L]Kembalian     : "+ tvKembalianTransaksiSukses.getText().toString() +"\n" +
                        "\n" +
                        "[C]Powered By GB System\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +

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
                        "[L]Penjual   : "+ tvPenjualTransaksiSukses.getText().toString() + "\n" +
                        "[L]No. Struk : "+ tvNoInvoiceDetailPesanan.getText().toString() + "\n" +
                        "[C]\n" +
                        "[C]--------------------------------\n" +
                        "[L]"+ items +
                        "[C]--------------------------------\n" +
                        "[L]\n" +
                        "[C]================================\n" +
                        "[L]\n" +
                        "[L]Pelanggan     : "+ tvPelangganTransaksiSukses.getText().toString() +"\n" +
                        "[L]Point         : "+ tvPointTransaksiSukses.getText().toString() +"\n" +
                        "[L]Diskon        : "+ tvDiskonTransaksiSukses.getText().toString() +"\n" +
                        "[L]Ongkir        : " + tvOngkirTransaksiSukses.getText().toString() + "\n" +
                        "[L]Total         : " + tvJumlahNominalTransaksi.getText().toString() + "\n" +
                        "[L]Metode Bayar  : "+ tvMetodePembayaranTransaksiSukses.getText().toString()+ "\n" +
                        "[L]Nama Bank     : "+ bank_name+ "\n" +
                        "[L]No Rekening   : "+ norek+ "\n" +
                        "[L]Uang Diterima : " + uang_diterima + "\n" +
                        "[L]Kembalian     : "+ tvKembalianTransaksiSukses.getText().toString() +"\n" +
                        "\n" +
                        "[C]Powered By GB System\n"
        );
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TransaksiSuksesActivity.this, KategoriActivity.class);
        startActivity(intent);
        finish();
    }
}