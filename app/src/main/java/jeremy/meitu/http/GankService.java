package jeremy.meitu.http;


import jeremy.meitu.entity.GankEntity;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by JIANGJIAN650 on 2018/5/19.
 */

public interface GankService {
    @GET("random/data/{type}/{num}")
    Observable<GankEntity> getRandom(@Path("type") String type, @Path("num") int num);

    @GET("data/{type}/{size}/{page}")
    Observable<GankEntity> getPic(@Path("type") String type, @Path("page") int page, @Path("size") int size);
}
