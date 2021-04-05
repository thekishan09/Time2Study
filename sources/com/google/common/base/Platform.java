package com.google.common.base;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.ServiceConfigurationError;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Platform {
    private static final String GWT_RPC_PROPERTY_NAME = "guava.gwt.emergency_reenable_rpc";
    private static final Logger logger = Logger.getLogger(Platform.class.getName());
    private static final PatternCompiler patternCompiler = loadPatternCompiler();

    private Platform() {
    }

    static long systemNanoTime() {
        return System.nanoTime();
    }

    static CharMatcher precomputeCharMatcher(CharMatcher matcher) {
        return matcher.precomputedInternal();
    }

    static <T extends Enum<T>> Optional<T> getEnumIfPresent(Class<T> enumClass, String value) {
        WeakReference<? extends Enum<?>> ref = Enums.getEnumConstants(enumClass).get(value);
        return ref == null ? Optional.absent() : Optional.m398of(enumClass.cast(ref.get()));
    }

    static String formatCompact4Digits(double value) {
        return String.format(Locale.ROOT, "%.4g", new Object[]{Double.valueOf(value)});
    }

    static boolean stringIsNullOrEmpty(@NullableDecl String string) {
        return string == null || string.isEmpty();
    }

    static String nullToEmpty(@NullableDecl String string) {
        return string == null ? "" : string;
    }

    static String emptyToNull(@NullableDecl String string) {
        if (stringIsNullOrEmpty(string)) {
            return null;
        }
        return string;
    }

    static CommonPattern compilePattern(String pattern) {
        Preconditions.checkNotNull(pattern);
        return patternCompiler.compile(pattern);
    }

    static boolean patternCompilerIsPcreLike() {
        return patternCompiler.isPcreLike();
    }

    private static PatternCompiler loadPatternCompiler() {
        return new JdkPatternCompiler();
    }

    private static void logPatternCompilerError(ServiceConfigurationError e) {
        logger.log(Level.WARNING, "Error loading regex compiler, falling back to next option", e);
    }

    private static final class JdkPatternCompiler implements PatternCompiler {
        private JdkPatternCompiler() {
        }

        public CommonPattern compile(String pattern) {
            return new JdkPattern(Pattern.compile(pattern));
        }

        public boolean isPcreLike() {
            return true;
        }
    }

    static void checkGwtRpcEnabled() {
        if (!Boolean.parseBoolean(System.getProperty(GWT_RPC_PROPERTY_NAME, "true"))) {
            throw new UnsupportedOperationException(Strings.lenientFormat("We are removing GWT-RPC support for Guava types. You can temporarily reenable support by setting the system property %s to true. For more about system properties, see %s. For more about Guava's GWT-RPC support, see %s.", GWT_RPC_PROPERTY_NAME, "https://stackoverflow.com/q/5189914/28465", "https://groups.google.com/d/msg/guava-announce/zHZTFg7YF3o/rQNnwdHeEwAJ"));
        }
    }
}
