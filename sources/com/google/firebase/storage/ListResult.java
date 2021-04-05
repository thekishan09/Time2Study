package com.google.firebase.storage;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
public final class ListResult {
    private static final String ITEMS_KEY = "items";
    private static final String NAME_KEY = "name";
    private static final String PAGE_TOKEN_KEY = "nextPageToken";
    private static final String PREFIXES_KEY = "prefixes";
    private final List<StorageReference> items;
    private final String pageToken;
    private final List<StorageReference> prefixes;

    ListResult(List<StorageReference> prefixes2, List<StorageReference> items2, String pageToken2) {
        this.prefixes = prefixes2;
        this.items = items2;
        this.pageToken = pageToken2;
    }

    static ListResult fromJSON(FirebaseStorage storage, JSONObject resultBody) throws JSONException {
        List<StorageReference> prefixes2 = new ArrayList<>();
        List<StorageReference> items2 = new ArrayList<>();
        if (resultBody.has(PREFIXES_KEY)) {
            JSONArray prefixEntries = resultBody.getJSONArray(PREFIXES_KEY);
            for (int i = 0; i < prefixEntries.length(); i++) {
                String pathWithoutTrailingSlash = prefixEntries.getString(i);
                if (pathWithoutTrailingSlash.endsWith("/")) {
                    pathWithoutTrailingSlash = pathWithoutTrailingSlash.substring(0, pathWithoutTrailingSlash.length() - 1);
                }
                prefixes2.add(storage.getReference(pathWithoutTrailingSlash));
            }
        }
        if (resultBody.has(ITEMS_KEY)) {
            JSONArray itemEntries = resultBody.getJSONArray(ITEMS_KEY);
            for (int i2 = 0; i2 < itemEntries.length(); i2++) {
                items2.add(storage.getReference(itemEntries.getJSONObject(i2).getString(NAME_KEY)));
            }
        }
        return new ListResult(prefixes2, items2, resultBody.optString(PAGE_TOKEN_KEY, (String) null));
    }

    public List<StorageReference> getPrefixes() {
        return this.prefixes;
    }

    public List<StorageReference> getItems() {
        return this.items;
    }

    public String getPageToken() {
        return this.pageToken;
    }
}
