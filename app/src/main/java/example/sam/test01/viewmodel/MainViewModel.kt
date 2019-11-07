package example.sam.test01.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import example.sam.test01.adapter.FreeAppItem
import example.sam.test01.api.AppApi
import example.sam.test01.api.EntryBean
import example.sam.test01.config.RetrofitManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

//视图列表视图viewmodel
class MainViewModel : ViewModel(){
     val appApi = RetrofitManager.create(AppApi::class.java)
     init {

     }

    //免费app list LiveData
    val freeAppItemLiveData = MutableLiveData<List<FreeAppItem>>()

    //搜索app
    fun searchApp(searchAppName:String?){

    }

    //加载所有数据
    @SuppressLint("CheckResult")
    fun loadAllData(){
        appApi.getLoadAllFreeInfoApi().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe (
            {
                freeAppItemLiveData.value = it.feed?.entry?:ArrayList()
            },{
                Log.e("error_response",""+it)
            }
        )
    }


    //加载免费的好相关信息
    @SuppressLint("CheckResult")
    fun findFreeAppOtherInfo(item:FreeAppItem){
        if(item is EntryBean){
            val id =
            appApi.getLoadFreeOtherInfoApi(id = "${item.id}").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe (
                {

                },{
                    Log.e("error_response",""+it)
                }
            )
        }
    }

}