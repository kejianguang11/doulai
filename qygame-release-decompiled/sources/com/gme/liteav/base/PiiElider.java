package com.gme.liteav.base;

import android.text.TextUtils;
import android.util.Patterns;
import androidx.core.app.NotificationCompat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class PiiElider {
    private static final String CONSOLE_ELISION = "[ELIDED:CONSOLE(0)] ELIDED CONSOLE MESSAGE";
    private static final String EMAIL_ELISION = "XXX@EMAIL.ELIDED";
    private static final String GOOD_GTLD_CHAR = "a-zA-Z -\ud7ff豈-\ufdcfﷰ-\uffef";
    private static final String GOOD_IRI_CHAR = "a-zA-Z0-9 -\ud7ff豈-\ufdcfﷰ-\uffef";
    private static final String GTLD = "[a-zA-Z -\ud7ff豈-\ufdcfﷰ-\uffef]{2,63}";
    private static final String HOST_NAME = "([a-zA-Z0-9 -\ud7ff豈-\ufdcfﷰ-\uffef]([a-zA-Z0-9 -\ud7ff豈-\ufdcfﷰ-\uffef\\-]{0,61}[a-zA-Z0-9 -\ud7ff豈-\ufdcfﷰ-\uffef]){0,1}\\.)+[a-zA-Z -\ud7ff豈-\ufdcfﷰ-\uffef]{2,63}";
    private static final String IRI = "[a-zA-Z0-9 -\ud7ff豈-\ufdcfﷰ-\uffef]([a-zA-Z0-9 -\ud7ff豈-\ufdcfﷰ-\uffef\\-]{0,61}[a-zA-Z0-9 -\ud7ff豈-\ufdcfﷰ-\uffef]){0,1}";
    private static final String MAC_ELISION = "01:23:45:67:89:AB";
    private static final String URL_ELISION = "HTTP://WEBADDRESS.ELIDED";
    private static final Pattern IP_ADDRESS = Pattern.compile("((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9]))");
    private static final Pattern DOMAIN_NAME = Pattern.compile("(([a-zA-Z0-9 -\ud7ff豈-\ufdcfﷰ-\uffef]([a-zA-Z0-9 -\ud7ff豈-\ufdcfﷰ-\uffef\\-]{0,61}[a-zA-Z0-9 -\ud7ff豈-\ufdcfﷰ-\uffef]){0,1}\\.)+[a-zA-Z -\ud7ff豈-\ufdcfﷰ-\uffef]{2,63}|" + IP_ADDRESS + ")");
    private static final Pattern LIKELY_EXCEPTION_LOG = Pattern.compile("\\sat\\sorg\\.chromium\\.[^ ]+.");
    private static final Pattern WEB_URL = Pattern.compile("(?:\\b|^)((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?(?:" + DOMAIN_NAME + ")(?:\\:\\d{1,5})?)(\\/(?:(?:[a-zA-Z0-9 -\ud7ff豈-\ufdcfﷰ-\uffef\\;\\/\\?\\:\\@\\&\\=\\#\\~\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?(?:\\b|$)");
    private static final Pattern MAC_ADDRESS = Pattern.compile("([0-9a-fA-F]{2}[-:]+){5}[0-9a-fA-F]{2}");
    private static final Pattern CONSOLE_MSG = Pattern.compile("\\[\\w*:CONSOLE.*\\].*");
    private static final String[] APP_NAMESPACE = {"org.chromium.", "com.google."};
    private static final String[] SYSTEM_NAMESPACE = {"android.accessibilityservice", "android.accounts", "android.animation", "android.annotation", "android.app", "android.appwidget", "android.bluetooth", "android.content", "android.database", "android.databinding", "android.drm", "android.gesture", "android.graphics", "android.hardware", "android.inputmethodservice", "android.location", "android.media", "android.mtp", "android.net", "android.nfc", "android.opengl", "android.os", "android.preference", "android.print", "android.printservice", "android.provider", "android.renderscript", "android.sax", "android.security", "android.service", "android.speech", "android.support", "android.system", "android.telecom", "android.telephony", "android.test", NotificationCompat.EXTRA_TEXT, "android.transition", "android.util", "android.view", "android.webkit", "android.widget", "com.android.", "dalvik.", "java.", "javax.", "org.apache.", "org.json.", "org.w3c.dom.", "org.xml.", "org.xmlpull."};

    public static String elideConsole(String str) {
        return CONSOLE_MSG.matcher(str).replaceAll(CONSOLE_ELISION);
    }

    public static String elideEmail(String str) {
        return Patterns.EMAIL_ADDRESS.matcher(str).replaceAll(EMAIL_ELISION);
    }

    public static String elideMac(String str) {
        return MAC_ADDRESS.matcher(str).replaceAll(MAC_ELISION);
    }

    public static String elideUrl(String str) {
        if (LIKELY_EXCEPTION_LOG.matcher(str).find()) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        Matcher matcher = WEB_URL.matcher(sb);
        int i = 0;
        while (matcher.find(i)) {
            int iStart = matcher.start();
            int iEnd = matcher.end();
            String strSubstring = sb.substring(iStart, iEnd);
            if (likelyToBeAppNamespace(strSubstring) || likelyToBeSystemNamespace(strSubstring)) {
                i = iEnd;
            } else {
                sb.replace(iStart, iEnd, URL_ELISION);
                i = iStart + 24;
                matcher = WEB_URL.matcher(sb);
            }
        }
        return sb.toString();
    }

    private static boolean likelyToBeAppNamespace(String str) {
        for (String str2 : APP_NAMESPACE) {
            if (str.startsWith(str2)) {
                return true;
            }
        }
        return false;
    }

    private static boolean likelyToBeSystemNamespace(String str) {
        for (String str2 : SYSTEM_NAMESPACE) {
            if (str.startsWith(str2)) {
                return true;
            }
        }
        return false;
    }

    public static String sanitizeStacktrace(String str) {
        String[] strArrSplit = str.split("\\n");
        strArrSplit[0] = elideUrl(strArrSplit[0]);
        for (int i = 1; i < strArrSplit.length; i++) {
            if (strArrSplit[i].startsWith("Caused by:")) {
                strArrSplit[i] = elideUrl(strArrSplit[i]);
            }
        }
        return TextUtils.join("\n", strArrSplit);
    }
}
