package com.example.mvpplayaround.ui.base

//the reason is to move common methods across without declaring it multiple times
interface BaseContract {

    interface View {

    }

    interface Presenter<V: View> {
        fun onViewCreated(view: V)
        fun onDestroy()
    }
}