package com.qmr.wanandroid.model.entity;

import com.qmr.base.model.BaseBean;

public class UserInfoBean extends BaseBean {

    /**
     * coinCount : 516
     * level : 6
     * rank : 1414
     * userId : 12677
     * username : q**777
     */

    private int coinCount;
    private int level;
    private String rank;
    private int userId;
    private String username;

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
