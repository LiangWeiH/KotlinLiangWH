package com.yimaotong.fruitbase.util.okhttputils.builder;

import com.yimaotong.fruitbase.util.okhttputils.OkHttpUtils;
import com.yimaotong.fruitbase.util.okhttputils.request.OtherRequest;
import com.yimaotong.fruitbase.util.okhttputils.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
