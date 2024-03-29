package com.mpmp.protoiotest.Contracts

import com.mpmp.protoiotest.Data.Responses.Question
import com.mpmp.protoiotest.MVP.BasePresenter
import com.mpmp.protoiotest.MVP.BaseView

interface QuestionContract {
    interface Presenter : BasePresenter {
        fun getQuestionFromTheData(questionId: Int): Question?
    }

    interface View : BaseView<Presenter> {
    }
}