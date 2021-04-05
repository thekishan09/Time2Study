package com.firebase.p002ui.auth.util.p008ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.TextViewCompat;
import com.firebase.p002ui.auth.C0719R;

/* renamed from: com.firebase.ui.auth.util.ui.SupportVectorDrawablesButton */
public class SupportVectorDrawablesButton extends AppCompatButton {
    public SupportVectorDrawablesButton(Context context) {
        super(context);
    }

    public SupportVectorDrawablesButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSupportVectorDrawablesAttrs(attrs);
    }

    public SupportVectorDrawablesButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSupportVectorDrawablesAttrs(attrs);
    }

    private void initSupportVectorDrawablesAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributeArray = getContext().obtainStyledAttributes(attrs, C0719R.styleable.SupportVectorDrawablesButton);
            Drawable drawableStart = null;
            Drawable drawableEnd = null;
            Drawable drawableTop = null;
            Drawable drawableBottom = null;
            if (Build.VERSION.SDK_INT >= 21) {
                drawableStart = attributeArray.getDrawable(C0719R.styleable.SupportVectorDrawablesButton_drawableStartCompat);
                drawableEnd = attributeArray.getDrawable(C0719R.styleable.SupportVectorDrawablesButton_drawableEndCompat);
                drawableTop = attributeArray.getDrawable(C0719R.styleable.SupportVectorDrawablesButton_drawableTopCompat);
                drawableBottom = attributeArray.getDrawable(C0719R.styleable.SupportVectorDrawablesButton_drawableBottomCompat);
            } else {
                int drawableStartId = attributeArray.getResourceId(C0719R.styleable.SupportVectorDrawablesButton_drawableStartCompat, -1);
                int drawableEndId = attributeArray.getResourceId(C0719R.styleable.SupportVectorDrawablesButton_drawableEndCompat, -1);
                int drawableTopId = attributeArray.getResourceId(C0719R.styleable.SupportVectorDrawablesButton_drawableTopCompat, -1);
                int drawableBottomId = attributeArray.getResourceId(C0719R.styleable.SupportVectorDrawablesButton_drawableBottomCompat, -1);
                if (drawableStartId != -1) {
                    drawableStart = AppCompatResources.getDrawable(getContext(), drawableStartId);
                }
                if (drawableEndId != -1) {
                    drawableEnd = AppCompatResources.getDrawable(getContext(), drawableEndId);
                }
                if (drawableTopId != -1) {
                    drawableTop = AppCompatResources.getDrawable(getContext(), drawableTopId);
                }
                if (drawableBottomId != -1) {
                    drawableBottom = AppCompatResources.getDrawable(getContext(), drawableBottomId);
                }
            }
            TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds((TextView) this, drawableStart, drawableTop, drawableEnd, drawableBottom);
            attributeArray.recycle();
        }
    }
}
