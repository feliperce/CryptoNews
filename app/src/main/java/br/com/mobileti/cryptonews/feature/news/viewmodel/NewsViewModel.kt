package br.com.mobileti.cryptonews.feature.news.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import br.com.mobileti.cryptonews.BuildConfig
import br.com.mobileti.cryptonews.data.worker.NewsWorker
import br.com.mobileti.cryptonews.feature.news.repository.NewsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val TAG = NewsViewModel::class.java.simpleName

    private val _dataLoadingLiveData = MutableLiveData<Boolean>()
    val dataLoadingLiveData: LiveData<Boolean> = _dataLoadingLiveData

    init {
        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresDeviceIdle(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<NewsWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .addTag(NewsWorker.TAG_WORKER)
            .build()


        //workManager.enqueue(workRequest)
        //getNews()
    }

    fun getNews() {
        viewModelScope.launch {
            /*newsRepository.getCachedNews(BuildConfig.API_KEY).collect {
                Log.d(TAG, it.status.toString())
            }*/

            newsRepository.syncNews(BuildConfig.API_KEY).collect {
                Log.d(TAG, it.status.toString())
            }
        }
    }

}
