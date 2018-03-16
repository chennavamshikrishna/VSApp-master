package com.vsapp.petcare.Utils.ui.pageindicatorview.animation;

import android.support.annotation.NonNull;
import com.vsapp.petcare.Utils.ui.pageindicatorview.animation.controller.AnimationController;
import com.vsapp.petcare.Utils.ui.pageindicatorview.animation.controller.ValueController;
import com.vsapp.petcare.Utils.ui.pageindicatorview.draw.data.Indicator;

public class AnimationManager {

    private AnimationController animationController;

    public AnimationManager(@NonNull Indicator indicator, @NonNull ValueController.UpdateListener listener) {
        this.animationController = new AnimationController(indicator, listener);
    }

    public void basic() {
        if (animationController != null) {
            animationController.end();
            animationController.basic();
        }
    }

    public void interactive(float progress) {
        if (animationController != null) {
            animationController.interactive(progress);
        }
    }

    public void end() {
        if (animationController != null) {
            animationController.end();
        }
    }
}
