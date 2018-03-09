package cz.mangoweb.appstore.ui.apps

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import cz.mangoweb.appstore.R
import cz.mangoweb.appstore.api.ApiViewModeFactory
import cz.mangoweb.appstore.ui.BoostActivity
import javax.inject.Inject

/**
 * Created by Vojtech Hrdina on 05/03/2018.
 */
class AppStoreActivity: BoostActivity() {

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    lateinit var appsViewModel: AppsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        appsViewModel = ViewModelProviders.of(this, apiViewModelFactory).get(AppsViewModel::class.java)
    }
}