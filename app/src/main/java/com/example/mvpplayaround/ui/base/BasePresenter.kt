package com.example.mvpplayaround.ui.base

abstract class BasePresenter<V: BaseContract.View>: BaseContract.Presenter<V> {

    protected var view: V? = null

    override fun onViewCreated(view: V) {
        this.view = view
    }

    override fun onDestroy() {
        view = null
    }
}