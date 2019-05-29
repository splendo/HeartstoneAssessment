package me.grapescan.cards.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.SoundEffectConstants
import android.widget.Checkable
import androidx.appcompat.widget.AppCompatImageView

class CheckableImageView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), Checkable {

    init {
        if (attrs != null) {
            val attrIdArray = intArrayOf(android.R.attr.checked)
            val styledAttrs = context.obtainStyledAttributes(attrs, attrIdArray)
            val checked = styledAttrs.getBoolean(0, false)
            styledAttrs.recycle()
            isChecked = checked
        }
        isClickable = true
        refreshDrawableState()
    }

    private var checked: Boolean = false
    private var listener: ((checkableImageView: CheckableImageView, isChecked: Boolean) -> Unit)? = null
    private var dispatching: Boolean = false

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

    override fun toggle() {
        isChecked = !checked
    }

    override fun isChecked(): Boolean {
        return checked
    }

    override fun setChecked(checked: Boolean) {
        if (this.checked != checked) {
            this.checked = checked
            refreshDrawableState()

            // Avoid loops if the listener calls isChecked = ...
            if (dispatching) {
                return
            }
            listener?.let {
                dispatching = true
                it(this, checked)
                dispatching = false
            }
        }
    }

    override fun performClick(): Boolean {
        toggle()

        val handled = super.performClick()
        if (!handled) {
            playSoundEffect(SoundEffectConstants.CLICK)
        }

        return handled
    }

    /**
     * Register a callback to be invoked when the checked state of this [CheckableImageView] changes.
     *
     * @param listener defines a callback to be called whn a view is checked
     */
    fun setOnCheckedChangeListener(listener: ((checkableImageView: CheckableImageView, isChecked: Boolean) -> Unit)?) {
        this.listener = listener
    }

    /**
     * Change the checked state of the view without calling the listener.
     *
     * @param checked the new checked state
     */
    fun setCheckedSilent(checked: Boolean) {
        if (this.checked != checked) {
            this.checked = checked
            refreshDrawableState()
        }
    }

    private companion object {
        @JvmField
        val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }
}
