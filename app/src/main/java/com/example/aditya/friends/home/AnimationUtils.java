package com.example.aditya.friends.home;

import android.animation.Animator;

public class AnimationUtils {

    public static abstract class AnimationEndListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animation) { }

        @Override
        public void onAnimationCancel(Animator animation) { }

        @Override
        public void onAnimationRepeat(Animator animation) { }
    }
}
