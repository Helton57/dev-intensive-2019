package ru.skillbranch.devintensive.ui.group

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.children
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_group.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.ui.adapters.UserAdapter
import ru.skillbranch.devintensive.viewmodels.GroupViewModel

class GroupActivity : AppCompatActivity() {

    private lateinit var usersAdapter: UserAdapter
    private lateinit var viewModel: GroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        initToolbar()
        initViews()
        initViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.handleSearchQuery(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.handleSearchQuery(newText ?: "")
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finishActivity()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViews() {
        usersAdapter = UserAdapter { viewModel.handleSelectedItem(it.id) }
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        with(rv_user_list) {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(this@GroupActivity)
            addItemDecoration(divider)
        }

        fab.setOnClickListener {
            viewModel.handleCreateGroup()
            finishActivity()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(GroupViewModel::class.java)
        viewModel.getUsersData().observe(this, { usersAdapter.updateData(it) })
        viewModel.getSelectedData().observe(this, {
            updateChips(it)
            toggleFab(it.size > 1)
        })
    }

    private fun toggleFab(isShow: Boolean) {
        if (isShow) fab.show()
        else fab.hide()
    }

    private fun addChipToGroup(userItem: UserItem) {
        val chip = Chip(this).apply {
            text = userItem.fullName
            chipIcon = resources.getDrawable(R.drawable.avatar_default, theme)
            isCloseIconVisible = true
            tag = userItem.id
            isClickable = true
            closeIconTint = ColorStateList.valueOf(Color.WHITE)
            chipBackgroundColor =
                ColorStateList.valueOf(resources.getColor(R.color.color_primary_light, theme))
            setTextColor(Color.WHITE)
        }

        chip.setOnCloseIconClickListener { viewModel.handleRemoveChip(it.tag.toString()) }
        chip_group.addView(chip)
    }

    private fun updateChips(listUsers: List<UserItem>) {
        chip_group.visibility = if (listUsers.isEmpty()) View.GONE else View.VISIBLE

        val users = listUsers
            .associate { user -> user.id to user }
            .toMutableMap()

        val views = chip_group.children.associate { view -> view.tag to view }

        for ((k, v) in views) {
            if (!users.containsKey(k))
                chip_group.removeView(v)
            else users.remove(k)
        }

        users.forEach { (_, v) ->
            addChipToGroup(v)
        }
    }

    private fun finishActivity() {
        finish()
//        overridePendingTransition(R.anim.idle, R.anim.bottom_down)
    }
}
