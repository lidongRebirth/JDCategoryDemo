package com.sxygsj.jdcategorydemo

/**
 * @Author LD
 * @Time 2023/10/13 10:28
 * @Describe
 * @Modify
 */
data class CategoryBean(
    var code: Int = 0,
    var data: List<TypeBean> = listOf()
)
//左侧分类Bean
data class TypeBean(
    var dataList: List<GoodsBean> = listOf(),
    var interfaceLink: String = "",
    var moduleStyle: String = "",
    //类别名称(左侧标题)
    var moduleTitle: String = "",
    var moreIcon: String = "",
    var moreLinkParam: String = "",
    var moreLinkType: String = "",
    var moreText: String = "",
    var moreTextDisplay: String = "",
    var type: String = ""
)

/**
 * 具体的商品
 */
data class GoodsBean(
    var desc: String = "",
    var id: String = "",
    //商品图片地址
    var imgURL: String = "",
    var linkParam: String = "",
    var linkType: String = "",
    var offline_time: String = "",
    var online_time: String = "",
    //商品名称
    var title: String = "",
    var type: String = ""
)