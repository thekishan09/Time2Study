package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.HashMap;

public class Barrier extends HelperWidget {
    public static final int BOTTOM = 3;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    private boolean mAllowsGoneWidget = true;
    private int mBarrierType = 0;
    private int mMargin = 0;

    public boolean allowedInBarrier() {
        return true;
    }

    public int getBarrierType() {
        return this.mBarrierType;
    }

    public void setBarrierType(int barrierType) {
        this.mBarrierType = barrierType;
    }

    public void setAllowsGoneWidget(boolean allowsGoneWidget) {
        this.mAllowsGoneWidget = allowsGoneWidget;
    }

    public boolean allowsGoneWidget() {
        return this.mAllowsGoneWidget;
    }

    public void copy(ConstraintWidget src, HashMap<ConstraintWidget, ConstraintWidget> map) {
        super.copy(src, map);
        Barrier srcBarrier = (Barrier) src;
        this.mBarrierType = srcBarrier.mBarrierType;
        this.mAllowsGoneWidget = srcBarrier.mAllowsGoneWidget;
        this.mMargin = srcBarrier.mMargin;
    }

    public void addToSolver(LinearSystem system) {
        this.mListAnchors[0] = this.mLeft;
        this.mListAnchors[2] = this.mTop;
        this.mListAnchors[1] = this.mRight;
        this.mListAnchors[3] = this.mBottom;
        for (int i = 0; i < this.mListAnchors.length; i++) {
            this.mListAnchors[i].mSolverVariable = system.createObjectVariable(this.mListAnchors[i]);
        }
        int i2 = this.mBarrierType;
        if (i2 >= 0 && i2 < 4) {
            ConstraintAnchor position = this.mListAnchors[this.mBarrierType];
            boolean hasMatchConstraintWidgets = false;
            int i3 = 0;
            while (true) {
                if (i3 >= this.mWidgetsCount) {
                    break;
                }
                ConstraintWidget widget = this.mWidgets[i3];
                if (this.mAllowsGoneWidget || widget.allowedInBarrier()) {
                    int i4 = this.mBarrierType;
                    if ((i4 != 0 && i4 != 1) || widget.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || widget.mLeft.mTarget == null || widget.mRight.mTarget == null) {
                        int i5 = this.mBarrierType;
                        if ((i5 == 2 || i5 == 3) && widget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mTop.mTarget != null && widget.mBottom.mTarget != null) {
                            hasMatchConstraintWidgets = true;
                            break;
                        }
                    } else {
                        hasMatchConstraintWidgets = true;
                        break;
                    }
                }
                i3++;
            }
            for (int i6 = 0; i6 < this.mWidgetsCount; i6++) {
                ConstraintWidget widget2 = this.mWidgets[i6];
                if (this.mAllowsGoneWidget || widget2.allowedInBarrier()) {
                    SolverVariable target = system.createObjectVariable(widget2.mListAnchors[this.mBarrierType]);
                    widget2.mListAnchors[this.mBarrierType].mSolverVariable = target;
                    int margin = 0;
                    if (widget2.mListAnchors[this.mBarrierType].mTarget != null && widget2.mListAnchors[this.mBarrierType].mTarget.mOwner == this) {
                        margin = 0 + widget2.mListAnchors[this.mBarrierType].mMargin;
                    }
                    int i7 = this.mBarrierType;
                    if (i7 == 0 || i7 == 2) {
                        system.addLowerBarrier(position.mSolverVariable, target, this.mMargin - margin, hasMatchConstraintWidgets);
                    } else {
                        system.addGreaterBarrier(position.mSolverVariable, target, this.mMargin + margin, hasMatchConstraintWidgets);
                    }
                }
            }
            int barrierParentStrength = 5;
            if (hasMatchConstraintWidgets) {
                barrierParentStrength = 4;
            }
            int i8 = this.mBarrierType;
            if (i8 == 0) {
                system.addEquality(this.mRight.mSolverVariable, this.mLeft.mSolverVariable, 0, 7);
                system.addEquality(this.mLeft.mSolverVariable, this.mParent.mRight.mSolverVariable, 0, barrierParentStrength);
                system.addEquality(this.mLeft.mSolverVariable, this.mParent.mLeft.mSolverVariable, 0, 0);
            } else if (i8 == 1) {
                system.addEquality(this.mLeft.mSolverVariable, this.mRight.mSolverVariable, 0, 7);
                system.addEquality(this.mLeft.mSolverVariable, this.mParent.mLeft.mSolverVariable, 0, barrierParentStrength);
                system.addEquality(this.mLeft.mSolverVariable, this.mParent.mRight.mSolverVariable, 0, 0);
            } else if (i8 == 2) {
                system.addEquality(this.mBottom.mSolverVariable, this.mTop.mSolverVariable, 0, 7);
                system.addEquality(this.mTop.mSolverVariable, this.mParent.mBottom.mSolverVariable, 0, barrierParentStrength);
                system.addEquality(this.mTop.mSolverVariable, this.mParent.mTop.mSolverVariable, 0, 0);
            } else if (i8 == 3) {
                system.addEquality(this.mTop.mSolverVariable, this.mBottom.mSolverVariable, 0, 7);
                system.addEquality(this.mTop.mSolverVariable, this.mParent.mTop.mSolverVariable, 0, barrierParentStrength);
                system.addEquality(this.mTop.mSolverVariable, this.mParent.mBottom.mSolverVariable, 0, 0);
            }
        }
    }

    public void setMargin(int margin) {
        this.mMargin = margin;
    }

    public int getMargin() {
        return this.mMargin;
    }
}
