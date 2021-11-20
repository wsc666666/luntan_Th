package com.nowcoder.model;

/**
 * Created by hasse on 2020/4/1
 */
public class User {
    private int id;
    private String name;
    private String password;
    private String salt;
    private String headUrl;
    private String zhName;
private String star;
private String star2;
private String star3;

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    private int lv;

    public String getStar3() {
        return star3;
    }

    public void setStar3(String star3) {
        this.star3 = star3;
    }

    public String getStar2() {
        return star2;
    }

    public void setStar2(String star2) {
        this.star2 = star2;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getJc() {
        return jc;
    }

    public void setJc(String jc) {
        this.jc = jc;
    }

    private String jc;



    public User() {

    }
    public User(String name) {
        this.name = name;
        this.password = "";
        this.salt = "";
        this.headUrl = "";
        this.zhName="";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }
}
