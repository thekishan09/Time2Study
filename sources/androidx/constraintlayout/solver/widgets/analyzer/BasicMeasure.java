package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.Optimizer;
import androidx.constraintlayout.solver.widgets.VirtualLayout;
import java.util.ArrayList;
import java.util.Iterator;

public class BasicMeasure {
    public static final int AT_MOST = Integer.MIN_VALUE;
    private static final boolean DEBUG = false;
    public static final int EXACTLY = 1073741824;
    public static final int FIXED = -3;
    public static final int MATCH_PARENT = -1;
    private static final int MODE_SHIFT = 30;
    public static final int UNSPECIFIED = 0;
    public static final int WRAP_CONTENT = -2;
    private ConstraintWidgetContainer constraintWidgetContainer;
    private Measure mMeasure = new Measure();
    private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets = new ArrayList<>();

    public static class Measure {
        public ConstraintWidget.DimensionBehaviour horizontalBehavior;
        public int horizontalDimension;
        public int measuredBaseline;
        public boolean measuredHasBaseline;
        public int measuredHeight;
        public boolean measuredNeedsSolverPass;
        public int measuredWidth;
        public boolean useDeprecated;
        public ConstraintWidget.DimensionBehaviour verticalBehavior;
        public int verticalDimension;
    }

    public enum MeasureType {
    }

    public interface Measurer {
        void didMeasures();

        void measure(ConstraintWidget constraintWidget, Measure measure);
    }

    public void updateHierarchy(ConstraintWidgetContainer layout) {
        this.mVariableDimensionsWidgets.clear();
        int childCount = layout.mChildren.size();
        for (int i = 0; i < childCount; i++) {
            ConstraintWidget widget = (ConstraintWidget) layout.mChildren.get(i);
            if (widget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || widget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT || widget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || widget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                this.mVariableDimensionsWidgets.add(widget);
            }
        }
        layout.invalidateGraph();
    }

    public BasicMeasure(ConstraintWidgetContainer constraintWidgetContainer2) {
        this.constraintWidgetContainer = constraintWidgetContainer2;
    }

    private void measureChildren(ConstraintWidgetContainer layout) {
        int childCount = layout.mChildren.size();
        Measurer measurer = layout.getMeasurer();
        for (int i = 0; i < childCount; i++) {
            ConstraintWidget child = (ConstraintWidget) layout.mChildren.get(i);
            if (!(child instanceof Guideline) && (!child.horizontalRun.dimension.resolved || !child.verticalRun.dimension.resolved)) {
                ConstraintWidget.DimensionBehaviour widthBehavior = child.getDimensionBehaviour(0);
                boolean skip = true;
                ConstraintWidget.DimensionBehaviour heightBehavior = child.getDimensionBehaviour(1);
                if (widthBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || child.mMatchConstraintDefaultWidth == 1 || heightBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || child.mMatchConstraintDefaultHeight == 1) {
                    skip = false;
                }
                if (!skip) {
                    measure(measurer, child, false);
                }
            }
        }
        measurer.didMeasures();
    }

    private void solveLinearSystem(ConstraintWidgetContainer layout, String reason, int w, int h) {
        int minWidth = layout.getMinWidth();
        int minHeight = layout.getMinHeight();
        layout.setMinWidth(0);
        layout.setMinHeight(0);
        layout.setWidth(w);
        layout.setHeight(h);
        layout.setMinWidth(minWidth);
        layout.setMinHeight(minHeight);
        this.constraintWidgetContainer.layout();
    }

    public void solverMeasure(ConstraintWidgetContainer layout, int optimizationLevel, int paddingX, int paddingY, int widthMode, int widthSize, int heightMode, int heightSize, int lastMeasureWidth, int lastMeasureHeight) {
        int heightSize2;
        int widthSize2;
        boolean allSolved;
        int optimizations;
        Measurer measurer;
        boolean containerWrapWidth;
        int j;
        int sizeDependentWidgetsCount;
        int maxIterations;
        int optimizations2;
        int heightSize3;
        ConstraintWidgetContainer constraintWidgetContainer2 = layout;
        int i = optimizationLevel;
        int i2 = widthMode;
        int i3 = heightMode;
        Measurer measurer2 = layout.getMeasurer();
        int childCount = constraintWidgetContainer2.mChildren.size();
        int startingWidth = layout.getWidth();
        int startingHeight = layout.getHeight();
        boolean optimizeWrap = Optimizer.enabled(i, 128);
        boolean optimize = optimizeWrap || Optimizer.enabled(i, 64);
        if (optimize) {
            int i4 = 0;
            while (true) {
                if (i4 >= childCount) {
                    break;
                }
                ConstraintWidget child = (ConstraintWidget) constraintWidgetContainer2.mChildren.get(i4);
                boolean ratio = (child.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (child.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && child.getDimensionRatio() > 0.0f;
                if (!child.isInHorizontalChain() || !ratio) {
                    if (child.isInVerticalChain() && ratio) {
                        optimize = false;
                        break;
                    } else if (child instanceof VirtualLayout) {
                        optimize = false;
                        break;
                    } else if (child.isInHorizontalChain() || child.isInVerticalChain()) {
                        optimize = false;
                    } else {
                        i4++;
                        int i5 = optimizationLevel;
                    }
                } else {
                    optimize = false;
                    break;
                }
            }
        }
        if (optimize && LinearSystem.sMetrics != null) {
            LinearSystem.sMetrics.measures++;
        }
        boolean allSolved2 = false;
        boolean optimize2 = ((i2 == 1073741824 && i3 == 1073741824) || optimizeWrap) & optimize;
        int computations = 0;
        if (optimize2) {
            widthSize2 = Math.min(layout.getMaxWidth(), widthSize);
            heightSize2 = Math.min(layout.getMaxHeight(), heightSize);
            if (i2 == 1073741824 && layout.getWidth() != widthSize2) {
                constraintWidgetContainer2.setWidth(widthSize2);
                layout.invalidateGraph();
            }
            if (i3 == 1073741824 && layout.getHeight() != heightSize2) {
                constraintWidgetContainer2.setHeight(heightSize2);
                layout.invalidateGraph();
            }
            if (i2 == 1073741824 && i3 == 1073741824) {
                allSolved = constraintWidgetContainer2.directMeasure(optimizeWrap);
                computations = 2;
            } else {
                allSolved = constraintWidgetContainer2.directMeasureSetup(optimizeWrap);
                if (i2 == 1073741824) {
                    allSolved &= constraintWidgetContainer2.directMeasureWithOrientation(optimizeWrap, 0);
                    computations = 0 + 1;
                }
                if (i3 == 1073741824) {
                    allSolved &= constraintWidgetContainer2.directMeasureWithOrientation(optimizeWrap, 1);
                    computations++;
                }
            }
            if (allSolved) {
                constraintWidgetContainer2.updateFromRuns(i2 == 1073741824, i3 == 1073741824);
            }
        } else {
            int heightSize4 = widthSize;
            int i6 = heightSize;
            constraintWidgetContainer2.horizontalRun.clear();
            constraintWidgetContainer2.verticalRun.clear();
            Iterator<ConstraintWidget> it = layout.getChildren().iterator();
            while (it.hasNext()) {
                ConstraintWidget child2 = it.next();
                child2.horizontalRun.clear();
                child2.verticalRun.clear();
                allSolved2 = allSolved;
            }
            widthSize2 = heightSize4;
            heightSize2 = i6;
        }
        if (!allSolved || computations != 2) {
            if (childCount > 0) {
                measureChildren(layout);
            }
            int optimizations3 = layout.getOptimizationLevel();
            int sizeDependentWidgetsCount2 = this.mVariableDimensionsWidgets.size();
            if (childCount > 0) {
                boolean z = allSolved;
                solveLinearSystem(constraintWidgetContainer2, "First pass", startingWidth, startingHeight);
            }
            if (sizeDependentWidgetsCount2 > 0) {
                boolean containerWrapWidth2 = layout.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                boolean containerWrapHeight = layout.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                int i7 = childCount;
                boolean z2 = optimize2;
                int i8 = 0;
                int minWidth = Math.max(layout.getWidth(), this.constraintWidgetContainer.getMinWidth());
                int minWidth2 = optimizeWrap;
                int minHeight = Math.max(layout.getHeight(), this.constraintWidgetContainer.getMinHeight());
                boolean measuredHeight = false;
                while (i8 < sizeDependentWidgetsCount2) {
                    int computations2 = computations;
                    ConstraintWidget widget = this.mVariableDimensionsWidgets.get(i8);
                    int widthSize3 = widthSize2;
                    if ((widget instanceof VirtualLayout) == 0) {
                        optimizations2 = optimizations3;
                        heightSize3 = heightSize2;
                    } else {
                        int preWidth = widget.getWidth();
                        heightSize3 = heightSize2;
                        int heightSize5 = widget.getHeight();
                        optimizations2 = optimizations3;
                        int measuredWidth = widget.getWidth();
                        boolean needSolverPass = measuredHeight | measure(measurer2, widget, true);
                        int measuredHeight2 = widget.getHeight();
                        if (measuredWidth != preWidth) {
                            widget.setWidth(measuredWidth);
                            if (containerWrapWidth2) {
                                int i9 = measuredWidth;
                                if (widget.getRight() > minWidth) {
                                    int i10 = preWidth;
                                    minWidth = Math.max(minWidth, widget.getRight() + widget.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
                                }
                            } else {
                                int i11 = preWidth;
                            }
                            needSolverPass = true;
                        } else {
                            int i12 = preWidth;
                        }
                        if (measuredHeight2 != heightSize5) {
                            widget.setHeight(measuredHeight2);
                            if (containerWrapHeight && widget.getBottom() > minHeight) {
                                minHeight = Math.max(minHeight, widget.getBottom() + widget.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
                            }
                            needSolverPass = true;
                        }
                        measuredHeight = needSolverPass | ((VirtualLayout) widget).needSolverPass();
                    }
                    i8++;
                    computations = computations2;
                    widthSize2 = widthSize3;
                    heightSize2 = heightSize3;
                    optimizations3 = optimizations2;
                }
                optimizations = optimizations3;
                int i13 = computations;
                int i14 = widthSize2;
                int i15 = heightSize2;
                int maxIterations2 = 2;
                int j2 = 0;
                while (j2 < maxIterations2) {
                    int i16 = 0;
                    while (i16 < sizeDependentWidgetsCount2) {
                        ConstraintWidget widget2 = this.mVariableDimensionsWidgets.get(i16);
                        if ((widget2 instanceof Helper) && !(widget2 instanceof VirtualLayout)) {
                            maxIterations = maxIterations2;
                        } else if (widget2 instanceof Guideline) {
                            maxIterations = maxIterations2;
                        } else {
                            maxIterations = maxIterations2;
                            if (widget2.getVisibility() != 8 && ((!widget2.horizontalRun.dimension.resolved || !widget2.verticalRun.dimension.resolved) && !(widget2 instanceof VirtualLayout))) {
                                int preWidth2 = widget2.getWidth();
                                int preHeight = widget2.getHeight();
                                sizeDependentWidgetsCount = sizeDependentWidgetsCount2;
                                int preBaselineDistance = widget2.getBaselineDistance();
                                j = j2;
                                int measuredWidth2 = widget2.getWidth();
                                boolean needSolverPass2 = measuredHeight | measure(measurer2, widget2, true);
                                int measuredHeight3 = widget2.getHeight();
                                if (measuredWidth2 != preWidth2) {
                                    widget2.setWidth(measuredWidth2);
                                    if (containerWrapWidth2) {
                                        containerWrapWidth = containerWrapWidth2;
                                        if (widget2.getRight() > minWidth) {
                                            measurer = measurer2;
                                            minWidth = Math.max(minWidth, widget2.getRight() + widget2.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
                                        } else {
                                            measurer = measurer2;
                                        }
                                    } else {
                                        containerWrapWidth = containerWrapWidth2;
                                        measurer = measurer2;
                                    }
                                    needSolverPass2 = true;
                                } else {
                                    containerWrapWidth = containerWrapWidth2;
                                    measurer = measurer2;
                                }
                                if (measuredHeight3 != preHeight) {
                                    widget2.setHeight(measuredHeight3);
                                    if (containerWrapHeight && widget2.getBottom() > minHeight) {
                                        minHeight = Math.max(minHeight, widget2.getBottom() + widget2.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
                                    }
                                    needSolverPass2 = true;
                                }
                                if (!widget2.hasBaseline() || preBaselineDistance == widget2.getBaselineDistance()) {
                                    measuredHeight = needSolverPass2;
                                    i16++;
                                    maxIterations2 = maxIterations;
                                    sizeDependentWidgetsCount2 = sizeDependentWidgetsCount;
                                    j2 = j;
                                    containerWrapWidth2 = containerWrapWidth;
                                    measurer2 = measurer;
                                } else {
                                    measuredHeight = true;
                                    i16++;
                                    maxIterations2 = maxIterations;
                                    sizeDependentWidgetsCount2 = sizeDependentWidgetsCount;
                                    j2 = j;
                                    containerWrapWidth2 = containerWrapWidth;
                                    measurer2 = measurer;
                                }
                            }
                        }
                        containerWrapWidth = containerWrapWidth2;
                        measurer = measurer2;
                        j = j2;
                        sizeDependentWidgetsCount = sizeDependentWidgetsCount2;
                        i16++;
                        maxIterations2 = maxIterations;
                        sizeDependentWidgetsCount2 = sizeDependentWidgetsCount;
                        j2 = j;
                        containerWrapWidth2 = containerWrapWidth;
                        measurer2 = measurer;
                    }
                    boolean containerWrapWidth3 = containerWrapWidth2;
                    Measurer measurer3 = measurer2;
                    int maxIterations3 = maxIterations2;
                    int j3 = j2;
                    int sizeDependentWidgetsCount3 = sizeDependentWidgetsCount2;
                    if (measuredHeight) {
                        solveLinearSystem(constraintWidgetContainer2, "intermediate pass", startingWidth, startingHeight);
                        measuredHeight = false;
                    }
                    j2 = j3 + 1;
                    maxIterations2 = maxIterations3;
                    sizeDependentWidgetsCount2 = sizeDependentWidgetsCount3;
                    containerWrapWidth2 = containerWrapWidth3;
                    measurer2 = measurer3;
                }
                Measurer measurer4 = measurer2;
                int i17 = maxIterations2;
                int i18 = j2;
                int i19 = sizeDependentWidgetsCount2;
                if (measuredHeight) {
                    solveLinearSystem(constraintWidgetContainer2, "2nd pass", startingWidth, startingHeight);
                    boolean needSolverPass3 = false;
                    if (layout.getWidth() < minWidth) {
                        constraintWidgetContainer2.setWidth(minWidth);
                        needSolverPass3 = true;
                    }
                    if (layout.getHeight() < minHeight) {
                        constraintWidgetContainer2.setHeight(minHeight);
                        needSolverPass3 = true;
                    }
                    if (needSolverPass3) {
                        solveLinearSystem(constraintWidgetContainer2, "3rd pass", startingWidth, startingHeight);
                    }
                }
            } else {
                int i20 = childCount;
                boolean z3 = optimizeWrap;
                optimizations = optimizations3;
                boolean z4 = optimize2;
                int i21 = computations;
                int i22 = widthSize2;
                int i23 = heightSize2;
                int i24 = sizeDependentWidgetsCount2;
            }
            constraintWidgetContainer2.setOptimizationLevel(optimizations);
            return;
        }
        boolean z5 = allSolved;
        Measurer measurer5 = measurer2;
        int i25 = childCount;
        boolean z6 = optimizeWrap;
        boolean z7 = optimize2;
        int i26 = computations;
        int i27 = widthSize2;
        int i28 = heightSize2;
    }

    private boolean measure(Measurer measurer, ConstraintWidget widget, boolean useDeprecated) {
        this.mMeasure.horizontalBehavior = widget.getHorizontalDimensionBehaviour();
        this.mMeasure.verticalBehavior = widget.getVerticalDimensionBehaviour();
        this.mMeasure.horizontalDimension = widget.getWidth();
        this.mMeasure.verticalDimension = widget.getHeight();
        this.mMeasure.measuredNeedsSolverPass = false;
        this.mMeasure.useDeprecated = useDeprecated;
        boolean horizontalMatchConstraints = this.mMeasure.horizontalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        boolean verticalMatchConstraints = this.mMeasure.verticalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        boolean horizontalUseRatio = horizontalMatchConstraints && widget.mDimensionRatio > 0.0f;
        boolean verticalUseRatio = verticalMatchConstraints && widget.mDimensionRatio > 0.0f;
        if (horizontalUseRatio && widget.mResolvedMatchConstraintDefault[0] == 4) {
            this.mMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        if (verticalUseRatio && widget.mResolvedMatchConstraintDefault[1] == 4) {
            this.mMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        measurer.measure(widget, this.mMeasure);
        widget.setWidth(this.mMeasure.measuredWidth);
        widget.setHeight(this.mMeasure.measuredHeight);
        widget.setHasBaseline(this.mMeasure.measuredHasBaseline);
        widget.setBaselineDistance(this.mMeasure.measuredBaseline);
        this.mMeasure.useDeprecated = false;
        return this.mMeasure.measuredNeedsSolverPass;
    }
}
