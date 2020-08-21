package edy.app.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;

import arte.programar.advertising.AdNativeView;
import arte.programar.advertising.helpers.AdInterstitialHelper;
import arte.programar.advertising.helpers.AdNativeHelper;
import arte.programar.advertising.helpers.AdViewHelper;
import arte.programar.network.ConnectionActivity;


public class MainActivity extends ConnectionActivity implements Connectable, Disconnectable {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String ID_AD_NATIVE = "ca-app-pub-3940256099942544/2247696110";
    private static final String ID_AD_VIEW = "ca-app-pub-3940256099942544/6300978111";
    private static final String ID_AD_INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712";

    // Views
    private AdNativeView mNativeView;
    private LinearLayoutCompat mContent;

    // Object
    private AdViewHelper adViewHelper;
    private AdInterstitialHelper adInterstitialHelper;

    // Thread
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNativeView = findViewById(R.id.nav);
        mContent = findViewById(R.id.adc);

        // Init Object
        MobileAds.initialize(getApplication());
        adViewHelper = new AdViewHelper(getApplication(), ID_AD_VIEW);
        adInterstitialHelper = AdInterstitialHelper.getInstance(getApplication(), ID_AD_INTERSTITIAL);
    }

    @Override
    protected Merlin createMerlin() {
        return new Merlin.Builder()
            .withConnectableCallbacks()
            .withDisconnectableCallbacks()
            .build(getApplicationContext());
    }

    private void nativeLoad(){
        AdNativeHelper.show(mNativeView, ID_AD_NATIVE);
    }

    private void adViewLoad(){
        adViewHelper.create();
        mContent.post(new Runnable() {
            @Override
            public void run() {
                mContent.removeAllViews();
                mContent.addView(adViewHelper.getAdView());
            }
        });
    }

    private void interstitialLoad(){
        adInterstitialHelper.show();
    }

    /**
     * Access to Internet (Ads Load)
     */
    @Override
    public void onConnect() {
        Log.d(TAG, "onConnect()");

        handler.post(new Runnable() {
            @Override
            public void run() {
                nativeLoad();
                adViewLoad();
                interstitialLoad();
            }
        });
    }

    @Override
    public void onDisconnect() {
        Log.d(TAG, "onDisconnect()");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplication(), "Internet not access", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerConnectable(this);
        registerDisconnectable(this);
    }

    @Override
    protected void onDestroy() {
        AdNativeHelper.destroy(mNativeView);
        adViewHelper.onDestroy();
        super.onDestroy();
    }
}