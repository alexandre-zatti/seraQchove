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
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.seraqchove.R
import com.example.seraqchove.data.viewModels.UserViewModel
import com.example.seraqchove.utils.Constants
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class LoginFragment : Fragment() {
    private lateinit var instanceUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        instanceUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        instanceUserViewModel.getLoggedUser().observe(viewLifecycleOwner, Observer { users ->
            if(users.isNotEmpty()){
                val action = LoginFragmentDirections.actionLoginFragmentToLocationsFragment(users[0])
                (requireActivity() as AppCompatActivity).supportActionBar?.show()
                findNavController().navigate(action)
            }
        })

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.register_btn.setOnClickListener {
            (requireActivity() as AppCompatActivity).supportActionBar?.show()
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        view.login_btn.setOnClickListener {
            logIn()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

    }

    private fun logIn() {
        val username = login_username.text.toString()
        val password = passw_username.text.toString()

        if(validateInput(username,password)){
            try {
                instanceUserViewModel.getUserByUsername(username).observe(viewLifecycleOwner, Observer { users->
                    if(users.isNotEmpty() && validatePassword(password,users[0].password)){
                        instanceUserViewModel.updateUserLoggedStatus(users[0].id, true)
                        val action = LoginFragmentDirections.actionLoginFragmentToLocationsFragment(users[0])
                        (requireActivity() as AppCompatActivity).supportActionBar?.show()
                        findNavController().navigate(action)
                    }else{
                        Toast.makeText(requireContext(), "Usuario ou Senha incorretos!", Toast.LENGTH_LONG).show()
                    }
                })
            }catch (e: SQLiteException){
                e.printStackTrace()
            }

        }else{
            Toast.makeText(requireContext(), "Preencha todos os campos corretamente!", Toast.LENGTH_LONG).show()
        }

    }

    private fun validateInput(username: String, password: String): Boolean{
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            return true
        }
        return false
    }

    private fun validatePassword(password: String, hashPassword: String): Boolean{
        val decryptPassword = decrypt(hashPassword)
        if(password == decryptPassword){
            return true
        }
        return false
    }

    private fun decrypt(strToDecrypt : String) : String {

        val ivParameterSpec =  IvParameterSpec(Base64.decode(Constants.IV, Base64.DEFAULT))

        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val spec =  PBEKeySpec(Constants.SECRET_KEY.toCharArray(), Base64.decode(Constants.SALT, Base64.DEFAULT), 10000, 256)
        val tmp = factory.generateSecret(spec);
        val secretKey =  SecretKeySpec(tmp.encoded, "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        return  String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT)))

    }

}