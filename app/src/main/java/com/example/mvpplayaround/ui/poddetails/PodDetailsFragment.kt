package com.example.mvpplayaround.ui.poddetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mvpplayaround.R
import com.example.mvpplayaround.databinding.FragmentPodDetailsBinding
import com.example.mvpplayaround.util.DateConverter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PodDetailsFragment : Fragment() {
    private var _binding: FragmentPodDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args by navArgs<PodDetailsFragmentArgs>()

    @Inject
    lateinit var podDetailsPresenter: PodDetailsPresenter

    private var isPodFavourite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPodDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isPodFavourite = args.favourite

        bindDataToView()

        binding.backViewLayout.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.favourite.setOnClickListener { onFavouriteClicked() }
    }

    private fun onFavouriteClicked(){
        if (isPodFavourite) {
            podDetailsPresenter.removeFavorite(args.pod)
        } else {
            podDetailsPresenter.addFavorite(args.pod)
        }

        isPodFavourite = !isPodFavourite
        isPodFavourite.let {
            binding.favourite
                .setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(), if (it) R.drawable.ic_favorite_filled
                        else R.drawable.ic_favorite_border
                    )
                )

        }
    }

    private fun bindDataToView() {
        binding.podTitle.text = args.pod.title
        binding.podDate.text = DateConverter.formatDateToDdMmYyyy(args.pod.date)
        binding.podExplanation.text = args.pod.explanation
        args.pod.url.let { url ->
            Glide.with(this@PodDetailsFragment)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.podImage)
        }

        binding.favourite
            .setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(), if (args.favourite) R.drawable.ic_favorite_filled
                    else R.drawable.ic_favorite_border
                )
            )

        binding.favourite.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}