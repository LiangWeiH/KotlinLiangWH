package com.base.fruitbase.framework.delegate;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * @time 2017/9/19 13:58
 */
public interface FragmentDelegate{

    void onAttach(Context context);

    void onCreate(Bundle savedInstanceState);

    void onCreateView(View view, Bundle savedInstanceState);

    void onActivityCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onDestroyView();

    void onDestroy();

    void onDetach();

}
