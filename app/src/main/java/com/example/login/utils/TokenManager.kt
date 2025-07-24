package com.example.login.utils

// Importaciones
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object TokenManager {
    private const val PREF_JWT_TOKEN = "jwt_token" // Renombrado para mayor claridad

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    // Ahora solo guardamos un token
    fun saveToken(context: Context, token: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(PREF_JWT_TOKEN, token)
        editor.apply()
    }

    fun getToken(context: Context): String? {
        return getSharedPreferences(context).getString(PREF_JWT_TOKEN, null)
    }

    fun clearTokens(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.remove(PREF_JWT_TOKEN)
        editor.apply()
    }
}