package com.nowcoder.util;

/**
 * Created by nowcoder on 2016/7/13.
 */
public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String STAR = "STAR";
    private static String DIS_STAR = "DISSTAR";
    private static String LIKE2 = "LIKE2";
    private static String DIS_LIKE2 = "DISLIKE2";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENT = "EVENT";
    private static String BIZ_TYPE = "TYPE";

    public static String getEventQueueKey() {
        return BIZ_EVENT;
    }

    public static String getLikeKey(int entityId, int entityType) {
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityId, int entityType) {
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }
    public static String getStarLikeKey(int entityId, int entityType) {
        return STAR + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getStarDisLikeKey(int entityId, int entityType) {
        return DIS_STAR + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }
    public static String getLike2Key(int entityId, int entityType) {
        return LIKE2 + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getDisLike2Key(int entityId, int entityType) {
        return DIS_LIKE2 + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }


}
