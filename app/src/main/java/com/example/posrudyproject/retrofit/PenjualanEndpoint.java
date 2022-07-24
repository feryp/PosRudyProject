package com.example.posrudyproject.retrofit;

import com.example.posrudyproject.ui.laporan.model.LaporanPelangganItem;
import com.example.posrudyproject.ui.laporan.model.LaporanPenjualItem;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerArtikelItem;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerKategoriItem;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerProdukItem;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerTipeItem;
import com.example.posrudyproject.ui.laporan.model.RiwayatTransaksiItem;
import com.example.posrudyproject.ui.laporan.model.RiwayatTransaksiPelangganItem;
import com.example.posrudyproject.ui.laporan.model.RiwayatTransaksiPenjualItem;
import com.example.posrudyproject.ui.laporan.model.SubRiwayatTransaksiPelangganItem;
import com.example.posrudyproject.ui.laporan.model.TransaksiTerakhirItem;
import com.example.posrudyproject.ui.pembayaran.model.BankItem;
import com.example.posrudyproject.ui.pembayaran.model.Penjualan;
import com.example.posrudyproject.ui.penjualan.model.PenjualanItem;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PenjualanEndpoint {
    @GET("/store/penjualanPerStore")
    Call<List<Penjualan>> getAllPenjualanPerStore(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store);

    @GET("/store/search")
    Call<List<PenjualanItem>> search(@Header("AUTHORIZATION") String token, @Query("keyword") String keyword);

    @GET("/store/searchPerStore")
    Call<List<Penjualan>> searchPerStore(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("keyword") String keyword);

    @POST("/store/add")
    Call<List<Penjualan>> savePenjualan(@Header("AUTHORIZATION") String token, @Body Penjualan penjualan);

    @POST("/store/update")
    Call<PenjualanItem> update(@Header("AUTHORIZATION") String token, @Body PenjualanItem penjualanItem);

    @POST("/store/delete")
    Call<PenjualanItem> deletePenjualan(@Header("AUTHORIZATION") String token, @Query("id") Long id);

    @GET("/store/rangkumanMobile")
    Call<Map> rangkumanPenjualanMobile (@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/store/rangkumanMobileKaryawan")
    Call<List<LaporanPenjualItem>> rekapKaryawan (@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/store/rangkumanMobilePenjualanKaryawan")
    Call<List<RiwayatTransaksiPenjualItem>> findByKaryawanId(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("id_karyawan") int id_karyawan, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/store/rangkumanTotalArtikel")
    Call<Map> rangkumanPenjualanMobilePerArtikel (@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/store/rangkumanMobileArtikel")
    Call<List<PenjualanPerArtikelItem>> rekapArtikel (@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/store/rangkumanTotalTipe")
    Call<Map> rangkumanPenjualanMobilePerTipe (@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/store/rangkumanMobileTipe")
    Call<List<PenjualanPerTipeItem>> rekapTipe (@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/store/rangkumanMobileTipeTerlaris")
    Call<List<PenjualanPerTipeItem>> rekapTipeTerlaris (@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/store/rangkumanMobileKategori")
    Call<List<PenjualanPerKategoriItem>> rekapKategori (@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/store/rangkumanMobileKategoriTerlaris")
    Call<List<PenjualanPerKategoriItem>> rekapKategoriTerlaris (@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/store/rangkumanMobileProduk")
    Call<List<PenjualanPerProdukItem>> rekapProduk (@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/store/rangkumanMobileProdukShorted")
    Call<List<PenjualanPerProdukItem>> rekapProdukShorted(@Header("AUTHORIZATION") String token,@Query("id_store") int id_store,@Query("start_date") String start_date,@Query("end_date") String end_date, @Query("orderBy") String orderBy,@Query("sortDir") String sortDir);

    @GET("/store/rangkumanMobileProdukTerlaris")
    Call<List<PenjualanPerProdukItem>> rekapProdukTerlaris (@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/store/rangkumanMobilePelanggan")
    Call<List<LaporanPelangganItem>> rekapPelanggan (@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/store/rangkumanMobilePelangganPerTanggal")
    Call<List<RiwayatTransaksiPelangganItem>> rekapPelangganPerTanggal (@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("start_date") String start_date, @Query("end_date") String end_date, @Query("no_hp_pelanggan") String no_hp_pelanggan);

    @GET("/pelanggan/totalPoin")
    Call<Double> totalPoin(@Header("AUTHORIZATION") String token, @Query("nama_pelanggan") String nama_pelanggan, @Query("no_hp_pelanggan") String no_hp_pelanggan);

    @GET("/store/subRiwayatPelanggan")
    Call <List<SubRiwayatTransaksiPelangganItem>> subRiwayatPelanggan(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("start_date") String start_date, @Query("end_date") String end_date, @Query("no_hp_pelanggan") String no_hp_pelanggan);

    @GET("/store/subRiwayatTerakhir")
    Call <List<TransaksiTerakhirItem>> subRiwayatTerakhir(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/store/riwayatPertanggal")
    Call <List<RiwayatTransaksiItem>> riwayatPertanggal(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/master/bank/all")
    Call<List<BankItem>> getAllBank(@Header("AUTHORIZATION") String token);
}
