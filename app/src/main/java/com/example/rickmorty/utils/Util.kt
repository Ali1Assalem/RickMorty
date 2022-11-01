package com.example.rickmorty.utils

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.airbnb.lottie.LottieAnimationView
import com.example.rickmorty.adapter.EpisodesPagingAdapter
import com.example.rickmorty.databinding.FragmentEpisodesBinding
import com.todkars.shimmer.ShimmerRecyclerView

class Util {

    companion object {
        fun loadingState(
            loadState: CombinedLoadStates,
            lottieAnimationView: LottieAnimationView,
            shimmerLoading: ShimmerRecyclerView,
            refreshBtn: Button,
            isMediator:Boolean=false,
            adapter: EpisodesPagingAdapter,
        ) {
            val refreshState =
                if (isMediator){
                    loadState.mediator?.refresh
                }else{
                    loadState.source.refresh
                }

            if (refreshState is LoadState.NotLoading){
                shimmerLoading.visibility = View.GONE
                lottieAnimationView.visibility = View.GONE
                refreshBtn.visibility = View.GONE
            }
            if(refreshState is LoadState.Loading){
                shimmerLoading.visibility = View.VISIBLE
                shimmerLoading.showShimmer()
                lottieAnimationView.visibility = View.GONE
                refreshBtn.visibility = View.GONE
            }
            if(refreshState is LoadState.Error){
                shimmerLoading.visibility = View.GONE
                lottieAnimationView.setAnimation("no-internet.json")
                lottieAnimationView.playAnimation()
                lottieAnimationView.visibility = View.VISIBLE
                refreshBtn.visibility = View.VISIBLE
            }
            if (adapter.itemCount != 0){
                shimmerLoading.visibility = View.GONE
                lottieAnimationView.visibility = View.GONE
                refreshBtn.visibility = View.GONE
            }
        }
    }
}