package io.liveui.boost.download

import android.Manifest
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.IOUtil
import io.liveui.boost.util.IntentUtil
import io.liveui.boost.util.permission.PermissionHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BoostDownloadManager @Inject constructor(val systemDownloader: SystemDownloader,
                                               val downloads: DownloadsHolder,
                                               val ioUtil: IOUtil,
                                               val contextProvider: ContextProvider,
                                               val permissionHelper: PermissionHelper) {

    fun downloadApp(appId: String): DownloadItem? {
        return if (hasStoragePermission()) {
            val downloadItem = systemDownloader.createDownloadItem(appId)
            systemDownloader.downloadFile(appId)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.doOnNext {
                        Timber.d("File downloaded - %s", it.uri)
                        it.uri?.let {
                            IntentUtil.openApk(contextProvider.app, it)
                        }
                    }
                    ?.doOnError {
                        Timber.e(it, "Download failed")
                    }
                    ?.subscribe()
            downloadItem
        } else {
            null
        }
    }

    fun hasStoragePermission(): Boolean {
        return permissionHelper.checkPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1)
    }

}