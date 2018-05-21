package jeremy.meitu.entity;

import android.text.TextUtils;

/**
 * Created by JIANGJIAN650 on 2018/5/19.
 */

public class BDInfo {
    private String title;
    private String imageUrl;
    private int imageWidth;
    private int imageHeight;
    private String date;

    public boolean isEmpty() {
        return TextUtils.isEmpty(imageUrl) || "null".equals(imageUrl) || "NULL".equals(imageUrl);
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "BDInfo{" +
                "title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageWidth=" + imageWidth +
                ", imageHeight=" + imageHeight +
                ", date='" + date + '\'' +
                '}';
    }
}
