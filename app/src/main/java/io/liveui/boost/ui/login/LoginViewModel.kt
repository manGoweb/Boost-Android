package io.liveui.boost.ui.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.liveui.boost.api.model.AuthRequest
import io.liveui.boost.api.model.AuthResponse
import io.liveui.boost.api.usecase.BoostAuthUseCase
import io.liveui.boost.common.model.Workspace
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginViewModel constructor(private val authUseCase: BoostAuthUseCase, private val workspace: Workspace) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val auth: MutableLiveData<AuthResponse> = MutableLiveData()

    val exception: MutableLiveData<Throwable> = MutableLiveData()

    override fun onCleared() {
        disposables.clear()
    }

    fun auth(username: String, password: String) {
        disposables.add(authUseCase.auth(AuthRequest(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    loadingStatus.value = true
                })
                .subscribe({ result ->
                    loadingStatus.value = false
                    auth.value = result
                    auth.let {
                        workspace.permToken = it.value?.token
                        workspace.save()
                    }
                }, { e ->
                    loadingStatus.value = false
                    exception.value = e
                })
        )
    }
}