package me.jessyan.armscomponent.commonsdk.data.http;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.data.http.api.GeeksApis;


/**
 * 对外隐藏进行网络请求的实现细节
 *
 * @author quchao
 * @date 2017/11/27
 */

public class HttpHelperImpl implements HttpHelper {

    private GeeksApis mGeeksApis;

    @Inject
    HttpHelperImpl() {

    }

    @Override
    public Observable<String> testPigAvAddress(String url) {
        return null;
    }
}
