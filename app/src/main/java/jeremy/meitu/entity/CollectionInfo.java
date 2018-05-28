package jeremy.meitu.entity;

import jeremy.easylite.annotation.EasyColumn;
import jeremy.easylite.annotation.EasyTable;

/**
 * Created by JIANGJIAN650 on 2018/5/22.
 */
@EasyTable(name = "db_collection")
public class CollectionInfo {
    @EasyColumn(name = "URL",unique = true)
    private String url;
    @EasyColumn(name = "WIDTH")
    private int w;
    @EasyColumn(name = "HEIGHT")
    private int h;
    @EasyColumn(name = "CREATETIME")
    private long createTime;
    @EasyColumn(name = "boolean")
    private boolean boo;

    public CollectionInfo() {
    }

    public CollectionInfo(String url, int w, int h, long createTime) {
        this.url = url;
        this.w = w;
        this.h = h;
        this.createTime = createTime;
    }

    public boolean isBoo() {
        return boo;
    }

    public void setBoo(boolean boo) {
        this.boo = boo;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CollectionInfo{" +
                "url='" + url + '\'' +
                ", w=" + w +
                ", h=" + h +
                ", createTime=" + createTime +
                ", boo=" + boo +
                '}';
    }
}
