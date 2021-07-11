package com.gunashekar.mentalhealth.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.gunashekar.mentalhealth.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    val TAG = "Error"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        var role = intent.getStringExtra("role")!!
        auth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            val userName = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (TextUtils.isEmpty(userName)){
                etName.error = "Name is required"
                etName.requestFocus()
            }else if (TextUtils.isEmpty(email)){
                etEmail.error = "Email is required"
                etEmail.requestFocus()
            }else if (TextUtils.isEmpty(password)){
                etPassword.error = "Password is required"
                etPassword.requestFocus()
            }else if (TextUtils.isEmpty(confirmPassword)){
                etConfirmPassword.error = "Confirm password is required"
                etConfirmPassword.requestFocus()
            }else if(password.length<8){
                etPassword.error = "Password length should be of at least 8 characters"
                etPassword.requestFocus()
            }
            else if (password != confirmPassword){
                etConfirmPassword.error = "Passwords doesn't match"
                etConfirmPassword.requestFocus()
            } else {
                registerUser(userName, email, password, role)
            }
        }

        btnLogin.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun registerUser(userName:String, email:String, password:String, role:String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    val userId: String = auth.currentUser!!.uid

                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId)

                    val userHashMap:HashMap<String, Any> = HashMap()
                    userHashMap["uid"] = userId
                    userHashMap["Name"] = userName
                    userHashMap["Email"] = email
                    userHashMap["Role"] = role
                    userHashMap["profileImage"] = ""

                    databaseReference.updateChildren(userHashMap)
                        .addOnCompleteListener(this){
//                            etName.setText("")
//                            etEmail.setText("")
//                            etPassword.setText("")
//                            etConfirmPassword.setText("")
                            val intent = Intent(this@SignUpActivity, UsersActivity::class.java)
                            startActivity(intent)
                            finish()
                    }
                } else {
                    Toast.makeText(applicationContext , "Some error occurred!! (make sure your email id is not registered before)", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener(){task->
                Log.d(TAG, "$task")
            }
    }
}