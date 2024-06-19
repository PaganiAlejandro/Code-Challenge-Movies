package com.alepagani.codechallengemovies.presentation.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.alepagani.codechallengemovies.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val editText: EditText
    private val clearButton: ImageButton

    private val _textChanges = MutableStateFlow("")
    val textChanges: StateFlow<String> get() = _textChanges

    init {
        LayoutInflater.from(context).inflate(R.layout.view_search_edit_text, this, true)

        editText = findViewById(R.id.searchEditText)
        clearButton = findViewById(R.id.clearButton)

        clearButton.setOnClickListener {
            editText.text.clear()
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = if (s.isNullOrEmpty()) GONE else VISIBLE
                _textChanges.value = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val a = context.obtainStyledAttributes(attrs, R.styleable.SearchEditText, defStyleAttr, 0)
        val hint = a.getString(R.styleable.SearchEditText_hint)
        val searchIcon = a.getDrawable(R.styleable.SearchEditText_searchIcon)
        val clearIcon = a.getDrawable(R.styleable.SearchEditText_clearIcon)

        editText.hint = hint ?: context.getString(R.string.default_hint)
        editText.setCompoundDrawablesWithIntrinsicBounds(searchIcon, null, null, null)
        clearButton.setImageDrawable(clearIcon ?: ContextCompat.getDrawable(context, R.drawable.ic_clear))

        a.recycle()
    }

    fun getText(): String {
        return editText.text.toString()
    }

    fun setText(text: String) {
        editText.setText(text)
    }
}