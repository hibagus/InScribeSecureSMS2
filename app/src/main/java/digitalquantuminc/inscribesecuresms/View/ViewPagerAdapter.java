package digitalquantuminc.inscribesecuresms.View;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bagus Hanindhito on 30/06/2017.
 * This class implement ViewPagerAdapter to be used together with TabLayout
 * Please set-up the Tab Item Title on the getPageTitle Override Method.
 * The sequence of the page title must be the same as the sequence on how the children activity are added into the ViewPager
 */

public class ViewPagerAdapter extends PagerAdapter {
    // region Global Variable
    private final List<Presenter> mPresenterList = new ArrayList<>();

    //endregion
    //region Override Method
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return mPresenterList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        return mPresenterList.get(position).getView();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Conversation";
            case 1:
                return "Compose";
            case 2:
                return "Contacts";
            case 3:
                return "Session";
            case 4:
                return "Profile";
            case 5:
                return "About";
        }
        return null;
    }

    //endregion
    //region Method
    public void addView(Presenter presenter) {
        mPresenterList.add(presenter);
    }
    //endregion
}
