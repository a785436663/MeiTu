package jeremy.meitu.random;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jeremy.meitu.R;
import jeremy.meitu.common.PhotoViewActivity;
import jeremy.meitu.entity.GankInfo;
import timber.log.Timber;

public class StaggeredAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<GankInfo> results;

    private static final int TYPE_ITEM = 0;
    private int screenWidth;
    private Random random;

    public List<GankInfo> getList() {
        return results;
    }

    public StaggeredAdapter() {
        random = new Random();
        results = new ArrayList<GankInfo>();
        screenWidth = ScreenUtils.getScreenWidth();
    }

    // RecyclerView的count设置为数据总条数+ 1（footerView）
    @Override
    public int getItemCount() {
        return results.size();
    }

    public void addItem(GankInfo info) {
        if (info == null)
            return;
        results.add(info);
    }

    public void addAllItem(ArrayList<GankInfo> infos) {
        if (infos == null || infos.size() == 0)
            return;
        results.addAll(infos);
    }

    public void clear() {
        results.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            SimpleDraweeView img = ((ItemViewHolder) holder).img;
            ViewGroup.LayoutParams params = img.getLayoutParams();
            //设置图片的相对于屏幕的宽高比
            params.width = screenWidth / 2;
            img.setAspectRatio((float) (position % 5 + 5) / 10f);
            img.setLayoutParams(params);
            String url = results.get(position).getUrl() + "?imageView2/0/w/"+(screenWidth / 2);
            final String bigUrl = results.get(position).getUrl() + "?imageView2/0/w/"+(screenWidth);
            Uri uri = Uri.parse(url);
            downLoadImage(img, uri);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotoViewActivity.start(v.getContext(),bigUrl);
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_picture, parent, false);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ItemViewHolder(view);
        }
        return null;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView img;

        public ItemViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.img);
        }
    }

    private void downLoadImage(SimpleDraweeView mSimpleDraweeView, Uri uri) {

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(new MyControllerListener(mSimpleDraweeView))
                .setUri(uri)
                .build();
        mSimpleDraweeView.setController(controller);
    }

    class MyControllerListener extends BaseControllerListener<ImageInfo> {
        SimpleDraweeView mSimpleDraweeView;

        public MyControllerListener(SimpleDraweeView mSimpleDraweeView) {
            this.mSimpleDraweeView = mSimpleDraweeView;
        }

        @Override
        public void onFinalImageSet(
                String id,
                @Nullable ImageInfo imageInfo,
                @Nullable Animatable anim) {
            if (imageInfo == null) {
                return;
            }
            QualityInfo qualityInfo = imageInfo.getQualityInfo();

            int height = imageInfo.getHeight();
            int width = imageInfo.getWidth();
        }

        @Override
        public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
        }
    }
}