package ru.skillbranch.devintensive.ui.profile

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.isRepositoryGithubValid
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    companion object{
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    private lateinit var viewModel: ProfileViewModel
    var isEditMode = false
    lateinit var viewFields : Map<String, TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initViews(savedInstanceState)
        initViewModel()
    }

    /**
     * Этот метод сохраняет состояние представления в bundle
     * Для API Level < 28 (Android P) этот метод будет выполняться в onStop(), и нет никаких гарантий относительно того,
     * произойдет ли это до или после onPause()
     * Для API Level >= 28 будет вызван после onStop()
     * Не будет вызван, если Activity будет явно закрыто пользователем при нажатии на системную клавишу back
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_EDIT_MODE, isEditMode)
    }

    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer { updateUi(it) })
        viewModel.getTheme().observe(this, Observer { updateTheme(it) })
    }

    private fun updateTheme(mode: Int) {
        delegate.setLocalNightMode(mode)
    }

    private fun updateUi(profile : Profile) {
        profile.toMap().also {
            for ((k, v) in viewFields){
                v.text = it[k].toString()
            }
        }
    }

    private fun initViews(savedInstanceState: Bundle?) {
        viewFields = mapOf(
            "nickName" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
        )

        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        showCurrentMode(isEditMode)

        btn_edit.setOnClickListener {
            if (isEditMode) saveProfileInfo()
            isEditMode = isEditMode.not()
            showCurrentMode(isEditMode)
        }

        btn_switch_theme.setOnClickListener{
            viewModel.switchTheme()
        }

        et_repository.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                wr_repository.error = if (!s.toString().isRepositoryGithubValid()){
                    "Невалидный адрес репозитория"
                } else {
                    null
                }
            }
        })
    }

    private fun showCurrentMode(isEdit: Boolean) {
        val info = viewFields.filter { setOf( "firstName", "lastName", "about", "repository" ).contains ( it.key )}
        for ((_, v) in info){
            v as EditText
            v.isFocusable = isEdit
            v.isFocusableInTouchMode = isEdit
            v.isEnabled = isEdit
            v.background.alpha = if (isEdit) 255 else 0
        }

        ic_eye.visibility = if (isEdit) View.GONE else View.VISIBLE

        wr_about.isCounterEnabled = isEdit

        with(btn_edit){
            val filter : ColorFilter? = if (isEdit){
                PorterDuffColorFilter(
                    resources.getColor(R.color.color_accent, theme),
                    PorterDuff.Mode.SRC_IN
                )
            } else {
                null
            }

            val icon = if (isEdit) {
                resources.getDrawable(R.drawable.ic_save_black_24dp, theme)
            } else {
                resources.getDrawable(R.drawable.ic_edit_black_24dp, theme)
            }

            background.colorFilter = filter
            setImageDrawable(icon)
        }
    }

    private fun saveProfileInfo(){
        validateRepository()
        Profile(
            firstName = et_first_name.text.toString(),
            lastName = et_last_name.text.toString(),
            about = et_about.text.toString(),
            repository = et_repository.text.toString()
        ).apply {
            viewModel.saveProfileData(this)
        }
    }

    private fun validateRepository(){
        if (!et_repository.text.toString().isRepositoryGithubValid()){
            et_repository.text.clear()
        }
    }

}


