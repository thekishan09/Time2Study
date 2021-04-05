package com.google.firebase.firestore.core;

import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.util.Assert;
import java.util.List;

public final class Target {
    public static final long NO_LIMIT = -1;
    private final String collectionGroup;
    private final Bound endAt;
    private final List<Filter> filters;
    private final long limit;
    private String memoizedCannonicalId;
    private final List<OrderBy> orderBy;
    private final ResourcePath path;
    private final Bound startAt;

    Target(ResourcePath path2, String collectionGroup2, List<Filter> filters2, List<OrderBy> orderBy2, long limit2, Bound startAt2, Bound endAt2) {
        this.path = path2;
        this.collectionGroup = collectionGroup2;
        this.orderBy = orderBy2;
        this.filters = filters2;
        this.limit = limit2;
        this.startAt = startAt2;
        this.endAt = endAt2;
    }

    public ResourcePath getPath() {
        return this.path;
    }

    public String getCollectionGroup() {
        return this.collectionGroup;
    }

    public boolean isDocumentQuery() {
        return DocumentKey.isDocumentKey(this.path) && this.collectionGroup == null && this.filters.isEmpty();
    }

    public List<Filter> getFilters() {
        return this.filters;
    }

    public long getLimit() {
        Assert.hardAssert(hasLimit(), "Called getLimit when no limit was set", new Object[0]);
        return this.limit;
    }

    public boolean hasLimit() {
        return this.limit != -1;
    }

    public Bound getStartAt() {
        return this.startAt;
    }

    public Bound getEndAt() {
        return this.endAt;
    }

    public List<OrderBy> getOrderBy() {
        return this.orderBy;
    }

    public String getCanonicalId() {
        String str = this.memoizedCannonicalId;
        if (str != null) {
            return str;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(getPath().canonicalString());
        if (this.collectionGroup != null) {
            builder.append("|cg:");
            builder.append(this.collectionGroup);
        }
        builder.append("|f:");
        for (Filter filter : getFilters()) {
            builder.append(filter.getCanonicalId());
        }
        builder.append("|ob:");
        for (OrderBy orderBy2 : getOrderBy()) {
            builder.append(orderBy2.getField().canonicalString());
            builder.append(orderBy2.getDirection().equals(OrderBy.Direction.ASCENDING) ? "asc" : "desc");
        }
        if (hasLimit()) {
            builder.append("|l:");
            builder.append(getLimit());
        }
        if (this.startAt != null) {
            builder.append("|lb:");
            builder.append(this.startAt.canonicalString());
        }
        if (this.endAt != null) {
            builder.append("|ub:");
            builder.append(this.endAt.canonicalString());
        }
        String sb = builder.toString();
        this.memoizedCannonicalId = sb;
        return sb;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Target target = (Target) o;
        String str = this.collectionGroup;
        if (str == null ? target.collectionGroup != null : !str.equals(target.collectionGroup)) {
            return false;
        }
        if (this.limit != target.limit || !this.orderBy.equals(target.orderBy) || !this.filters.equals(target.filters) || !this.path.equals(target.path)) {
            return false;
        }
        Bound bound = this.startAt;
        if (bound == null ? target.startAt != null : !bound.equals(target.startAt)) {
            return false;
        }
        Bound bound2 = this.endAt;
        if (bound2 != null) {
            return bound2.equals(target.endAt);
        }
        if (target.endAt == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode = this.orderBy.hashCode() * 31;
        String str = this.collectionGroup;
        int i = 0;
        int hashCode2 = str != null ? str.hashCode() : 0;
        long j = this.limit;
        int result = (((((((hashCode + hashCode2) * 31) + this.filters.hashCode()) * 31) + this.path.hashCode()) * 31) + ((int) (j ^ (j >>> 32)))) * 31;
        Bound bound = this.startAt;
        int result2 = (result + (bound != null ? bound.hashCode() : 0)) * 31;
        Bound bound2 = this.endAt;
        if (bound2 != null) {
            i = bound2.hashCode();
        }
        return result2 + i;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Query(");
        builder.append(this.path.canonicalString());
        if (this.collectionGroup != null) {
            builder.append(" collectionGroup=");
            builder.append(this.collectionGroup);
        }
        if (!this.filters.isEmpty()) {
            builder.append(" where ");
            for (int i = 0; i < this.filters.size(); i++) {
                if (i > 0) {
                    builder.append(" and ");
                }
                builder.append(this.filters.get(i).toString());
            }
        }
        if (!this.orderBy.isEmpty()) {
            builder.append(" order by ");
            for (int i2 = 0; i2 < this.orderBy.size(); i2++) {
                if (i2 > 0) {
                    builder.append(", ");
                }
                builder.append(this.orderBy.get(i2));
            }
        }
        builder.append(")");
        return builder.toString();
    }
}
