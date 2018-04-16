package io.liveui.boost.common

import android.arch.lifecycle.MutableLiveData
import io.liveui.boost.db.Workspace
import io.liveui.boost.db.WorkspaceDao

class UserSession(val workspaceDao: WorkspaceDao) {

    var workspace: Workspace = Workspace(url = "")
    var workspaceChanged: MutableLiveData<Workspace> = MutableLiveData() //TODO reload screens on workspace change

    init {
        workspaceDao.getWorkspaces().observeForever({
            it?.find { it.active == 1 }?.let {
                workspace = it
                workspaceChanged.postValue(it)
            }
        })
    }

}