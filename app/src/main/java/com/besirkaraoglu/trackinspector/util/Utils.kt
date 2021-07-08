package com.besirkaraoglu.trackinspector.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.besirkaraoglu.trackinspector.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception
import java.util.*

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    requireContext().showToast(message, duration)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

inline fun <T : View> T.showIf(condition: (T) -> Boolean) {
    if (condition(this)) {
        show()
    } else {
        hide()
    }
}

fun tsToString(ts:Long) : String {
    val cal = Calendar.getInstance(Locale.getDefault())
    cal.timeInMillis = ts
    return "${cal.get(Calendar.MINUTE)}:${cal.get(Calendar.SECOND)}"
}


inline fun <T: ImageView> T.setFav(condition: (T) -> Boolean){
    if(condition(this)){
        fav()
    }else{
        notFav()
    }
}

fun ImageView.fav() {
    setImageResource(R.drawable.ic_twotone_favorite_24)
}

fun ImageView.notFav() {
    setImageResource(R.drawable.ic_baseline_favorite_border_24)
}

fun bitmapLoader(url: String): Palette.Swatch? {
    var swatch: Palette.Swatch? = null
    Picasso.get()
        .load(url)
        .into(object : Target{
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                if (bitmap != null) {
                     swatch = Palette.from(bitmap).maximumColorCount(2).generate().dominantSwatch
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

            }
        })
    return swatch
}