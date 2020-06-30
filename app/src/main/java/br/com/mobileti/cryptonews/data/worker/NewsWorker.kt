package br.com.mobileti.cryptonews.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.mobileti.cryptonews.feature.news.repository.NewsRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class NewsWorker(

    context: Context,
    workerParams: WorkerParameters
) :
    CoroutineWorker(context, workerParams), KoinComponent {

    val newsRepository : NewsRepository by inject()

    override suspend fun doWork(): Result {
        Log.d("TAGG", "worker!!")
        return Result.success()
    }
}