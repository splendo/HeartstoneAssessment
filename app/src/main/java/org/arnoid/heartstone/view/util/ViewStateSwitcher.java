package org.arnoid.heartstone.view.util;

import android.support.annotation.IdRes;
import android.view.View;

/**
 * Helper class for view states switching.
 */
public class ViewStateSwitcher {

    private View vContent;
    private View vError;
    private View vEmpty;

    public enum State {
        Content,
        Empty,
        Error,
        None,
    }

    public ViewStateSwitcher(View content, View vError, View empty) {
        this.vContent = content;
        this.vError = vError;
        this.vEmpty = empty;
    }

    public void switchTo(State state) {
        this.vContent.setVisibility(View.GONE);
        this.vError.setVisibility(View.GONE);
        this.vEmpty.setVisibility(View.GONE);

        View enabledView = null;

        switch (state) {
            case Content:
                enabledView = vContent;
                break;
            case Error:
                enabledView = vError;
                break;
            case Empty:
                enabledView = vEmpty;
                break;
            default:
                //All views will be hidden
                break;
        }

        if (enabledView != null) {
            enabledView.setVisibility(View.VISIBLE);
        }
    }

    public void content() {
        switchTo(State.Content);
    }

    public void empty() {
        switchTo(State.Empty);
    }

    public void error() {
        switchTo(State.Error);
    }

    public static class Builder {

        private View view;
        private int contentId = -1;
        private int errorId = -1;
        private int emptyId = -1;

        private View vContent = null;
        private View vError = null;
        private View vEmpty = null;

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

        public Builder error(View error) {
            this.vError = error;
            return this;
        }

        public Builder empty(View empty) {
            this.vEmpty = empty;
            return this;
        }

        public Builder content(View content) {
            this.vContent = content;
            return this;
        }

        public ViewStateSwitcher build() {
            View content = vContent == null ? contentId > 0 ? view.findViewById(contentId) : null : vContent;
            View error = vError == null ? errorId > 0 ? view.findViewById(errorId) : null : vError;
            View empty = vEmpty == null ? emptyId > 0 ? view.findViewById(emptyId) : null : vEmpty;

            return new ViewStateSwitcher(content, error, empty);
        }
    }
}
