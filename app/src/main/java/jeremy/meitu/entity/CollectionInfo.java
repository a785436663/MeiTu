package jeremy.meitu.entity;

/**
 * Created by JIANGJIAN650 on 2018/5/22.
 */

public class CollectionInfo {
    private String url;
    private int w;
    private int h;
    private String createTime;

    public CollectionInfo(String url, int w, int h, String createTime) {
        this.url = url;
        this.w = w;
        this.h = h;
        this.createTime = createTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
