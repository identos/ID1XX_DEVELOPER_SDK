package com.identos.android.id100.demo.data;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.identos.android.id100.library.card.germanhealth.GermanHealthCard;

class GermanHealthCardFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final int POSITION_PERSONAL_DATA = 0;
    private static final int POSITION_INSURANCE_DATA = 1;

    private final GermanHealthCard germanHealthCard;

    GermanHealthCardFragmentPagerAdapter(FragmentManager fragmentManager, GermanHealthCard germanHealthCard) {
        super(fragmentManager);

        this.germanHealthCard = germanHealthCard;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POSITION_PERSONAL_DATA:
                return DataFragment.newInstance(DataUtils.createListFromData(germanHealthCard.getPersonalData()));

            case POSITION_INSURANCE_DATA:
                return DataFragment.newInstance(DataUtils.createListFromData(germanHealthCard.getInsuranceData()));

            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case POSITION_PERSONAL_DATA:
                return "Personal data";

            case POSITION_INSURANCE_DATA:
                return "Insurance data";

            default:
                return "";
        }
    }
}