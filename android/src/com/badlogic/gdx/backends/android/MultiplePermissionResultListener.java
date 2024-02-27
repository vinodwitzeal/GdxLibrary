package com.badlogic.gdx.backends.android;

import java.util.Map;

public interface MultiplePermissionResultListener {
    void onResult(Map<String,Boolean> map);
}
