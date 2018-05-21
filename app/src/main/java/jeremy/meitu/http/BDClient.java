package jeremy.meitu.http;

import jeremy.baselib.http.RetrofitClient;
import jeremy.meitu.entity.BDEntity;
import okhttp3.OkHttpClient;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JIANGJIAN650 on 2018/5/19.
 */

public class BDClient extends RetrofitClient<BDService> {
    public static final BDClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final BDClient INSTANCE = new BDClient();
    }

    private BDClient() {
        super();
    }

    @Override
    protected OkHttpClient.Builder addInterceptor(OkHttpClient.Builder builder) {
        return builder;
    }

    @Override
    protected String getBaseUrl() {
        return "http://image.baidu.com/";
    }

    @Override
    protected Class<BDService> createService() {
        return BDService.class;
    }

    public Observable<BDEntity> getImages(String col, String tag, int page, int size) {
        return getService().getImages(col, tag, 0, page, size, "channel", 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
