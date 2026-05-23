package com.loc;

import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class k extends Exception {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private int f;
    private int g;
    private volatile boolean h;
    private String i;
    private Map<String, List<String>> j;

    /* JADX WARN: Removed duplicated region for block: B:52:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0182  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public k(String str) {
        String str2;
        String str3;
        super(str);
        this.a = "未知的错误";
        this.b = "";
        this.c = "";
        this.d = "1900";
        this.e = "UnknownError";
        int i = -1;
        this.f = -1;
        this.g = -1;
        this.h = false;
        this.a = str;
        if ("IO 操作异常 - IOException".equals(str)) {
            this.f = 21;
            this.d = "1902";
            str3 = "IOException";
        } else {
            if ("socket 连接异常 - SocketException".equals(str)) {
                i = 22;
            } else if ("socket 连接超时 - SocketTimeoutException".equals(str)) {
                this.f = 23;
                this.d = "1802";
                str3 = "SocketTimeoutException";
            } else if ("无效的参数 - IllegalArgumentException".equals(str)) {
                this.f = 24;
                this.d = "1901";
                str3 = "IllegalArgumentException";
            } else if ("空指针异常 - NullPointException".equals(str)) {
                this.f = 25;
                this.d = "1903";
                str3 = "NullPointException";
            } else if ("url异常 - MalformedURLException".equals(str)) {
                this.f = 26;
                this.d = "1803";
                str3 = "MalformedURLException";
            } else if ("未知主机 - UnKnowHostException".equals(str)) {
                this.f = 27;
                this.d = "1804";
                str3 = "UnknownHostException";
            } else if ("服务器连接失败 - UnknownServiceException".equals(str)) {
                this.f = 28;
                this.d = "1805";
                str3 = "CannotConnectToHostException";
            } else if ("协议解析错误 - ProtocolException".equals(str)) {
                this.f = 29;
                this.d = "1801";
                str3 = "ProtocolException";
            } else {
                if ("http连接失败 - ConnectionException".equals(str)) {
                    this.f = 30;
                    str2 = "1806";
                } else if ("服务QPS超限".equalsIgnoreCase(str)) {
                    this.f = 30;
                    str2 = "2001";
                } else if ("未知的错误".equals(str)) {
                    i = 31;
                } else if ("key鉴权失败".equals(str)) {
                    i = 32;
                } else {
                    if (!"requeust is null".equals(str)) {
                        if ("request url is empty".equals(str)) {
                            this.f = 2;
                        } else if (!"response is null".equals(str)) {
                            if ("thread pool has exception".equals(str)) {
                                i = 4;
                            } else if ("sdk name is invalid".equals(str)) {
                                i = 5;
                            } else if ("sdk info is null".equals(str)) {
                                this.f = 6;
                            } else if ("sdk packages is null".equals(str)) {
                                this.f = 7;
                            } else if ("线程池为空".equals(str)) {
                                i = 8;
                            } else if ("获取对象错误".equals(str)) {
                                i = 101;
                            } else if ("DNS解析失败".equals(str)) {
                                this.f = 3;
                            }
                        }
                        if ("IO 操作异常 - IOException".equals(str)) {
                            this.g = 7;
                            return;
                        }
                        if ("socket 连接异常 - SocketException".equals(str)) {
                            this.g = 6;
                            return;
                        }
                        if ("socket 连接超时 - SocketTimeoutException".equals(str)) {
                            this.g = 2;
                            return;
                        }
                        if (!"未知主机 - UnKnowHostException".equals(str)) {
                            if ("http连接失败 - ConnectionException".equals(str)) {
                                this.g = 6;
                                return;
                            } else if (!"未知的错误".equals(str) && "DNS解析失败".equals(str)) {
                                this.g = 3;
                                return;
                            }
                        }
                        this.g = 9;
                        return;
                    }
                    i = 1;
                }
                this.d = str2;
                str3 = "ConnectionException";
            }
            this.f = i;
            if ("IO 操作异常 - IOException".equals(str)) {
            }
        }
        this.e = str3;
        if ("IO 操作异常 - IOException".equals(str)) {
        }
    }

    public k(String str, String str2, String str3) {
        this(str);
        this.b = str2;
        this.c = str3;
    }

    public final String a() {
        return this.a;
    }

    public final void a(String str) {
        this.i = str;
    }

    public final void a(Map<String, List<String>> map) {
        this.j = map;
    }

    public final String b() {
        return this.d;
    }

    public final String c() {
        return this.e;
    }

    public final String d() {
        return this.b;
    }

    public final String e() {
        return this.c;
    }

    public final int f() {
        return this.f;
    }

    public final int g() {
        return this.g;
    }

    public final int h() {
        this.g = 10;
        return 10;
    }

    public final boolean i() {
        return this.h;
    }

    public final void j() {
        this.h = true;
    }
}
