package br.com.mobileti.cryptonews.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import br.com.mobileti.cryptonews.BuildConfig
import br.com.mobileti.cryptonews.feature.news.repository.NewsRepository
import kotlinx.coroutines.flow.collect
import org.koin.core.KoinComponent
import org.koin.core.inject

class NewsWorker(
    context: Context,
    workerParams: WorkerParameters
) :
    CoroutineWorker(context, workerParams), KoinComponent {

    companion object {
        const val TAG_WORKER = "newsWorkerTag"
    }

    private val newsRepository : NewsRepository by inject()

    override suspend fun doWork(): Result {
        newsRepository.syncNews(BuildConfig.API_KEY).collect()
        return Result.success()
    }
}