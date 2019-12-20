package com.krayem.hearthstone.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class CardClass (@Id var id:Long,val cardId:String, val name:String)