package com.example.kanj.whatever

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.example.kanj.base.AbstractActivity
import com.example.kanj.base.MainActivityPresenter
import com.example.kanj.base.MainActivityScene
import com.example.kanj.whatever.dagger.ActivityComponent
import com.example.kanj.whatever.dagger.DaggerActivityComponent
import kotlinx.android.synthetic.main.activity_main.*

const val PULL_FRAG_TAG = "PULL_FRAG_TAG"

class MainActivity : AbstractActivity<ActivityComponent, MainActivityScene, MainActivityPresenter>(), MainActivityScene {
    private var component: ActivityComponent? = null
    var repoInput: String? = null
    var displayedDialog: AlertDialog? = null

    override fun injector(): ActivityComponent {
        component?.let {
            return it
        }

        val comp = DaggerActivityComponent.builder()
                .appComponent((application as MyApplication).component)
                .build()
        component = comp
        return comp
    }

    override fun setupViewAndInject(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        bt_add_repo.setOnClickListener({onAddRepoClick()})
        setSupportActionBar(toolbar)
        injector().inject(this)
    }

    override fun showPullFragment(args: Bundle) {
        val fragment = PullListFragment()
        fragment.arguments = args
        supportFragmentManager.beginTransaction()
                .add(R.id.frag_container, fragment, PULL_FRAG_TAG)
                .commit()
        invalidateOptionsMenu()
    }

    override fun removePullFragment() {
        val frag = supportFragmentManager.findFragmentByTag(PULL_FRAG_TAG)
        supportFragmentManager.beginTransaction()
                .remove(frag)
                .commit()
        invalidateOptionsMenu()
        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.removeItem(
                if (presenter.isRepoAdded()) {
                    R.id.add_repo
                } else {
                    R.id.remove_repo
                }
        )
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.add_repo -> {
                onAddRepoClick()
                return true
            }
            R.id.remove_repo -> {
                onRemoveRepoClick()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun onAddRepoClick() {
        //presenter.onAddRepoClick()
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle(R.string.repo_input_title).setMessage(R.string.repo_input_body)

        val et = EditText(this)
        et.inputType = InputType.TYPE_CLASS_TEXT
        alertBuilder.setView(et)

        alertBuilder.setPositiveButton(R.string.ok, {dialog,_ ->
            presenter.onRepoInput(et.text.toString().trim())
            dialog.dismiss()
        })

        alertBuilder.setNegativeButton(R.string.cancel, {dialog, _ ->
            dialog.dismiss()
        })

        displayedDialog = alertBuilder.show()
    }

    override fun showInvalidInputError() {
        Toast.makeText(this, R.string.err_repo_input, Toast.LENGTH_LONG).show()
    }

    fun onRemoveRepoClick() {
        presenter.onRemoveRepoClick()
    }

    override fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onDestroy() {
        super.onDestroy()
        displayedDialog?.dismiss()
    }
}
