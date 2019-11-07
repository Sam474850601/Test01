package example.sam.test01

import android.app.Application
import example.sam.test01.config.Constants
import example.sam.test01.config.RetrofitManager

class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        RetrofitManager.getInstance().buildHttpsRetrofit(Constants.Url.api_base_url)
    }

}