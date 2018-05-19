package jeremy.baselib.http;

import android.content.Context;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by JIANGJIAN650 on 2018/5/18.
 */

public abstract class RetrofitClient<T> {
    private static final long DEFAULT_TIME_OUT = 20;

    protected Retrofit mRetrofit;
    //RXjava
    protected Map<String, CompositeSubscription> mSubscriptionsMap = new HashMap<>();
    private T mService = null;

    public RetrofitClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);


        addInterceptor(builder);

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                //添加返回值为observable<T>的支持 添加之后ArtistService中返回值就变成了Observable
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //增加返回值为Gson的支持
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getBaseUrl())
                .build();
        mService = mRetrofit.create(createService());
    }

    protected abstract OkHttpClient.Builder addInterceptor(OkHttpClient.Builder builder);

    protected abstract String getBaseUrl();

    protected abstract Class<T> createService();

    public T getService() {
        return mService;
    }

    public void unsubscribe(String tag) {
        if (mSubscriptionsMap.containsKey(tag)) {
            CompositeSubscription subscriptions = mSubscriptionsMap.get(tag);
            subscriptions.unsubscribe();
            mSubscriptionsMap.remove(tag);
        }
    }

    protected void addSubscription(String tag, Subscription subscription) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        CompositeSubscription subscriptions;
        if (mSubscriptionsMap.containsKey(tag)) {
            subscriptions = mSubscriptionsMap.get(tag);
        } else {
            subscriptions = new CompositeSubscription();
        }
        subscriptions.add(subscription);
        mSubscriptionsMap.put(tag, subscriptions);
    }
}
