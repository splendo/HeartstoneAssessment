package laurens.hearthstoneassessment.utils

import android.content.Context
import android.view.MotionEvent
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.GestureDetector
import android.view.View
import android.view.View.OnTouchListener


const val SWIPE_THRESHOLD = 100
const val SWIPE_VELOCITY_THRESHOLD = 100

typealias SwipeCallback = () -> Unit

class SwipeTouchListener(
    c: Context,
    val onSwipeRight: SwipeCallback = {},
    val onSwipeLeft: SwipeCallback = {},
    val onSwipeUp: SwipeCallback = {},
    val onSwipeDown: SwipeCallback = {}
) : OnTouchListener {

    private val gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(c, GestureListener())
    }


    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        view.performClick()
        return gestureDetector.onTouchEvent(motionEvent)
    }

    private inner class GestureListener : SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(first: MotionEvent, second: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            val diffY = second.y - first.y
            val diffX = second.x - first.x
            return when {
                Math.abs(diffX) > Math.abs(diffY) ->
                    possiblyFireSwipeEvent(velocityX, diffX, onSwipeLeft, onSwipeRight)
                else -> possiblyFireSwipeEvent(velocityY, diffY, onSwipeDown, onSwipeUp)
            }
        }
    }

    private fun possiblyFireSwipeEvent(
        velocity: Float,
        difference: Float,
        onSwipePositive: SwipeCallback,
        onSwipeNegativeOrZero: SwipeCallback
    ): Boolean {
        val isSwipe = Math.abs(difference) > SWIPE_THRESHOLD && Math.abs(velocity) > SWIPE_VELOCITY_THRESHOLD
        if (!isSwipe) {
            return false
        }
        when {
            difference > 0 -> onSwipePositive()
            else -> onSwipeNegativeOrZero()
        }
        return true
    }
}