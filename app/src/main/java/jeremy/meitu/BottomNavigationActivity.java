package jeremy.meitu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import jeremy.meitu.base.BaseActivity;
import jeremy.meitu.classify.BDMainClassifyFram;
import jeremy.meitu.classify.BDSortFrag;
import jeremy.meitu.random.RandomFrag;

public class BottomNavigationActivity extends BaseActivity {
    RandomFrag randomFrag;
    BDMainClassifyFram mainFrag;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_random:
                    switchFragment(R.id.fl_root, randomFrag);
                    return true;
                case R.id.navigation_sort:
                    switchFragment(R.id.fl_root, mainFrag);
                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };


    @Override
    protected void initView(Bundle savedInstanceState) {
        randomFrag = new RandomFrag();
        mainFrag = new BDMainClassifyFram();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        switchFragment(R.id.fl_root, randomFrag);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_bottom_navigation;
    }

}
