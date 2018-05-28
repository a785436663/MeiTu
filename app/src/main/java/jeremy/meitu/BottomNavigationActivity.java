package jeremy.meitu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import jeremy.easylite.api.EasyDatabaseUtil;
import jeremy.easylite.api.utils.LogUtils;
import jeremy.meitu.base.BaseActivity;
import jeremy.meitu.classify.BDMainClassifyFram;
import jeremy.meitu.collection.CollectionFrag;
import jeremy.meitu.random.RandomFrag;

public class BottomNavigationActivity extends BaseActivity {
    RandomFrag randomFrag;
    BDMainClassifyFram mainFrag;
    CollectionFrag collectionFrag;
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
                case R.id.navigation_collection:
                    switchFragment(R.id.fl_root, collectionFrag);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void initView(Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        randomFrag = new RandomFrag();
        mainFrag = new BDMainClassifyFram();
        collectionFrag = new CollectionFrag();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        switchFragment(R.id.fl_root, randomFrag);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_bottom_navigation;
    }

}
