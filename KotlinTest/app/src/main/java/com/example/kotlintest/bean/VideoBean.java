package com.example.kotlintest.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Time:2020/05/09 15:05
 * Author: LiangWH
 * Description:
 */
public class VideoBean  {
    public static final int VIDEO = 1;
    public static final int TEXT = 2;
    public static final int BANNER = 3;



    public String nextPageUrl;
    public long nextPublishTime;
    public String newestIssueType;
    public Object dialog;
    public List<IssueListBean> issueList;

    public static class IssueListBean implements MultiItemEntity{


        public int itemType;
        public IssueListBean(int itemType) {
            this.itemType = itemType;
        }
        @Override
        public int getItemType() {
            return itemType;
        }
        public long releaseTime;
        public String type;
        public long date;
        public long publishTime;
        public int count;
        public List<ItemListBean> itemList;

        public static class ItemListBean {

            public String type;
            public DataBean data;
            public Object tag;
            public int id;
            public int adIndex;

            public static class DataBean {
                public String text;
                public String image;
                public String dataType;
                public int id;
                public String title;
                public String description;
                public String library;
                public ConsumptionBean consumption;
                public String resourceType;
                public String slogan;
                public ProviderBean provider;
                public String category;
                public AuthorBean author;
                public CoverBean cover;
                public String playUrl;
                public String thumbPlayUrl;
                public int duration;
                public WebUrlBean webUrl;
                public long releaseTime;
                public Object campaign;
                public Object waterMarks;
                public boolean ad;
                public String type;
                public String titlePgc;
                public String descriptionPgc;
                public String remark;
                public boolean ifLimitVideo;
                public int searchWeight;
                public Object brandWebsiteInfo;
                public int idx;
                public Object shareAdTrack;
                public Object favoriteAdTrack;
                public Object webAdTrack;
                public long date;
                public Object promotion;
                public Object label;
                public String descriptionEditor;
                public boolean collected;
                public boolean reallyCollected;
                public boolean played;
                public Object lastViewTime;
                public Object playlists;
                public Object src;
                public List<TagsBean> tags;
                public List<PlayInfoBean> playInfo;
                public List<?> adTrack;
                public List<?> labelList;
                public List<?> subtitles;

                public static class ConsumptionBean {
                    /**
                     * collectionCount : 383
                     * shareCount : 226
                     * replyCount : 6
                     * realCollectionCount : 117
                     */

                    public int collectionCount;
                    public int shareCount;
                    public int replyCount;
                    public int realCollectionCount;
                }

                public static class ProviderBean {
                    /**
                     * name : 投稿
                     * alias : PGC2
                     * icon :
                     */

                    public String name;
                    public String alias;
                    public String icon;
                }

                public static class AuthorBean {
                    /**
                     * id : 2162
                     * icon : http://img.kaiyanapp.com/98beab66d3885a139b54f21e91817c4f.jpeg
                     * name : 开眼广告精选
                     * description : 为广告人的精彩创意点赞
                     * link :
                     * latestReleaseTime : 1588986009000
                     * videoNum : 1326
                     * adTrack : null
                     * follow : {"itemType":"author","itemId":2162,"followed":false}
                     * shield : {"itemType":"author","itemId":2162,"shielded":false}
                     * approvedNotReadyVideoCount : 0
                     * ifPgc : true
                     * recSort : 0
                     * expert : false
                     */

                    public int id;
                    public String icon;
                    public String name;
                    public String description;
                    public String link;
                    public long latestReleaseTime;
                    public int videoNum;
                    public Object adTrack;
                    public FollowBean follow;
                    public ShieldBean shield;
                    public int approvedNotReadyVideoCount;
                    public boolean ifPgc;
                    public int recSort;
                    public boolean expert;

                    public static class FollowBean {
                        /**
                         * itemType : author
                         * itemId : 2162
                         * followed : false
                         */

                        @SerializedName("itemType")
                        public String itemTypeX;
                        public int itemId;
                        public boolean followed;
                    }

                    public static class ShieldBean {
                        /**
                         * itemType : author
                         * itemId : 2162
                         * shielded : false
                         */

                        @SerializedName("itemType")
                        public String itemTypeX;
                        public int itemId;
                        public boolean shielded;
                    }
                }

                public static class CoverBean {


                    public String feed;
                    public String detail;
                    public String blurred;
                    public Object sharing;
                    public String homepage;
                }

                public static class WebUrlBean {
                    /**
                     * raw : http://www.eyepetizer.net/detail.html?vid=194140
                     * forWeibo : http://www.eyepetizer.net/detail.html?vid=194140&resourceType=video&utm_campaign=routine&utm_medium=share&utm_source=weibo&uid=0
                     */

                    public String raw;
                    public String forWeibo;
                }

                public static class TagsBean {
                    /**
                     * id : 1155
                     * name : 关于新型冠状病毒
                     * actionUrl : eyepetizer://tag/1155/?title=%E5%85%B3%E4%BA%8E%E6%96%B0%E5%9E%8B%E5%86%A0%E7%8A%B6%E7%97%85%E6%AF%92
                     * adTrack : null
                     * desc : null
                     * bgPicture : http://img.kaiyanapp.com/c35e2324a3595a87209ef06c2e686d40.png?imageMogr2/quality/60/format/jpg
                     * headerImage : http://img.kaiyanapp.com/c35e2324a3595a87209ef06c2e686d40.png?imageMogr2/quality/60/format/jpg
                     * tagRecType : IMPORTANT
                     * childTagList : null
                     * childTagIdList : null
                     * haveReward : false
                     * ifNewest : false
                     * newestEndTime : null
                     * communityIndex : 0
                     */

                    public int id;
                    public String name;
                    public String actionUrl;
                    public Object adTrack;
                    public Object desc;
                    public String bgPicture;
                    public String headerImage;
                    public String tagRecType;
                    public Object childTagList;
                    public Object childTagIdList;
                    public boolean haveReward;
                    public boolean ifNewest;
                    public Object newestEndTime;
                    public int communityIndex;
                }

                public static class PlayInfoBean {
                    /**
                     * height : 720
                     * width : 1280
                     * urlList : [{"name":"aliyun","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=194140&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss","size":18745367},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=194140&resourceType=video&editionType=high&source=ucloud&playUrlType=url_oss","size":18745367}]
                     * name : 高清
                     * type : high
                     * url : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=194140&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss
                     */

                    public int height;
                    public int width;
                    public String name;
                    public String type;
                    public String url;
                    public List<UrlListBean> urlList;

                    public static class UrlListBean {
                        /**
                         * name : aliyun
                         * url : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=194140&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss
                         * size : 18745367
                         */

                        public String name;
                        public String url;
                        public int size;
                    }
                }
            }
        }
    }
}
