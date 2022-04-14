package com.yilan.sdk.sdkdemo;

import com.yilan.sdk.sdkdemo.feed.FeedMedia;

import java.util.ArrayList;
import java.util.List;

public class MockData {

    static String[] urls = {
            "https://vv.qianpailive.com/7d80/20210714/dfdb4ee9b51c121c7127187ed4411863?auth_key=1626283328-0-0-9b10fd44889fa42ebab0200477b07554",
            "https://vv.qianpailive.com/386e/20210714/f565c8e864722678f4a0e1b64570c7fe?auth_key=1626283328-0-0-8aa72bd4ba852e9f1855352b30694da0",
            "https://vv.qianpailive.com/7452/20210714/d0ddbe5bc1ed7155dc838ac2617e18e9?auth_key=1626283328-0-0-1c0f2094de201fbe1ec41f82baa88fbd",
    };

    public static String getPlayerUrl() {
        return "https://vv.qianpailive.com/7d80/20210714/dfdb4ee9b51c121c7127187ed4411863?auth_key=1626283328-0-0-9b10fd44889fa42ebab0200477b07554";
    }

    public static String getPlayerUrl(int index) {
        return urls[index];
    }

    public static List<FeedMedia> getMockFeed() {
        List<FeedMedia> media = new ArrayList<>();
        media.add(new FeedMedia(
                "宁波男子骑摩托艇因太重开出潜艇效果！网友：伤害性不大侮辱性极强",
                "https://img.yilanvaas.com/b582/20210902/655c4a3eb114c5c85be5abe17ca590c1!open_largepgc",
                "https://vv.qianpailive.com/245c/20210902/8db9c49ec8e2106db24f3df0efc4e452?auth_key=1630582602-0-0-28c3d20c13249d31ce36075f33d27413"
        ));
        media.add(new FeedMedia(
                "浙江临海一男子为躲债800万房子350万卖给儿子儿媳，法院判了：撤销",
                "https://img.yilanvaas.com/e05e/20210902/750686da0c6550c2913dc54dfbb6062d!open_largepgc",
                "https://vv.qianpailive.com/7452/20210714/d0ddbe5bc1ed7155dc838ac2617e18e9?auth_key=1626283328-0-0-1c0f2094de201fbe1ec41f82baa88fbd"
        ));
        media.add(new FeedMedia(
                "江苏南通一团伙闲鱼低价卖二手苹果机诈骗141万：退款就拉黑",
                "https://img.yilanvaas.com/0fae/20210902/51ea144f8046af97222477acd38b761d!open_largepgc",
                "https://vv.qianpailive.com/386e/20210714/f565c8e864722678f4a0e1b64570c7fe?auth_key=1626283328-0-0-8aa72bd4ba852e9f1855352b30694da0"
        ));
        media.add(new FeedMedia(
                "宁波男子骑摩托艇因太重开出潜艇效果！网友：伤害性不大侮辱性极强",
                "https://img.yilanvaas.com/b582/20210902/655c4a3eb114c5c85be5abe17ca590c1!open_largepgc",
                "https://vv.qianpailive.com/245c/20210902/8db9c49ec8e2106db24f3df0efc4e452?auth_key=1630582602-0-0-28c3d20c13249d31ce36075f33d27413"
        ));
        media.add(new FeedMedia(
                "浙江临海一男子为躲债800万房子350万卖给儿子儿媳，法院判了：撤销",
                "https://img.yilanvaas.com/e05e/20210902/750686da0c6550c2913dc54dfbb6062d!open_largepgc",
                "https://vv.qianpailive.com/7452/20210714/d0ddbe5bc1ed7155dc838ac2617e18e9?auth_key=1626283328-0-0-1c0f2094de201fbe1ec41f82baa88fbd"
        ));
        media.add(new FeedMedia(
                "江苏南通一团伙闲鱼低价卖二手苹果机诈骗141万：退款就拉黑",
                "https://img.yilanvaas.com/0fae/20210902/51ea144f8046af97222477acd38b761d!open_largepgc",
                "https://vv.qianpailive.com/386e/20210714/f565c8e864722678f4a0e1b64570c7fe?auth_key=1626283328-0-0-8aa72bd4ba852e9f1855352b30694da0"
        ));
        media.add(new FeedMedia(
                "宁波男子骑摩托艇因太重开出潜艇效果！网友：伤害性不大侮辱性极强",
                "https://img.yilanvaas.com/b582/20210902/655c4a3eb114c5c85be5abe17ca590c1!open_largepgc",
                "https://vv.qianpailive.com/245c/20210902/8db9c49ec8e2106db24f3df0efc4e452?auth_key=1630582602-0-0-28c3d20c13249d31ce36075f33d27413"
        ));
        media.add(new FeedMedia(
                "浙江临海一男子为躲债800万房子350万卖给儿子儿媳，法院判了：撤销",
                "https://img.yilanvaas.com/e05e/20210902/750686da0c6550c2913dc54dfbb6062d!open_largepgc",
                "https://vv.qianpailive.com/7452/20210714/d0ddbe5bc1ed7155dc838ac2617e18e9?auth_key=1626283328-0-0-1c0f2094de201fbe1ec41f82baa88fbd"
        ));
        media.add(new FeedMedia(
                "江苏南通一团伙闲鱼低价卖二手苹果机诈骗141万：退款就拉黑",
                "https://img.yilanvaas.com/0fae/20210902/51ea144f8046af97222477acd38b761d!open_largepgc",
                "https://vv.qianpailive.com/386e/20210714/f565c8e864722678f4a0e1b64570c7fe?auth_key=1626283328-0-0-8aa72bd4ba852e9f1855352b30694da0"
        ));
        for (int i = 0; i < media.size(); i++) {
            media.get(i).videoId = "feed" + i;
        }
        return media;
    }

    public static List<FeedMedia> getMockUgc() {
        String[] names = {"娱乐情报站", "体坛在线", "上游新闻", "政知道", "南昌晚报"};
        List<FeedMedia> media = new ArrayList<>();
        media.add(new FeedMedia(
                "我和我的父辈吴京吴磊演父子",
                "https://img.yilanvaas.com/4c11/20210908/43f338825f05639e69b865cc79db60b1!open_middleugc",
                "http://vv.qianpailive.com/8700/20210908/1ddde70ae557397e9275cb3686ecb3ee"
        ));
        media.add(new FeedMedia(
                "这个多功能捞面夹收到的宝宝是不是很好用！夹馒头、夹鸡蛋、夹面条还能用来打鸡蛋，厨房有他就够了",
                "https://img.yilanvaas.com/fa4e/20210910/33334fa87aefc0446a1c5a755ecf0581!open_middleugc",
                "http://vv.qianpailive.com/5b81/20210910/589407c0ba30aa5ad777d0865ae3acc4"
        ));
        media.add(new FeedMedia(
                "官宣！国庆节放假调休安排来了，共7天",
                "https://img.yilanvaas.com/56c6/20210908/b5d94fe2e7a74941f35145b86a688b45!open_middleugc",
                "http://vv.qianpailive.com/7f25/20210908/24a52c6c238d051af84cac01175d4ebb"
        ));
        media.add(new FeedMedia(
                "墨西哥发生7.1级地震，天空现诡异蓝光",
                "https://img.yilanvaas.com/b560/20210908/3c2adff3f425b85c5af523f6a3eaf5f0!open_middleugc",
                "http://vv.qianpailive.com/c130/20210908/41da06a781c7102b03b83ad15acd87ad"
        ));
        media.add(new FeedMedia(
                "有一种美丽叫干净舒服",
                "https://img.yilanvaas.com/d20e/20210910/5f1257f07d229d3f1ea471be0517669a!open_middleugc",
                "http://vv.qianpailive.com/00b8/20210910/b8b56ab887f3d7555f3c38c0d3cb1bb7"
        ));

        media.add(new FeedMedia(
                "我和我的父辈吴京吴磊演父子",
                "https://img.yilanvaas.com/4c11/20210908/43f338825f05639e69b865cc79db60b1!open_middleugc",
                "http://vv.qianpailive.com/8700/20210908/1ddde70ae557397e9275cb3686ecb3ee"
        ));
        media.add(new FeedMedia(
                "这个多功能捞面夹收到的宝宝是不是很好用！夹馒头、夹鸡蛋、夹面条还能用来打鸡蛋，厨房有他就够了",
                "https://img.yilanvaas.com/fa4e/20210910/33334fa87aefc0446a1c5a755ecf0581!open_middleugc",
                "http://vv.qianpailive.com/5b81/20210910/589407c0ba30aa5ad777d0865ae3acc4"
        ));
        media.add(new FeedMedia(
                "官宣！国庆节放假调休安排来了，共7天",
                "https://img.yilanvaas.com/56c6/20210908/b5d94fe2e7a74941f35145b86a688b45!open_middleugc",
                "http://vv.qianpailive.com/7f25/20210908/24a52c6c238d051af84cac01175d4ebb"
        ));
        media.add(new FeedMedia(
                "墨西哥发生7.1级地震，天空现诡异蓝光",
                "https://img.yilanvaas.com/b560/20210908/3c2adff3f425b85c5af523f6a3eaf5f0!open_middleugc",
                "http://vv.qianpailive.com/c130/20210908/41da06a781c7102b03b83ad15acd87ad"
        ));
        media.add(new FeedMedia(
                "有一种美丽叫干净舒服",
                "https://img.yilanvaas.com/d20e/20210910/5f1257f07d229d3f1ea471be0517669a!open_middleugc",
                "http://vv.qianpailive.com/00b8/20210910/b8b56ab887f3d7555f3c38c0d3cb1bb7"
        ));

        for (int i = 0; i < media.size(); i++) {
            media.get(i).videoId = "ugc_" + i;
            media.get(i).name = names[i % 4];
        }
        return media;
    }

}
