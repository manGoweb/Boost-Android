package io.liveui.boost.common

import android.arch.lifecycle.MutableLiveData
import io.liveui.boost.api.model.TeamUser

class UserSession {

    val user: MutableLiveData<TeamUser> = MutableLiveData()

}