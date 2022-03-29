package com.example.crimereporting.ui.authentication

import com.example.crimereporting.repository.UserRepository
import org.junit.Assert.*

import org.junit.Before

class AuthenticationViewModelTest {

    lateinit var viewModel: AuthenticationViewModel
    @Before
    fun setUp() {
        viewModel = AuthenticationViewModel()
//        viewModel.userRepository = UserRepository()
    }
}