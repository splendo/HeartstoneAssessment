package com.krayem.hearthstone.ui.main

import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet

class ImageTransition : TransitionSet(){
    init {
        ordering = ORDERING_TOGETHER
        addTransition(ChangeBounds())
        addTransition(ChangeTransform())
        addTransition(ChangeImageTransform())
    }

//    public ImageTransition() {
//        setOrdering(ORDERING_TOGETHER);
//        addTransition(new ChangeBounds()).
//            addTransition(new ChangeTransform()).
//            addTransition(new ChangeImageTransform()));
//    }
}