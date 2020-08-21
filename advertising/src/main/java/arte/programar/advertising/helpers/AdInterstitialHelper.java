package arte.programar.advertising.helpers;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AdInterstitialHelper extends AdListener {
    private static final String TAG = "arte.programar::".concat(AdInterstitialHelper.class.getSimpleName());

    // Instance
    private static AdInterstitialHelper instance;

    /**
     * Instance
     *
     * @param context
     * @param adKey
     * @return
     */
    public static AdInterstitialHelper getInstance(Context context, String adKey) {
        if (instance == null) {
            synchronized (AdInterstitialHelper.class) {
                if (instance == null) {
                    instance = new AdInterstitialHelper(context, adKey, new AdRequest.Builder().build());
                }
            }
        }

        return instance;
    }

    /**
     * Instance
     * @param context
     * @param adKey
     * @param request
     * @return
     */
    public static AdInterstitialHelper getInstance(Context context, String adKey, AdRequest request) {
        if (instance == null) {
            synchronized (AdInterstitialHelper.class) {
                if (instance == null) {
                    instance = new AdInterstitialHelper(context, adKey, request);
                }
            }
        }

        return instance;
    }

    public static void destroyInstance(){
        if (instance != null) {
            instance = null;
        }
    }

    // Variables
    private AdRequest mRequest;
    private InterstitialAd mInterstitial;
    private MutableLiveData<Boolean> showInterstitial = new MutableLiveData<>(false);

    /**
     * Constructor
     *
     * @param context
     * @param adKey
     * @param request
     */
    private AdInterstitialHelper(Context context, String adKey, AdRequest request){
        mRequest = request;
        mInterstitial = new InterstitialAd(context);
        mInterstitial.setAdUnitId(adKey);
    }

    /**
     * Interstitial Show
     */
    public void show() {
        if (mInterstitial != null) {
            mInterstitial.setAdListener(this);
            mInterstitial.loadAd(mRequest);
        }
    }

    @Override
    public void onAdLoaded() {
        if (mInterstitial != null) {
            if (mInterstitial.isLoaded()) {
                mInterstitial.show();
                showInterstitial.postValue(true);
            } else {
                showInterstitial.postValue(false);
            }
        }
    }

    @Override
    public void onAdClosed() {
        showInterstitial.postValue(false);
    }

}
