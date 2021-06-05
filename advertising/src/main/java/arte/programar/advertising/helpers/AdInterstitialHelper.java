package arte.programar.advertising.helpers;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AdInterstitialHelper {
    private static final String TAG = "arte.programar::".concat(AdInterstitialHelper.class.getSimpleName());

    // Variables
    private final String mAdKey;
    private final Activity mActivity;
    private final AdRequest mRequest;
    private final MutableLiveData<Boolean> showInterstitial = new MutableLiveData<>(false);
    private InterstitialAd mInterstitial;

    /**
     * Constructor
     *
     * @param activity
     * @param adKey
     */
    public AdInterstitialHelper(Activity activity, String adKey) {
        this.mAdKey = adKey;
        this.mActivity = activity;
        this.mRequest = new AdRequest.Builder().build();

        load();
    }

    /**
     * InterstitialAd load
     */
    private void load() {
        InterstitialAd.load(mActivity.getApplicationContext(), mAdKey, mRequest, new InterstitialAdLoadCallback() {

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                Log.d(TAG, "onAdLoaded()");
                AdInterstitialHelper.this.mInterstitial = interstitialAd;
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        AdInterstitialHelper.this.mInterstitial = null;
                        setShowInterstitial(false);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        AdInterstitialHelper.this.mInterstitial = null;
                        setShowInterstitial(false);
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        Log.d(TAG, "onAdShowedFullScreenContent()");
                    }

                });

            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.i(TAG, "onAdFailedToLoad() : ".concat(loadAdError.getMessage()));
                Log.i(TAG, String.format("domain: %s, code: %d, message: %s", loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage()));
                mInterstitial = null;
                setShowInterstitial(false);
            }

        });
    }

    /**
     * Interstitial Show
     */
    public void showInterstitial() {
        Log.d(TAG, "Interstital is ".concat(mInterstitial == null ? "null" : "is not null"));

        if (mInterstitial != null) {
            mInterstitial.show(mActivity);
            setShowInterstitial(true);
        } else {
            load();
        }
    }

    public MutableLiveData<Boolean> getShowInterstitial() {
        return showInterstitial;
    }

    private void setShowInterstitial(Boolean isVisible) {
        this.showInterstitial.postValue(isVisible);
    }
}
