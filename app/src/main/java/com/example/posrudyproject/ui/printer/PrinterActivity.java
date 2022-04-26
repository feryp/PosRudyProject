package com.example.posrudyproject.ui.printer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.posrudyproject.R;
import com.google.zxing.BarcodeFormat;
import com.imagpay.BTCommands;
import com.imagpay.BTListener;
import com.imagpay.BTPrinter;
import com.imagpay.BluetoothListener;
import com.imagpay.utils.BitmapUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

import androidx.core.app.ActivityCompat;

public class PrinterActivity extends Activity implements OnClickListener, AdapterView.OnItemClickListener {
    protected Button mBtnReconn;
    protected EditText mEtContent;
    protected RadioButton mRbChinese;
    protected RadioButton mRbJapanese;
    protected RadioButton mRbKorean;
    protected RadioButton mRbEnglish;
    protected RadioButton mRbThai;
    protected RadioGroup mRgLang;
    protected RadioButton mRbNormal;
    protected RadioButton mRbDoubleW;
    protected RadioButton mRbDoubleH;
    protected RadioButton mRbDouble;
    protected RadioGroup mRgSize;
    protected Button mBtPrint;
    protected Button mBtPic;
    protected Button mBtnScan;
    protected Button mBtTicket;
    protected Button mBtBarcode;
    protected Button mBtBox;
    protected Button mBtLabel;
    protected Button mBtLabelMulti;
    protected Button mBtLabelTear;
    protected Button mBtFile;
    protected Button mBtDebug;
    protected Button mBtPhoto;
    protected ImageView mIvTest;
    protected RadioButton mRbPaper1;
    protected RadioButton mRbPaper2;
    protected RadioButton mRbPaper3;
    protected FlowRadioGroup mRgPaperSize;
    protected Button mBtTextAsPic;
    protected Button mBtQRCode;
    protected Button mBtQrLabelMulti;
    protected RadioButton mRbOther;
    protected RadioButton mRbRussia;
    private ProgressDialog mProgressDialog;
    private Dialog mListDialog;
    private ListView mListPaired;
    private ListView mListSearched;
    private ListAdapter mAdapterPair;
    private ListAdapter mAdapterSearch;

    private static String encode = "GB2312";// default "UTF-8"
    private static final String ChCode = "GB2312";
    private static final String JaCode = "shift-jis";
    private static final String KoCode = "euc-kr";
    private static final String EnCode = "GBK";
    private static final String TlCode = "x-windows-874";// 泰文
    private static final String RusCode = "windows-1251";// 俄语

    String content = "请在编辑框中输入你要打印的内容！";
    String barcodeContent = "123456789";
    private String ChContent = "请在编辑框中输入你要打印的内容！";
    private String JaContent = "編集ボックスに印刷したい内容を入力してください";
    private String KoContent = "좀 상자에 입력한 내용을 너 인쇄할!";
    private String EnContent = "Please input you want to print in the edit box!";
    private String TlContent = "ทดสอบตัวอักษรนี้ที่อยู่ในมุ้ง ABCDEFGH";
    private String RusContent = "Пожалуйста, введите содержимое, которое вы хотите распечатать, в поле для редактирования.";

    private byte[] lang = BTCommands.LANG_DEFAULT;
    private byte[] size = BTCommands.DEFAULT_SIZE;
    int paperWidth = 350;
    int paperHeight = 120;
    BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;
    Bitmap mBitmap;
    private AlertDialog mLabelDialog;
    private AlertDialog mMultiLabelDialog;

    private BTPrinter mBTPrinter;
    private static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    private boolean isPrintImageOK = false;
    private boolean isConnecting = false;
    private boolean isQRLabel = true;
    private boolean isOtherLang = false;
    public static final int PERMISSION_BLUETOOTH = 1;
    public static final int PERMISSION_BLUETOOTH_ADMIN = 1;
    public static final int PERMISSION_BLUETOOTH_CONNECT = 1;
    public static final int PERMISSION_BLUETOOTH_SCAN = 1;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    Toast.makeText(PrinterActivity.this, (CharSequence) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case 100:
                    if (mProgressDialog != null)
                        mProgressDialog.dismiss();
                    break;
                case 101:
                    mProgressDialog = PublicUtil.getDialog(PrinterActivity.this, "", "printting......");
                    mProgressDialog.show();
                    break;
                case 102:
                    if (mProgressDialog != null)
                        if (!isPrintImageOK)
                            mProgressDialog.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_myprinter);

        initView();
        initSdk();
        initAdapter();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initAdapter() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_list, null);
        mListPaired = (ListView) view.findViewById(R.id.list_paired);
        mListSearched = (ListView) view.findViewById(R.id.list_searched);
        mBtnScan = (Button) view.findViewById(R.id.btn_scan);
        mBtnScan.setOnClickListener(PrinterActivity.this);

        mListDialog = new AlertDialog.Builder(this, THEME_HOLO_LIGHT)
                .setTitle(R.string.tip_select)
                .setView(view)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mBTPrinter.stopDiscovery();
                        mAdapterPair.clear();
                        mAdapterSearch.clear();
                    }
                })
                .create();

        mAdapterPair = new ListAdapter(this);
        mAdapterSearch = new ListAdapter(this);
        mListPaired.setAdapter(mAdapterPair);
        mListSearched.setAdapter(mAdapterSearch);
        mListPaired.setOnItemClickListener(this);
        mListSearched.setOnItemClickListener(this);
    }

    private void initSdk() {
        mBTPrinter =
                BTPrinter.getInstance()
                        .setContext(PrinterActivity.this)
                        .setBluetoothListener(new BluetoothListener() {
                            @Override
                            public boolean isReader(BluetoothDevice bluetoothDevice) {
                                mAdapterSearch.addDevice(bluetoothDevice);
                                return false;
                            }

                            @Override
                            public void startedConnect(BluetoothDevice bluetoothDevice) {
                                sendMsg("start discovery......");
                            }

                            @Override
                            public void connected(final BluetoothDevice bluetoothDevice) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setBarTitle(bluetoothDevice);
                                    }
                                });
                            }

                            @Override
                            public void disConnect() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setBarTitle(null);
                                    }
                                });
                            }

                            @Override
                            public void startedDiscovery() {
                                sendMsg("start discovery......");
                            }

                            @Override
                            public void finishedDiscovery() {
                                sendMsg("finish discovery......");
                                mListDialog.setTitle(R.string.tip_select);
                            }
                        })
                        .init();
        mBTPrinter.addListener(new BTListener() {
            @Override
            public void paresDate(String arg0) {
                Log.i("paresDate", "" + arg0);
                if (BTCommands.BITMAP_OK.equals(arg0)) {
                    isPrintImageOK = true;
                    sendMsg("Bitmap ok");
                    mBTPrinter.paperOut(25);
                    handler.sendEmptyMessage(100);
                }
            }
        });
        mBTPrinter.showLog(true);
    }

    private void initView() {
        mBtnReconn = (Button) findViewById(R.id.btn_reconn);
        mBtnReconn.setOnClickListener(PrinterActivity.this);
        mEtContent = (EditText) findViewById(R.id.et_content);
        mRbChinese = (RadioButton) findViewById(R.id.RbChinese);
        mRbJapanese = (RadioButton) findViewById(R.id.RbJapanese);
        mRbKorean = (RadioButton) findViewById(R.id.RbKorean);
        mRbEnglish = (RadioButton) findViewById(R.id.RbEnglish);
        mRbThai = (RadioButton) findViewById(R.id.RbThai);
        mRbRussia = (RadioButton) findViewById(R.id.RbRussia);
        mRbOther = (RadioButton) findViewById(R.id.RbOther);
        mRgLang = (RadioGroup) findViewById(R.id.rg_lang);
        mRbNormal = (RadioButton) findViewById(R.id.rb_normal);
        mRbDoubleW = (RadioButton) findViewById(R.id.rb_double_w);
        mRbDoubleH = (RadioButton) findViewById(R.id.rb_double_h);
        mRbDouble = (RadioButton) findViewById(R.id.rb_double);
        mRgSize = (RadioGroup) findViewById(R.id.rg_font_size);
        mBtPrint = (Button) findViewById(R.id.bt_print);
        mBtPrint.setOnClickListener(PrinterActivity.this);
        mBtPic = (Button) findViewById(R.id.bt_pic);
        mBtPic.setOnClickListener(PrinterActivity.this);
        mBtTicket = (Button) findViewById(R.id.bt_ticket);
        mBtTicket.setOnClickListener(PrinterActivity.this);
        mBtBarcode = (Button) findViewById(R.id.bt_barcode);
        mBtBarcode.setOnClickListener(PrinterActivity.this);
        mBtBox = (Button) findViewById(R.id.bt_box);
        mBtBox.setOnClickListener(PrinterActivity.this);
        mBtLabel = (Button) findViewById(R.id.bt_label);
        mBtLabel.setOnClickListener(PrinterActivity.this);
        mBtLabelMulti = (Button) findViewById(R.id.bt_bar_label_multi);
        mBtLabelMulti.setOnClickListener(PrinterActivity.this);
        mBtLabelTear = (Button) findViewById(R.id.bt_label_tear);
        mBtLabelTear.setOnClickListener(PrinterActivity.this);
        mBtFile = (Button) findViewById(R.id.bt_file);
        mBtFile.setOnClickListener(PrinterActivity.this);
        mBtDebug = (Button) findViewById(R.id.bt_debug);
        mBtDebug.setOnClickListener(PrinterActivity.this);
        mBtPhoto = (Button) findViewById(R.id.bt_photo);
        mBtPhoto.setOnClickListener(PrinterActivity.this);
        mIvTest = (ImageView) findViewById(R.id.iv_test);
        mRbPaper1 = (RadioButton) findViewById(R.id.rb_paper1);
        mRbPaper2 = (RadioButton) findViewById(R.id.rb_paper2);
        mRbPaper3 = (RadioButton) findViewById(R.id.rb_paper3);
        mRgPaperSize = (FlowRadioGroup) findViewById(R.id.rg_paper_size);
        mBtTextAsPic = (Button) findViewById(R.id.bt_text_as_pic);
        mBtTextAsPic.setOnClickListener(PrinterActivity.this);
        mBtQRCode = (Button) findViewById(R.id.bt_barcode_all);
        mBtQRCode.setOnClickListener(PrinterActivity.this);
        mBtQrLabelMulti = (Button) findViewById(R.id.bt_qr_label_multi);
        mBtQrLabelMulti.setOnClickListener(PrinterActivity.this);

        mRgLang.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mRbChinese.getId() == checkedId) {
                    encode = ChCode;
                    lang = BTCommands.LANG_DEFAULT;
                    content = ChContent;
                } else if (mRbJapanese.getId() == checkedId) {
                    encode = JaCode;
                    lang = BTCommands.LANG_JAPAN;
                    content = JaContent;
                } else if (mRbKorean.getId() == checkedId) {
                    encode = KoCode;
                    lang = BTCommands.LANG_KOREAN;
                    content = KoContent;
                } else if (mRbEnglish.getId() == checkedId) {
                    content = EnContent;
                    lang = BTCommands.LANG_DEFAULT;
                } else if (mRbThai.getId() == checkedId) {
                    content = TlContent;
                    encode = TlCode;
                    lang = BTCommands.LANG_THAI;
                } else if (mRbRussia.getId() == checkedId) {
                    content = RusContent;
                    encode = RusCode;
                    lang = BTCommands.LANG_RUSSIA;
                }
                if (mRbOther.getId() == checkedId) {
                    isOtherLang = true;
                    content = ChContent;
                } else {
                    isOtherLang = false;
                }
            }
        });

        mRgSize.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mRbNormal.getId() == checkedId) {
                    size = BTCommands.DEFAULT_SIZE;
                } else if (mRbDoubleW.getId() == checkedId) {
                    size = BTCommands.DOUBLE_WIDTH_SIZE;
                } else if (mRbDoubleH.getId() == checkedId) {
                    size = BTCommands.DOUBLE_HEIGHT_SIZE;
                } else if (mRbDouble.getId() == checkedId) {
                    size = BTCommands.DOUBLE_SIZE;
                }
            }
        });

        mRgPaperSize.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mRbPaper1.getId() == checkedId) {
                    paperWidth = 300;
                    paperHeight = 120;
                } else if (mRbPaper2.getId() == checkedId) {
                    paperWidth = 200;
                    paperHeight = 120;
                } else if (mRbPaper3.getId() == checkedId) {
                    paperWidth = 300;
                    paperHeight = 120;
                }
            }
        });

        initDialog();
    }

    private void initDialog() {
        final String[] items =
                {
                        getString(R.string.label_bar),
                        getString(R.string.label_qr)
                };
        mLabelDialog = new AlertDialog
                .Builder(PrinterActivity.this)
                .setTitle(R.string.print_barcode)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getEtContent();
                        Bitmap bitmap = null;

                        try {
                            if (which == 0) { // barcode
                                bitmap = BitmapUtils.createBarcode(barcodeContent, paperWidth, paperHeight, 16);
                            } else if (which == 1) { // qrcode
                                bitmap = BitmapUtils.createQRCode(barcodeContent, paperHeight + 40);
                            }
                            //PrinterUtils.saveImg2SDCard(bitmap, "/sdcard/", "123");
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PrinterActivity.this, "Error input", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        printLabel(bitmap);
                    }
                })
                .create();


        final View dialogView = LayoutInflater.from(PrinterActivity.this).inflate(R.layout.dialog_et, null);
        mMultiLabelDialog = new AlertDialog
                .Builder(PrinterActivity.this)
                .setTitle(R.string.label)
                .setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getEtContent();
                        Bitmap bitmap = null;

                        try {
                            if (isQRLabel) {
                                bitmap = BitmapUtils.createQRCode(barcodeContent, paperHeight + 40);
                            } else {
                                bitmap = BitmapUtils.createBarcode(barcodeContent, paperWidth, paperHeight, 16);
                            }
                            //PrinterUtils.saveImg2SDCard(bitmap, "/sdcard/", "123");
                            EditText etCount = (EditText) dialogView.findViewById(R.id.et_count);
                            int i = Integer.parseInt(etCount.getText().toString());
                            printMultipleLabel(bitmap, i);
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PrinterActivity.this, "Error input", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                })
                .create();

      /*  mBarLabelDialog = new AlertDialog
                .Builder(MainActivity.this)
                .setTitle(R.string.print_barcode)
                .setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getEtContent();
                        Bitmap bitmap = null;

                        try {
                            bitmap = BitmapUtils.createBarcode(barcodeContent, paperWidth, paperHeight, 16);
                            EditText etCount = (EditText) dialogView.findViewById(R.id.et_count);
                            int i = Integer.parseInt(etCount.getText().toString());
                            printMultipleLabel(bitmap, i);
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Error input", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                })
                .create();*/
    }

    @Override
    public void onClick(View view) {
        if (!mBTPrinter.isConnected() && view.getId() != R.id.btn_reconn && view.getId() != R.id.btn_scan) {
            Toast.makeText(this, "Plz connect bluetooth first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (view.getId() == R.id.btn_reconn) {
            showDeviceDialog();
        } else if (view.getId() == R.id.btn_scan) {
            searchDevice();
        } else if (view.getId() == R.id.bt_print) {
            print();
        } else if (view.getId() == R.id.bt_pic) {
            printPic();
        } else if (view.getId() == R.id.bt_ticket) {
            printTicket();
        } else if (view.getId() == R.id.bt_barcode) {
            printBarcode();
        } else if (view.getId() == R.id.bt_box) {
            testMoneyBox();
        } else if (view.getId() == R.id.bt_label) {
            showPrintLabelDialog();
        } else if (view.getId() == R.id.bt_bar_label_multi) {
            showBarLabelDialog();
        } else if (view.getId() == R.id.bt_label_tear) {
            labelToTear();
        } else if (view.getId() == R.id.bt_file) {
            goPhotoAlbum();
        } else if (view.getId() == R.id.bt_photo) {
            goCamera();
        } else if (view.getId() == R.id.bt_text_as_pic) {
            printContentAsPic();
        } else if (view.getId() == R.id.bt_barcode_all) {
            showListDialog();
        } else if (view.getId() == R.id.bt_qr_label_multi) {
            showQRLabelDialog();
        }
    }

    void testMoneyBox() {
        mBTPrinter.moneyBox();
    }

    void labelPaperOut() {
        mBTPrinter.labelPaperOut();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        connectDevice((ListAdapter) parent.getAdapter(), position);
    }

    /**
     * Show paired device
     */
    private void showDeviceDialog() {
        mAdapterPair.clear();
        mAdapterSearch.clear();
        if (mBTPrinter.isConnected()) {
            mBTPrinter.close();
        }

        mListDialog.show();
        mBtnScan.setVisibility(View.VISIBLE);
        mListDialog.setTitle(R.string.tip_select);
        List<BluetoothDevice> bondedDevices = mBTPrinter.getBondedDevices();
        for (BluetoothDevice device : bondedDevices) {
            mAdapterPair.addDevice(device);
        }
    }

    /**
     * Search adn add bluetooth device in isPrinter callback
     */
    private void searchDevice() {
        mBtnScan.setVisibility(View.GONE);
        mListDialog.setTitle(R.string.tip_scan);
        mBTPrinter.startDiscovery();
    }

    /**
     * Select item to connect
     */
    private void connectDevice(ListAdapter adapter, int position) {
        if (mBTPrinter != null) {
            mBTPrinter.stopDiscovery();
        }
        if (isConnecting) {
            Toast.makeText(this, R.string.tip_no_repeat, Toast.LENGTH_SHORT).show();
            return;
        }
        final BluetoothDevice dev = adapter.getDevice(position);
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if (dev != null) {
                    isConnecting = true;
                    boolean connect = mBTPrinter.connect(dev);
                    isConnecting = false;
                    if (connect) {
                        sendMsg("connected successful");
                        runOnUiThread(new Runnable() {
                            public void run() {
                                //lv.setVisibility(View.GONE);
                                mListDialog.dismiss();
                            }
                        });
                    } else {
                        sendMsg("connected failed");
                    }
                }
            }
        });
    }

    private void showPrintLabelDialog() {
        if (mLabelDialog != null)
            mLabelDialog.show();
    }

    private void showBarLabelDialog() {
        if (mMultiLabelDialog != null) {
            isQRLabel = false;
            mMultiLabelDialog.show();
        }
    }

    private void showQRLabelDialog() {
        if (mMultiLabelDialog != null) {
            isQRLabel = true;
            mMultiLabelDialog.show();
        }
    }

    private void printLabel(final Bitmap bitmap) {
        if (bitmap == null)
            return;
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mBTPrinter.labelBack();
                mBTPrinter.printLabelBitmap(bitmap);
                mBTPrinter.labelPaperOut();
                mBTPrinter.labelToTearing();
            }
        });
    }

    private void printMultipleLabel(final Bitmap bitmap, final int num) {
        if (bitmap == null)
            return;
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < num; i++) {
                    if (i == 0) {
                        //mBTPrinter.labelToTearing();
                        mBTPrinter.labelBack();
                    } else if (i % 5 == 0) {
                        mBTPrinter.labelToTearing();

                        mBTPrinter.labelBack((byte) 100);
                    }
                    mBTPrinter.printLabelBitmap(bitmap);
                    mBTPrinter.labelPaperOut();
                }
                mBTPrinter.labelToTearing();
            }
        });
    }

    private void labelToTear() {
        mBTPrinter.labelToTearing();
    }

    private void print() {
        if (isOtherLang) {
            printContent();
        } else {
            printContentAsPic();
        }
    }

    protected void printContent() {
        try {
            mBTPrinter.reset();
            getEtContent();
            try {
                mBTPrinter.write(lang);
                mBTPrinter.write(size);
                mBTPrinter.write(content.getBytes(encode));
                mBTPrinter.lineFeed();
                mBTPrinter.lineFeed();
                mBTPrinter.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void printContentAsPic() {
        try {
            mBTPrinter.reset();
            getEtContent();
            try {
                mBTPrinter.printBitmap(textAsBitmap(content, 25, Alignment.ALIGN_NORMAL));
                mBTPrinter.lineFeed();
                mBTPrinter.lineFeed();
                mBTPrinter.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getEtContent() {
        String printCon = mEtContent.getText().toString();
        if (!TextUtils.isEmpty(printCon)) {
            content = printCon;
            barcodeContent = printCon;
        } else {
            content = "请在编辑框中输入你要打印的内容！";
            barcodeContent = "123456789";
        }
    }

    protected void printTicket() {
        try {
            mBTPrinter.reset();
            mBTPrinter.write(BTCommands.LANG_DEFAULT);
            // mBTPrinter.write(BTCommands.ALIGN_CENTER);
            mBTPrinter.alignCenter();
            mBTPrinter.write(BTCommands.DOUBLE_HEIGHT_SIZE); // size *2
            mBTPrinter.println("POS Signed Order");
            // mBTPrinter.write(BTCommands.ALIGN_LEFT);
            mBTPrinter.alignLeft();
            mBTPrinter.write(BTCommands.DEFAULT_SIZE);// size normal
            mBTPrinter.println("The cardholder stub   \nPlease properly keep");
            mBTPrinter.println("--------------------------");
            mBTPrinter.println("Merchant Name:ABC");
            mBTPrinter.println("Merchant No.:846584000103052");
            mBTPrinter.println("Terminal No.:12345678");
            mBTPrinter.println("categories: visa card");
            mBTPrinter.println("Period of Validity:2016/07");
            mBTPrinter.println("Batch no.:000101");
            mBTPrinter.println("Card Number:");
            mBTPrinter.println("622202400******0269");
            mBTPrinter.println("Trade Type:consumption");
            mBTPrinter.println("Serial No.:000024  \nAuthenticode:096706");
            mBTPrinter.println("Date/Time:2016/09/01 11:27:12");
            mBTPrinter.println("Ref.No.:123456789012345");
            mBTPrinter.println("Amount:$ 100.00");
            mBTPrinter.println("--------------------------");
            mBTPrinter.lineFeed();
            mBTPrinter.lineFeed();
            mBTPrinter.lineFeed();
            mBTPrinter.lineFeed();
            mBTPrinter.lineFeed();
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendEmptyMessage(12);
        }
    }

    protected void printBarcode() {
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mBTPrinter.reset();
                // mBTPrinter.write(BTCommands.ALIGN_LEFT);
                mBTPrinter.alignLeft();
                mBTPrinter.write(BTCommands.DEFAULT_SIZE);
                mBTPrinter.println("Code128");
                mBTPrinter.printBarcode(new byte[]{0x7b, 0x42, 0x4e, 0x6f, 0x2e, 0x7b, 0x43, 12, 34, 56}, BTCommands.CODE128, 100);
                mBTPrinter.lineFeed();

                mBTPrinter.println("Code39");
                mBTPrinter.printBarcode("123456789", BTCommands.CODE39, 100);

                // Print qrcode
                mBTPrinter.lineFeed();
                mBTPrinter.reset();
                // mBTPrinter.write(BTCommands.ALIGN_LEFT);
                mBTPrinter.alignLeft();
                mBTPrinter.println("QRCode");
                // mBTPrinter.write(BTCommands.ALIGN_CENTER);
                mBTPrinter.alignCenter();
                mBTPrinter.printQRCode("QRCODE://ABCDEFGHIJK", 300, 300);

                mBTPrinter.lineFeed();
                mBTPrinter.lineFeed();
                mBTPrinter.lineFeed();
                mBTPrinter.lineFeed();
            }
        });
    }

    protected void printBarcodePic(final BarcodeFormat format) {
        getEtContent();
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Bitmap bitmap;
                    bitmap = BitmapUtils.createBarcode(barcodeContent, 350, 200, format, 16);
                    mBTPrinter.printBitmap(bitmap);
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PrinterActivity.this, "Error input", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void printPic() {
        //Print bitmap
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if (mBitmap != null) {
                    mBTPrinter.printBitmap(mBitmap);
                    return;
                }
                mBTPrinter.write(BTCommands.ALIGN_LEFT);
                mBTPrinter.println("Image");
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inPreferredConfig = Bitmap.Config.RGB_565;
                opt.inPurgeable = true;
                opt.inInputShareable = true;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qrcode, opt);
                mBTPrinter.write(BTCommands.ALIGN_CENTER);
                mBTPrinter.printBitmap(bitmap);
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                mBTPrinter.lineFeed();
                mBTPrinter.lineFeed();
                mBTPrinter.lineFeed();
                mBTPrinter.lineFeed();
            }
        });
    }

    private void sendMsg(String str) {
        Message msg = handler.obtainMessage();
        msg.what = 10;
        msg.obj = str;
        handler.sendMessage(msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setBarTitle(BluetoothDevice device) {
        if (mBTPrinter.isConnected()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            setTitle(getString(R.string.app_name) + "\t\t\t\tConnected: " + (device == null ? null : device.getName()));
        } else {
            setTitle(getString(R.string.app_name) + "\t\t\t\tNot connected");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBTPrinter.close();
    }

    public static Bitmap textAsBitmap(String text, float textSize, Alignment ali) {
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);
        StaticLayout layout = new StaticLayout(text, textPaint, 380, ali, 1.0f, 0.0f, true);
        Bitmap bitmap = Bitmap.createBitmap(layout.getWidth(), layout.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        layout.draw(canvas);
        // ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // bitmap.compress(CompressFormat.JPEG, 10, baos);
        // byte[] bytes = baos.toByteArray();
        // return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    private File cameraSavePath;//拍照照片路径
    private Uri uri;//照片uri
    private String photoPath;

    //激活相机操作
    private void goCamera() {
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        ////if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        ////    //第二个参数为 包名.fileprovider
        ////    uri = FileProvider.getUriForFile(MainActivity.this, "com.example.hxd.pictest.fileprovider", cameraSavePath);
        ////    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        ////} else {
        uri = Uri.fromFile(cameraSavePath);
        //}
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        PrinterActivity.this.startActivityForResult(intent, 1);
    }

    //激活相册操作
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //    photoPath = String.valueOf(cameraSavePath);
            //} else {
            photoPath = uri.getEncodedPath();
            //}
            Log.d("拍照返回图片路径:", photoPath);

            Glide.with(PrinterActivity.this).load(photoPath).into(mIvTest);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    mBitmap = PrinterUtils.compressBitmap(photoPath, 300, 200);
                }
            }).start();

        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            photoPath = PhotoAlbum.getRealPathFromUri(this, data.getData());
            Glide.with(PrinterActivity.this).load(photoPath).into(mIvTest);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    mBitmap = PrinterUtils.compressBitmap(photoPath, 300, 200);
                }
            }).start();
        }
    }


    private void showListDialog() {
        final String[] items =
                {
                        BarcodeFormat.AZTEC.name(),
                        BarcodeFormat.CODABAR.name(),
                        BarcodeFormat.CODE_39.name(),
                        BarcodeFormat.CODE_93.name(),
                        BarcodeFormat.CODE_128.name(),
                        BarcodeFormat.DATA_MATRIX.name(),
                        BarcodeFormat.EAN_8.name(),
                        BarcodeFormat.EAN_13.name(),
                        BarcodeFormat.ITF.name(),
                        BarcodeFormat.MAXICODE.name(),
                        BarcodeFormat.PDF_417.name(),
                        BarcodeFormat.QR_CODE.name(),
                        BarcodeFormat.RSS_14.name(),
                        BarcodeFormat.RSS_EXPANDED.name(),
                        BarcodeFormat.UPC_A.name(),
                        BarcodeFormat.UPC_E.name(),
                        BarcodeFormat.UPC_EAN_EXTENSION.name(),
                };
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(PrinterActivity.this);
        listDialog.setTitle(R.string.print_barcode);
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        printBarcodePic(BarcodeFormat.AZTEC);
                        break;
                    case 1:
                        printBarcodePic(BarcodeFormat.CODABAR);

                        break;
                    case 2:
                        printBarcodePic(BarcodeFormat.CODE_39);

                        break;
                    case 3:
                        printBarcodePic(BarcodeFormat.CODE_93);

                        break;
                    case 4:
                        printBarcodePic(BarcodeFormat.CODE_128);

                        break;
                    case 5:
                        printBarcodePic(BarcodeFormat.DATA_MATRIX);

                        break;
                    case 6:
                        printBarcodePic(BarcodeFormat.EAN_8);

                        break;
                    case 7:
                        printBarcodePic(BarcodeFormat.EAN_13);

                        break;
                    case 8:
                        printBarcodePic(BarcodeFormat.ITF);

                        break;
                    case 9:
                        printBarcodePic(BarcodeFormat.MAXICODE);

                        break;
                    case 10:
                        printBarcodePic(BarcodeFormat.PDF_417);

                        break;
                    case 11:
                        printBarcodePic(BarcodeFormat.QR_CODE);

                        break;
                    case 12:
                        printBarcodePic(BarcodeFormat.RSS_14);

                        break;
                    case 13:
                        printBarcodePic(BarcodeFormat.RSS_EXPANDED);

                        break;
                    case 14:
                        printBarcodePic(BarcodeFormat.UPC_A);

                        break;
                    case 15:
                        printBarcodePic(BarcodeFormat.UPC_E);

                        break;
                    case 16:
                        printBarcodePic(BarcodeFormat.UPC_EAN_EXTENSION);

                        break;
                }
            }
        });
        listDialog.show();
    }

}
