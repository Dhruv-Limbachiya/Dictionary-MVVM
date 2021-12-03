package com.example.dictionary.ui.adapter

import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import com.example.dictionary.data.local.entities.WordItemEntity
import com.example.dictionary.databinding.LayoutWordItemBinding
import com.example.dictionary.ui.adapter.WordListAdapter.WordListViewHolder

/**
 * Created By Dhruv Limbachiya on 01-12-2021 06:42 PM.
 */
class WordListAdapter :
    ListAdapter<WordItemEntity, WordListViewHolder>(WordItemDiffUtilCallback()) {

    var partOfSpeechIds = mutableListOf<String>()
    var definitionIds = mutableListOf<String>()
    var exampleIds = mutableListOf<String>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListViewHolder {

        val binding = LayoutWordItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return WordListViewHolder(binding, partOfSpeechIds, definitionIds, exampleIds)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: WordListViewHolder, position: Int) {
        val currentWord = currentList[position]
        holder.bind(currentWord)

        partOfSpeechIds.forEachIndexed { index, text ->
            if (holder.partOfSpeechTextViews.isNotEmpty()) {
                holder.partOfSpeechTextViews[index].text = text
            }
        }

        definitionIds.forEachIndexed { index, text ->
            if (holder.definitionTextViews.isNotEmpty()) {
                holder.definitionTextViews[index].text = "$index $text"
            }
        }

        exampleIds.forEachIndexed { index, text ->
            if (holder.exampleTextViews.isNotEmpty()) {
                holder.exampleTextViews[index].text = "Example $text"
            }
        }
    }

    class WordListViewHolder(
        private val binding: LayoutWordItemBinding,
        private val partOfSpeechIds: MutableList<String>,
        private val definitionIds: MutableList<String>,
        private val exampleIds: MutableList<String>
    ) :
        RecyclerView.ViewHolder(binding.root) {

        val partOfSpeechTextViews = mutableListOf<TextView>()
        val definitionTextViews = mutableListOf<TextView>()
        val exampleTextViews = mutableListOf<TextView>()

        init {
            partOfSpeechIds.forEachIndexed { index, partOfSpeech ->
                val textView: TextView? = binding.dynamicLayout.findViewById(index + 1)
                textView?.let {
                    partOfSpeechTextViews.add(textView)
                }
            }
            definitionIds.forEachIndexed { index, definition ->
                val textView: TextView? = binding.dynamicLayout.findViewById(index + 1)
                textView?.let {
                    definitionTextViews.add(textView)
                }

            }
            exampleIds.forEachIndexed { index, example ->
                val textView: TextView? = binding.dynamicLayout.findViewById(index + 1)
                textView?.let {
                    exampleTextViews.add(textView)
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(word: WordItemEntity) {
            binding.word = word

            binding.dynamicLayout.removeAllViews()

            // For each word
            word.meanings.forEachIndexed { i, meaning ->
                // For each meaning
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0,10,0,0)
                }
                val partOfSpeechTextView = TextView(binding.dynamicLayout.context).apply {
                    setLayoutParams(layoutParams)
                    setTextColor(context.getColorStateList(R.color.black))
                    tag = "partOfSpeech$i"
                    id = i + 1
                    text = meaning.partOfSpeech
                    partOfSpeechIds.add("partOfSpeech$i")
                    typeface = Typeface.DEFAULT_BOLD
                    setTextColor(context.getColorStateList(R.color.black))
                    textSize = 18f
                }

                binding.dynamicLayout.addView(partOfSpeechTextView)

                meaning.definitions.forEachIndexed { index, definition ->
                    // For each definition

                    val definitionLayoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0,10,0,0)
                    }

                    if (definition.definition?.isNotEmpty() == true) {
                        val definitionTextView = TextView(binding.dynamicLayout.context).apply {
                            setLayoutParams(definitionLayoutParams)
                            text = definition.definition
                            tag = "definitionTextView$index"
                            text = "${index + 1}. ${definition.definition}"
                            id = index + 1
                            textSize = 16f
                            setTextColor(context.getColorStateList(R.color.black))
                            definitionIds.add("definitionTextView$index")
                        }

                        binding.dynamicLayout.addView(definitionTextView)
                    }

                    val exampleLayoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0,4,0,6)
                    }
                    if (definition.example?.isNotEmpty() == true) {
                        val exampleTextView = TextView(binding.dynamicLayout.context).apply {
                            setLayoutParams(exampleLayoutParams)
                            text = definition.example
                            tag = "exampleTextView$index"
                            id = index + 1
                            textSize = 16f
                            text = "Example: ${definition.example}"
                            exampleIds.add("exampleTextView$index")
                        }
                        binding.dynamicLayout.addView(exampleTextView)
                    }
                }
            }
        }
    }
}

class WordItemDiffUtilCallback : DiffUtil.ItemCallback<WordItemEntity>() {
    override fun areItemsTheSame(oldItem: WordItemEntity, newItem: WordItemEntity) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: WordItemEntity, newItem: WordItemEntity) =
        oldItem.id == newItem.id
}
