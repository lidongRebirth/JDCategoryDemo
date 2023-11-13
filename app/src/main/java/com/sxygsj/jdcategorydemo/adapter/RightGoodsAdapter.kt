package com.sxygsj.jdcategorydemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sxygsj.jdcategorydemo.GoodsBean
import com.sxygsj.jdcategorydemo.R

/**
 * @Author LD
 * @Time 2023/10/13 11:53
 * @Describe
 * @Modify
 */
class RightGoodsAdapter(private val dataList: MutableList<GoodsBean>) :
    RecyclerView.Adapter<RightGoodsAdapter.MyGridViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RightGoodsAdapter.MyGridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_right_goods,parent,false)
        return MyGridViewHolder(view)
    }

    override fun onBindViewHolder(holder: RightGoodsAdapter.MyGridViewHolder, position: Int) {
        val goodsBean=dataList[position]
        // TODO: 增加错误图片
        Glide.with(holder.ivGoodsPic.context).load(goodsBean.imgURL).into(holder.ivGoodsPic)
        holder.tvGoodsName.text=goodsBean.title
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyGridViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //商品图片
        val ivGoodsPic = view.findViewById<ImageView>(R.id.ivGoodsPic)

        //商品名称
        val tvGoodsName = view.findViewById<TextView>(R.id.tvGoodsName)


    }
}