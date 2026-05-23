package com.getui.gtc.base.http;

import android.net.Network;
import android.os.Build;
import android.util.Log;
import com.getui.gtc.base.http.Interceptor;
import com.getui.gtc.base.http.Request;
import com.getui.gtc.base.http.Response;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

/* JADX INFO: loaded from: classes.dex */
public class BridgeInterceptor implements Interceptor {
    @Override // com.getui.gtc.base.http.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        URLConnection uRLConnectionOpenConnection;
        Request.Builder builderAddHeader;
        String str;
        Request request = chain.request();
        Request.Builder builder = new Request.Builder(request);
        Network network = request.network();
        if (network == null || Build.VERSION.SDK_INT < 21) {
            uRLConnectionOpenConnection = request.url().openConnection();
        } else {
            Log.d("GTC", "gtc h use n");
            uRLConnectionOpenConnection = network.openConnection(request.url());
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
        RequestBody requestBodyBody = request.body();
        if (requestBodyBody != null) {
            MediaType mediaTypeContentType = requestBodyBody.contentType();
            if (mediaTypeContentType != null) {
                builder.addHeader("Content-Type", mediaTypeContentType.toString());
            }
            long jContentLength = requestBodyBody.contentLength();
            if (jContentLength != -1) {
                builderAddHeader = builder.addHeader("Content-Length", Long.toString(jContentLength));
                str = "Transfer-Encoding";
            } else {
                builderAddHeader = builder.addHeader("Transfer-Encoding", "chunked");
                str = "Content-Length";
            }
            builderAddHeader.removeHeader(str);
        }
        if (request.header("Host") == null) {
            builder.addHeader("Host", request.url().getHost());
        }
        if (request.header("Connection") == null) {
            builder.addHeader("Connection", "Keep-Alive");
        }
        boolean z = false;
        if (request.header("Accept-Encoding") == null && request.header("Range") == null) {
            z = true;
            builder.addHeader("Accept-Encoding", "gzip");
        }
        Response responseProceed = ((RealInterceptorChain) chain).proceed(builder.build(), httpURLConnection);
        Response.Builder builderRequest = new Response.Builder(responseProceed).request(request);
        if (z && "gzip".equalsIgnoreCase(responseProceed.header("Content-Encoding")) && responseProceed.body() != null) {
            builderRequest.body(ResponseBody.create(responseProceed.body().contentType(), -1L, new GZIPInputStream(responseProceed.body().byteStream()))).removeHeader("Content-Encoding").removeHeader("Content-Length");
        }
        return builderRequest.build();
    }
}
