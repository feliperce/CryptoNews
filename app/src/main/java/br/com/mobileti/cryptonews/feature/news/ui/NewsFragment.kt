package br.com.mobileti.cryptonews.feature.news.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import br.com.mobileti.cryptonews.R
import br.com.mobileti.cryptonews.data.worker.NewsWorker
import br.com.mobileti.cryptonews.databinding.MainFragmentBinding
import br.com.mobileti.cryptonews.feature.news.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    private val viewModel: NewsViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil.inflate<MainFragmentBinding>(
            inflater, R.layout.main_fragment, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with (viewModel) {
            newsLiveData.observe(viewLifecycleOwner, Observer {
                val adapter = NewsAdapter(it)
                newsRecyclerView.adapter = adapter
                newsRecyclerView.layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            })
        }

    }
}
