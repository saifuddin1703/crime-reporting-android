package com.example.crimereporting.ui.home

import androidx.lifecycle.ViewModel
import com.example.crimereporting.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    @Inject lateinit var userRepository: UserRepository
}