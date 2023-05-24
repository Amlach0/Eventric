package com.eventric.ui.auth.signin

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
class SigninViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var signinCodeResult = MutableStateFlow<Operation?>(null)

    suspend fun signin(name:String, surname:String, email: String, password: String, confirmPassword: String, birthDate: String) {
        signinCodeResult.value = LoadingOperation

        var user: User = User(email, name, surname, null, birthDate)

        signinCodeResult.value = tryOperation {
            if(password.isNotEmpty() && password.equals(confirmPassword)){
                userRepository.createAccount(user, password)
            }
        }
    }
}
