package com.eventric.ui.auth.signup

import androidx.lifecycle.ViewModel
import com.eventric.repo.UserRepository
import com.eventric.utils.ErrorOperation
import com.eventric.utils.LoadingOperation
import com.eventric.utils.Operation
import com.eventric.utils.tryOperation
import com.eventric.vo.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    var signupCodeResult = MutableStateFlow<Operation?>(null)

    val userIdFlow = MutableStateFlow("")
    val userFlow = userIdFlow.flatMapLatest { id ->
        if (id != "")
            userRepository.getUser(id).map { it.second }
        else
            flowOf()
    }

    fun setUserId(id: String) {
        userIdFlow.value = id
    }

    suspend fun signupOrEdit(
        name: String,
        surname: String,
        email: String,
        password: String,
        confirmPassword: String,
        birthDate: String,
        bio: String,
        isEdit: Boolean,
    ) {
        signupCodeResult.value = LoadingOperation

        val user = (if (isEdit) userFlow.first() else User()).copy(
            email = email,
            name = name,
            surname = surname,
            image = null,
            bio = bio,
            birthDate = birthDate,
        )

        if ((password.isNotEmpty() && password == confirmPassword) || isEdit)
            signupCodeResult.value = tryOperation {
                if (isEdit)
                    userRepository.editUser(userIdFlow.value, user, password)
                else
                    userRepository.createAccount(user, password)
            }
        else
            signupCodeResult.value = ErrorOperation(Throwable("password non predente o non corrisponde"))
    }

    suspend fun deleteUser() = userRepository.deleteUser(userIdFlow.value)

}
