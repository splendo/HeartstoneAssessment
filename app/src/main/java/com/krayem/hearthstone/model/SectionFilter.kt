package com.krayem.hearthstone.model

import com.krayem.hearthstone.objectbox.ObjectBoxStringListConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class SectionFilter(
    @Id var id: Long,
    @Convert(converter = ObjectBoxStringListConverter::class, dbType = String::class)
    val types:List<String>,
    @Convert(converter = ObjectBoxStringListConverter::class, dbType = String::class)
    val mechanics: List<String>,
    @Convert(converter = ObjectBoxStringListConverter::class, dbType = String::class)
    val classes:List<String>,
    val minRarity:Int,
    val maxRarity:Int,
    val sortBy:Int,
    val descending:Boolean
){
    companion object{
        const val SORT_BY_ALPHABETICAL = 1
        const val SORT_BY_RARITY = 2
    }
}