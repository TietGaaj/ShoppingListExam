package com.example.shoppingliststartcodekotlin.data

import com.google.firebase.firestore.Exclude

data class Product(var name:String = "", var quantity:Int=0,@get:Exclude var id:String="") {}
