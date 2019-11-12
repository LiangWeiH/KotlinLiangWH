package com.example.kotlintest.bean

/**
 *Time:2019/11/06 11:29
 *Author: LiangWH
 *Description:
 */
data class HomePhotoBean(
    val counts: Int,
    val feedList: List<Feed>,
    val is_history: Boolean,
    val message: String,
    val more: Boolean,
    val result: String
)

data class Feed(
    val author_id: String,
    val collected: Boolean,
    val comment_list_prefix: List<Any>,
    val comments: Int,
    val content: String,
    val created_at: String,
    val data_type: String,
    val delete: Boolean,
    val event_tags: List<String>,
    val excerpt: String,
    val favorite_list_prefix: List<Any>,
    val favorites: Int,
    val image_count: Int,
    val images: List<Image>,
    val is_favorite: Boolean,
    val last_read: Boolean,
    val parent_comments: String,
    val passed_time: String,
    val post_id: Int,
    val published_at: String,
    val recom_type: String,
    val recommend: Boolean,
    val reward_list_prefix: List<Any>,
    val rewardable: Boolean,
    val rewards: String,
    val rqt_id: String,
    val shares: Int,
    val site: Site,
    val site_id: String,
    val sites: List<Any>,
    val tags: List<String>,
    val title: String,
    val title_image: Any,
    val type: String,
    val update: Boolean,
    val url: String,
    val views: Int
)

data class Image(
    val description: String,
    val excerpt: String,
    val height: Int,
    val img_id: Int,
    val img_id_str: String,
    val title: String,
    val user_id: Int,
    val width: Int
)

data class Site(
    val description: String,
    val domain: String,
    val followers: Int,
    val has_everphoto_note: Boolean,
    val icon: String,
    val is_bind_everphoto: Boolean,
    val is_following: Boolean,
    val name: String,
    val site_id: String,
    val type: String,
    val url: String,
    val verification_list: List<Verification>,
    val verifications: Int,
    val verified: Boolean,
    val verified_reason: String,
    val verified_type: Int
)

data class Verification(
    val verification_reason: String,
    val verification_type: Int
)