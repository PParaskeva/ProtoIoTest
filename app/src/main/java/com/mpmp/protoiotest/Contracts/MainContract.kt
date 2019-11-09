package com.mpmp.protoiotest.Contracts

import com.mpmp.protoiotest.MVP.BasePresenter
import com.mpmp.protoiotest.MVP.BaseView

interface MainContract {

    interface Presenter : BasePresenter {
        suspend fun getQuestions()
    }

    interface View : BaseView<Presenter> {
        suspend fun moveToQuestionFragment()
        suspend fun showProgressBar()
        suspend fun hideProgressBar()
    }
}