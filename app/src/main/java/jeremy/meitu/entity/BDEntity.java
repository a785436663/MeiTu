package jeremy.meitu.entity;

import java.util.ArrayList;

import jeremy.baselib.http.HttpResult;

/**
 * Created by JIANGJIAN650 on 2018/5/19.
 */

public class BDEntity extends HttpResult {
    private String sort;
    private int totalNum;
    private int startIndex;
    private int returnNumber;
    private ArrayList<BDInfo> imgs;

    @Override
    public boolean isOk() {
        return true;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(int returnNumber) {
        this.returnNumber = returnNumber;
    }

    public ArrayList<BDInfo> getImgs() {
        for (int i = imgs.size() - 1; i >= 0; i--) {
            BDInfo info = imgs.get(i);
            if (info.isEmpty())
                imgs.remove(i);
        }
        return imgs;
    }

    public void setImgs(ArrayList<BDInfo> imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        return "BDEntity{" +
                "sort='" + sort + '\'' +
                ", totalNum=" + totalNum +
                ", startIndex=" + startIndex +
                ", returnNumber=" + returnNumber +
                ", imgs=" + imgs +
                '}';
    }
}
