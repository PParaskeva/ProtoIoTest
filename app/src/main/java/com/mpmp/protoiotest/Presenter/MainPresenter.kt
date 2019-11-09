package com.mpmp.protoiotest.Presenter

import com.mpmp.protoiotest.Contracts.MainContract
import com.mpmp.protoiotest.Data.Data
import com.mpmp.protoiotest.Model.Model

class MainPresenter(val mView: MainContract.View?) : MainContract.Presenter {

    override fun start() {
        mView?.setPresenter(this)
    }

    override suspend fun getQuestions() {
        mView?.showProgressBar()
        Model().getQuestions().let {
            Data.getQuestionsResponse = it
            mView?.hideProgressBar()
            mView?.moveToQuestionFragment()
        }
    }

}
