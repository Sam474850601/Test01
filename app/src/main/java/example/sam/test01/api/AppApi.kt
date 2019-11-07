package example.sam.test01.api

import com.google.gson.annotations.SerializedName
import example.sam.test01.adapter.FreeAppItem
import io.reactivex.Observable
import io.reactivex.Observer
import retrofit2.http.GET
import retrofit2.http.Query

//app 的后台数据api
interface AppApi{

    //获取所有列表
    @GET("rss/topfreeapplications/limit=100/json")
    fun getLoadAllFreeInfoApi():Observable<LoadAllFreeApiResponseBean>

    @GET("lookup")
    fun getLoadFreeOtherInfoApi(@Query("id") id:String):Observable<LoadFreeOtherInfoResponseBean>

}

//
class LoadAllFreeApiResponseBean:Result(){
    var feed:FeedBean? = null
}


class FeedBean{
    var entry:List<EntryBean> ? = null

}


class EntryBean: FreeAppItem{
    override fun freeAppAdapterComments(): String? ="(17)"

    override fun freeAppAdapterLikes(): Int =3

    override fun freeAppAdapterAppName(): String? = im_name?.label

    override fun freeAppAdapterDesc(): String? = category?.attributes?.label

    override fun freeAppAdapterIcon(): String? = im_image?.get(0)?.label

    @SerializedName("im:name")
    var im_name:AttributeBean? = null
    @SerializedName("im:image")
    var im_image:List<AttributeBean>? = null


    @SerializedName("im:contentType")
    var im_contentType:AttributeBean? = null

    var id:AttributeBean? = null
    var category:AttributeBean? = null
}

class  AttributeBean{
    var label:String? = null
    var attributes:AttributeChild? = null

}

class  AttributeChild{
    var label:String? = null
    @SerializedName("im:id")
    var im_id:String? = null
    @SerializedName("im:bundleId")
    var im_bundleId:String? = null
    @SerializedName("im:assetType")
    var im_assetType:String? = null
    var rel:String? = null
    var type:String? = null
    var height:String? = null
    var width:String? = null
    var term:String? = null
    var scheme:String? = null
    var amount:String? = null
    var currency:String? = null
}

open class Result{
    open var errorMessage:String? = null
}



//加载免费app其他信息
class LoadFreeOtherInfoResponseBean : Result(){
    var resultCount:Int? = 0
    var results:LoadFreeOtherInfoResponseResult? = null
}

class LoadFreeOtherInfoResponseResult{
    //评分
    var averageUserRating:Int? = 0;
    //评论数目
    var userRatingCount:Int? = 0;


}