package com.example.shoppingliststartcodekotlin.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object Repository {

    //private lateinit var db: FirebaseFirestore
    var products = mutableListOf<Product>()

    //listener to changes that we can then use in the Activity
    private var productListener = MutableLiveData<MutableList<Product>>()


    fun getData(): MutableLiveData<MutableList<Product>> {
        //db = Firebase.firestore
        if (products.isEmpty())
            createTestData()
        productListener.value = products //we inform the listener we have new data
        return productListener
    }

    fun createTestData()
    {   var produkt : Product = Product("kartofler",0)
         products.add(produkt)
        var produkt1 : Product = Product("bær",0)
        products.add(produkt1)
        var produkt2 : Product = Product("kød",0)
        products.add(produkt2)

    }

}