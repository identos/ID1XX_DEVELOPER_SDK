package com.identos.android.id100.demo.data;

import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.identos.android.id100.demo.R;
import com.identos.android.id100.demo.util.Utils;
import com.identos.android.id100.demo.util.UsbReceiver;
import com.identos.android.id100.library.card.Card;
import com.identos.android.id100.library.card.belgiumeid.BelgianCard;
import com.identos.android.id100.library.card.germanhealth.GermanHealthCard;
import com.identos.android.id100.library.ccid.CcidReader;
import com.identos.android.id100.library.ccid.OnCardEventListener;
import com.identos.android.id100.library.ccid.UsbReader;
import com.identos.android.id100.library.logging.ConsoleLogger;
import com.identos.android.id100.library.logging.FileLogger;

import java.io.IOException;

public class DataActivity extends AppCompatActivity implements UsbReceiver.Callback {
    public static final String EXTRA_READER = "extra:reader";
    public static final String EXTRA_CARD = "extra:card";

    private UsbReader usbReader;

    private UsbReceiver usbReceiver;

    private final OnCardEventListener onCardEventListener = new OnCardEventListener() {
        @Override
        public void onCardEvent(CcidReader reader, int event) {
            if (event == CcidReader.CARD_EVENT_CARD_REMOVED) {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Card card = getIntent().getParcelableExtra(EXTRA_CARD);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        if(card instanceof GermanHealthCard) {
            viewPager.setAdapter(new GermanHealthCardFragmentPagerAdapter(getSupportFragmentManager(), (GermanHealthCard) card));
        } else if(card instanceof BelgianCard) {
            viewPager.setAdapter(new BelgianCardFragmentPagerAdapter(getSupportFragmentManager(), (BelgianCard) card));
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        usbReader = getIntent().getParcelableExtra(EXTRA_READER);

        if (usbReader == null) {
            finish();
            return;
        }

        ConsoleLogger consoleLogger = Utils.createConsoleLogger();
        FileLogger fileLogger = Utils.createFileLogger(this);

        usbReader.attachLogger(consoleLogger);
        usbReader.attachLogger(fileLogger);

        getSupportActionBar().setTitle(String.format(getString(R.string.title_activity_card_in_reader), Utils.getUsbDeviceName(usbReader.getDevice())));

        usbReceiver = new UsbReceiver(this);

        registerReceiver(usbReceiver, new IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (usbReader != null) {
            usbReader.addOnCardEventListener(onCardEventListener);

            try {
                usbReader.open(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (usbReader != null) {
            usbReader.removeOnCardEventListener(onCardEventListener);
            usbReader.close();
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
    public void onDeviceAttached(UsbDevice device) {
        // not needed
    }

    @Override
    public void onDeviceDetached(UsbDevice device) {
        if (usbReader != null && usbReader.getDevice().equals(device)) {
            finish();
        }
    }

    @Override
    public void onPermissionReceived(UsbDevice device, boolean granted) {
        // not needed
    }
}