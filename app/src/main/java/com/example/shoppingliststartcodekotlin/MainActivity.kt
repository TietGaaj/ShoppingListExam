package com.example.shoppingliststartcodekotlin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingliststartcodekotlin.adapters.ProductAdapter
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.data.Repository
import com.example.shoppingliststartcodekotlin.data.Repository.deleteProduct
import com.example.shoppingliststartcodekotlin.data.Repository.getData
import com.example.shoppingliststartcodekotlin.data.Repository.listener
import com.example.shoppingliststartcodekotlin.data.Repository.productListener
import com.example.shoppingliststartcodekotlin.data.Repository.products
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Attributes

class MainActivity : AppCompatActivity() {

    var context: Context? = null
    lateinit var adapter: ProductAdapter
    private var registration: ListenerRegistration? = null
    private lateinit var db : FirebaseFirestore

    fun addproduct(){

        val newproduct = Product(
                name = editName.text.toString(),
                quantity = editQuan.text.toString().toInt()
        )
                db.collection("Product")
                .add(newproduct)
                .addOnSuccessListener { documentReference ->
                    Log.d("Error","DocumentSnapshot written with ID:"+documentReference.id)
                    newproduct.id = documentReference.id
                }
                .addOnFailureListener{e ->Log.w("Error", "Error adding document",e)}
    }
    private fun deleteall(){
        val batch = db.batch()
        for(Product in products){
            val ref = db.collection("Product").document(Product.id)
            batch.delete(ref)
        }
        batch.commit().addOnCompleteListener{
            products.clear()
            adapter.notifyDataSetChanged()
        }
    }


    private fun upd(product: Product, name:String,quantity:Int){
        db.collection("Product").document(product.id)
            .update("name",product.name,"quantity",product.quantity)

    }
    //you need to have an Adapter for the products
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(applicationContext)
        db = Firebase.firestore
        addProduct.setOnClickListener{ addproduct()}
        delete.setOnClickListener{deleteall()}

        Repository.getData().observe(this, Observer {
            Log.d("Products","Found ${it.size} products")
            updateUI()
        })
        val query = db.collection("Product")
        registration = query.addSnapshotListener(listener)

    }

    fun updateUI() {

        val layoutManager = LinearLayoutManager(this)

        /*you need to have a defined a recylerView in your
        xml file - in this case the id of the recyclerview should
        be "recyclerView" - as the code line below uses that */

        recyclerView.layoutManager = layoutManager

        adapter = ProductAdapter(Repository.products)

      /*connecting the recyclerview to the adapter  */
        recyclerView.adapter = adapter

    }


}