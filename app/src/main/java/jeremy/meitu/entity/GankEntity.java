package jeremy.meitu.entity;

import java.util.ArrayList;

import jeremy.baselib.http.HttpResult;

/**
 * Created by JIANGJIAN650 on 2018/5/19.
 */

public class GankEntity extends HttpResult {
    private boolean error;
    private ArrayList<GankInfo> results;

    @Override
    public boolean isOk() {
        return !error;
    }

    public ArrayList<GankInfo> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "GankEntity{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
