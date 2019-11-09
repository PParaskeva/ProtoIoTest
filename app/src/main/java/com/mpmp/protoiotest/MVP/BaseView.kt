package com.mpmp.protoiotest.MVP

interface BaseView<T> {
    fun setPresenter(presenter: T)
}