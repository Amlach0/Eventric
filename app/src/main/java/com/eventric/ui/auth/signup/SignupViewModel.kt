package com.eventric.ui.auth.signup

import androidx.lifecycle.ViewModel
import com.eventric.repo.UserRepository
import com.eventric.utils.LoadingOperation
import com.eventric.utils.Operation
import com.eventric.utils.tryOperation
import com.eventric.vo.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var signupCodeResult = MutableStateFlow<Operation?>(null)

    val userIdFlow = MutableStateFlow("")
    val userFlow = userIdFlow.flatMapLatest { id ->
        if (id != "")
            userRepository.getUser(id).map { it.second }
        else
            flowOf()
    }

    fun setEventId(id: String) {
        userIdFlow.value = id
    }
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
