package edu.farmingdale.alrajab.week9chatapp

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Message(
    val key:String?=null,
    val author:String?=null,
    val body:String?=null,
    val date:String?=null,
    val media:String?=null
){}
