package io.liveui.boost.ui.workspace

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import io.liveui.boost.BuildConfig
import io.liveui.boost.R
import io.liveui.boost.api.CheckViewModelFactory
import io.liveui.boost.common.model.Workspace
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.login.LoginActivity
import io.liveui.boost.util.ProgressViewObserver
import io.liveui.boost.util.ext.*
import kotlinx.android.synthetic.main.fragment_workspace.*
import javax.inject.Inject

class WorkspaceFragment : BoostFragment() {

    @Inject
    lateinit var checkViewModelFactory: CheckViewModelFactory

    lateinit var workspaceViewModel: WorkspaceViewModel

    @Inject
    lateinit var workspace: Workspace

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workspace, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workspaceViewModel = ViewModelProviders.of(this, checkViewModelFactory).get(WorkspaceViewModel::class.java)
        workspaceViewModel.serverExists.observe(this, Observer {
            if (it!!) {
                workspace.save()
                startActivity(Intent(context, LoginActivity::class.java))
            } else {
                workspace.clear()
                view.showSnackBar("Server doesn't exists", Snackbar.LENGTH_SHORT)
            }
        })

        workspaceViewModel.loadingStatus.observe(this, ProgressViewObserver(progress_bar))
        workspaceViewModel.loadingStatus.observe(this, ProgressViewObserver(til_workspace_name, false))
        workspaceViewModel.loadingStatus.observe(this, ProgressViewObserver(til_workspace_url, false))
        workspaceViewModel.loadingStatus.observe(this, ProgressViewObserver(btn_continue, false))

        workspace_url.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, BuildConfig.URL))

        btn_continue.setOnClickListener({
            workspace.name = workspace_name.getString()
            workspace.url = workspace_url.getString()
            workspaceViewModel.checkServer()
        })

    }
}