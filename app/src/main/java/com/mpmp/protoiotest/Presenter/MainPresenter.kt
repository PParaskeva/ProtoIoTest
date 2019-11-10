package com.mpmp.protoiotest.Presenter

import com.mpmp.protoiotest.Contracts.MainContract
import com.mpmp.protoiotest.Data.Data
import com.mpmp.protoiotest.Data.Responses.PossibleAnswer
import com.mpmp.protoiotest.Data.Responses.Question
import com.mpmp.protoiotest.Enum.QuestionType
import com.mpmp.protoiotest.Model.Model

class MainPresenter(val mView: MainContract.View?) : MainContract.Presenter {


    override fun start() {
        mView?.setPresenter(this)
    }

    override suspend fun getResults() {
        Model().getResults().let {
            Data.getResultsResponse = it
        }
    }

    override suspend fun getQuestions() {
        Model().getQuestions().let { getQuestionsResponse ->
            getQuestionsResponse?.questions?.forEach { question ->
                question?.correctAnswerList = buildCorrectListAnswer(question)

                if (question?.questionType == QuestionType.TRUE_FALSE.type) {
                    question.possibleAnswers = buildPossibleAnswersTrueFalse()
                }

            }

            Data.getQuestionsResponse = getQuestionsResponse
        }
    }

    private fun buildPossibleAnswersTrueFalse(): List<PossibleAnswer?>? {
        return listOf(
            PossibleAnswer(0, "False"),
            PossibleAnswer(1, "True")
        )
    }

    private fun buildCorrectListAnswer(question: Question?): List<Int>? {
        return when (question?.correctAnswer) {
            is List<*> -> {
                question.correctAnswer.map { (it as Double).toInt() }
            }
            is Boolean -> {
                listOf(
                    if (question.correctAnswer) {
                        1
                    } else {
                        0
                    }
                )
            }
            is Double -> {
                listOf(question.correctAnswer.toInt())
            }
            else -> {
                listOf()
            }
        }

    }
}
