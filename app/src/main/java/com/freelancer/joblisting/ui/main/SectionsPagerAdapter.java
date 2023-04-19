package com.freelancer.joblisting.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.freelancer.FieldFormFragment;
import com.freelancer.FinalizeJobListing;
import com.freelancer.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 *
 * @TODO: ViewPager and FragmentPagerAdapter are deprecated. Migrate to ViewPager2 and FragmentStateAdapter.
 * <p>
 * Contributors: Zaroon Iqbal, Spencer Carlson
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    /**
     * this method is used to determine which fragment should be displayed on each of the tabbed views
     * Contributors: Zaroon Iqbal
     */
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        FieldFormFragment fieldFormFragment = FieldFormFragment.newInstance();
        FinalizeJobListing finalizeJobListing = FinalizeJobListing.newInstance();
        JobInfoFragment job = new JobInfoFragment();//general listing information fragment

        switch (position) {
            case 0:
                return job;
            case 1:
                return fieldFormFragment;
            case 2:
            default:
                return finalizeJobListing;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }
}