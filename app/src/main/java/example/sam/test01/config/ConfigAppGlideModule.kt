package example.sam.test01.config

import android.content.Context
import android.os.Environment
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.module.AppGlideModule

//glide 图片disk缓存
@GlideModule
class ConfigAppGlideModule : AppGlideModule(){

    private val sdRootPath = Environment.getExternalStorageDirectory().path
    private var appRootPath: String? = null
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        appRootPath = context.cacheDir.path
        builder.setDiskCache(
            DiskLruCacheFactory(getStorageDirectory()!! + "/test01/GlideDisk", (1024 * 1024 * 100).toLong())
        )
    }

    private fun getStorageDirectory(): String? {
        return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)
            sdRootPath
        else
            appRootPath
    }
}