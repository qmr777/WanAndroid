package com.qmr.base.model;

import com.google.gson.Gson;

import java.io.Serializable;

public class BaseBean implements Serializable {

    public static Gson gson = new Gson();

    public String toJson() {
        return gson.toJson(this);
    }

}
