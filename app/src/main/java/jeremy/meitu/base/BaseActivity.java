package jeremy.meitu.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import jeremy.meitu.utils.ActivityUtils;
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
        ActivityUtils.add(this);
    }

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int getContentView();

    Fragment oldF;

    protected void switchFragment(@IdRes int containerViewId, Fragment f) {
        if (f == null) {
            return;
        }
        FragmentManager fragmentM = getSupportFragmentManager();//管理对象
        FragmentTransaction trans = fragmentM.beginTransaction();//切换碎片
        if (oldF != null) {
            trans.hide(oldF);
        }
        if (!f.isAdded()) {
            trans.add(containerViewId, f);
        }
        trans.show(f);
        oldF = f;
        trans.commit();
    }

    @Override
    protected void onDestroy() {
        ActivityUtils.remove(this);
        super.onDestroy();
    }

    protected void exit() {
        ActivityUtils.removeAll();
    }
}
