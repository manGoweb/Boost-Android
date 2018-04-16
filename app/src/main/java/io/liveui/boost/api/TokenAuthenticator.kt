package io.liveui.boost.api

import io.liveui.boost.api.model.RefreshTokenRequest
import io.liveui.boost.api.service.BoostAuthService
import io.liveui.boost.db.Workspace
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber

class TokenAuthenticator constructor(private val authService: BoostAuthService,
                                     private val workspace: Workspace): Authenticator {

    override fun authenticate(route: Route?, response: Response?): Request? {
        Timber.e("Auth start")
        authService.refreshToken(RefreshTokenRequest(workspace.permToken)).blockingSubscribe()
        Timber.e("Auth end")
        return response?.request()
    }
}