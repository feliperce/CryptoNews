package br.com.mobileti.cryptonews.home.feature.home.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.mobileti.cryptonews.home.feature.home.repository.HomeRepository
import kotlinx.coroutines.flow.collect
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NewsUpdateWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params), KoinComponent {

    private val homeRepository: HomeRepository by inject()

    override suspend fun doWork(): Result {
        homeRepository.refreshNews().collect()
        return Result.success()
    }

    companion object {
        const val TAG = "NewsUpdateWorker"
    }
}