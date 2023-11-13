package com.sxygsj.jdcategorydemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sxygsj.jdcategorydemo.R
import com.sxygsj.jdcategorydemo.TypeBean

/**
 * @Author LD
 * @Time 2023/10/13 11:31
 * @Describe
 * @Modify
 */
class RightAdapter(private val dataList: MutableList<TypeBean>): RecyclerView.Adapter<RightAdapter.MyRightViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RightAdapter.MyRightViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_right,parent,false)
        return MyRightViewHolder(view)
    }

    override fun onBindViewHolder(holder: RightAdapter.MyRightViewHolder, position: Int) {
        val typeBean=dataList[position]
        holder.tvRightName.text=typeBean.moduleTitle


        //列表的填充
        val gridLayoutManager = GridLayoutManager(holder.rcyGoods.context,3)
        holder.rcyGoods.layoutManager=gridLayoutManager

        val list = typeBean.dataList
        val gridViewAdapter:RightGoodsAdapter=RightGoodsAdapter(list.toMutableList())
        holder.rcyGoods.adapter=gridViewAdapter

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyRightViewHolder(view:View):RecyclerView.ViewHolder(view){
        //右侧列表的标题
        val tvRightName=view.findViewById<TextView>(R.id.tvRightTitleName)
        //右侧的GridView
        val rcyGoods=view.findViewById<RecyclerView>(R.id.rcyGoods)
    }

}