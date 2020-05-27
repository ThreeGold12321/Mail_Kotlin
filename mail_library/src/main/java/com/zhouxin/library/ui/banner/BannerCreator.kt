package com.zhouxin.library.ui.banner

import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer

/**
 * @author zhouxin on 2020/4/22.
 */
object BannerCreator {
    //创建默认风格的轮播
    fun setDefault(banner: Banner, data: List<*>) {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                //设置图片加载器
                .setImageLoader(GlideImageLoader())
                .setImages(data)
                .setBannerAnimation(Transformer.Default)
                //自动播放
                .isAutoPlay(true)
                //设置间隔时间
                .setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.CENTER)
                .start()
    }
}