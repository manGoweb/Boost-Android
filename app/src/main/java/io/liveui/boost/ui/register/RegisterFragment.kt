package io.liveui.boost.ui.register

import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.EXTRA_WORKSPACE
import io.liveui.boost.R
import io.liveui.boost.api.model.User
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.db.Workspace
import io.liveui.boost.ui.BoostFragment
import kotlinx.android.synthetic.main.fragment_register.*
import javax.inject.Inject

class RegisterFragment: BoostFragment() {

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    lateinit var registerModel: RegisterViewModel

    var workspace: Workspace? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        workspace = arguments?.getParcelable(EXTRA_WORKSPACE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerModel = ViewModelProviders.of(this, apiViewModelFactory).get(RegisterViewModel::class.java)
        registerModel.workspace = workspace

        registerModel.register(first_name.text.toString(),
                last_name.text.toString(),
                user_name.text.toString(),
                email.text.toString(),
                password.text.toString())

        registerModel.register.observe(this, Observer<User> {

        })
    }
}