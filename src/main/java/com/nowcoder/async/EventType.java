package com.nowcoder.async;

/**
 * Created by hasse on 2020/4/1
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public String typeToString(int i){
        if(i==0) return "赞了";
        else if (i==1) return "评论了";
        else if (i==2) return "登陆了";
        else if (i==3) return "发送邮件了";
        else return "事件未定义";
    }
}
