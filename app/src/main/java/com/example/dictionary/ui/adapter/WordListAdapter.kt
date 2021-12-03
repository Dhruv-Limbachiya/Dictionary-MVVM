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
import com.example.dictionary.data.model.Definition
import com.example.dictionary.data.model.Meaning
import com.example.dictionary.databinding.LayoutWordItemBinding
import com.example.dictionary.ui.adapter.WordListAdapter.WordListViewHolder

/**
 * Created By Dhruv Limbachiya on 01-12-2021 06:42 PM.
 */
class WordListAdapter :
    ListAdapter<WordItemEntity, WordListViewHolder>(WordItemDiffUtilCallback()) {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListViewHolder {

        val binding = LayoutWordItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return WordListViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: WordListViewHolder, position: Int) {
        val currentWord = currentList[position]
        holder.bind(currentWord)
    }

    class WordListViewHolder(
        private val binding: LayoutWordItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {


        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(word: WordItemEntity) {
            binding.word = word

            binding.dynamicLayout.removeAllViews()
            // For each word
            word.meanings.forEachIndexed { i, meaning ->
                // For each meaning
                createPartOfSpeechTextView(meaning, i, binding)

                meaning.definitions.forEachIndexed { index, definition ->
                    // For each definition
                    if (definition.definition?.isNotEmpty() == true) {
                        createDefinitionTextView(definition, index, binding)
                    }

                    if (definition.example?.isNotEmpty() == true) {
                        createExampleTextView(definition, index, binding)
                    }
                }
            }
        }
    }
}


/**
 * Creates TextView for part of speech text.
 */
@RequiresApi(Build.VERSION_CODES.M)
private fun createPartOfSpeechTextView(
    meaning: Meaning,
    index: Int,
    binding: LayoutWordItemBinding,
) {
    val layoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(0, 10, 0, 0)
    }
    val partOfSpeechTextView = TextView(binding.dynamicLayout.context).apply {
        setLayoutParams(layoutParams)
        setTextColor(context.getColorStateList(R.color.black))
        id = index + 1
        text = meaning.partOfSpeech
        typeface = Typeface.DEFAULT_BOLD
        setTextColor(context.getColorStateList(R.color.black))
        textSize = 18f
    }
    binding.dynamicLayout.addView(partOfSpeechTextView)
}

/**
 * Creates TextView for definition text.
 */
@RequiresApi(Build.VERSION_CODES.M)
private fun createDefinitionTextView(
    definition: Definition,
    index: Int,
    binding: LayoutWordItemBinding,
) {
    val definitionLayoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(0, 10, 0, 0)
    }

    val definitionTextView = TextView(binding.dynamicLayout.context).apply {
        layoutParams = definitionLayoutParams
        text = definition.definition
        text = "${index + 1}. ${definition.definition}"
        id = index + 1
        textSize = 16f
        setTextColor(context.getColorStateList(R.color.black))
    }

    binding.dynamicLayout.addView(definitionTextView)
}

/**
 * Creates TextView for example text.
 */
private fun createExampleTextView(
    definition: Definition,
    index: Int,
    binding: LayoutWordItemBinding
) {
    val exampleLayoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(0, 4, 0, 6)
    }
    val exampleTextView = TextView(binding.dynamicLayout.context).apply {
        layoutParams = exampleLayoutParams
        text = definition.example
        id = index + 1
        textSize = 16f
        text = "Example: ${definition.example}"
    }
    binding.dynamicLayout.addView(exampleTextView)
}

class WordItemDiffUtilCallback : DiffUtil.ItemCallback<WordItemEntity>() {
    override fun areItemsTheSame(oldItem: WordItemEntity, newItem: WordItemEntity) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: WordItemEntity, newItem: WordItemEntity) =
        oldItem.id == newItem.id
}
