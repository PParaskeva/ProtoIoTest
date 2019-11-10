package com.mpmp.protoiotest.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpmp.protoiotest.Adapters.QuestionAdapter
import com.mpmp.protoiotest.Contracts.QuestionContract
import com.mpmp.protoiotest.Data.Responses.Question
import com.mpmp.protoiotest.Presenter.QuestionsPresenter
import com.mpmp.protoiotest.R
import kotlinx.android.synthetic.main.fragment_question.*
import com.google.android.material.snackbar.Snackbar
import com.mpmp.protoiotest.Data.Data
import com.mpmp.protoiotest.MainActivity
import kotlinx.coroutines.*


class QuestionFragment : Fragment(), QuestionContract.View {

    var mPresenter: QuestionContract.Presenter? = null

    var mAdapter: QuestionAdapter? = null
    var snackbar: Snackbar? = null

    var mQuestionId: Int? = null
    var mQuestion: Question? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = QuestionsPresenter(this)
        arguments?.let {
            mQuestionId = it.getInt(QUESTION_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.start()
        mQuestion = mQuestionId?.let { mPresenter?.getQuestionFromTheData(it) }
        setUpRecyclerView()
        setUpContinueButton()
    }

    private fun setUpRecyclerView() {
        questionFragmentRecyclerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = null
            mAdapter = QuestionAdapter(mQuestion)
            adapter = mAdapter
        }
    }

    private fun setUpContinueButton() {
        continueButton?.setOnClickListener {
            onContinueClick()
        }
    }

    fun onContinueClick() {
        try {
            CoroutineScope(Dispatchers.Default).launch {
                if (mAdapter?.mSelectedAnswerPosition == null) {
                    mAdapter?.mSelectedAnswerPosition = -1
                }
                runBlocking(Dispatchers.Main) {
                    mAdapter?.notifyDataSetChanged()
                }
                delay(3000)
                if (mAdapter?.isTheAnswerCorrect == true) {
                    mQuestionId?.let {
                        Data.userData?.correctAnswersList?.add(it)
                        Data.userData?.totalPoints =
                            (Data.userData?.totalPoints ?: 0) + (mQuestion?.points ?: 0)
                    }

                } else {
                    mQuestionId?.let {
                        Data.userData?.wrongAnswersList?.add(it)
                    }
                }
                activity?.let { (it as MainActivity).moveToQuestionFragment() }
            }
        } catch (t: Throwable) {

        }
    }

    override fun setPresenter(presenter: QuestionContract.Presenter) {
        mPresenter = presenter
    }

    companion object {

        val QUESTION_ID = "questionId"

        @JvmStatic
        fun newInstance(questionId: Int) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putInt(QUESTION_ID, questionId)
                }
            }
    }
}
