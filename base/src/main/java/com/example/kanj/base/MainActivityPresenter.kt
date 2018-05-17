package com.example.kanj.base

interface MainActivityPresenter : AbstractActivityPresenter<MainActivityScene> {
    //fun onAddRepoClick()

    fun onRemoveRepoClick()

    fun onRepoInput(input: String)

    fun isRepoAdded(): Boolean
}