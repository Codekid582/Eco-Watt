package com.example.kotlin_assignment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.kotlin_assignment.UserData.User_Data

class Login_Function : ViewModel(){
    private val userList = listOf(
        User_Data(
            UserID = "U001",
            UserName = "Lee",
            Email = "lee@gmail.com",
            Password = "123456"
        ),
        User_Data(
            UserID = "U002",
            UserName = "Ali",
            Email = "ali@gmail.com",
            Password = "abcdef"
        )
    )

    var currentUser by mutableStateOf<User_Data?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun signIn(email: String,password: String): Boolean {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Please enter email and password"
            return false
        }

        val foundUser = userList.find {
            user -> user.Email.equals(other = email.trim(),ignoreCase = true) && user.Password == password
        }

        return if (foundUser != null) {
            currentUser = foundUser
            errorMessage = null
            true
        }
        else {
            currentUser = null
            errorMessage = "Invalid email or password"
            false
        }
    }

    fun clearError() {
        errorMessage = null
    }

    fun signOut() {
        currentUser = null
        errorMessage = null
    }

}