package com.codeogenic.checkablebutton;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;


/**
 * Created by clem_gumbs on 2/3/17.
 */

public class CheckableButton extends LinearLayout implements Checkable {


    private Context mContext;

    // # Background Style Attributes
    private int checkedBackgroundColor = Color.BLACK;
    private int uncheckedBackgroundColor = Color.BLACK;
    private int mFocusBackgroundColor = Color.TRANSPARENT;
    private int mDisabledBackgroundColor = Color.parseColor("#f6f7f9");
    private int mDisabledTextColor = Color.parseColor("#bec2c9");
    private int mDisabledBorderColor = Color.parseColor("#dddfe2");
    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    // # Text Style Attributes
    private int checkedTextColor = Color.WHITE;
    private int uncheckedTextColor = Color.WHITE;
    private int mTextPosition = 1;
    private int mDefaultTextSize = Utils.spToPx(getContext(), 15);
    private int mDefaultTextGravity = 0x11; // Gravity.CENTER
    private String mText = null;
    private String mDefaultTextFont = "";

    private int mBorderColor = Color.TRANSPARENT;
    private int checkedBorderColor = Color.TRANSPARENT;
    private int mBorderWidth = 0;
    private int checkedBorderWidth = 0;
    private Typeface mTextTypeFace;
    private boolean disableClick;

    private int mRadius = 0;

    private boolean mTextAllCaps = false;
    private boolean isRippleEffect = false;
    private TextView mTextView;
    private OnCheckedChangeListener mListener;
    private boolean isChecked;

    public CheckableButton(Context context) {
        super(context);
        this.mContext = context;
        mTextTypeFace = Utils.findFont(mContext, mDefaultTextFont, null);
        init();
    }

    public CheckableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray attrsArray = context.obtainStyledAttributes(attrs, R.styleable.CheckableButton, 0, 0);
        initAttributesArray(attrs, attrsArray);
        attrsArray.recycle();
        init();
    }

    /**
     * Initialize CheckableButton View
     */
    private void init() {

        //Init The container view: LinearLayout
        this.setOrientation(LinearLayout.VERTICAL);
        LayoutParams containerParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(containerParams);
        this.setGravity(Gravity.CENTER);
        this.setClickable(true);
        this.setFocusable(true);
        if (getPaddingLeft() == 0 && getPaddingRight() == 0 && getPaddingTop() == 0 && getPaddingBottom() == 0) {
            this.setPadding(10, 10, 10, 10);
        }

        //init the Textview:
        setUpTextView();
        setUpTextColorStates();

        setupBackground();

        // for (View view : views) {
        //this.addView(mTextView);
        //}
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        if (isChecked) {
            setChecked(true);
        }


    }

    private void setUpTextView() {
        if (mText != null) {
            mTextView = new TextView(mContext);
            mTextView.setText(mText);
            mTextView.setGravity(mDefaultTextGravity);
            // mTextView.setTextColor(isEnabled() ? checkedTextColor : mDisabledTextColor);
            mTextView.setTextSize(Utils.pxToSp(getContext(), mDefaultTextSize));

            mTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            if (!isInEditMode() && mTextTypeFace != null) {
                mTextView.setTypeface(mTextTypeFace);
            }
        }
    }

    private void setUpTextColorStates() {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled}, // enabled
                new int[]{-android.R.attr.state_enabled}, // disabled
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_checked},  // pressed
                new int[]{android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[]{
                uncheckedTextColor,
                mDisabledTextColor,
                uncheckedTextColor,
                checkedTextColor,
                uncheckedTextColor,
        };

        ColorStateList myList = new ColorStateList(states, colors);
        mTextView.setTextColor(myList);

        if (mTextView != null) {
            this.addView(mTextView);
        }
    }

    private void initAttributesArray(AttributeSet attrs, TypedArray attrsArray) {

        checkedBackgroundColor = attrsArray.getColor(R.styleable.CheckableButton_cb_checkedColor, checkedBackgroundColor);
        uncheckedBackgroundColor = attrsArray.getColor(R.styleable.CheckableButton_cb_unCheckColor, uncheckedBackgroundColor);
        //mFocusBackgroundColor = lightenColor(uncheckedBackgroundColor);
        mDisabledBackgroundColor = attrsArray.getColor(R.styleable.CheckableButton_cb_disabledColor, mDisabledBackgroundColor);

        this.setEnabled(attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "enabled", true));

        mDisabledTextColor = attrsArray.getColor(R.styleable.CheckableButton_cb_disabledTextColor, mDisabledTextColor);
        mDisabledBorderColor = attrsArray.getColor(R.styleable.CheckableButton_cb_disabledBorderColor, mDisabledBorderColor);
        checkedTextColor = attrsArray.getColor(R.styleable.CheckableButton_cb_checkedTextColor, checkedTextColor);
        uncheckedTextColor = attrsArray.getColor(R.styleable.CheckableButton_cb_uncheckedTextColor, uncheckedTextColor);
        // if default color is set then the icon's color is the same (the default for icon's color)
        isChecked = attrsArray.getBoolean(R.styleable.CheckableButton_cb_isChecked, false);
        mDefaultTextSize = (int) attrsArray.getDimension(R.styleable.CheckableButton_cb_textSize, mDefaultTextSize);
        mDefaultTextGravity = attrsArray.getInt(R.styleable.CheckableButton_cb_textGravity, mDefaultTextGravity);

        mBorderColor = attrsArray.getColor(R.styleable.CheckableButton_cb_unCheckBorderColor, mBorderColor);
        checkedBorderColor = attrsArray.getColor(R.styleable.CheckableButton_cb_checkedBorderColor, checkedBorderColor);
        mBorderWidth = (int) attrsArray.getDimension(R.styleable.CheckableButton_cb_borderWidth, mBorderWidth);
        checkedBorderWidth = (int) attrsArray.getDimension(R.styleable.CheckableButton_cb_checkedborderWidth, checkedBorderWidth);

        mRadius = (int) attrsArray.getDimension(R.styleable.CheckableButton_cb_radius, mRadius);

        mTextAllCaps = attrsArray.getBoolean(R.styleable.CheckableButton_cb_textAllCaps, false);

        String text = attrsArray.getString(R.styleable.CheckableButton_cb_text);
        if (text != null)
            mText = mTextAllCaps ? text.toUpperCase() : text;
    }

    /**
     * Uses the backgroud color of the unchecked state to create a lighter color
     * to be used for the focusable drawable
     *
     * @param color
     * @return
     */
    private int lightenColor(int color) {
        float[] hsv = new float[3];
        int mColor;
        Color.colorToHSV(color, hsv);
        // hsv[2] *= 0.8f; // value component darken color
        hsv[2] = 1.0f - 0.8f * (1.0f - hsv[2]);
        mColor = Color.HSVToColor(hsv);
        return mColor;
    }

    /**
     * SETup container's background
     * assign drawable states
     */
    private void setupBackground() {

        // Default Drawable
        GradientDrawable defaultDrawable = new GradientDrawable();
        defaultDrawable.setCornerRadius(mRadius);
        defaultDrawable.setColor(uncheckedBackgroundColor);

        //Focus Drawable
        lightenColor(uncheckedBackgroundColor);
        GradientDrawable focusDrawable = new GradientDrawable();
        focusDrawable.setCornerRadius(mRadius);
        focusDrawable.setColor(mFocusBackgroundColor);

        // Disabled Drawable
        GradientDrawable disabledDrawable = new GradientDrawable();
        disabledDrawable.setCornerRadius(mRadius);
        disabledDrawable.setColor(mDisabledBackgroundColor);
        disabledDrawable.setStroke(mBorderWidth, mDisabledBorderColor);

        // Disabled Drawable disabled
        GradientDrawable disabledDrawable2 = new GradientDrawable();
        disabledDrawable2.setCornerRadius(mRadius);
        disabledDrawable2.setColor(mDisabledBackgroundColor);
        disabledDrawable2.setStroke(mBorderWidth, mDisabledBorderColor);

        // checked Drawable
        GradientDrawable drawable3 = new GradientDrawable();
        drawable3.setCornerRadius(mRadius);
        drawable3.setColor(checkedBackgroundColor);


        // Handle Border
        if (mBorderColor != 0) {
            defaultDrawable.setStroke(mBorderWidth, mBorderColor);
            disabledDrawable.setStroke(mBorderWidth, mBorderColor);
        }
        if (checkedBorderColor != 0) {
            drawable3.setStroke(checkedBorderWidth, checkedBorderColor);
            disabledDrawable2.setStroke(mBorderWidth, checkedBorderColor);
        }

        // Handle disabled border color
        if (!isEnabled()) {
            defaultDrawable.setStroke(mBorderWidth, mDisabledBorderColor);
        }


        if (isRippleEffect && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setBackground(getRippleDrawable(defaultDrawable, focusDrawable, disabledDrawable));

        } else {

            StateListDrawable states = new StateListDrawable();
            // Focus/Pressed Drawable
            GradientDrawable drawable2 = new GradientDrawable();
            drawable2.setCornerRadius(mRadius);
            drawable2.setColor(mFocusBackgroundColor);

            // Handle Button Border
            if (mBorderColor != 0) {
                drawable2.setStroke(mBorderWidth, mBorderColor);
            }

            if (!isEnabled()) {
                drawable2.setStroke(mBorderWidth, mDisabledBorderColor);
            }

            if (mFocusBackgroundColor != 0) {
                states.addState(new int[]{android.R.attr.state_pressed}, drawable2);
                states.addState(new int[]{android.R.attr.state_focused}, drawable2);
                states.addState(new int[]{-android.R.attr.state_enabled}, disabledDrawable);
                states.addState(new int[]{-android.R.attr.state_enabled, -android.R.attr.state_checked}, disabledDrawable2);
            }
            if (checkedBackgroundColor != 0) {
                states.addState(new int[]{android.R.attr.state_checked}, drawable3);
                states.addState(new int[]{-android.R.attr.state_checked}, defaultDrawable);
            }
            states.addState(new int[]{}, defaultDrawable);


            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                this.setBackgroundDrawable(states);
            } else {
                this.setBackground(states);
            }

        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Drawable getRippleDrawable(Drawable defaultDrawable, Drawable focusDrawable, Drawable disabledDrawable) {
        if (!isEnabled()) {
            return disabledDrawable;
        } else {
            return new RippleDrawable(ColorStateList.valueOf(mFocusBackgroundColor), defaultDrawable, focusDrawable);
        }

    }

    @Override
    public void setChecked(boolean checked) {
        if (isChecked && !checked) {
            switchToHide();
            isChecked = false;
        } else if (!isChecked && checked) {
            switchToCheck();
            isChecked = true;
        }
        refreshDrawableState();
        if (mListener != null) {
            mListener.onCheckedChanged(this, checked);
        }
    }

    private void switchToCheck() {
        //Might apply checked animations in the future
        //invalidate();
    }

    private void switchToHide() {
        //Might apply checked animations in the future
        // invalidate();
    }


    public TextView getTextView() {
        return mTextView;
    }

    public void setTextView(TextView mTextView) {
        this.mTextView = mTextView;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        this.setChecked(!isChecked());
    }

    public void setOnCheckChangeLisnter(OnCheckedChangeListener onCheckChangeLisnter) {
        mListener = onCheckChangeLisnter;
    }

    public void setText(String text) {
        if (mTextView != null)
            this.mTextView.setText(text);
        else
            mText = text;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(View buttonView, boolean isChecked);
    }

    public int getCheckedBackgroundColor() {
        return checkedBackgroundColor;
    }

    public void setCheckedBackgroundColor(int checkedBackgroundColor) {
        this.checkedBackgroundColor = checkedBackgroundColor;
        setupBackground();
    }

    public int getUncheckedBackgroundColor() {
        return uncheckedBackgroundColor;
    }

    public void setUncheckedBackgroundColor(int uncheckedBackgroundColor) {
        this.uncheckedBackgroundColor = uncheckedBackgroundColor;
        setupBackground();
    }

    public int getFocusBackgroundColor() {
        return mFocusBackgroundColor;
    }

    /**
     * Right Now this is disabled
     * @param mFocusBackgroundColor
     */
    public void setFocusBackgroundColor(int mFocusBackgroundColor) {
        this.mFocusBackgroundColor = mFocusBackgroundColor;
        setupBackground();
    }

    public int getDisabledBackgroundColor() {
        return mDisabledBackgroundColor;
    }

    public void setDisabledBackgroundColor(int mDisabledBackgroundColor) {
        this.mDisabledBackgroundColor = mDisabledBackgroundColor;
        setupBackground();
    }

    public int getDisabledTextColor() {
        return mDisabledTextColor;
    }

    public void setDisabledTextColor(int mDisabledTextColor) {
        this.mDisabledTextColor = mDisabledTextColor;
    }

    public int getDisabledBorderColor() {
        return mDisabledBorderColor;
    }

    public void setDisabledBorderColor(int mDisabledBorderColor) {
        this.mDisabledBorderColor = mDisabledBorderColor;
        setupBackground();
    }

    public int getCheckedTextColor() {
        return checkedTextColor;
    }

    public void setCheckedTextColor(int checkedTextColor) {
        this.checkedTextColor = checkedTextColor;
    }

    public int getUncheckedTextColor() {
        return uncheckedTextColor;
    }

    public void setUncheckedTextColor(int uncheckedTextColor) {
        this.uncheckedTextColor = uncheckedTextColor;
        setupBackground();
    }

    public int getDefaultTextSize() {
        return mDefaultTextSize;
    }

    public void setDefaultTextSize(int mDefaultTextSize) {
        this.mDefaultTextSize = Utils.spToPx(getContext(), mDefaultTextSize);
    }

    public int getDefaultTextGravity() {
        return mDefaultTextGravity;
    }

    public void setDefaultTextGravity(int mDefaultTextGravity) {
        this.mDefaultTextGravity = mDefaultTextGravity;
    }

    public String getText() {
        return mText;
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setBorderColor(int mBorderColor) {
        this.mBorderColor = mBorderColor;
        setupBackground();
    }

    public int getCheckedBorderColor() {
        return checkedBorderColor;
    }

    public void setCheckedBorderColor(int checkedBorderColor) {
        this.checkedBorderColor = checkedBorderColor;
        setupBackground();
    }

    public int getBorderWidth() {
        return mBorderWidth;
    }

    public void setBorderWidth(int mBorderWidth) {
        this.mBorderWidth = mBorderWidth;
        setupBackground();
    }

    public int getCheckedBorderWidth() {
        return checkedBorderWidth;
    }

    public void setCheckedBorderWidth(int checkedBorderWidth) {
        this.checkedBorderWidth = checkedBorderWidth;
        setupBackground();
    }

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int mRadius) {
        this.mRadius = mRadius;
        setupBackground();
    }
}
