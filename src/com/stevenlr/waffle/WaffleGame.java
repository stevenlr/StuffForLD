/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface WaffleGame {

	int viewportWidth() default 800;
	int viewportHeight() default 600;
	int pixelAspect() default 1;
	String title() default "Waffle game";
	boolean showFps() default false;
}
