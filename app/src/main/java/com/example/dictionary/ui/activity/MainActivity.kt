package com.example.dictionary.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.dictionary.R
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.ui.adapter.WordListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mViewModel: MainViewModel

    private lateinit var mAdapter: WordListAdapter

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        mAdapter = WordListAdapter()

        collectFromFlow()
    }

    private fun collectFromFlow() {
        lifecycleScope.launchWhenStarted {
            mViewModel.wordInfoState.collect {
                if(it.wordsInfo?.isNotEmpty() == true){
                    mAdapter.submitList(it.wordsInfo)
                    mBinding.rvWords.adapter = mAdapter
                }
            }

            mViewModel.uiEvents.collectLatest {

            }
        }
    }
}