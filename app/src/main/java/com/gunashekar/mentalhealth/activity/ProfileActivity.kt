package com.gunashekar.mentalhealth.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.gunashekar.mentalhealth.R
import com.gunashekar.mentalhealth.model.User
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.imgBack
import kotlinx.android.synthetic.main.activity_users.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var firebaseUser: FirebaseUser
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                userName.text = user!!.Name

                if (user.profileImage == "") {
                    userImage.setImageResource(R.drawable.profile_image)
//                    imgProfile.setImageResource(com.google.firebase.database.R.drawable.profile_image)
                } else {
                    Glide.with(this@ProfileActivity).load(user.profileImage).into(userImage)
                }
            }
        })

        imgBack.setOnClickListener {
            onBackPressed()
        }

    }
}