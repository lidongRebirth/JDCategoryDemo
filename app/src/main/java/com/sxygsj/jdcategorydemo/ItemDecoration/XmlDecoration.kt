package com.sxygsj.jdcategorydemo.ItemDecoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.sxygsj.jdcategorydemo.R

/**
 * @Author LD
 * @Time 2023/10/16 15:31
 * @Describe 使用XML布局来作为头部和悬浮头部
 * @Modify
 */
class XmlDecoration(private val titleDataList: List<String>) : ItemDecoration() {

    companion object {
        const val TAG = "ceshi_xml"
    }

    private var headTitleView: View? = null



    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (headTitleView == null) {
            headTitleView =
                LayoutInflater.from(parent.context).inflate(R.layout.head_itemview, null, false)

            //获取父布局的宽度，将其作为headTitleView的宽度
            val width = parent.layoutManager?.width?:0


            headTitleView?.measure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
        }
        headTitleView?.let {
            //以下两种方式都可以
            //方式一
            //距离ItemView的上方偏移topHeight高度
            outRect.top = it.measuredHeight

            //方式二
//            val layoutParams = view.layoutParams as RecyclerView.LayoutParams
//            layoutParams.topMargin = it.measuredHeight
//            view.layoutParams = layoutParams
        }
    }

    /**
     * 绘制头部
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val bottom = child.top

            headTitleView?.let {
                val top = bottom - it.measuredHeight
                val itemView = parent.getChildAt(i)
                val position = parent.getChildAdapterPosition(itemView)
                //获取该位置的标题名称
                val groupTitleName = titleDataList[position].toUpperCase()
                //控件设置内容
                it.findViewById<TextView>(R.id.tvTitle).text = groupTitleName

                // 保存 Canvas 的状态
                c.save()
                // 平移 Canvas，使 View 绘制在正确位置
                c.translate(0f, top.toFloat())
                it.layout(0, top, parent.measuredWidth, bottom)
                it.draw(c)
                c.restore()
            }
        }
    }

    /**
     * getItemOffsets 是针对每一个 ItemView，而 onDraw 方法却是针对 RecyclerView 本身，所以在 onDraw 方法中需要遍历屏幕上可见的 ItemView
     * 绘制悬浮头部
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)


        //---------------------------------------无动画的悬浮固定头部----------------------------------
//        val left = 0
//        val right=parent.measuredWidth
//        val top=0
//        val bottom =
//        var titleName=""
//
//        val itemView = parent.getChildAt(0)
//        val position = parent.getChildAdapterPosition(itemView)
//        titleName=titleDataList[position].toUpperCase()
//        //固定头部
//        val rightLayoutManager = parent.layoutManager as LinearLayoutManager
//        val position = rightLayoutManager.findFirstVisibleItemPosition()
//        Log.i(TAG, "onDrawOver: position为：$position")
//        titleName=titleDataList[position].toUpperCase()
//        Log.i(TAG, "onDrawOver: title为：$titleName")
//        headTitleView?.let {
//            it.findViewById<TextView>(R.id.tvTitle).text = titleName
//            Log.i(TAG, "onDrawOver: 设置的名字为$titleName")
//            it.layout(left,top,right,it.measuredHeight)
//            it.draw(c)
//        }

        //-----------------------------有动画被顶上去的效果的悬浮头部--------------------------------------------

        val itemView = parent.getChildAt(0)
        val position = parent.getChildAdapterPosition(itemView)
        var titleName = titleDataList[position].toUpperCase()


        val left = 0
        val right = parent.measuredWidth
        //默认的指定高度
        var height = headTitleView?.measuredHeight ?: 0
        //当前ItemView的底部
        var bottom = itemView.bottom
        if (bottom<height){
            height=bottom
        }
        headTitleView?.let {
            it.findViewById<TextView>(R.id.tvTitle).text = titleName
            c.save()
            // 平移 Canvas，使 View 绘制在正确位置
            c.translate(0f, (height-it.measuredHeight).toFloat())
            it.layout(left, height-it.measuredHeight, right, height)
            it.draw(c)
            c.restore()
        }
    }
}