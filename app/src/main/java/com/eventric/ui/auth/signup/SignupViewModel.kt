package com.eventric.ui.auth.signup

import androidx.lifecycle.ViewModel
import com.eventric.utils.LoadingOperation
import com.eventric.utils.Operation
import com.eventric.repo.UserRepository
import com.eventric.utils.tryOperation
import com.eventric.vo.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var signupCodeResult = MutableStateFlow<Operation?>(null)

    suspend fun signup(name:String, surname:String, email: String, password: String, confirmPassword: String, birthDate: String) {
        signupCodeResult.value = LoadingOperation

        val user = User(email, name, surname, null, birthDate)

        signupCodeResult.value = tryOperation {
            if(password.isNotEmpty() && password == confirmPassword){
                userRepository.createAccount(user, password)
            }
        }
    }
}
