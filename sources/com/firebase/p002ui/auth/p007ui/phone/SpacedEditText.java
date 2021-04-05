package com.firebase.p002ui.auth.p007ui.phone;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.widget.TextView;
import com.firebase.p002ui.auth.C0719R;
import com.google.android.material.textfield.TextInputEditText;

/* renamed from: com.firebase.ui.auth.ui.phone.SpacedEditText */
public final class SpacedEditText extends TextInputEditText {
    private SpannableStringBuilder mOriginalText = new SpannableStringBuilder("");
    private float mProportion;

    public SpacedEditText(Context context) {
        super(context);
    }

    public SpacedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    /* access modifiers changed from: package-private */
    public void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, C0719R.styleable.SpacedEditText);
        this.mProportion = array.getFloat(C0719R.styleable.SpacedEditText_spacingProportion, 1.0f);
        array.recycle();
    }

    public void setText(CharSequence text, TextView.BufferType type) {
        this.mOriginalText = new SpannableStringBuilder(text);
        super.setText(getSpacedOutString(text), TextView.BufferType.SPANNABLE);
    }

    public void setSelection(int index) {
        int newIndex = Math.min(Math.max((index * 2) - 1, 0), (this.mOriginalText.length() * 2) - 1);
        try {
            super.setSelection(newIndex);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException(e.getMessage() + ", requestedIndex=" + index + ", newIndex= " + newIndex + ", originalText=" + this.mOriginalText);
        }
    }

    private SpannableStringBuilder getSpacedOutString(CharSequence text) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        int textLength = text.length();
        int lastSpaceIndex = -1;
        for (int i = 0; i < textLength - 1; i++) {
            builder.append(text.charAt(i));
            builder.append(" ");
            lastSpaceIndex += 2;
            builder.setSpan(new ScaleXSpan(this.mProportion), lastSpaceIndex, lastSpaceIndex + 1, 33);
        }
        if (textLength != 0) {
            builder.append(text.charAt(textLength - 1));
        }
        return builder;
    }

    public Editable getUnspacedText() {
        return this.mOriginalText;
    }
}
