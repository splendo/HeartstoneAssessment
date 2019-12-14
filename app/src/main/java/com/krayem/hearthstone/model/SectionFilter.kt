package com.krayem.hearthstone.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class SectionFilter(
    @Id var id: Long,
    val index: Int,
    val typesJsonArray: String
)