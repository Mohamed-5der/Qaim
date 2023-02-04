package com.Abdo.qaim.Interfaces;

import java.util.List;

public interface ICallback {
    public void onSuccess(Object response, String msg);
    public void onFail(String msg);
}
