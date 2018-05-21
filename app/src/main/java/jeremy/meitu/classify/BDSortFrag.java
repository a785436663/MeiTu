package jeremy.meitu.classify;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import jeremy.meitu.R;
import jeremy.meitu.base.BaseFragment;
import jeremy.meitu.entity.BDEntity;
import jeremy.meitu.http.BDClient;
import jeremy.meitu.utils.Utils;
import rx.Subscriber;
import timber.log.Timber;

/**
 * Created by JIANGJIAN650 on 2018/5/21.
 */
public class BDSortFrag extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TITLE_TAB = "tabTitle";
    public static final String TITLE_TAG = "tag";

    public static BDSortFrag newInstance(String tabTitle, String tag) {
        Bundle args = new Bundle();
        BDSortFrag fragment = new BDSortFrag();
        args.putString(TITLE_TAB, tabTitle);
        args.putString(TITLE_TAG, tag);
        fragment.setArguments(args);
        return fragment;
    }

    SwipeRefreshLayout mSwipeRefreshWidget;
    RecyclerView mRecyclerView;
    StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    int lastVisibleItem;
    BDStaggeredAdapter adapter;

    String tag;
    String tabTitle;
    int mPage = 0;
    int mSize = 10;

    @Override
    protected int setView() {
        return R.layout.frag_bd_tag;
    }

    @Override
    protected void init(View view) {
        mSwipeRefreshWidget = findViewById(view, R.id.bd_swipe_refresh_widget);
        mRecyclerView = findViewById(view, R.id.bd_recylerview);
        initRefresh();
        Timber.e("init:" + (mSwipeRefreshWidget));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {
        if (getArguments() != null) {
            tabTitle = getArguments().getString(TITLE_TAB);
            tag = getArguments().getString(TITLE_TAG);
        }

        Timber.e("mSwipeRefreshWidget:" + (mSwipeRefreshWidget));
        mSwipeRefreshWidget.setRefreshing(true);
        loadData(mPage = 0, mSize);
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
                    loadData(++mPage, mSize);
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

        adapter = new BDStaggeredAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    private void loadData(final int page, int size) {
        if (TextUtils.isEmpty(tabTitle)) {
            mSwipeRefreshWidget.setRefreshing(false);
            return;
        }
        if (TextUtils.isEmpty(tag))
            tag = "全部";
        BDClient.getInstance().getImages(tabTitle, tag, page * size, size).subscribe(new Subscriber<BDEntity>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e + "");
                mSwipeRefreshWidget.setRefreshing(false);
            }

            @Override
            public void onNext(BDEntity bdEntity) {
                Timber.d(bdEntity + "");
                if (page == 0)
                    adapter.clear();
                adapter.addAllItem(bdEntity.getImgs());
                adapter.notifyDataSetChanged();
                mSwipeRefreshWidget.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        loadData(mPage = 0, mSize);
    }
}
