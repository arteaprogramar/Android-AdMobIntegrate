package arte.programar.advertising;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAd.Image;
import com.google.android.gms.ads.nativead.NativeAdView;


/**
 * Base class for a template view. *
 */
public class AdNativeView extends FrameLayout {

    // Constant
    private static final int SMALL_VIEW = R.layout.adt_small_template_view;
    private static final int MEDIUM_VIEW = R.layout.adt_medium_template_view;

    private static final String MEDIUM_TEMPLATE = "medium_template";
    private static final String SMALL_TEMPLATE = "small_template";
    private static final int DEFAULT_ATTR = 0;

    // AdNative Custom
    private Drawable adIcon;
    private Drawable adCover;
    private String adTitle;
    private String adSubtitle;
    private String adTextButton;

    // Variables
    private int templateType;
    private NativeAd nativeAd;
    private NativeAdView nativeAdView;
    private TextView primaryView;
    private TextView secondaryView;
    private RatingBar ratingBar;
    private TextView tertiaryView;
    private ImageView iconView;
    private MediaView mediaView;
    private Button callToActionView;
    private RelativeLayout background;
    private TextView ad_notification;


    /**
     * Constructor
     *
     * @param context
     */
    public AdNativeView(Context context) {
        super(context);
    }

    /**
     * Constructor
     *
     * @param context
     * @param attrs
     */
    public AdNativeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    /**
     * Constructor
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public AdNativeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    /**
     * Constructor
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AdNativeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    /**
     * Only Store
     *
     * @param nativeAd
     * @return
     */
    public static boolean adHasOnlyStore(NativeAd nativeAd) {
        String store = nativeAd.getStore();
        String advertiser = nativeAd.getAdvertiser();
        return !TextUtils.isEmpty(store) && TextUtils.isEmpty(advertiser);
    }

    /**
     * AdIcon
     *
     * @param adIcon
     */
    private void setAdIcon(Drawable adIcon) {
        if (adIcon == null) {
            this.adIcon = ContextCompat.getDrawable(getContext(), R.drawable.adt_logo);
        } else {
            this.adIcon = adIcon;
        }
    }

    private void setAdTitle(String adTitle) {
        if (adTitle == null) {
            this.adTitle = "Arte al Programar";
        } else {
            this.adTitle = adTitle;
        }
    }

    private void setAdSubtitle(String adSubtitle) {
        if (adSubtitle == null) {
            this.adSubtitle = "Dear user, thank you for using our application. We work very hard for you.";
        } else {
            this.adSubtitle = adSubtitle;
        }
    }

    private void setAdTextButton(String adTextButton) {
        if (adTextButton == null) {
            this.adTextButton = "Follow us";
        } else {
            this.adTextButton = adTextButton;
        }
    }

    private void setTemplateType(int templateType) {
        if (templateType == SMALL_VIEW || templateType == MEDIUM_VIEW) {
            this.templateType = templateType;
        } else {
            this.templateType = SMALL_VIEW;
        }
    }

    private void setAdCover(Drawable adCover) {
        if (adCover == null) {
            this.adCover = ContextCompat.getDrawable(getContext(), R.drawable.adt_cover);
        } else {
            this.adCover = adCover;
        }
    }

    private void set(String title, String subtitle, String textButton, Drawable icon) {
        setAdIcon(icon);
        setAdTitle(title);
        setAdSubtitle(subtitle);
        setAdTextButton(textButton);
    }

    /**
     * Create AdCustom
     *
     * @param title
     * @param subtitle
     * @param textButton
     * @param icon
     */
    public void createAdSmallCustom(String title, String subtitle, String textButton, Drawable icon) {
        set(title, subtitle, textButton, icon);

        primaryView.setText(adTitle);
        secondaryView.setText(adSubtitle);
        callToActionView.setText(adTextButton);
        iconView.setImageDrawable(icon);

        invalidate();
    }

    /**
     * Create AdCustom
     *
     * @param title
     * @param subtitle
     * @param textButton
     * @param icon
     * @param cover
     */
    public void createAdMediumCustom(String title, String subtitle, String textButton, Drawable icon, Drawable cover) {
        setAdCover(cover);
        mediaView.setBackground(cover);

        createAdSmallCustom(title, subtitle, textButton, icon);
    }

    /**
     * InitView
     *
     * @param context
     * @param attributeSet
     */
    private void initView(Context context, AttributeSet attributeSet) {
        TypedArray attr = context.getTheme().obtainStyledAttributes(
                attributeSet, R.styleable.AdNativeView, DEFAULT_ATTR, DEFAULT_ATTR
        );

        try {
            setTemplateType(attr.getResourceId(R.styleable.AdNativeView_adt_template, SMALL_VIEW));

            // AdCustom
            setAdIcon(attr.getDrawable(R.styleable.AdNativeView_adt_icon));
            setAdCover(attr.getDrawable(R.styleable.AdNativeView_adt_cover));
            setAdTitle(attr.getString(R.styleable.AdNativeView_adt_title));

            setAdSubtitle(attr.getString(R.styleable.AdNativeView_adt_subtitle));
            setAdTextButton(attr.getString(R.styleable.AdNativeView_adt_text_button));
        } finally {
            attr.recycle();
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(templateType, this);
    }

    /**
     * Get template type
     *
     * @return
     */
    public String getTemplateTypeName() {
        if (templateType == SMALL_VIEW) {
            return MEDIUM_TEMPLATE;
        } else if (templateType == MEDIUM_VIEW) {
            return SMALL_TEMPLATE;
        } else {
            return "";
        }
    }

    /**
     * CastView
     */
    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        nativeAdView = findViewById(R.id.native_ad_view);
        primaryView = findViewById(R.id.primary);
        secondaryView = findViewById(R.id.secondary);
        tertiaryView = findViewById(R.id.body);

        ratingBar = findViewById(R.id.rating_bar);
        ratingBar.setEnabled(false);

        callToActionView = findViewById(R.id.cta);

        iconView = findViewById(R.id.icon);

        mediaView = findViewById(R.id.media_view);
        background = findViewById(R.id.background);

        ad_notification = findViewById(R.id.ad_notification_view);

        // CustomViews
        iconView.setImageDrawable(adIcon);
        primaryView.setText(adTitle);
        secondaryView.setText(adSubtitle);
        callToActionView.setText(adTextButton);

        if (templateType == MEDIUM_VIEW) {
            mediaView.setBackground(adCover);
            mediaView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    /**
     * Show AdLoad
     *
     * @param nativeAd
     */
    public void setNativeAd(NativeAd nativeAd) {
        this.nativeAd = nativeAd;

        callToActionView.setVisibility(View.VISIBLE);
        ad_notification.setVisibility(View.VISIBLE);

        String store = nativeAd.getStore();
        String advertiser = nativeAd.getAdvertiser();
        String headline = nativeAd.getHeadline();
        String body = nativeAd.getBody();
        String cta = nativeAd.getCallToAction();
        Double starRating = nativeAd.getStarRating();
        Image icon = nativeAd.getIcon();

        String secondaryText;

        nativeAdView.setCallToActionView(callToActionView);
        nativeAdView.setHeadlineView(primaryView);

        if (templateType == MEDIUM_VIEW) {
            mediaView.setBackground(null);
            nativeAdView.setMediaView(mediaView);
        }

        secondaryView.setVisibility(VISIBLE);
        if (adHasOnlyStore(nativeAd)) {
            nativeAdView.setStoreView(secondaryView);
            secondaryText = store;
        } else if (!TextUtils.isEmpty(advertiser)) {
            nativeAdView.setAdvertiserView(secondaryView);
            secondaryText = advertiser;
        } else {
            secondaryText = "";
        }

        primaryView.setText(headline);
        callToActionView.setText(cta);

        //  Set the secondary view to be the star rating if available.
        if (starRating != null && starRating > 0) {
            secondaryView.setVisibility(GONE);
            ratingBar.setVisibility(VISIBLE);
            ratingBar.setMax(5);
            nativeAdView.setStarRatingView(ratingBar);
        } else {
            secondaryView.setText(secondaryText);
            secondaryView.setVisibility(VISIBLE);
            ratingBar.setVisibility(GONE);
        }

        if (icon != null) {
            iconView.setVisibility(VISIBLE);
            iconView.setImageDrawable(icon.getDrawable());
        } else {
            iconView.setVisibility(GONE);
        }

        if (tertiaryView != null) {
            tertiaryView.setText(body);
            nativeAdView.setBodyView(tertiaryView);
        }

        nativeAdView.setNativeAd(nativeAd);
    }

    /**
     * Get Native AdView
     *
     * @return
     */
    public NativeAdView getNativeAdView() {
        return nativeAdView;
    }

    /**
     * To prevent memory leaks, make sure to destroy your ad when you don't need it anymore. This
     * method does not destroy the template view.
     * https://developers.google.com/admob/android/native-unified#destroy_ad
     */
    public void destroyNativeAd() {
        if (nativeAd != null) {
            nativeAd.destroy();
        }
    }
}
