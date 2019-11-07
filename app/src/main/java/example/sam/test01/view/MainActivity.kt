package example.sam.test01.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import example.sam.test01.R
import example.sam.test01.adapter.FreeAppAdapter
import example.sam.test01.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.app_activity_main.*

//测试程序页面， 包含搜索功能，热门top 10, 以及100 app数据
class MainActivity : AppCompatActivity(), TextWatcher {
    //免费app列表
    val freeAppAdapter =FreeAppAdapter()
     var mainViewModel:MainViewModel? = null

    override fun afterTextChanged(seachEditable: Editable?) {
        val searchContentLen = seachEditable?.length ?: 0
        //根据内容是否为空显示提示输入搜索
        searchPromptLayout.visibility = if(searchContentLen>0) View.GONE else View.VISIBLE
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_main)
        initViews()
        bindViewModels()
    }



    //初始化相关视图
    private fun initViews() {
        //搜索框监听
        searchEt.addTextChangedListener(this)

        //初始化免费app列表视图
        with(freeAppRv){
            layoutManager = LinearLayoutManager(context)
            adapter = freeAppAdapter
        }

    }

    //绑定相关的viewmodel
    private fun bindViewModels() {
        ViewModelProviders.of(this)[MainViewModel::class.java].apply {
            mainViewModel = this
            freeAppItemLiveData.observe(this@MainActivity, Observer {
                freeAppAdapter.setNewData(it)
            })
            loadAllData()
        }

    }


    override fun onDestroy() {
        searchEt.removeTextChangedListener(this)
        super.onDestroy()
    }

}
