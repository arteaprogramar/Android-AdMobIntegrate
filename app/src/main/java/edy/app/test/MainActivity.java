package edy.app.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.gms.ads.MobileAds;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.helper.MerlinActivity;

import arte.programar.advertising.AdNativeView;
import arte.programar.advertising.helpers.AdInterstitialHelper;
import arte.programar.advertising.helpers.AdNativeHelper;
import arte.programar.advertising.helpers.AdViewHelper;

public class MainActivity extends MerlinActivity implements Connectable, Disconnectable {
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
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNativeView = findViewById(R.id.nav);
        mContent = findViewById(R.id.adc);

        // Init Object
        MobileAds.initialize(this, initializationStatus -> {
        });

        adViewHelper = new AdViewHelper(getApplication(), ID_AD_VIEW);
        adInterstitialHelper = new AdInterstitialHelper(this, ID_AD_INTERSTITIAL);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerConnectable(this);
        registerDisconnectable(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        AdNativeHelper.destroy(mNativeView);
        adViewHelper.onDestroy();
        super.onDestroy();
    }

    @Override
    protected Merlin createMerlin() {
        return new Merlin.Builder()
                .withConnectableCallbacks()
                .withDisconnectableCallbacks()
                .build(getApplicationContext());
    }

    @Override
    public void onConnect() {
        Log.d(TAG, "onConnect()");

        handler.post(() -> {
            nativeLoad();
            adViewLoad();
        });

        handler.postDelayed(this::interstitialLoad, 3000);
    }

    @Override
    public void onDisconnect() {
        Log.d(TAG, "onDisconnect()");
        runOnUiThread(() -> Toast.makeText(getApplication(), "Internet not access", Toast.LENGTH_LONG).show());
    }

    private void nativeLoad() {
        AdNativeHelper.show(mNativeView, ID_AD_NATIVE);
    }

    private void adViewLoad() {
        adViewHelper.create();

        mContent.post(() -> {
            mContent.removeAllViews();
            mContent.addView(adViewHelper.getAdView());
        });
    }

    private void interstitialLoad() {
        adInterstitialHelper.showInterstitial();
    }
}