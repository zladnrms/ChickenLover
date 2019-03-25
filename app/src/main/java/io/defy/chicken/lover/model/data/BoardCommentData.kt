package io.defy.chicken.lover.model.data

data class BoardCommentData(val _id : Int, val name : String, val content : String, var thumbs_up : Array<String>, val write_date : String, var invisible : Int)