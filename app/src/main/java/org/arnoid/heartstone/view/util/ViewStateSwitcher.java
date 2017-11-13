package org.arnoid.heartstone.view.util;

import android.support.annotation.IdRes;
import android.view.View;

public class ViewState {

    private View content;
    private View error;
    private View empty;

    public ViewState(View content, View error, View empty) {
        this.content = content;
        this.error = error;
        this.empty = empty;
    }

    public static class Builder {

        private View view;
        private @IdRes
        int contentId = -1;
        private @IdRes
        int errorId = -1;
        private @IdRes
        int emptyId = -1;

        private Builder(View view) {
            this.view = view;
        }

        public static Builder withView(View view) {
            return new Builder(view);
        }

        public Builder content(@IdRes int contentId) {
            this.contentId = contentId;
            return this;
        }

        public Builder error(@IdRes int errorId) {
            this.errorId = errorId;
            return this;
        }

        public Builder empty(@IdRes int emptyId) {
            this.emptyId = emptyId;
            return this;
        }

        public ViewState build() {
            View content = contentId > 0 ? view.findViewById(contentId) : null;
            View error = errorId > 0 ? view.findViewById(errorId) : null;
            View empty = emptyId > 0 ? view.findViewById(emptyId) : null;
        }
    }
}
