package com.identos.android.id100.demo;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.identos.android.id100.demo.data.DataActivity;
import com.identos.android.id100.demo.logging.LogActivity;
import com.identos.android.id100.demo.util.UsbReceiver;
import com.identos.android.id100.demo.util.Utils;
import com.identos.android.id100.library.card.Card;
import com.identos.android.id100.library.card.CardHelper;
import com.identos.android.id100.library.card.CardInfo;
import com.identos.android.id100.library.ccid.CcidReader;
import com.identos.android.id100.library.ccid.OnCardEventListener;
import com.identos.android.id100.library.ccid.UsbReader;
import com.identos.android.id100.library.logging.ConsoleLogger;
import com.identos.android.id100.library.logging.FileLogger;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements UsbReceiver.Callback {
    private UsbManager usbManager;

    private UsbReceiver usbReceiver;

    private final ArrayList<UsbReader> attachedReaders = new ArrayList<>();

    private final ArrayList<UsbDevice> devicesWithoutPermission = new ArrayList<>();

    private UsbReaderAdapter usbReaderAdapter;

    private FrameLayout contentLayout;

    private ConsoleLogger consoleLogger;

    private FileLogger fileLogger;

    private BottomSheetBehavior bottomSheetBehavior;

    private TextView cardTypeTextView;

    private TextView atrTextView;

    private Button readCardButton;

    private final OnCardEventListener onCardEventListener = new OnCardEventListener() {
        @Override
        public void onCardEvent(CcidReader reader, int event) {
            switch (event) {
                case CcidReader.CARD_EVENT_CARD_REMOVED:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hideBottomSheet();
                        }
                    });
                    break;

                case CcidReader.CARD_EVENT_CARD_INSERTED:
                case CcidReader.CARD_EVENT_CARD_PRESENT:
                    readCardInfo(reader);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        contentLayout = (FrameLayout) findViewById(R.id.content);
        cardTypeTextView = (TextView) findViewById(R.id.cardType);
        atrTextView = (TextView) findViewById(R.id.atr);
        readCardButton = (Button) findViewById(R.id.readCard);

        usbManager = (UsbManager) getSystemService(USB_SERVICE);

        usbReaderAdapter = new UsbReaderAdapter(MainActivity.this, attachedReaders);

        consoleLogger = Utils.createConsoleLogger();
        fileLogger = Utils.createFileLogger(this);

        for (UsbReader reader : UsbReader.getUsbReaders(usbManager)) {
            if (usbManager.hasPermission(reader.getDevice())) {
                reader.attachLogger(consoleLogger);
                reader.attachLogger(fileLogger);
                attachedReaders.add(reader);
            } else {
                devicesWithoutPermission.add(reader.getDevice());
            }
        }

        // ListView, displays the attached readers
        ListView listView = (ListView) findViewById(android.R.id.list);
        View emptyView = findViewById(android.R.id.empty);

        listView.setEmptyView(emptyView);

        listView.setAdapter(usbReaderAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               readCardInfo(attachedReaders.get(position));
            }
        });

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        bottomSheetBehavior.setPeekHeight(0);
        // BroadcastReceiver to receive usb events
        usbReceiver = new UsbReceiver(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(UsbReceiver.ACTION_USB_PERMISSION);

        registerReceiver(usbReceiver, filter);

        if (!devicesWithoutPermission.isEmpty()) {
            usbManager.requestPermission(devicesWithoutPermission.get(0), PendingIntent.getBroadcast(this, 0, new Intent(UsbReceiver.ACTION_USB_PERMISSION), 0));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Open each of the attached readers
        for (UsbReader reader : attachedReaders) {
            reader.addOnCardEventListener(onCardEventListener);

            try {
                reader.open(this);
            } catch (IOException e) {
                e.printStackTrace();
                Snackbar.make(contentLayout, R.string.sb_failed_to_open_reader, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        for (UsbReader reader : attachedReaders) {
            reader.removeOnCardEventListener(onCardEventListener);
            reader.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (usbReceiver != null) {
            unregisterReceiver(usbReceiver);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_log:
                startActivity(new Intent(MainActivity.this, LogActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDeviceAttached(UsbDevice device) {
        if (UsbReader.isSupportedReader(device)) {
            devicesWithoutPermission.add(device);

            if (devicesWithoutPermission.size() == 1) {
                usbManager.requestPermission(devicesWithoutPermission.get(0), PendingIntent.getBroadcast(this, 0, new Intent(UsbReceiver.ACTION_USB_PERMISSION), 0));
            }
        }
    }

    @Override
    public void onDeviceDetached(UsbDevice device) {
        // Check if the detached device is an attached reader
        for (int i = 0; i < attachedReaders.size(); i++) {
            if (attachedReaders.get(i).getDevice().equals(device)) {
                attachedReaders.get(i).detachLoggers();
                attachedReaders.remove(i--);
            }
        }

        usbReaderAdapter.notifyDataSetChanged();

        devicesWithoutPermission.remove(device);

        hideBottomSheet();
    }

    @Override
    public void onPermissionReceived(UsbDevice device, boolean granted) {
        if (granted) {
            devicesWithoutPermission.remove(device);

            UsbReader reader = new UsbReader(device);
            reader.addOnCardEventListener(onCardEventListener);
            reader.attachLogger(consoleLogger);
            reader.attachLogger(fileLogger);

            try {
                reader.open(this);
            } catch (IOException e) {
                Snackbar.make(contentLayout, R.string.sb_failed_to_open_reader, Snackbar.LENGTH_SHORT).show();
            }

            attachedReaders.add(reader);

            usbReaderAdapter.notifyDataSetChanged();
        } else {
            Snackbar.make(contentLayout, R.string.sb_permission_denied, Snackbar.LENGTH_SHORT).show();
        }

        if (!devicesWithoutPermission.isEmpty()) {
            usbManager.requestPermission(devicesWithoutPermission.get(0), PendingIntent.getBroadcast(this, 0, new Intent(UsbReceiver.ACTION_USB_PERMISSION), 0));
        }
    }

    private void readCardInfo(final CcidReader reader) {
        CardHelper helper = CardHelper.createCardHelper(this, reader, null);

        helper.readCardInfo(new CardHelper.ReadCardInfoAsyncListener() {
            @Override
            public void onCardInfoRead(CardInfo cardInfo) {
                showBottomSheet(reader, cardInfo);
            }

            @Override
            public void onError(int errorCode) {
                switch (errorCode) {
                    case CardHelper.CARD_ERROR_NO_CARD_PRESENT:
                        Snackbar.make(contentLayout, R.string.sb_no_card_present, Snackbar.LENGTH_SHORT).show();
                        break;

                    case CardHelper.CARD_ERROR_UNSUPPORTED_CARD:
                        Snackbar.make(contentLayout, R.string.sb_error_invalid_card, Snackbar.LENGTH_SHORT).show();
                        break;

                    default:
                        Snackbar.make(contentLayout, R.string.sb_error_card_info_default, Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void showBottomSheet(final CcidReader reader, final CardInfo info) {
        int type = info.cardType;

        switch (type) {
            case CardInfo.CARD_TYPE_GERMAN_HEALTH:
                cardTypeTextView.setText(R.string.card_type_german);
                readCardButton.setEnabled(true);
                break;

            case CardInfo.CARD_TYPE_AUSTRIAN_HEALTH:
                cardTypeTextView.setText(R.string.card_type_austrian);
                break;

            case CardInfo.CARD_TYPE_BELGIAN_EID:
                cardTypeTextView.setText(R.string.card_type_belgian);
                readCardButton.setEnabled(true);
                break;

            case CardInfo.CARD_TYPE_UNKNOWN:
            default:
                cardTypeTextView.setText(R.string.card_type_unknown);
        }

        atrTextView.setText(com.identos.android.id100.library.Utils.toHexString(info.atr.getBytes()));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        readCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readCard(reader, info);
            }
        });
    }

    private void hideBottomSheet() {
        readCardButton.setEnabled(false);
        readCardButton.setOnClickListener(null);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void readCard(final CcidReader reader, CardInfo info) {
        hideBottomSheet();

        CardHelper cardHelper = CardHelper.createCardHelper(this, reader, info);

        if(cardHelper == null) {
            return;
        }

        cardHelper.readCard(new CardHelper.ReadCardAsyncListener() {
            @Override
            public void onCardRead(Card card) {
                Intent dataActivityIntent = new Intent(MainActivity.this, DataActivity.class);
                dataActivityIntent.putExtra(DataActivity.EXTRA_READER, reader);
                dataActivityIntent.putExtra(DataActivity.EXTRA_CARD, card);

                startActivity(dataActivityIntent);
            }

            @Override
            public void onError(int errorCode) {
                switch (errorCode) {
                    case CardHelper.CARD_ERROR_NO_CARD_PRESENT:
                        Snackbar.make(contentLayout, R.string.sb_no_card_present, Snackbar.LENGTH_SHORT).show();
                        break;

                    case CardHelper.CARD_ERROR_UNSUPPORTED_CARD:
                        Snackbar.make(contentLayout, R.string.sb_error_invalid_card, Snackbar.LENGTH_SHORT).show();
                        break;

                    default:
                        Snackbar.make(contentLayout, R.string.sb_error_card_data_default, Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}