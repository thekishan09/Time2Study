package androidx.constraintlayout.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class PriorityGoalRow extends ArrayRow {
    private static final boolean DEBUG = false;
    private static final float epsilon = 1.0E-4f;
    static Cache sCache = null;
    static RowVariable sRowVariable = new RowVariable();
    ArrayList<GoalVariable> goals = new ArrayList<>();
    HashMap<Integer, GoalVariable> keyedGoals = new HashMap<>();
    Cache mCache;

    static class GoalVariable implements Comparable {
        float[] strengths = new float[8];
        int variableId;

        GoalVariable() {
        }

        public void reset() {
            Arrays.fill(this.strengths, 0.0f);
            this.variableId = -1;
        }

        public String toString() {
            return toString(PriorityGoalRow.sCache);
        }

        public String toString(Cache cache) {
            String result = "[ ";
            for (int i = 0; i < 8; i++) {
                result = result + this.strengths[i] + " ";
            }
            return result + "] " + cache.mIndexedVariables[this.variableId];
        }

        public void add(GoalVariable added) {
            for (int i = 0; i < 8; i++) {
                float[] fArr = this.strengths;
                fArr[i] = fArr[i] + added.strengths[i];
                if (Math.abs(fArr[i]) < 1.0E-4f) {
                    this.strengths[i] = 0.0f;
                }
            }
        }

        public final boolean isNegative() {
            for (int i = 7; i >= 0; i--) {
                float[] fArr = this.strengths;
                if (fArr[i] > 0.0f) {
                    return false;
                }
                if (fArr[i] < 0.0f) {
                    return true;
                }
            }
            return false;
        }

        public final boolean isSmallerThan(GoalVariable variable) {
            int i = 7;
            while (i >= 0) {
                float comparedValue = variable.strengths[i];
                float value = this.strengths[i];
                if (value == comparedValue) {
                    i--;
                } else if (value < comparedValue) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }

        public final boolean isNull() {
            for (int i = 0; i < 8; i++) {
                if (this.strengths[i] != 0.0f) {
                    return false;
                }
            }
            return true;
        }

        public int compareTo(Object o) {
            return this.variableId - ((GoalVariable) o).variableId;
        }
    }

    static class RowVariable {
        float value;
        int variableId;

        RowVariable() {
        }
    }

    public void clear() {
        int count = this.goals.size();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                this.mCache.goalVariablePool.release(this.goals.get(i));
            }
        }
        this.goals.clear();
        this.keyedGoals.clear();
        this.constantValue = 0.0f;
    }

    public PriorityGoalRow(Cache cache) {
        super(cache);
        this.mCache = cache;
    }

    public SolverVariable getPivotCandidate(LinearSystem system, boolean[] avoid) {
        sCache = this.mCache;
        GoalVariable pivot = null;
        int count = this.goals.size();
        for (int i = 0; i < count; i++) {
            GoalVariable v = this.goals.get(i);
            if (!avoid[v.variableId]) {
                if (pivot == null) {
                    if (v.isNegative()) {
                        pivot = v;
                    }
                } else if (v.isSmallerThan(pivot)) {
                    pivot = v;
                }
            }
        }
        if (pivot == null) {
            return null;
        }
        return this.mCache.mIndexedVariables[pivot.variableId];
    }

    public void addError(SolverVariable error) {
        GoalVariable goalVariable = this.mCache.goalVariablePool.acquire();
        if (goalVariable == null) {
            goalVariable = new GoalVariable();
        } else {
            goalVariable.reset();
        }
        goalVariable.strengths[error.strength] = 1.0f;
        goalVariable.variableId = error.f32id;
        this.goals.add(goalVariable);
        this.keyedGoals.put(Integer.valueOf(goalVariable.variableId), goalVariable);
        error.addToRow(this);
    }

    /* access modifiers changed from: package-private */
    public final GoalVariable create(GoalVariable key, SolverVariable error, float value) {
        GoalVariable goalVariable = new GoalVariable();
        for (int i = 0; i < 8; i++) {
            float strength = key.strengths[i];
            if (strength != 0.0f) {
                float v = value * strength;
                if (Math.abs(v) < 1.0E-4f) {
                    v = 0.0f;
                }
                goalVariable.strengths[i] = v;
            }
        }
        goalVariable.variableId = error.f32id;
        return goalVariable;
    }

    public void updateFromRow(ArrayRow definition, boolean removeFromDefinition) {
        GoalVariable goalVariable = this.keyedGoals.get(Integer.valueOf(definition.variable.f32id));
        if (goalVariable != null) {
            this.goals.remove(goalVariable);
            this.keyedGoals.remove(Integer.valueOf(goalVariable.variableId));
            this.mCache.goalVariablePool.release(goalVariable);
            int current = definition.variables.getHead();
            int currentSize = definition.variables.getCurrentSize();
            while (current != -1 && 0 < currentSize) {
                current = definition.variables.getVariable(sRowVariable, current);
                RowVariable v = sRowVariable;
                GoalVariable existing = find(v.variableId);
                float value = v.value;
                SolverVariable solverVariable = this.mCache.mIndexedVariables[v.variableId];
                if (existing == null) {
                    GoalVariable added = create(goalVariable, solverVariable, value);
                    this.goals.add(added);
                    this.keyedGoals.put(Integer.valueOf(added.variableId), added);
                    solverVariable.addToRow(this);
                } else {
                    existing.add(create(goalVariable, solverVariable, value));
                    if (existing.isNull()) {
                        this.goals.remove(existing);
                        this.keyedGoals.remove(Integer.valueOf(existing.variableId));
                        this.mCache.goalVariablePool.release(existing);
                        solverVariable.removeFromRow(this);
                    }
                }
                this.constantValue += definition.constantValue * value;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final GoalVariable find(int id) {
        return this.keyedGoals.get(Integer.valueOf(id));
    }

    public String toString() {
        Collections.sort(this.goals);
        String result = "" + " goal -> (" + this.constantValue + ") : ";
        Iterator<GoalVariable> it = this.goals.iterator();
        while (it.hasNext()) {
            result = result + it.next().toString(this.mCache) + " ";
        }
        return result;
    }
}
