package com.example.shoppingliststartcodekotlin.data

import android.content.Context
import android.util.Log
import android.view.ContextMenu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import com.example.shoppingliststartcodekotlin.MainActivity
import com.example.shoppingliststartcodekotlin.R
import com.example.shoppingliststartcodekotlin.adapters.ProductAdapter
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import io.grpc.InternalChannelz.id
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*



object Repository {
    private lateinit var adapter: ArrayAdapter<Product>
    private lateinit var db : FirebaseFirestore



    var products = mutableListOf<Product>()

    //listener to changes that we can then use in the Activity
     var productListener = MutableLiveData<MutableList<Product>>()




    fun getData(): MutableLiveData<MutableList<Product>> {
        db = Firebase.firestore
        if (products.isEmpty())
            readDataFromFirebase()
        productListener.value = products //we inform the listener we have new data
        return productListener
    }

   private fun readDataFromFirebase(){
        val db = Firebase.firestore
        db.collection("Product").get()
                .addOnSuccessListener { result ->
                    for (document in result){
                        Log.d("Repository","${document.id}=>${document.data}")
                        val product = document.toObject<Product>()
                        product.id = document.id
                        products.add(product)
                    }
                    productListener.value = products
                }
                .addOnFailureListener{exception -> Log.d("Repository","Error getting documents",exception)}
    }

    fun deleteProduct(index:Int){
        val product = products[index]
        db.collection("product").document(product.id).delete().addOnSuccessListener {
            Log.d("Snapshot","DocumentSnapshot with id ${product.id} succesfully deleted!")
            products.removeAt(index)
        }
                .addOnFailureListener{ e -> Log.w("Error","Error deleting document", e)}
    }
     var listener = EventListener<QuerySnapshot>{ querySnapshot: QuerySnapshot?, error: FirebaseFirestoreException? ->
        run {

            val db = Firebase.firestore
            val docRef = db.collection("Product")
            docRef.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.d("Repository", "Listen failed.", e)
                    return@addSnapshotListener
                }
                products.clear()
                for (document in snapshot?.documents!!) {
                    Log.d("Repository_snapshotlist", "${document.id} => ${document.data}")
                    val product = document.toObject<Product>()!!
                    product.id = document.id
                    products.add(product)
                }

                productListener.value = products
            }
        }


    }




}