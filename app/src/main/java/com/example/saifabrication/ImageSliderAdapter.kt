package com.example.saifabrication

// ImageSliderAdapter.kt
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter

class ImageSliderAdapter(private val context: Context, private val imageList: List<Int>) : PagerAdapter() {
    override fun getCount(): Int = imageList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.slider_image_item, container, false)
        val imageView: ImageView = view.findViewById(R.id.imageSlider)
        imageView.setImageResource(imageList[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
