package com.eventric.ui.auth.signup

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.eventric.repo.EventRepository
import com.eventric.repo.ImagesRepository
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
    private val eventRepository: EventRepository,
    private val imagesRepository: ImagesRepository,
) : ViewModel() {

    var signupCodeResult = MutableStateFlow<Operation?>(null)
    var deleteUserCodeResult = MutableStateFlow<Operation?>(null)

    val userIdFlow = MutableStateFlow("")
    val userFlow = userIdFlow.flatMapLatest { id ->
        if (id != "")
            userRepository.getUser(id).map { it.second }
        else
            flowOf(User())
    }

    val uriImageFlow = userIdFlow.flatMapLatest { userId ->
        imagesRepository.downloadUserImage(userId)
    }

    fun setUserId(id: String) {
        userIdFlow.value = id
    }

    suspend fun signupOrEdit(
        name: String,
        surname: String,
        uriImage: Uri,
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
            bio = bio,
            birthDate = birthDate,
        )

        if ((password.isNotEmpty() && password == confirmPassword) || isEdit)
            signupCodeResult.value = tryOperation {
                if (isEdit)
                    userRepository.editUser(userIdFlow.value, user, password)
                else
                    userRepository.createAccount(user, password)
                if (uriImage != Uri.EMPTY)
                    imagesRepository.uploadUserImage(uriImage, userIdFlow.value)
            }
        else
            signupCodeResult.value = ErrorOperation(Throwable("password non presente o non corrisponde"))
    }

    suspend fun deleteUser() {
        val userId = userIdFlow.value
        deleteUserCodeResult.value = tryOperation {
            if (userId != "") {
                eventRepository.removeUserSubscribeFromAll(userId)
                userRepository.removeUserFollowFromAll(userId)
                userRepository.deleteUser(userId)
                imagesRepository.deleteUserImage(userId)
            }
        }
    }

}
