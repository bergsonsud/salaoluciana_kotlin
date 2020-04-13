package com.example.salaoluciana.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.salaoluciana.R
import com.example.salaoluciana.activities.MainActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.main_activity.*

class LoginActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var callbackManager: CallbackManager
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        setupGoogleButton()
        setupSignInClientGoogle()
        
        setupFacebook()
        setupEmail()

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            GoMainAcitivity()
        }

    }

    override fun onStart() {
        super.onStart()

    }

    private fun setupEmail() {
        button_login_email_senha.setOnClickListener {
            startActivity(Intent(this,LoginEmailActivity::class.java))
        }
    }

    //GOOGLE

    private fun setupGoogleButton() {
        sign_in_button.setOnClickListener {
            signInGoogle()
        }
    }

    private fun setupSignInClientGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //facebook
        callbackManager.onActivityResult(requestCode, resultCode, data)
        //facebook

        if (requestCode == CODE){
            Log.i(TAG,"RequestCODE")
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            task.getResult(ApiException::class.java)?.let { account ->
                firebaseAuthWithGoogle(account)
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->

            if (task.isSuccessful) {
                GoMainAcitivity()
            }
        }
    }

    private fun signInGoogle() {
        startActivityForResult(googleSignInClient.signInIntent, CODE)
    }


    //GOOGLE

    //FACEBOOK



    private fun setupFacebook() {
        callbackManager = CallbackManager.Factory.create()

        buttonFacebookLogin.setReadPermissions("email", "public_profile")
        buttonFacebookLogin.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")

            }

            override fun onError(error: FacebookException) {

            }
        })
        buttonFacebookLogin.setOnClickListener {
            signInFacebook()
        }
    }

    private fun signInFacebook() {

    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    GoMainAcitivity()
                }
            }
    }


    private fun GoMainAcitivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    companion object{
        private const val TAG = "result"
        private const val CODE = 9100
    }
}