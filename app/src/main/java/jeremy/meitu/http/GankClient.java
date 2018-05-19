package jeremy.meitu.http;

import jeremy.baselib.http.RetrofitClient;
import okhttp3.OkHttpClient;

/**
 * Created by JIANGJIAN650 on 2018/5/19.
 */

public class GankClient extends RetrofitClient<GankService> {

    public static final GankClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final GankClient INSTANCE = new GankClient();
    }

    private GankClient() {
        super();
    }

    @Override
    protected OkHttpClient.Builder addInterceptor(OkHttpClient.Builder builder) {
        return builder;
    }

    @Override
    protected String getBaseUrl() {
        return "http://gank.io/api/";
    }

    @Override
    protected Class<GankService> createService() {
        return GankService.class;
    }
}
