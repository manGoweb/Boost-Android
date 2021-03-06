package io.liveui.boost.ui.overview

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.overview.grid.OverviewGridAdapter
import io.liveui.boost.ui.overview.list.OverviewListAdapter
import io.liveui.boost.ui.teams.TeamsViewModel
import io.liveui.boost.ui.view.adapter.SpaceItemDecoration
import io.liveui.boost.util.ProgressViewObserver
import io.liveui.boost.util.ext.replaceItemDecoration
import kotlinx.android.synthetic.main.fragment_overview.*
import javax.inject.Inject

class OverviewFragment : BoostFragment() {

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    lateinit var teamsViewModel: TeamsViewModel

    lateinit var overviewViewModel: OverviewViewModel

    @Inject
    lateinit var overviewGridAdapter: OverviewGridAdapter

    @Inject
    lateinit var overviewListAdapter: OverviewListAdapter

    private val gridDecoration by lazy {
        SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.overview_column_space))
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity?.let {
            teamsViewModel = ViewModelProviders.of(it, apiViewModelFactory).get(TeamsViewModel::class.java)
            overviewViewModel = ViewModelProviders.of(it, apiViewModelFactory).get(OverviewViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamsViewModel.loadingStatus.observe(this, ProgressViewObserver(progress_bar))
        teamsViewModel.loadingStatus.observe(this, ProgressViewObserver(recycler_view, false))

        overviewViewModel.overviewData.observe(this, overviewGridAdapter)
        overviewViewModel.overviewData.observe(this, overviewListAdapter)

        recycler_view.adapter = overviewGridAdapter
        recycler_view.layoutManager = GridLayoutManager(context, 2)
        recycler_view.addItemDecoration(gridDecoration)

        btn_show_grid.setOnClickListener {
            recycler_view.replaceItemDecoration(gridDecoration)
            recycler_view.adapter = overviewGridAdapter
            overviewViewModel.showGrid()
        }
        btn_show_list.setOnClickListener {
            recycler_view.removeItemDecoration(gridDecoration)
            recycler_view.adapter = overviewListAdapter
            overviewViewModel.showList()
        }

        teamsViewModel.activeTeam.observe(this, Observer {
            if (it != null && !it.id.isEmpty()) {
                overviewViewModel.activeTeam.postValue(it)
            } else {
                overviewViewModel.activeTeam.postValue(null)
            }
        })

        overviewViewModel.layoutManager.observe(this, Observer {
            recycler_view.layoutManager = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}