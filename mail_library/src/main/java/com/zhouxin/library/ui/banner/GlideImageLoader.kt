package com.zhouxin.library.ui.banner

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.youth.banner.loader.ImageLoader

/**
 * @author zhouxin on 2020/4/24.
 */
class GlideImageLoader : ImageLoader() {

    companion object {
        private val options = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    override fun createImageView(context: Context?): ImageView {
        return AppCompatImageView(context)
    }

    override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
        Glide.with(context)
                .load(path)
                .apply(options)
                .into(imageView)
    }

}