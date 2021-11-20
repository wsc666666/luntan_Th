package com.nowcoder.model;
/**
 * Created by hasse on 2020/4/1
 */
public class Like {
    private int id;
    private int userId;
    private int entityType;
    private int likedId;
    private int likedType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public int getLikedId() {
        return likedId;
    }

    public void setLikedId(int likedId) {
        this.likedId = likedId;
    }

    public int getLikedType() {
        return likedType;
    }

    public void setLikedType(int likedType) {
        this.likedType = likedType;
    }


}
