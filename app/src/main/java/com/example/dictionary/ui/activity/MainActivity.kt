package com.example.dictionary.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dictionary.R
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.ui.adapter.WordListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mViewModel: MainViewModel

    @Inject
    lateinit var mAdapter: WordListAdapter

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        collectFromFlow()

        mBinding.etSearch.doOnTextChanged { text, start, before, count ->
            mViewModel.searchWord(text.toString())
        }
    }

    private fun collectFromFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.wordInfoState.collect {
                    mAdapter.submitList(it.wordsInfo ?: emptyList())
                    mBinding.rvWords.adapter = mAdapter
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            mViewModel.uiEvents.collectLatest {
                when(it) {
                    is MainViewModel.UIEvent.SnackBarEvent -> {
                        Snackbar.make(
                            mBinding.root,
                            it.message,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}