package com.gunashekar.mentalhealth.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.gunashekar.mentalhealth.R
import com.gunashekar.mentalhealth.adapter.UserAdapter
import com.gunashekar.mentalhealth.model.User
import kotlinx.android.synthetic.main.activity_users.*

class UsersActivity : AppCompatActivity() {
    var userList = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        userRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        getUsersList()
    }

    fun getUsersList() {
        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

//        var userid = firebase.uid
//        FirebaseMessaging.getInstance().subscribeToTopic("/topics/$userid")

        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
//                val currentUser = snapshot.getValue(User::class.java)
//                if (currentUser!!.profileImage == ""){
//                    imgProfile.setImageResource(com.google.firebase.database.R.drawable.profile_image)
//                }else{
//                    Glide.with(this@UsersActivity).load(currentUser.profileImage).into(imgProfile)
//                }

                for (dataSnapShot in snapshot.children) {
                    val user = dataSnapShot.getValue(User::class.java)
                    if (user != null) {
                        Toast.makeText(applicationContext, user.Name, Toast.LENGTH_SHORT).show()
                    }
                    if (!user!!.uid.equals(firebase.uid) && user!!.Role.equals("Doctor")) {
                        userList.add(user)
                    }
                }

                val userAdapter = UserAdapter(this@UsersActivity, userList)
                userRecyclerView.adapter = userAdapter
            }

        })
    }
}