package arte.programar.advertising.helpers;

import android.util.Log;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import arte.programar.advertising.AdNativeView;

public class AdNativeHelper {
    private static final String TAG = "arte.programar::".concat(AdNativeHelper.class.getSimpleName());

    /**
     * NativeAd load & show
     * Require view inflater!
     *
     * @param view
     * @param adKey
     */
    public static void show(final AdNativeView view, String adKey) {
        try {
            AdLoader loader = new AdLoader.Builder(view.getContext(), adKey).forUnifiedNativeAd(
                    new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                        @Override
                        public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                            view.setNativeAd(unifiedNativeAd);
                        }
                    }
            ).build();
            loader.loadAd(new AdRequest.Builder().build());
        } catch (Exception ex) {
            Log.w(TAG, ex);
        }
    }

    /**
     * NativeAd load & show
     * Require view inflater and AdRequest Build
     *
     * @param view
     * @param adKey
     * @param request
     */
    public static void show(final AdNativeView view, String adKey, AdRequest request) {
        try {
            AdLoader loader = new AdLoader.Builder(view.getContext(), adKey).forUnifiedNativeAd(
                    new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                        @Override
                        public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                            view.setNativeAd(unifiedNativeAd);
                        }
                    }
            ).build();
            loader.loadAd(request);
        } catch (Exception ex) {
            Log.w(TAG, ex);
        }
    }

    /**
     * Instance Destroy
     *
     * @param view
     */
    public static void destroy(AdNativeView view) {
        if (view != null) {
            view.destroyNativeAd();
        }
    }

}
