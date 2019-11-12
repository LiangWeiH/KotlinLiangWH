package com.yimaotong.fruitbase.protocal.netutil.signstrategy;

import java.util.Map;

/**
 * @time 2017/9/23 13:00
 */
public interface ISignStrategy {
    Map<String,String> sign(Map<String, String> map);
}
