package com.mpmp.protoiotest.Presenter

import com.google.gson.Gson
import com.mpmp.protoiotest.Contracts.QuestionContract
import com.mpmp.protoiotest.Model.Model

class QuestionsPresenter(val mView: QuestionContract.View?) : QuestionContract.Presenter {

    override fun start() {
        mView?.setPresenter(this)
    }

    override suspend fun getQuestions() {
        Model().getQuestions().let {
            val gson = Gson().toJson(it)
            println(gson)
        }
    }

    override suspend fun checkIfTheQuestionsIsCorrect() {

    }
}