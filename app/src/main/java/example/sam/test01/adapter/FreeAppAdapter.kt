package example.sam.test01.adapter

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import example.sam.test01.R
import example.sam.test01.viewmodel.MainViewModel

//所有免费app列表adapter
class FreeAppAdapter : BaseQuickAdapter<FreeAppItem, FreeAppLifeViewHolder>(R.layout.app_item_free_apps) {

    override fun convert(helper: FreeAppLifeViewHolder?, item: FreeAppItem?) {
        helper?.makeLifeOnCreate()
        //评论数目
        val commentTotalTv = helper?.getView<TextView>(R.id.commentTotalTv)
        //app排名位置
        val bankPositionTv = helper?.getView<TextView>(R.id.bankPositionTv)
        //app描述
        val descTv = helper?.getView<TextView>(R.id.descTv)
        //app图标
        val appIconIv = helper?.getView<ImageView>(R.id.appIconIv)
        //appName名字
        val appNameTv = helper?.getView<TextView>(R.id.appNameTv)
        //好评打分
        val likesRb = helper?.getView<RatingBar>(R.id.likesRb)

        val currentPosition = helper?.adapterPosition ?: 0
        commentTotalTv?.text = item?.freeAppAdapterComments() ?: "--"
        bankPositionTv?.text = "${currentPosition+1}"
        descTv?.text = item?.freeAppAdapterDesc() ?: "--"
        appNameTv?.text = item?.freeAppAdapterAppName() ?: "--"

        if (appIconIv is ImageView) {
            //偶数是圆形，奇数是圆角
            val options = if (0 == (currentPosition+1) % 2) RequestOptions.circleCropTransform()
                          else RequestOptions.bitmapTransform(RoundedCorners(8))
            Glide.with(appIconIv).load(item?.freeAppAdapterIcon() ?: "")
                .placeholder(R.mipmap.ic_dd01)
                .apply(options)
                .into(appIconIv)
        }

        likesRb?.rating = item?.freeAppAdapterLikes()?.toFloat()?:0.0f

       val mainViewModel = MainViewModel()


    }



    override fun onViewDetachedFromWindow(holder: FreeAppLifeViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.makeLifeOnDestroy()
    }

}

//赋予免费app列表具有可见屏幕生命周期
class FreeAppLifeViewHolder(val view: View) : BaseViewHolder(view), LifecycleOwner {
    val life =   LifecycleRegistry(this)
    override fun getLifecycle(): Lifecycle = life
    fun makeLifeOnCreate(){
        life.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }
    fun makeLifeOnDestroy(){
        life.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}


//所有免费app列表视图模型
interface FreeAppItem {

    //app 名字
    fun freeAppAdapterAppName(): String?

    //app 描述
    fun freeAppAdapterDesc(): String?

    //app 好评打分，0到5分（即星星数目）
    fun freeAppAdapterLikes(): Int

    //app 图标地址
    fun freeAppAdapterIcon(): String?

    //app 评论个数
    fun freeAppAdapterComments(): String?
}

