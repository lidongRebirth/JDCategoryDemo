package com.sxygsj.jdcategorydemo.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sxygsj.jdcategorydemo.R
import com.sxygsj.jdcategorydemo.TypeBean

/**
 * @Author LD
 * @Time 2023/10/13 10:35
 * @Describe
 * @Modify
 */
class LeftAdapter(private val dataList: MutableList<TypeBean>) :
    RecyclerView.Adapter<LeftAdapter.MyLeftViewHolder>() {

    //默认选中的位置
    var selectPosition = 0
    private var listener:LeftClickListener?=null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): LeftAdapter.MyLeftViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_left, parent, false)
        return MyLeftViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeftAdapter.MyLeftViewHolder, position: Int) {
        val typeBean = dataList[position]
        holder.tvLeftMenuName.text = typeBean.moduleTitle
        holder.itemView.isSelected = selectPosition == position
        //是选中位置,小蓝线显示
        holder.line.isVisible = holder.itemView.isSelected
        if (holder.itemView.isSelected){
            holder.tvLeftMenuName.setTypeface(null,Typeface.BOLD)
        }else{
            holder.tvLeftMenuName.setTypeface(null,Typeface.NORMAL)
        }

        //左侧列表的点击事件
        holder.itemView.setOnClickListener {
            if (holder.adapterPosition in 0 until itemCount) {
                this.selectPosition = holder.adapterPosition
                //这个就实现了selected的更新了
                notifyDataSetChanged()
                //点击事件
                listener?.onItemClick(position)
            }
        }
    }

    /**
     * 选中左侧区域，主要用于滑动右侧时需要联动左侧列表
     */
    fun setSelectedNum(selectedNum: Int) {
        if (selectedNum in 0 until itemCount) {
            selectPosition = selectedNum
            notifyDataSetChanged()
        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyLeftViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //左侧菜单名称
        val tvLeftMenuName = view.findViewById<TextView>(R.id.tvLeftMenuName)
        //左侧蓝色线
        val line=view.findViewById<View>(R.id.vLeftLine)
    }

    fun setLeftClickListener(listener: LeftClickListener){
        this.listener=listener
    }
    interface LeftClickListener {
        fun onItemClick(position: Int)
    }

}