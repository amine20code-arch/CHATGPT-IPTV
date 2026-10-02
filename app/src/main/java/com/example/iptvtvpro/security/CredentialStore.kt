package com.example.iptvtvpro.security

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object CredentialStore {
    private const val alias="iptv_tv_pro_key"
    private fun key():SecretKey { val ks=java.security.KeyStore.getInstance("AndroidKeyStore").apply{load(null)}; if(!ks.containsAlias(alias)){val kg=KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore");kg.init(KeyGenParameterSpec.Builder(alias,KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_GCM).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE).build());kg.generateKey()};return (ks.getKey(alias,null) as SecretKey)}
    fun put(context:Context,name:String,value:String){val c=Cipher.getInstance("AES/GCM/NoPadding");c.init(Cipher.ENCRYPT_MODE,key());val enc=Base64.encodeToString(c.iv+c.doFinal(value.toByteArray(StandardCharsets.UTF_8)),Base64.NO_WRAP);context.getSharedPreferences("secure_credentials",0).edit().putString(name,enc).apply()}
    fun get(context:Context,name:String):String?=runCatching{val b=Base64.decode(context.getSharedPreferences("secure_credentials",0).getString(name,null)?:return null,Base64.NO_WRAP);val c=Cipher.getInstance("AES/GCM/NoPadding");c.init(Cipher.DECRYPT_MODE,key(),GCMParameterSpec(128,b.copyOfRange(0,12)));String(c.doFinal(b.copyOfRange(12,b.size)),StandardCharsets.UTF_8)}.getOrNull()
}
