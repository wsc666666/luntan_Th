package com.nowcoder.model;

/**
 * Created by hasse on 2020/4/1
 */
public class EntityType {
    public static int ENTITY_NEWS = 1;
    public static int ENTITY_COMMENT = 2;
    public static int ENTITY_ANSWER = 3;
    public static String getType(int i){

      if (i==1) return "问题";
        else if (i==2) return "评论";
        else if (i==3) return "回答";
        else return "事件未定义";
    }
}
