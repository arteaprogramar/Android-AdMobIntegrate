package arte.programar.advertising.helpers;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AdViewHelper {
    private static final String TAG = "arte.programar::".concat(AdViewHelper.class.getSimpleName());

    // Variable
    private final String mAdKey;
    private final Context mContext;
    private final AdRequest mRequest;
    private AdView mAdView;

    /**
     * Constructor
     *
     * @param context
     * @param adKey
     */
    public AdViewHelper(Context context, String adKey) {
        this.mContext = context;
        this.mAdKey = adKey;
        this.mRequest = new AdRequest.Builder().build();
    }

    /**
     * Constructor
     *
     * @param context
     * @param adKey
     * @param request
     */
    public AdViewHelper(Context context, String adKey, AdRequest request) {
        this.mContext = context;
        this.mAdKey = adKey;
        this.mRequest = request;
    }

    /**
     * Create AdView Programmatically
     */
    public void create() {
        try {
            mAdView = new AdView(mContext);
            mAdView.setAdSize(getAdViewSize());
            mAdView.setAdUnitId(mAdKey);
            mAdView.loadAd(mRequest);
        } catch (Exception ex) {
            Log.w(TAG, ex);
        }
    }

    /**
     * Banner size according to Device Metrics and Screens Support
     * https://material.io/tools/devices/
     * https://developer.android.com/guide/practices/screens_support?hl=es-419#range
     * <p>
     * It is important to mention that the density of the screen (dp) is affected
     * by the orientation and multi-window of the device.
     *
     * Adaptive Banners
     * Adaptive banners are the next generation of responsive ads, maximizing performance
     * by optimizing ad size for each device.
     * https://developers.google.com/admob/android/banner/adaptive
     *
     * @return
     */
    private AdSize getAdViewSize() {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();

        int height = (int) (metrics.heightPixels / metrics.density + 0.5);
        int width = (int) (metrics.widthPixels / metrics.density + 0.5);

        Log.w(TAG, "W: ".concat(String.valueOf(width)).concat(" H: ").concat(String.valueOf(height)));

        if (width >= 1024) {
            if (height >= 1024) return AdSize.MEDIUM_RECTANGLE;
            else if (height >= 720) return AdSize.LEADERBOARD;
            else if (height >= 480) return AdSize.FULL_BANNER;
            else if (height >= 320) return AdSize.LARGE_BANNER;
            else return AdSize.BANNER;
        } else if (width >= 640) {
            if (height >= 720) return AdSize.MEDIUM_RECTANGLE;
            else if (height >= 480) return AdSize.LEADERBOARD;
            else return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(mContext, width);
        } else if (width >= 320) {
            if (height >= 720) return AdSize.LARGE_BANNER;
            else if (height >= 480) return AdSize.FULL_BANNER;
            else return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(mContext, width);
        } else {
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(mContext, width);
        }
    }

    /**
     * Return AdView object
     *
     * @return
     */
    public AdView getAdView() {
        return mAdView;
    }

    /**
     * Pause
     */
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
    }

    /**
     * Resume
     */
    public void onResume() {
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /**
     * Destroy
     */
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
    }
}
