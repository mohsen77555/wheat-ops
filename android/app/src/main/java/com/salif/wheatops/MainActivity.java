package com.salif.wheatops;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // The app is a light-only design. Prevent Android/Samsung "dark mode for
        // all apps" from algorithmically darkening the WebView, which washes out
        // colors and can hide content. The web layer also declares
        // <meta name="color-scheme" content="light"> as a second guard.
        try {
            WebView webView = this.getBridge().getWebView();
            WebSettings settings = webView.getSettings();
            if (WebViewFeature.isFeatureSupported(WebViewFeature.ALGORITHMIC_DARKENING)) {
                WebSettingsCompat.setAlgorithmicDarkeningAllowed(settings, false);
            } else if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                WebSettingsCompat.setForceDark(settings, WebSettingsCompat.FORCE_DARK_OFF);
            }
        } catch (Exception ignored) {
        }
    }
}
