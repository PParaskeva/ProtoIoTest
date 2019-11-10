package com.mpmp.protoiotest.Presenter

import com.mpmp.protoiotest.Contracts.QuestionContract
import com.mpmp.protoiotest.Data.Data
import com.mpmp.protoiotest.Data.Responses.Question

class QuestionsPresenter(val mView: QuestionContract.View?) : QuestionContract.Presenter {

    override fun start() {
        mView?.setPresenter(this)
    }

    override fun getQuestionFromTheData(questionId: Int): Question? =
        Data.getQuestionsResponse?.questions?.find {
            it?.qId == questionId
        }

}