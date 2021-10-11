package com.example.seraqchove.fragments

import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.seraqchove.R
import com.example.seraqchove.data.entities.User
import com.example.seraqchove.data.viewModels.UserViewModel
import com.example.seraqchove.utils.Constants
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class RegisterFragment : Fragment() {
    private lateinit var instanceUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        instanceUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.register_btn.setOnClickListener {
            createUser()
        }

        return view
    }

    private fun createUser() {
        val username = register_username.text.toString()
        val password = register_passw.text.toString()
        val repeatPassword = register_passw_repeat.text.toString()

        if(validatePassword(password,repeatPassword) && validateInput(username,password,repeatPassword)){
            try{
                val passwordHash = encrypt(password)
                val user = User(0,username,passwordHash,false)
                instanceUserViewModel.createUser(user)
                Toast.makeText(requireContext(), "Usuario criado com sucesso!", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }catch (e: SQLiteException){
                e.printStackTrace()
            }

        }else{
            Toast.makeText(requireContext(), "Preencha todos os campos corretamente!", Toast.LENGTH_LONG).show()
        }
    }

    private fun validatePassword(password: String, repeatPassword: String): Boolean{
        if(password == repeatPassword){
            return true
        }
        return false
    }

    private fun validateInput(username: String, password: String, repeatPassword: String): Boolean{
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(repeatPassword)){
            return true
        }
        return false
    }

    private fun encrypt(strToEncrypt: String) :  String{
        val ivParameterSpec = IvParameterSpec(Base64.decode(Constants.IV, Base64.DEFAULT))

        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val spec =  PBEKeySpec(Constants.SECRET_KEY.toCharArray(), Base64.decode(Constants.SALT, Base64.DEFAULT), 10000, 256)
        val tmp = factory.generateSecret(spec)
        val secretKey =  SecretKeySpec(tmp.encoded, "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
        return Base64.encodeToString(cipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8)), Base64.DEFAULT)
    }

}