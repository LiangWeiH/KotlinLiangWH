package com.example.kotlintest.config

class Contstants {
    companion object {
        /**
         * 图虫推荐API
         * page：翻页值。从 1 开始。需要搭配 json 中的 pose_id 字段使用。可为空
         * post_id：如果是第一页则不需要添加该字段。否则，需要加上该字段，该字段的值为上一页最后一个 json 中的 post_id 值
         * type：如果是第一页则是 refresh，如果是加载更多，则是 loadmore。可为空
         *
         *
         * 图片地址：https://photo.tuchong.com/ + user_id +/f/ + img_id 即图片地址 例如：https://photo.tuchong.com/1673709/f/25389444.jpg
         */
        var TUCHONG_API = "https://api.tuchong.com/"

        /**
         * 图虫推荐
         */
        var GETHOMEPHOTOAPI = TUCHONG_API + "feed-app"
    }
}