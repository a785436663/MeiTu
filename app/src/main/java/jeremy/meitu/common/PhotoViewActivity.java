package jeremy.meitu.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import jeremy.meitu.R;
import jeremy.meitu.base.BaseActivity;
import jeremy.meitu.widget.MyPhotoView;

public class PhotoViewActivity extends BaseActivity {
    private final static String KEY_URL = "url";

    public static void start(Context context, String url) {
        context.startActivity(new Intent(context, PhotoViewActivity.class)
                .putExtra(KEY_URL, url));
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        MyPhotoView photoView = (MyPhotoView) findViewById(R.id.photo_view);
        String url = getIntent().getStringExtra(KEY_URL);
        photoView.setImageUri(url,null);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_photoview;
    }
}
