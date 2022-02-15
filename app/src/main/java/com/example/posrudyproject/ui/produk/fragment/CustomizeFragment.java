package com.example.posrudyproject.ui.produk.fragment;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posrudyproject.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CustomizeFragment extends Fragment {

    public static final String KEY_User_Document1 = "doc1";
    private String Document_img1="";

    private static final int RESULT_OK = 0;

    MaterialRadioButton rdLensaProduk1, rdLensaProduk2, rdFrameProduk1, rdFrameProduk2;
    AppCompatTextView tvLensaProduk1, tvLensaProduk2, tvFrameProduk1, tvFrameProduk2;
    TextInputEditText etArtikelBaru, etCustomKode, etNamaBarangBaru, etTipeBaru, etKuantitas, etHargaJual, etCatatan;
    MaterialCardView cardUploadImage;
    RoundedImageView imItemCustom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_customize, container, false);

        //INIT VIEW
        rdLensaProduk1 = v.findViewById(R.id.rd_lens_produk_1);
        rdLensaProduk2 = v.findViewById(R.id.rd_lens_produk_2);
        rdFrameProduk1 = v.findViewById(R.id.rd_frame_produk_1);
        rdFrameProduk2 = v.findViewById(R.id.rd_frame_produk_2);
        tvLensaProduk1 = v.findViewById(R.id.tv_rd_lens_produk_1);
        tvLensaProduk2 = v.findViewById(R.id.tv_rd_lens_produk_2);
        tvFrameProduk1 = v.findViewById(R.id.tv_rd_frame_produk_1);
        tvFrameProduk2 = v.findViewById(R.id.tv_rd_frame_produk_2);
        etArtikelBaru = v.findViewById(R.id.et_artikel_baru);
        etCustomKode = v.findViewById(R.id.et_custom_kode);
        etNamaBarangBaru = v.findViewById(R.id.et_nama_barang_baru);
        etTipeBaru = v.findViewById(R.id.et_tipe_baru);
        etKuantitas = v.findViewById(R.id.et_kuantitas_baru);
        etHargaJual = v.findViewById(R.id.et_harga_jual_baru);
        etCatatan = v.findViewById(R.id.et_catatan_custom);
        cardUploadImage = v.findViewById(R.id.card_upload_image_custom);
        imItemCustom = v.findViewById(R.id.im_barang_baru_custom);

        cardUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        return v;
    }

    private void selectImage() {
        final CharSequence[] options = { "Ambil Gambar", "Pilih dari Galeri", "Batal"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Masukkan Gambar!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                if (options[item].equals("Ambil Gambar"))
                {
                    Intent ambilGambar = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg"); //Contoh aja
                    ambilGambar.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult(ambilGambar, 1);
                }
                else if (options[item].equals("Pilih dari Galeri"))
                {
                    Intent pilihGaleri = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pilihGaleri, 2);
                }
                else if (options[item].equals("Batal"))
                {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (resultCode == 1){
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()){
                    if (temp.getName().equals("temp.jpg")){
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    bitmap = getResizedBitmap(bitmap, 400);
                    imItemCustom.setImageBitmap(bitmap);
                    BitMapToString(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e){
                        e.printStackTrace();
                    } catch (IOException e){
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else if (resultCode == 2){
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail = getResizedBitmap(thumbnail, 400);
                Log.w("path of image from gallery .....*****************...........", picturePath+ "");
                imItemCustom.setImageBitmap(thumbnail);
                BitMapToString(thumbnail);
            }
        }
    }

    public String BitMapToString(Bitmap itemImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        itemImage.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRation = (float) width / (float) height;
        if (bitmapRation > 1){
            width = maxSize;
            height = (int) (width / bitmapRation);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRation);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);

    }
}