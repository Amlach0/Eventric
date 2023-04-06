package com.eventric.ui.auth.login

import androidx.lifecycle.ViewModel
import com.eventric.repo.LoadingOperation
import com.eventric.repo.Operation
import com.eventric.repo.UserRepository
import com.eventric.repo.tryOperation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var loginCodeResult = MutableStateFlow<Operation?>(null)

    suspend fun login(email: String, password: String) {
        loginCodeResult.value = LoadingOperation

        loginCodeResult.value = tryOperation {
            userRepository.login(email.trim(), password)
        }
    }
}
