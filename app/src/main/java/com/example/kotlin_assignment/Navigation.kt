package com.example.kotlin_assignment

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_assignment.HomeScreen
import com.example.kotlin_assignment.LoginScreen
import com.example.kotlin_assignment.Routes
import com.example.kotlin_assignment.SignUpScreen
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val loginFunction: Login_Function = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {

        composable(Routes.LOGIN) {
            LoginScreen(
                loginFunction = loginFunction,
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = {
                    navController.navigate(Routes.SIGN_UP)
                }
            )
        }

        composable(Routes.SIGN_UP) {
            SignUpScreen(
                onSignUp = { username, email, password ->

                    // 之后连接 Firebase 时，
                    // 可以在这里创建用户账号。

                    navController.navigate(Routes.LOGIN)
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.HOME) {
            //HomeScreen()
            DashboardScreen()
        }
    }
}