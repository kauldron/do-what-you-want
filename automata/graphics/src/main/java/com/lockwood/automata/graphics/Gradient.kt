package com.lockwood.automata.graphics

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt
import androidx.annotation.Px

private const val DEFAULT_RADIUS_SIZE = 0.0F
private const val DEFAULT_STROKE_SIZE = DEFAULT_RADIUS_SIZE

fun buildGradientDrawable(
    @ColorInt colors: IntArray,
    gradientShape: Int = GradientDrawable.RECTANGLE,
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT,
): GradientDrawable = GradientDrawable(orientation, colors).apply {
    shape = gradientShape
}

fun buildGradientDrawable(
    @ColorInt colors: IntArray,
    gradientShape: Int = GradientDrawable.RECTANGLE,
    orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT,
    @ColorInt strokeColor: Int = Color.TRANSPARENT,
    @Px radius: Float = DEFAULT_RADIUS_SIZE,
    @Px stroke: Float = DEFAULT_STROKE_SIZE,
): GradientDrawable = buildGradientDrawable(colors, gradientShape, orientation).apply {
    cornerRadius = radius
    setStroke(stroke.toInt(), strokeColor)
}