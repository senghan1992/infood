package infofood.senghan1992.com.infofood.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import infofood.senghan1992.com.infofood.fragments.HomeFragment;
import infofood.senghan1992.com.infofood.fragments.RecipeFragment;
import infofood.senghan1992.com.infofood.fragments.SearchFragment;
import infofood.senghan1992.com.infofood.fragments.SettingsFragment;

public class HomeActivityAdapter extends FragmentStatePagerAdapter {

    int MAX_PAGE = 4;
    Fragment cur_fragment = new Fragment();

    public HomeActivityAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position < 0 || MAX_PAGE < position)
            position = 0;

        switch (position) {
            case 0:
                cur_fragment = new HomeFragment().create(position);
                break;
            case 1:
                cur_fragment = new RecipeFragment().create(position);
                break;
            case 2:
                cur_fragment = new SearchFragment().create(position);
                break;
            case 3:
                cur_fragment = new SettingsFragment().create(position);
                break;
        }

        //return new HomeFragment().create(position);
        return cur_fragment;
    }

    @Override
    public int getCount() {
        return MAX_PAGE;
    }

}//adapter_viewpager