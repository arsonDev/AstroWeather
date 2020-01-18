package pl.arsonproject.astroweather.ui.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}