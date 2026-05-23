package com.nirvana.tools.jsoner;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public interface Jsoner {
    void fromJson(JSONObject jSONObject);

    JSONObject toJson();
}
