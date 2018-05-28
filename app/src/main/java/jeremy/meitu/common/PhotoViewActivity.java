package jeremy.meitu.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import jeremy.meitu.R;
import jeremy.meitu.base.BaseActivity;
import jeremy.meitu.entity.CollectionInfo;
import jeremy.meitu.entity.CollectionInfoEasyDao;
import jeremy.meitu.widget.MyPhotoView;

public class PhotoViewActivity extends BaseActivity {
    private final static String KEY_URL = "url";

    public static void start(Context context, String url) {
        context.startActivity(new Intent(context, PhotoViewActivity.class)
                .putExtra(KEY_URL, url));
    }

    int mW;
    int mH;
    String url;
    boolean isLoad = false;

    @Override
    protected void initView(Bundle savedInstanceState) {
        initToolBar();
        MyPhotoView photoView = (MyPhotoView) findViewById(R.id.photo_view);
        url = getIntent().getStringExtra(KEY_URL);
        photoView.setOnSizeListener(new MyPhotoView.OnSizeListener() {
            @Override
            public void onSize(int w, int h) {
                mW = w;
                mH = h;
                isLoad = true;
            }
        });
        photoView.setImageUri(url, null);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_photoview;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photo, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem action_collcetion = menu.findItem(R.id.action_collcetion);
        long urlCount = CollectionInfoEasyDao.getIns().count("url=?", url);
        action_collcetion.setTitle(urlCount > 0 ? "取消收藏" : "收藏");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_collcetion) {
            if ("收藏".equals(item.getTitle()))
                CollectionInfoEasyDao.getIns().save(new CollectionInfo(url, mW, mH, System.currentTimeMillis()));
            else
                CollectionInfoEasyDao.getIns().delete("url=?", url);
            supportInvalidateOptionsMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
