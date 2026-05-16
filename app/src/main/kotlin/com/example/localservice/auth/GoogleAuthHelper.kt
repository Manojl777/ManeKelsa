package com.example.localservice.auth

import android.content.Context
import android.content.Intent
import com.example.localservice.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GoogleAuthHelper(context: Context) {

    private val gso = GoogleSignInOptions.Builder(
        GoogleSignInOptions.DEFAULT_SIGN_IN
    )
        .requestIdToken(
            context.getString(R.string.default_web_client_id)
        )
        .requestEmail()
        .build()

    val googleSignInClient: GoogleSignInClient =
        GoogleSignIn.getClient(context, gso)

    fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }
}