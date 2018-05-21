package jeremy.meitu.http;

import jeremy.meitu.entity.BDEntity;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by JIANGJIAN650 on 2018/5/19.
 */
public interface BDService {
//    百度图片有分类图片api和搜索api
//    百度图片分类图片api
//    以GET形式提交，返回JSON
//    URL：http://image.baidu.com/data/imgs?col=&tag=&sort=&pn=&rn=&p=channel&from=1
//    参数：col=大类&tag=分类&sort=0&pn=开始条数&rn=显示数量&p=channel&from=1
//    PS：sort可以为0和1，作用。。未知
//    例子：http://image.baidu.com/data/imgs?col=美女&tag=小清新&sort=0&pn=10&rn=10&p=channel&from=1

    /**
     * @param col  美女  壁纸  动漫 搞笑
     * @param tag  全部 美腿 清纯等
     * @param sort 0
     * @param pn   开始条目
     * @param rn   显示数量
     * @param p    channel
     * @param from 1
     * @return
     */
    @GET("data/imgs")
    Observable<BDEntity> getImages(@Query("col") String col, @Query("tag") String tag, @Query("sort") int sort, @Query("pn") int pn, @Query("rn") int rn, @Query("p") String p, @Query("from") int from);


//    百度图片搜索图片api
//    以GET形式提交，返回JSON
//    URL：http://image.baidu.com/i?tn=baiduimagejson&word=&pn=&rn=&ie=utf8
//    参数：word=关键字&pn=开始条数&rn=显示数量
//    PS：ie=utf8 是字符编码，但是！有时候是gb18030，所以看情况而定转码
//    例子：https://image.baidu.com/search/acjson?tn=resultjson_com&catename=pcindexhot&ipn=rj&ct=201326592&is=&fp=result&queryWord=&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-1&z=&ic=0&word=刘德华&face=0&istype=2&qc=&nc=1&fr=&pn=0&rn=30

    /**
     * @param tn   resultjson_com
     * @param ipn  rj
     * @param word 关键字
     * @param pn   开始条目
     * @param rn   显示数量
     * @param ie   utf8  gb18030
     * @return
     */
    @Deprecated
    @GET("search/acjson")
    Observable<BDEntity> search(@Query("tn") String tn, @Query("ipn") String ipn, @Query("word") String word, @Query("pn") int pn, @Query("rn") int rn, @Query("ie") String ie);
}
