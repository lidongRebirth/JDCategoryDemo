package com.sxygsj.jdcategorydemo

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.sxygsj.jdcategorydemo.adapter.LeftAdapter
import com.sxygsj.jdcategorydemo.adapter.RightAdapter
import com.sxygsj.jdcategorydemo.databinding.ActivityMainBinding
import com.sxygsj.jdcategorydemo.ItemDecoration.XmlDecoration
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
@Author LD
@Time 2023/10/13 9:50
@Describe 仿京东的实现种类分类
https://www.jianshu.com/p/41b4b749ac28
@Modify
 */
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val dataList = mutableListOf<TypeBean>()
    private val leftAdapter = LeftAdapter(dataList)

    private val rightAdapter = RightAdapter(dataList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()

        initRcy()
    }

    private fun initData() {
        val json: String? = getJson(this, "category.json")
        val gson = Gson()
        val categoryBean = gson.fromJson<CategoryBean>(json, CategoryBean::class.java)
        dataList.addAll(categoryBean.data)
        leftAdapter.notifyDataSetChanged()
        rightAdapter.notifyDataSetChanged()

    }

    private fun initRcy() {
        initLeftRcy()
        initRightRcy()
    }

    private fun initLeftRcy() {
        val linearLayoutManager = LinearLayoutManager(this)
        binding.leftRcy.layoutManager = linearLayoutManager
        binding.leftRcy.adapter = leftAdapter

        leftAdapter.setLeftClickListener(object : LeftAdapter.LeftClickListener {
            override fun onItemClick(position: Int) {
                var layoutManager = binding.rightRcy.layoutManager as LinearLayoutManager

                //将不在屏幕上的item移动到屏幕上
                //将该item移动到第一项
                layoutManager.scrollToPositionWithOffset(position, 0)
            }
        })

    }

    private fun initRightRcy() {
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rightRcy.layoutManager = linearLayoutManager
        binding.rightRcy.adapter = rightAdapter

        binding.rightRcy.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //无法下滑，移动到最后时，将左侧列表的最后一项设置为选中
                if (!recyclerView.canScrollVertically(1)) {
                    leftAdapter.setSelectedNum(dataList.size-1)
                }
                //右侧列表可以滑动
                else {
                    val rightLayoutManager = binding.rightRcy.layoutManager as LinearLayoutManager
                    val position = rightLayoutManager.findFirstVisibleItemPosition()
                    leftAdapter.setSelectedNum(position)
                }
            }
        })

        val titleNameList=dataList.map {it.moduleTitle  }
        binding.rightRcy.addItemDecoration(XmlDecoration(titleNameList))
    }

    /**
     * 得到json文件中的内容
     *
     * @param context
     * @param fileName
     * @return
     */
    private fun getJson(context: Context, fileName: String?): String? {
        val stringBuilder = StringBuilder()
        //获得assets资源管理器
        val assetManager = context.assets
        //使用IO流读取json文件内容
        try {
            val bufferedReader = BufferedReader(
                InputStreamReader(
                    assetManager.open(fileName!!), "utf-8"
                )
            )
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }

}