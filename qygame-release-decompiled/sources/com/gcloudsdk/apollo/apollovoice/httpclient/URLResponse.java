package com.gcloudsdk.apollo.apollovoice.httpclient;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class URLResponse {
    public int status = 0;
    public int httpCode = 0;
    public String statusMsg = "";
    public String httpCodeMsg = "";
    public String URL = "";
    public String version = "";
    public byte[] body = null;
    public Map<String, String> headers = new HashMap();

    URLResponse() {
    }
}
