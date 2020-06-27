package com.qmr.wanandroid.network.base;

import android.accounts.NetworkErrorException;

public class WanNetworkException extends NetworkErrorException {

    public WanNetworkException() {
    }

    public WanNetworkException(String msg) {
        super(msg);
    }

}
