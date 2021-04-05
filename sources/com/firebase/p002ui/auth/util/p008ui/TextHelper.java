package com.firebase.p002ui.auth.util.p008ui;

import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

/* renamed from: com.firebase.ui.auth.util.ui.TextHelper */
public class TextHelper {
    public static void boldAllOccurencesOfText(SpannableStringBuilder builder, String text, String textToBold) {
        int fromIndex = 0;
        while (fromIndex < text.length()) {
            int start = text.indexOf(textToBold, fromIndex);
            int end = textToBold.length() + start;
            if (start != -1 && end < text.length()) {
                builder.setSpan(new StyleSpan(1), start, end, 17);
                fromIndex = end + 1;
            } else {
                return;
            }
        }
    }
}
