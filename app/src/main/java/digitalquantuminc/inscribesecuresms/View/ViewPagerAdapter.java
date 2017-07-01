package digitalquantuminc.inscribesecuresms.View;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bagus Hanindhito on 30/06/2017.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private final List<Presenter> mPresenterList = new ArrayList<>();

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return mPresenterList.size();
    }

    public void addView(Presenter presenter) {
        mPresenterList.add(presenter);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View currentView = mPresenterList.get(position).getView();
        return currentView;
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

}
