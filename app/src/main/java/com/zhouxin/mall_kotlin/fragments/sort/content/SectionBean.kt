package com.zhouxin.mall_kotlin.fragments.sort.content

import com.chad.library.adapter.base.entity.SectionEntity

/**
 * @author zhouxin on 2020/4/26.
 */
class SectionBean : SectionEntity<SectionContentItemEntity> {

    var isMore = false
    var id = -1

    constructor(sectionContentItemEntity: SectionContentItemEntity) : super(sectionContentItemEntity)

    constructor(isHeader: Boolean, header: String) : super(isHeader, header)
}