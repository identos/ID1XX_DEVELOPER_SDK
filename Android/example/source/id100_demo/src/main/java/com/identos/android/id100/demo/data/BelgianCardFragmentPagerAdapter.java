package com.identos.android.id100.demo.data;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.identos.android.id100.library.card.belgiumeid.BelgianCard;

class BelgianCardFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final int POSITION_PERSONAL_DATA = 0;
    private static final int POSITION_ADDRESS = 1;

    private BelgianCard belgianCard;

    public BelgianCardFragmentPagerAdapter(FragmentManager fm, BelgianCard belgianCard) {
        super(fm);

        this.belgianCard = belgianCard;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POSITION_PERSONAL_DATA:
                return DataFragment.newInstance(DataUtils.createListFromData(belgianCard.getPersonalData()));

            case POSITION_ADDRESS:
                return DataFragment.newInstance(DataUtils.createListFromData(belgianCard.getAddress()));

            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case POSITION_PERSONAL_DATA:
                return "Personal data";

            case POSITION_ADDRESS:
                return "Address";

            default:
                return "";
        }
    }
}