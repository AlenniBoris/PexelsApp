package com.example.pexapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.pexapp.screens.main.MainScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object ExtraFunctions {
    fun hasInternetConnection(context: Context): Boolean{
        var res: Boolean = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run{
            res = when{
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                else -> false
            }
        }
        return res
    }

    fun changeSearch(scope: CoroutineScope, mainScreenViewModel: MainScreenViewModel, title: String, id: String){
        mainScreenViewModel.queryTextChanged(title)
        mainScreenViewModel.selectedFeaturedCollectionIdChanged(id)
        scope.launch {
            mainScreenViewModel.getQueryPhotos(title)
        }
    }
}