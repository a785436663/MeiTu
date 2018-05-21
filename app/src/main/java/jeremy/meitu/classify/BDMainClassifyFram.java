package jeremy.meitu.classify;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jeremy.meitu.R;
import jeremy.meitu.base.BaseFragment;

/**
 * Created by JIANGJIAN650 on 2018/5/21.
 */

public class BDMainClassifyFram extends BaseFragment {
    private TabLayout tl;
    private ViewPager vp;
    //当标签数目小于等于4个时，标签栏不可滑动
    public static final int MOVABLE_COUNT = 4;
    private String[] tabs = new String[]{"美女", "壁纸", "明星", "搞笑", "动漫", "宠物"};
    private List<Fragment> fragments;

    @Override
    protected int setView() {
        return R.layout.frag_bd_main;
    }

    @Override
    protected void init(View view) {
        tl = findViewById(view, R.id.tl);
        vp = findViewById(view, R.id.vp);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initDatas();
        initViewPager();
        initTabLayout();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {
    }

    private void initTabLayout() {
        //MODE_FIXED标签栏不可滑动，各个标签会平分屏幕的宽度
        tl.setTabMode(tabs.length <= MOVABLE_COUNT ? TabLayout.MODE_FIXED : TabLayout.MODE_SCROLLABLE);
        //指示条的颜色
        tl.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.holo_blue_dark));
        tl.setSelectedTabIndicatorHeight((int) getResources().getDimension(R.dimen.indicatorHeight));
        //关联tabLayout和ViewPager,两者的选择和滑动状态会相互影响
        tl.setupWithViewPager(vp);
        //自定义标签布局
        for (int i = 0; i < tabs.length; i++) {
            TabLayout.Tab tab = tl.getTabAt(i);
            TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.tabview_main, tl, false);
            tv.setText(tabs[i]);
            tab.setCustomView(tv);
        }
    }

    private void initViewPager() {
        vp.setAdapter(new MyPagerAdapter(getFragmentManager()));
    }

    private void initDatas() {
        fragments = new ArrayList<>();
        for (int i = 0; i < tabs.length; i++) {
            fragments.add(BDSortFrag.newInstance(tabs[i]));
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        /**
         * 如果不是自定义标签布局，需要重写该方法
         */
//        @Nullable
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return tabs.get(position);
//        }
    }
}
