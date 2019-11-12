package com.example.bottomnavigation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlintest.R


class VideoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.title = getString(R.string.title_dashboard)
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return view
    }


}