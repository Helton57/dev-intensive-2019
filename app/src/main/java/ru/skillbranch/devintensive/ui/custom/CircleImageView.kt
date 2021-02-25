package ru.skillbranch.devintensive.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

//вернуться на 4 урок и доделать задание, тогда и этот класс появится
@SuppressLint("AppCompatCustomView")
class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr), View.OnLongClickListener {
    override fun onLongClick(p0: View?): Boolean {
        return true
    }
}