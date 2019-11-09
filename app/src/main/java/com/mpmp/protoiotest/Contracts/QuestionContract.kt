package com.mpmp.protoiotest.Contracts

import com.mpmp.protoiotest.MVP.BasePresenter
import com.mpmp.protoiotest.MVP.BaseView

interface QuestionContract {
    interface Presenter : BasePresenter {
        suspend fun getQuestions()
        suspend fun checkIfTheQuestionsIsCorrect()
    }

    interface View : BaseView<Presenter> {
        suspend fun showProgressBar()
        suspend fun hideProgressBar()
        suspend fun showQuestion()
    }
}