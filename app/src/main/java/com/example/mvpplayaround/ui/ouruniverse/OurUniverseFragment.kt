package com.example.mvpplayaround.ui.ouruniverse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvpplayaround.R
import com.example.mvpplayaround.data.remote.models.AstronomyPicture
import com.example.mvpplayaround.databinding.FragmentOurUniverseBinding
import com.example.mvpplayaround.databinding.ReorderlistDialogBinding
import com.example.mvpplayaround.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OurUniverseFragment : Fragment(), OurUniverseContract.View, PodsRecyclerAdapter.PodsListener,
    FavouritePodsRecyclerAdapter.PodsListener {

    private var _binding: FragmentOurUniverseBinding? = null

    // recycler views
    private var podsRecycleAdapter: PodsRecyclerAdapter? = null
    private var favouritePodsRecyclerAdapter: FavouritePodsRecyclerAdapter? = null

    private lateinit var bindingReorderDialog: ReorderlistDialogBinding
    private lateinit var reorderAlertDialog: AlertDialog

    @Inject
    lateinit var ourUniversePresenter: OurUniversePresenter

    private var hasFetchLatest = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOurUniverseBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ourUniversePresenter.onViewCreated(this)

        setupReorderListModal()
        setupRecyclerViews()
        retainFilterValues()
        initializeLatestPodsAdapter()


        if (!hasFetchLatest) {
            ourUniversePresenter.fetchLatestPods()
        } else {
            ourUniversePresenter.pinFavorite()
        }

        binding.content.isNestedScrollingEnabled = true

        bindingReorderDialog.reset.setOnClickListener {
            setTitleChecked()
            applyFilter()
            reorderAlertDialog.dismiss()
        }

        bindingReorderDialog.applyBtn.setOnClickListener {
            applyFilter()
            reorderAlertDialog.dismiss()
        }

        bindingReorderDialog.title.setOnClickListener {
            setTitleChecked()
        }

        bindingReorderDialog.date.setOnClickListener {
            setDateChecked()
        }

        binding.reorderList.setOnClickListener {
            reorderAlertDialog.show()
        }

    }

    private fun retainFilterValues(){
        when(ourUniversePresenter.getFilterBy()){
            Constants.PodsFilter.TITLE -> {
                setTitleChecked()
            }

            Constants.PodsFilter.DATE -> {
                setDateChecked()
            }

        }
    }

    private fun applyFilter() {
        var filterBy: Constants.PodsFilter = Constants.PodsFilter.TITLE
        if (bindingReorderDialog.title.isChecked) {
            filterBy = Constants.PodsFilter.TITLE
        }

        if (bindingReorderDialog.date.isChecked) {
            filterBy = Constants.PodsFilter.DATE
        }

        ourUniversePresenter.applyFilter(filterBy)
    }


    private fun setTitleChecked() {
        bindingReorderDialog.title.isChecked = true
        bindingReorderDialog.date.isChecked = false
    }

    private fun setDateChecked() {
        bindingReorderDialog.title.isChecked = false
        bindingReorderDialog.date.isChecked = true
    }

    private fun setupRecyclerViews() {
        val layoutManager = LinearLayoutManager(context)
        binding.latestRecyclerView.layoutManager = layoutManager

        val layoutManager1 = LinearLayoutManager(context)
        binding.favouriteRecyclerView.layoutManager = layoutManager1

    }

    private fun initializeLatestPodsAdapter() {
        podsRecycleAdapter = context?.let { PodsRecyclerAdapter(it, ArrayList(), this@OurUniverseFragment) }
        binding.latestRecyclerView.adapter = podsRecycleAdapter

        favouritePodsRecyclerAdapter =
            context?.let { FavouritePodsRecyclerAdapter(it, ArrayList(), this@OurUniverseFragment) }
        binding.favouriteRecyclerView.adapter = favouritePodsRecyclerAdapter
    }

    private fun setupReorderListModal() {
        bindingReorderDialog = ReorderlistDialogBinding.inflate(layoutInflater)
        //initialize builder
        val alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alert.setView(bindingReorderDialog.root)
        reorderAlertDialog = alert.create()
    }

    private fun hideViewsOnLoad(){
        binding.content.visibility = View.GONE
        binding.favoritesLayout.visibility = View.GONE
        binding.reorderList.visibility = View.GONE
    }

    private fun showViewsAfterSuccessfulLoad(favouritePods: List<AstronomyPicture>) {
        binding.loadingBar.visibility = View.GONE
        binding.content.visibility = View.VISIBLE
        binding.favoritesLayout.visibility = if (favouritePods.isNotEmpty()) View.VISIBLE else View.GONE
        binding.reorderList.visibility = View.VISIBLE
    }

    override fun loading(isLoading: Boolean) {
        if (isLoading){
            binding.loadingBar.visibility = View.VISIBLE
            hideViewsOnLoad()
        } else {
            binding.loadingBar.visibility = View.GONE
        }
    }

    override fun getLatestPodsAndFavouritePods(
        latestPods: List<AstronomyPicture>,
        favouritePods: List<AstronomyPicture>
    ) {
        hasFetchLatest = true
        showViewsAfterSuccessfulLoad(favouritePods)
        podsRecycleAdapter?.updateItems(latestPods)
        favouritePodsRecyclerAdapter?.updateItems(favouritePods)
    }


    override fun onErrorOccurred() {
        binding.loadingBar.visibility = View.GONE
        hideViewsOnLoad()

        findNavController().navigate(R.id.action_FirstFragment_to_errorDialog)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        ourUniversePresenter.onDestroy()

    }

    private fun goToPodDetails(isFavourite: Boolean, pod: AstronomyPicture) {
        val action = OurUniverseFragmentDirections
            .actionFirstFragmentToPodDetailsFragment(isFavourite, pod)
        findNavController().navigate(action)
    }

    override fun onPodsClicked(pod: AstronomyPicture) {
        goToPodDetails(false, pod)
    }

    override fun onFavoritePodClicked(pod: AstronomyPicture) {
        goToPodDetails(true, pod)
    }

}