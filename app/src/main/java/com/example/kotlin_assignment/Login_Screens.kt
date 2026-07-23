package com.example.kotlin_assignment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.util.Patterns


// UI Colours
private val EcoGreen = Color(0xFF3D9462)
private val InputTextColour = Color(0xFF555555)
private val ErrorColour = Color(0xFFFFD6D6)


// =========================
// Navigation routes
// =========================

object Routes {
    const val LOGIN = "login"
    const val SIGN_UP = "sign_up"
    const val HOME = "home"
}

// =========================
// Login Screen
// =========================

@Composable
fun LoginScreen(
    loginFunction: Login_Function,
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit
) {

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var keepLoggedIn by rememberSaveable {
        mutableStateOf(false)
    }

    var errorMessage by rememberSaveable {
        mutableStateOf("")
    }

    val displayedErrorMessage =
        if (errorMessage.isNotEmpty()) {
            errorMessage
        } else {
            loginFunction.errorMessage ?: ""
        }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoGreen)
            .imePadding()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 28.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            EcoWattLogo()

            Spacer(
                modifier = Modifier.height(55.dp)
            )

            AuthInputField(
                title = "Email Address",
                value = email,
                onValueChange = {
                    email = it
                    errorMessage = ""
                    loginFunction.clearError()
                },
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            AuthInputField(
                title = "Password",
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = ""
                    loginFunction.clearError()
                },
                isPassword = true,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = keepLoggedIn,
                    onCheckedChange = {
                        keepLoggedIn = it
                    },
                    modifier = Modifier
                        .size(28.dp)
                        .scale(0.75f),

                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.White,
                        uncheckedColor = Color.White,
                        checkmarkColor = EcoGreen
                    )
                )

                Text(
                    text = "Keep me logged in",
                    color = Color.White,
                    fontSize = 10.sp
                )
            }

            if (displayedErrorMessage.isNotEmpty()) {

                Text(
                    text = displayedErrorMessage,
                    modifier = Modifier.fillMaxWidth(),
                    color = ErrorColour,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            } else {

                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }

            AuthButton(
                text = "LOGIN",
                onClick = {

                    when {
                        email.isBlank() -> {
                            errorMessage = "Please enter your email address."
                        }

                        password.isBlank() -> {
                            errorMessage = "Please enter your password."
                        }

                        else -> {
                            val loginSuccessful = loginFunction.signIn(
                                email = email,
                                password = password
                            )

                            if (loginSuccessful) {
                                onLoginSuccess()
                            }
                        }
                    }
                }
            )

            Spacer(
                modifier = Modifier.height(35.dp)
            )

            Text(
                text = "Don't have an account?",
                color = Color.White,
                fontSize = 11.sp
            )

            TextButton(
                onClick = onSignUpClick,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFF43D5D2)
                ),
                contentPadding = PaddingValues(0.dp)
            ) {

                Text(
                    text = "Sign up here",
                    fontSize = 11.sp
                )
            }
        }
    }
}


// =========================
// Sign Up Screen
// =========================

@Composable
fun SignUpScreen(
    onSignUp: (
        username: String,
        email: String,
        password: String
    ) -> Unit,

    onLoginClick: () -> Unit
) {

    var username by rememberSaveable {
        mutableStateOf("")
    }

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var confirmPassword by rememberSaveable {
        mutableStateOf("")
    }

    var errorMessage by rememberSaveable {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoGreen)
            .imePadding()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 28.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            EcoWattLogo()

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            AuthInputField(
                title = "Username",
                value = username,
                onValueChange = {
                    username = it
                    errorMessage = ""
                },
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            AuthInputField(
                title = "Email Address",
                value = email,
                onValueChange = {
                    email = it
                    errorMessage = ""
                },
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            AuthInputField(
                title = "Password",
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = ""
                },
                isPassword = true,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            AuthInputField(
                title = "Confirm Password",
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    errorMessage = ""
                },
                isPassword = true,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            if (errorMessage.isNotEmpty()) {

                Text(
                    text = errorMessage,
                    modifier = Modifier.fillMaxWidth(),
                    color = ErrorColour,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )
            }

            AuthButton(
                text = "SIGN UP",
                onClick = {

                    when {
                        username.isBlank() -> {
                            errorMessage = "Please enter a username."
                        }

                        email.isBlank() -> {
                            errorMessage = "Please enter your email address."
                        }

                        !isValidEmail(email) -> {
                            errorMessage = "Please enter a valid email address."
                        }

                        password.isBlank() -> {
                            errorMessage = "Please enter a password."
                        }

                        password.length < 6 -> {
                            errorMessage =
                                "Password must contain at least 6 characters."
                        }

                        confirmPassword.isBlank() -> {
                            errorMessage = "Please confirm your password."
                        }

                        password != confirmPassword -> {
                            errorMessage = "Passwords do not match."
                        }

                        else -> {
                            onSignUp(
                                username.trim(),
                                email.trim(),
                                password
                            )
                        }
                    }
                }
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            TextButton(
                onClick = onLoginClick,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFF43D5D2)
                )
            ) {

                Text(
                    text = "Already have an account? Login here",
                    fontSize = 11.sp
                )
            }
        }
    }
}


// =========================
// Reusable input field
// =========================

@Composable
private fun AuthInputField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = title,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),

            singleLine = true,

            visualTransformation = if (isPassword) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },

            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),

            shape = RoundedCornerShape(6.dp),

            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,

                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,

                focusedTextColor = InputTextColour,
                unfocusedTextColor = InputTextColour,

                cursorColor = EcoGreen
            )
        )
    }
}


// =========================
// Reusable button
// =========================

@Composable
private fun AuthButton(
    text: String,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .width(145.dp)
            .height(42.dp),

        shape = RoundedCornerShape(6.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = EcoGreen
        ),

        contentPadding = PaddingValues(0.dp)
    ) {

        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}


// =========================
// Logo
// =========================

@Composable
private fun EcoWattLogo() {

    Text(
        text = "Eco💡\nWatt",
        color = Color.White,
        fontSize = 34.sp,
        lineHeight = 40.sp,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center
    )
}


// =========================
// Temporary Home Screen
// =========================

@Composable
fun HomeScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F4)),

        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "Welcome to Eco Watt",
            color = EcoGreen,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

private fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS
        .matcher(email.trim())
        .matches()
}