package com.mpmp.protoiotest.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mpmp.protoiotest.Adapters.QuestionAdapter
import com.mpmp.protoiotest.Contracts.QuestionContract
import com.mpmp.protoiotest.Data.Responses.Question
import com.mpmp.protoiotest.Presenter.QuestionsPresenter

import com.mpmp.protoiotest.R
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.coroutines.*

class QuestionFragment : Fragment(), QuestionContract.View {

    var mPresenter: QuestionContract.Presenter? = null

    var mAdapter: QuestionAdapter? = null

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
    }

    private fun setUpRecyclerView() {
        questionFragmentRecyclerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = null
            mAdapter = QuestionAdapter(mQuestion, checkIfTheAnswerIsCorrect)
            adapter = mAdapter
        }
    }

    val checkIfTheAnswerIsCorrect: (() -> Unit)? = {
        try {
            CoroutineScope(Dispatchers.Default).launch {
                delay(1000)
                runBlocking(Dispatchers.Main) {
                    mAdapter?.notifyDataSetChanged()
                }
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
