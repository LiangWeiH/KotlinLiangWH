package com.yimaotong.fruitbase.framework.delegate;

import android.os.Bundle;

/**
 * @time 2017/9/19 13:58
 */
public interface ActivityDelegate{

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onDestroy();
}
