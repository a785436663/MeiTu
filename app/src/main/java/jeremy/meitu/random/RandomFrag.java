package jeremy.meitu.random;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;

import jeremy.meitu.R;
import jeremy.meitu.base.BaseFragment;
import jeremy.meitu.entity.GankEntity;
import jeremy.meitu.http.GankClient;
import jeremy.meitu.utils.Utils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by JIANGJIAN650 on 2018/5/19.
 */

public class RandomFrag extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout mSwipeRefreshWidget;
    RecyclerView mRecyclerView;
    StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    int lastVisibleItem;
    StaggeredAdapter adapter;

    @Override
    protected int setView() {
        return R.layout.frag_random;
    }

    @Override
    protected void init(View view) {
        mSwipeRefreshWidget = findViewById(view, R.id.swipe_refresh_widget);
        mRecyclerView = findViewById(view, R.id.recylerview);
        initRefresh();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {
        mSwipeRefreshWidget.setRefreshing(true);
        loadData();
    }

    private void initRefresh() {
        mSwipeRefreshWidget.setColorSchemeResources(R.color.color1, R.color.color2,
                R.color.color3, R.color.color4);
        mSwipeRefreshWidget.setOnRefreshListener(this);

        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {
                    mSwipeRefreshWidget.setRefreshing(true);
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    loadData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = Utils.getLastPosition(mStaggeredGridLayoutManager);
            }

        });

        mRecyclerView.setHasFixedSize(true);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new StaggeredAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    private void loadData() {
        Observable<GankEntity> o = GankClient.getInstance().getService().getRandom("福利", 10);
        o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GankEntity>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("GankEntity onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("GankEntity" + e);
                    }

                    @Override
                    public void onNext(GankEntity gankEntity) {
                        Timber.d(gankEntity + "");
                        adapter.addAllItem(gankEntity.getResults());
                        adapter.notifyDataSetChanged();
                        mSwipeRefreshWidget.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        loadData();
    }
}
