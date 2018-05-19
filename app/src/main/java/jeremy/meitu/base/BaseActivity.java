package jeremy.meitu.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import timber.log.Timber;

/**
 * Created by JIANGJIAN650 on 2018/5/19.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        Timber.tag(getClass().getSimpleName());
        initView(savedInstanceState);
    }

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int getContentView();

    protected void switchFragment(@IdRes int containerViewId, Fragment f) {
        FragmentManager fragmentM = getSupportFragmentManager();//管理对象
        FragmentTransaction trans = fragmentM.beginTransaction();//切换碎片
        trans.replace(containerViewId, f);
        trans.commit();
    }
}
