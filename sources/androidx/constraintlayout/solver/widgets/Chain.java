package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;

class Chain {
    private static final boolean DEBUG = false;

    Chain() {
    }

    static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem system, int orientation) {
        ChainHead[] chainsArray;
        int chainsSize;
        int offset;
        if (orientation == 0) {
            offset = 0;
            chainsSize = constraintWidgetContainer.mHorizontalChainsSize;
            chainsArray = constraintWidgetContainer.mHorizontalChainsArray;
        } else {
            offset = 2;
            chainsSize = constraintWidgetContainer.mVerticalChainsSize;
            chainsArray = constraintWidgetContainer.mVerticalChainsArray;
        }
        for (int i = 0; i < chainsSize; i++) {
            ChainHead first = chainsArray[i];
            first.define();
            applyChainConstraints(constraintWidgetContainer, system, orientation, offset, first);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:300:0x0643 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:304:0x0655  */
    /* JADX WARNING: Removed duplicated region for block: B:305:0x065a  */
    /* JADX WARNING: Removed duplicated region for block: B:308:0x0661  */
    /* JADX WARNING: Removed duplicated region for block: B:309:0x0666  */
    /* JADX WARNING: Removed duplicated region for block: B:311:0x0669  */
    /* JADX WARNING: Removed duplicated region for block: B:316:0x0681  */
    /* JADX WARNING: Removed duplicated region for block: B:318:0x0685  */
    /* JADX WARNING: Removed duplicated region for block: B:319:0x0691  */
    /* JADX WARNING: Removed duplicated region for block: B:321:0x0694 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void applyChainConstraints(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r44, androidx.constraintlayout.solver.LinearSystem r45, int r46, int r47, androidx.constraintlayout.solver.widgets.ChainHead r48) {
        /*
            r0 = r44
            r10 = r45
            r11 = r48
            androidx.constraintlayout.solver.widgets.ConstraintWidget r12 = r11.mFirst
            androidx.constraintlayout.solver.widgets.ConstraintWidget r13 = r11.mLast
            androidx.constraintlayout.solver.widgets.ConstraintWidget r14 = r11.mFirstVisibleWidget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r15 = r11.mLastVisibleWidget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r11.mHead
            r1 = r12
            r2 = 0
            r3 = 0
            float r4 = r11.mTotalWeight
            androidx.constraintlayout.solver.widgets.ConstraintWidget r8 = r11.mFirstMatchConstraintWidget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r11.mLastMatchConstraintWidget
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r0.mListDimensionBehaviors
            r5 = r5[r46]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r16 = r1
            if (r5 != r6) goto L_0x0025
            r5 = 1
            goto L_0x0026
        L_0x0025:
            r5 = 0
        L_0x0026:
            r18 = r5
            r5 = 0
            r6 = 0
            r19 = 0
            if (r46 != 0) goto L_0x0052
            int r1 = r9.mHorizontalChainStyle
            if (r1 != 0) goto L_0x0034
            r1 = 1
            goto L_0x0035
        L_0x0034:
            r1 = 0
        L_0x0035:
            int r5 = r9.mHorizontalChainStyle
            r22 = r1
            r1 = 1
            if (r5 != r1) goto L_0x003e
            r1 = 1
            goto L_0x003f
        L_0x003e:
            r1 = 0
        L_0x003f:
            int r5 = r9.mHorizontalChainStyle
            r6 = 2
            if (r5 != r6) goto L_0x0046
            r5 = 1
            goto L_0x0047
        L_0x0046:
            r5 = 0
        L_0x0047:
            r19 = r2
            r21 = r3
            r23 = r5
            r6 = r16
            r16 = r1
            goto L_0x0075
        L_0x0052:
            int r1 = r9.mVerticalChainStyle
            if (r1 != 0) goto L_0x0058
            r1 = 1
            goto L_0x0059
        L_0x0058:
            r1 = 0
        L_0x0059:
            int r5 = r9.mVerticalChainStyle
            r22 = r1
            r1 = 1
            if (r5 != r1) goto L_0x0062
            r1 = 1
            goto L_0x0063
        L_0x0062:
            r1 = 0
        L_0x0063:
            int r5 = r9.mVerticalChainStyle
            r6 = 2
            if (r5 != r6) goto L_0x006a
            r5 = 1
            goto L_0x006b
        L_0x006a:
            r5 = 0
        L_0x006b:
            r19 = r2
            r21 = r3
            r23 = r5
            r6 = r16
            r16 = r1
        L_0x0075:
            if (r21 != 0) goto L_0x0162
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r6.mListAnchors
            r1 = r1[r47]
            r25 = 4
            if (r23 == 0) goto L_0x0081
            r25 = 1
        L_0x0081:
            int r26 = r1.getMargin()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r6.mListDimensionBehaviors
            r5 = r5[r46]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r5 != r3) goto L_0x0095
            int[] r3 = r6.mResolvedMatchConstraintDefault
            r3 = r3[r46]
            if (r3 != 0) goto L_0x0095
            r3 = 1
            goto L_0x0096
        L_0x0095:
            r3 = 0
        L_0x0096:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r1.mTarget
            if (r5 == 0) goto L_0x00a7
            if (r6 == r12) goto L_0x00a7
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r1.mTarget
            int r5 = r5.getMargin()
            int r26 = r26 + r5
            r5 = r26
            goto L_0x00a9
        L_0x00a7:
            r5 = r26
        L_0x00a9:
            if (r23 == 0) goto L_0x00b1
            if (r6 == r12) goto L_0x00b1
            if (r6 == r14) goto L_0x00b1
            r25 = 5
        L_0x00b1:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r1.mTarget
            if (r2 == 0) goto L_0x00e8
            if (r6 != r14) goto L_0x00c6
            androidx.constraintlayout.solver.SolverVariable r2 = r1.mSolverVariable
            r29 = r4
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r1.mTarget
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            r30 = r7
            r7 = 6
            r10.addGreaterThan(r2, r4, r5, r7)
            goto L_0x00d4
        L_0x00c6:
            r29 = r4
            r30 = r7
            androidx.constraintlayout.solver.SolverVariable r2 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r1.mTarget
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            r7 = 7
            r10.addGreaterThan(r2, r4, r5, r7)
        L_0x00d4:
            if (r3 == 0) goto L_0x00da
            if (r23 != 0) goto L_0x00da
            r2 = 5
            goto L_0x00dc
        L_0x00da:
            r2 = r25
        L_0x00dc:
            androidx.constraintlayout.solver.SolverVariable r4 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r1.mTarget
            androidx.constraintlayout.solver.SolverVariable r7 = r7.mSolverVariable
            r10.addEquality(r4, r7, r5, r2)
            r25 = r2
            goto L_0x00ec
        L_0x00e8:
            r29 = r4
            r30 = r7
        L_0x00ec:
            if (r18 == 0) goto L_0x0128
            int r2 = r6.getVisibility()
            r4 = 8
            if (r2 == r4) goto L_0x0114
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r6.mListDimensionBehaviors
            r2 = r2[r46]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r2 != r4) goto L_0x0114
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r6.mListAnchors
            int r4 = r47 + 1
            r2 = r2[r4]
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r4 = r6.mListAnchors
            r4 = r4[r47]
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            r24 = r1
            r1 = 0
            r7 = 5
            r10.addGreaterThan(r2, r4, r1, r7)
            goto L_0x0116
        L_0x0114:
            r24 = r1
        L_0x0116:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r6.mListAnchors
            r1 = r1[r47]
            androidx.constraintlayout.solver.SolverVariable r1 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r0.mListAnchors
            r2 = r2[r47]
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            r4 = 7
            r7 = 0
            r10.addGreaterThan(r1, r2, r7, r4)
            goto L_0x012a
        L_0x0128:
            r24 = r1
        L_0x012a:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r6.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mTarget
            if (r1 == 0) goto L_0x0150
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r1.mOwner
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r4 = r2.mListAnchors
            r4 = r4[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x014c
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r4 = r2.mListAnchors
            r4 = r4[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = r4.mOwner
            if (r4 == r6) goto L_0x0149
            goto L_0x014c
        L_0x0149:
            r19 = r2
            goto L_0x0153
        L_0x014c:
            r2 = 0
            r19 = r2
            goto L_0x0153
        L_0x0150:
            r2 = 0
            r19 = r2
        L_0x0153:
            if (r19 == 0) goto L_0x0159
            r2 = r19
            r6 = r2
            goto L_0x015c
        L_0x0159:
            r2 = 1
            r21 = r2
        L_0x015c:
            r4 = r29
            r7 = r30
            goto L_0x0075
        L_0x0162:
            r29 = r4
            r30 = r7
            if (r15 == 0) goto L_0x01cf
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r13.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mTarget
            if (r1 == 0) goto L_0x01cf
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r15.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r15.mListDimensionBehaviors
            r2 = r2[r46]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r2 != r3) goto L_0x0188
            int[] r2 = r15.mResolvedMatchConstraintDefault
            r2 = r2[r46]
            if (r2 != 0) goto L_0x0188
            r2 = 1
            goto L_0x0189
        L_0x0188:
            r2 = 0
        L_0x0189:
            if (r2 == 0) goto L_0x01a3
            if (r23 != 0) goto L_0x01a3
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r1.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r3.mOwner
            if (r3 != r0) goto L_0x01a3
            androidx.constraintlayout.solver.SolverVariable r3 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r1.mTarget
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            int r5 = r1.getMargin()
            int r5 = -r5
            r7 = 5
            r10.addEquality(r3, r4, r5, r7)
            goto L_0x01ba
        L_0x01a3:
            if (r23 == 0) goto L_0x01ba
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r1.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r3.mOwner
            if (r3 != r0) goto L_0x01ba
            androidx.constraintlayout.solver.SolverVariable r3 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r1.mTarget
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            int r5 = r1.getMargin()
            int r5 = -r5
            r7 = 4
            r10.addEquality(r3, r4, r5, r7)
        L_0x01ba:
            androidx.constraintlayout.solver.SolverVariable r3 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r4 = r13.mListAnchors
            int r5 = r47 + 1
            r4 = r4[r5]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            int r5 = r1.getMargin()
            int r5 = -r5
            r7 = 6
            r10.addLowerThan(r3, r4, r5, r7)
        L_0x01cf:
            if (r18 == 0) goto L_0x01ef
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r0.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            androidx.constraintlayout.solver.SolverVariable r1 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r13.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r13.mListAnchors
            int r4 = r47 + 1
            r3 = r3[r4]
            int r3 = r3.getMargin()
            r4 = 7
            r10.addGreaterThan(r1, r2, r3, r4)
        L_0x01ef:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r7 = r11.mWeightedMatchConstraintsWidgets
            if (r7 == 0) goto L_0x02d8
            int r1 = r7.size()
            r2 = 1
            if (r1 <= r2) goto L_0x02cf
            r3 = 0
            r4 = 0
            boolean r5 = r11.mHasUndefinedWeights
            if (r5 == 0) goto L_0x0209
            boolean r5 = r11.mHasComplexMatchWeights
            if (r5 != 0) goto L_0x0209
            int r5 = r11.mWidgetsMatchCount
            float r5 = (float) r5
            r29 = r5
        L_0x0209:
            r5 = 0
        L_0x020a:
            if (r5 >= r1) goto L_0x02c4
            java.lang.Object r20 = r7.get(r5)
            r2 = r20
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r2
            float[] r0 = r2.mWeight
            r0 = r0[r46]
            r20 = 0
            int r26 = (r0 > r20 ? 1 : (r0 == r20 ? 0 : -1))
            if (r26 >= 0) goto L_0x024d
            r26 = r0
            boolean r0 = r11.mHasComplexMatchWeights
            if (r0 == 0) goto L_0x0243
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r2.mListAnchors
            int r20 = r47 + 1
            r0 = r0[r20]
            androidx.constraintlayout.solver.SolverVariable r0 = r0.mSolverVariable
            r39 = r1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r2.mListAnchors
            r1 = r1[r47]
            androidx.constraintlayout.solver.SolverVariable r1 = r1.mSolverVariable
            r25 = r6
            r40 = r7
            r6 = 0
            r7 = 4
            r10.addEquality(r0, r1, r6, r7)
            r17 = r8
            r7 = 7
            r8 = 0
            goto L_0x02b5
        L_0x0243:
            r39 = r1
            r25 = r6
            r40 = r7
            r7 = 4
            r0 = 1065353216(0x3f800000, float:1.0)
            goto L_0x0256
        L_0x024d:
            r26 = r0
            r39 = r1
            r25 = r6
            r40 = r7
            r7 = 4
        L_0x0256:
            int r1 = (r0 > r20 ? 1 : (r0 == r20 ? 0 : -1))
            if (r1 != 0) goto L_0x0270
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r2.mListAnchors
            int r6 = r47 + 1
            r1 = r1[r6]
            androidx.constraintlayout.solver.SolverVariable r1 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r2.mListAnchors
            r6 = r6[r47]
            androidx.constraintlayout.solver.SolverVariable r6 = r6.mSolverVariable
            r17 = r8
            r7 = 7
            r8 = 0
            r10.addEquality(r1, r6, r8, r7)
            goto L_0x02b5
        L_0x0270:
            r17 = r8
            r7 = 7
            r8 = 0
            if (r3 == 0) goto L_0x02af
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r3.mListAnchors
            r1 = r1[r47]
            androidx.constraintlayout.solver.SolverVariable r1 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r3.mListAnchors
            int r26 = r47 + 1
            r6 = r6[r26]
            androidx.constraintlayout.solver.SolverVariable r6 = r6.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r7 = r2.mListAnchors
            r7 = r7[r47]
            androidx.constraintlayout.solver.SolverVariable r7 = r7.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r8 = r2.mListAnchors
            int r31 = r47 + 1
            r8 = r8[r31]
            androidx.constraintlayout.solver.SolverVariable r8 = r8.mSolverVariable
            r41 = r3
            androidx.constraintlayout.solver.ArrayRow r3 = r45.createRow()
            r31 = r3
            r32 = r4
            r33 = r29
            r34 = r0
            r35 = r1
            r36 = r6
            r37 = r7
            r38 = r8
            r31.createRowEqualMatchDimensions(r32, r33, r34, r35, r36, r37, r38)
            r10.addConstraint(r3)
            goto L_0x02b1
        L_0x02af:
            r41 = r3
        L_0x02b1:
            r1 = r2
            r3 = r0
            r4 = r3
            r3 = r1
        L_0x02b5:
            int r5 = r5 + 1
            r2 = 1
            r0 = r44
            r8 = r17
            r6 = r25
            r1 = r39
            r7 = r40
            goto L_0x020a
        L_0x02c4:
            r39 = r1
            r41 = r3
            r25 = r6
            r40 = r7
            r17 = r8
            goto L_0x02de
        L_0x02cf:
            r39 = r1
            r25 = r6
            r40 = r7
            r17 = r8
            goto L_0x02de
        L_0x02d8:
            r25 = r6
            r40 = r7
            r17 = r8
        L_0x02de:
            if (r14 == 0) goto L_0x035e
            if (r14 == r15) goto L_0x02eb
            if (r23 == 0) goto L_0x02e5
            goto L_0x02eb
        L_0x02e5:
            r35 = r9
            r33 = r40
            goto L_0x0362
        L_0x02eb:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r12.mListAnchors
            r1 = r1[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r13.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r1.mTarget
            if (r3 == 0) goto L_0x02fe
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r1.mTarget
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            goto L_0x02ff
        L_0x02fe:
            r3 = 0
        L_0x02ff:
            r20 = r3
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r2.mTarget
            if (r3 == 0) goto L_0x030a
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r2.mTarget
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            goto L_0x030b
        L_0x030a:
            r3 = 0
        L_0x030b:
            r24 = r3
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r14.mListAnchors
            r8 = r3[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r15.mListAnchors
            int r3 = r47 + 1
            r7 = r1[r3]
            if (r20 == 0) goto L_0x0354
            if (r24 == 0) goto L_0x0354
            r1 = 1056964608(0x3f000000, float:0.5)
            if (r46 != 0) goto L_0x0324
            float r1 = r9.mHorizontalBiasPercent
            r26 = r1
            goto L_0x0328
        L_0x0324:
            float r1 = r9.mVerticalBiasPercent
            r26 = r1
        L_0x0328:
            int r27 = r8.getMargin()
            int r28 = r7.getMargin()
            androidx.constraintlayout.solver.SolverVariable r2 = r8.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r6 = r7.mSolverVariable
            r31 = 5
            r1 = r45
            r3 = r20
            r4 = r27
            r5 = r26
            r32 = r6
            r6 = r24
            r34 = r7
            r33 = r40
            r7 = r32
            r32 = r8
            r8 = r28
            r35 = r9
            r9 = r31
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x035c
        L_0x0354:
            r34 = r7
            r32 = r8
            r35 = r9
            r33 = r40
        L_0x035c:
            goto L_0x063f
        L_0x035e:
            r35 = r9
            r33 = r40
        L_0x0362:
            if (r22 == 0) goto L_0x04b1
            if (r14 == 0) goto L_0x04b1
            r1 = r14
            r2 = r14
            int r3 = r11.mWidgetsMatchCount
            if (r3 <= 0) goto L_0x0375
            int r3 = r11.mWidgetsCount
            int r4 = r11.mWidgetsMatchCount
            if (r3 != r4) goto L_0x0375
            r24 = 1
            goto L_0x0377
        L_0x0375:
            r24 = 0
        L_0x0377:
            r20 = r24
            r8 = r1
            r9 = r2
        L_0x037b:
            if (r8 == 0) goto L_0x04a9
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r1 = r8.mNextChainWidget
            r1 = r1[r46]
            r7 = r1
        L_0x0382:
            if (r7 == 0) goto L_0x0391
            int r1 = r7.getVisibility()
            r5 = 8
            if (r1 != r5) goto L_0x0393
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r1 = r7.mNextChainWidget
            r7 = r1[r46]
            goto L_0x0382
        L_0x0391:
            r5 = 8
        L_0x0393:
            if (r7 != 0) goto L_0x03a2
            if (r8 != r15) goto L_0x0398
            goto L_0x03a2
        L_0x0398:
            r39 = r7
            r40 = r8
            r41 = r9
            r0 = 8
            goto L_0x0497
        L_0x03a2:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r8.mListAnchors
            r6 = r1[r47]
            androidx.constraintlayout.solver.SolverVariable r4 = r6.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r6.mTarget
            if (r1 == 0) goto L_0x03b1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r6.mTarget
            androidx.constraintlayout.solver.SolverVariable r1 = r1.mSolverVariable
            goto L_0x03b2
        L_0x03b1:
            r1 = 0
        L_0x03b2:
            if (r9 == r8) goto L_0x03bf
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r9.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.SolverVariable r1 = r2.mSolverVariable
            r19 = r1
            goto L_0x03db
        L_0x03bf:
            if (r8 != r14) goto L_0x03d9
            if (r9 != r8) goto L_0x03d9
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r12.mListAnchors
            r2 = r2[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 == 0) goto L_0x03d4
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r12.mListAnchors
            r2 = r2[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            goto L_0x03d5
        L_0x03d4:
            r2 = 0
        L_0x03d5:
            r1 = r2
            r19 = r1
            goto L_0x03db
        L_0x03d9:
            r19 = r1
        L_0x03db:
            r1 = 0
            r2 = 0
            r3 = 0
            int r24 = r6.getMargin()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r8.mListAnchors
            int r25 = r47 + 1
            r0 = r0[r25]
            int r0 = r0.getMargin()
            if (r7 == 0) goto L_0x0403
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r7.mListAnchors
            r1 = r5[r47]
            androidx.constraintlayout.solver.SolverVariable r2 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r8.mListAnchors
            int r26 = r47 + 1
            r5 = r5[r26]
            androidx.constraintlayout.solver.SolverVariable r3 = r5.mSolverVariable
            r26 = r1
            r28 = r2
            r31 = r3
            goto L_0x041d
        L_0x0403:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r13.mListAnchors
            int r26 = r47 + 1
            r5 = r5[r26]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r5.mTarget
            if (r1 == 0) goto L_0x040f
            androidx.constraintlayout.solver.SolverVariable r2 = r1.mSolverVariable
        L_0x040f:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r8.mListAnchors
            int r26 = r47 + 1
            r5 = r5[r26]
            androidx.constraintlayout.solver.SolverVariable r3 = r5.mSolverVariable
            r26 = r1
            r28 = r2
            r31 = r3
        L_0x041d:
            if (r26 == 0) goto L_0x0424
            int r1 = r26.getMargin()
            int r0 = r0 + r1
        L_0x0424:
            if (r9 == 0) goto L_0x0432
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r9.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            int r1 = r1.getMargin()
            int r24 = r24 + r1
        L_0x0432:
            if (r4 == 0) goto L_0x0489
            if (r19 == 0) goto L_0x0489
            if (r28 == 0) goto L_0x0489
            if (r31 == 0) goto L_0x0489
            r1 = r24
            if (r8 != r14) goto L_0x0449
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r14.mListAnchors
            r2 = r2[r47]
            int r1 = r2.getMargin()
            r32 = r1
            goto L_0x044b
        L_0x0449:
            r32 = r1
        L_0x044b:
            r1 = r0
            if (r8 != r15) goto L_0x045b
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r15.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            int r1 = r2.getMargin()
            r34 = r1
            goto L_0x045d
        L_0x045b:
            r34 = r1
        L_0x045d:
            r1 = 5
            if (r20 == 0) goto L_0x0464
            r1 = 7
            r36 = r1
            goto L_0x0466
        L_0x0464:
            r36 = r1
        L_0x0466:
            r5 = 1056964608(0x3f000000, float:0.5)
            r1 = r45
            r2 = r4
            r3 = r19
            r37 = r4
            r4 = r32
            r38 = r0
            r0 = 8
            r25 = r6
            r6 = r28
            r39 = r7
            r7 = r31
            r40 = r8
            r8 = r34
            r41 = r9
            r9 = r36
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0497
        L_0x0489:
            r38 = r0
            r37 = r4
            r25 = r6
            r39 = r7
            r40 = r8
            r41 = r9
            r0 = 8
        L_0x0497:
            int r1 = r40.getVisibility()
            if (r1 == r0) goto L_0x04a1
            r1 = r40
            r9 = r1
            goto L_0x04a3
        L_0x04a1:
            r9 = r41
        L_0x04a3:
            r8 = r39
            r19 = r39
            goto L_0x037b
        L_0x04a9:
            r40 = r8
            r41 = r9
            r38 = r40
            goto L_0x0641
        L_0x04b1:
            r0 = 8
            if (r16 == 0) goto L_0x063f
            if (r14 == 0) goto L_0x063f
            r1 = r14
            r2 = r14
            int r3 = r11.mWidgetsMatchCount
            if (r3 <= 0) goto L_0x04c6
            int r3 = r11.mWidgetsCount
            int r4 = r11.mWidgetsMatchCount
            if (r3 != r4) goto L_0x04c6
            r24 = 1
            goto L_0x04c8
        L_0x04c6:
            r24 = 0
        L_0x04c8:
            r8 = r1
            r9 = r2
        L_0x04ca:
            if (r8 == 0) goto L_0x05b7
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r1 = r8.mNextChainWidget
            r1 = r1[r46]
        L_0x04d0:
            if (r1 == 0) goto L_0x04dd
            int r2 = r1.getVisibility()
            if (r2 != r0) goto L_0x04dd
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r2 = r1.mNextChainWidget
            r1 = r2[r46]
            goto L_0x04d0
        L_0x04dd:
            if (r8 == r14) goto L_0x059d
            if (r8 == r15) goto L_0x059d
            if (r1 == 0) goto L_0x059d
            if (r1 != r15) goto L_0x04e8
            r1 = 0
            r7 = r1
            goto L_0x04e9
        L_0x04e8:
            r7 = r1
        L_0x04e9:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r8.mListAnchors
            r6 = r1[r47]
            androidx.constraintlayout.solver.SolverVariable r5 = r6.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r6.mTarget
            if (r1 == 0) goto L_0x04f8
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r6.mTarget
            androidx.constraintlayout.solver.SolverVariable r1 = r1.mSolverVariable
            goto L_0x04f9
        L_0x04f8:
            r1 = 0
        L_0x04f9:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r9.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.SolverVariable r4 = r2.mSolverVariable
            r1 = 0
            r2 = 0
            r3 = 0
            int r19 = r6.getMargin()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r8.mListAnchors
            int r25 = r47 + 1
            r0 = r0[r25]
            int r0 = r0.getMargin()
            if (r7 == 0) goto L_0x052f
            r25 = r1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r7.mListAnchors
            r1 = r1[r47]
            androidx.constraintlayout.solver.SolverVariable r2 = r1.mSolverVariable
            r25 = r2
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r1.mTarget
            if (r2 == 0) goto L_0x0527
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r1.mTarget
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            goto L_0x0528
        L_0x0527:
            r2 = 0
        L_0x0528:
            r28 = r2
            r31 = r25
            r25 = r1
            goto L_0x0547
        L_0x052f:
            r25 = r1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r15.mListAnchors
            r1 = r1[r47]
            if (r1 == 0) goto L_0x0539
            androidx.constraintlayout.solver.SolverVariable r2 = r1.mSolverVariable
        L_0x0539:
            r25 = r1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r8.mListAnchors
            int r28 = r47 + 1
            r1 = r1[r28]
            androidx.constraintlayout.solver.SolverVariable r1 = r1.mSolverVariable
            r28 = r1
            r31 = r2
        L_0x0547:
            if (r25 == 0) goto L_0x054e
            int r1 = r25.getMargin()
            int r0 = r0 + r1
        L_0x054e:
            if (r9 == 0) goto L_0x055c
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r9.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            int r1 = r1.getMargin()
            int r19 = r19 + r1
        L_0x055c:
            r1 = 4
            if (r24 == 0) goto L_0x0563
            r1 = 7
            r32 = r1
            goto L_0x0565
        L_0x0563:
            r32 = r1
        L_0x0565:
            if (r5 == 0) goto L_0x058e
            if (r4 == 0) goto L_0x058e
            if (r31 == 0) goto L_0x058e
            if (r28 == 0) goto L_0x058e
            r34 = 1056964608(0x3f000000, float:0.5)
            r1 = r45
            r2 = r5
            r3 = r4
            r36 = r4
            r4 = r19
            r37 = r5
            r5 = r34
            r34 = r6
            r6 = r31
            r20 = r7
            r7 = r28
            r38 = r8
            r8 = r0
            r39 = r9
            r9 = r32
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x059a
        L_0x058e:
            r36 = r4
            r37 = r5
            r34 = r6
            r20 = r7
            r38 = r8
            r39 = r9
        L_0x059a:
            r19 = r20
            goto L_0x05a3
        L_0x059d:
            r38 = r8
            r39 = r9
            r19 = r1
        L_0x05a3:
            int r0 = r38.getVisibility()
            r1 = 8
            if (r0 == r1) goto L_0x05af
            r0 = r38
            r9 = r0
            goto L_0x05b1
        L_0x05af:
            r9 = r39
        L_0x05b1:
            r8 = r19
            r0 = 8
            goto L_0x04ca
        L_0x05b7:
            r38 = r8
            r39 = r9
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r14.mListAnchors
            r0 = r0[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r12.mListAnchors
            r1 = r1[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r1.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r15.mListAnchors
            int r2 = r47 + 1
            r8 = r1[r2]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r13.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r1.mTarget
            if (r9 == 0) goto L_0x0620
            if (r14 == r15) goto L_0x05ea
            androidx.constraintlayout.solver.SolverVariable r1 = r0.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r2 = r9.mSolverVariable
            int r3 = r0.getMargin()
            r6 = 4
            r10.addEquality(r1, r2, r3, r6)
            r42 = r7
            r43 = r8
            r20 = r9
            goto L_0x0626
        L_0x05ea:
            r6 = 4
            if (r7 == 0) goto L_0x0619
            androidx.constraintlayout.solver.SolverVariable r2 = r0.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r3 = r9.mSolverVariable
            int r4 = r0.getMargin()
            androidx.constraintlayout.solver.SolverVariable r1 = r8.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r5 = r7.mSolverVariable
            int r25 = r8.getMargin()
            r26 = 4
            r28 = r1
            r1 = r45
            r20 = r5
            r5 = 1056964608(0x3f000000, float:0.5)
            r6 = r28
            r42 = r7
            r7 = r20
            r43 = r8
            r8 = r25
            r20 = r9
            r9 = r26
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0626
        L_0x0619:
            r42 = r7
            r43 = r8
            r20 = r9
            goto L_0x0626
        L_0x0620:
            r42 = r7
            r43 = r8
            r20 = r9
        L_0x0626:
            r1 = r42
            if (r1 == 0) goto L_0x063c
            if (r14 == r15) goto L_0x063c
            r2 = r43
            androidx.constraintlayout.solver.SolverVariable r3 = r2.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r4 = r1.mSolverVariable
            int r5 = r2.getMargin()
            int r5 = -r5
            r6 = 4
            r10.addEquality(r3, r4, r5, r6)
            goto L_0x0641
        L_0x063c:
            r2 = r43
            goto L_0x0641
        L_0x063f:
            r38 = r25
        L_0x0641:
            if (r22 != 0) goto L_0x0645
            if (r16 == 0) goto L_0x06c6
        L_0x0645:
            if (r14 == 0) goto L_0x06c6
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r14.mListAnchors
            r0 = r0[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r15.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r0.mTarget
            if (r2 == 0) goto L_0x065a
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r0.mTarget
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            goto L_0x065b
        L_0x065a:
            r2 = 0
        L_0x065b:
            r20 = r2
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r1.mTarget
            if (r2 == 0) goto L_0x0666
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r1.mTarget
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            goto L_0x0667
        L_0x0666:
            r2 = 0
        L_0x0667:
            if (r13 == r15) goto L_0x0681
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r13.mListAnchors
            int r4 = r47 + 1
            r3 = r3[r4]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r3.mTarget
            if (r4 == 0) goto L_0x067a
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r3.mTarget
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            r27 = r4
            goto L_0x067c
        L_0x067a:
            r27 = 0
        L_0x067c:
            r2 = r27
            r24 = r2
            goto L_0x0683
        L_0x0681:
            r24 = r2
        L_0x0683:
            if (r14 != r15) goto L_0x0691
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r14.mListAnchors
            r0 = r2[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r14.mListAnchors
            int r3 = r47 + 1
            r1 = r2[r3]
            r9 = r1
            goto L_0x0692
        L_0x0691:
            r9 = r1
        L_0x0692:
            if (r20 == 0) goto L_0x06c4
            if (r24 == 0) goto L_0x06c4
            r25 = 1056964608(0x3f000000, float:0.5)
            int r26 = r0.getMargin()
            if (r15 != 0) goto L_0x06a0
            r1 = r13
            r15 = r1
        L_0x06a0:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r15.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            int r27 = r1.getMargin()
            androidx.constraintlayout.solver.SolverVariable r2 = r0.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r7 = r9.mSolverVariable
            r28 = 5
            r1 = r45
            r3 = r20
            r4 = r26
            r5 = r25
            r6 = r24
            r8 = r27
            r31 = r9
            r9 = r28
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x06c6
        L_0x06c4:
            r31 = r9
        L_0x06c6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.Chain.applyChainConstraints(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer, androidx.constraintlayout.solver.LinearSystem, int, int, androidx.constraintlayout.solver.widgets.ChainHead):void");
    }
}
