package com.base.fruitbase.util.okhttputils.builder;

import com.base.fruitbase.util.okhttputils.OkHttpUtils;
import com.base.fruitbase.util.okhttputils.request.OtherRequest;
import com.base.fruitbase.util.okhttputils.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
