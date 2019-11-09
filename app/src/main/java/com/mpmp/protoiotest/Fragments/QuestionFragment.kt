package com.mpmp.protoiotest.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mpmp.protoiotest.Contracts.QuestionContract
import com.mpmp.protoiotest.Presenter.QuestionsPresenter

import com.mpmp.protoiotest.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionFragment : Fragment(), QuestionContract.View {

    var mPresenter: QuestionContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = QuestionsPresenter(this)
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
        CoroutineScope(Dispatchers.IO).launch {
            try {
                mPresenter?.getQuestions()
            } catch (t: Throwable) {
                println(t.message)
            }
        }

    }

    override suspend fun showProgressBar() {

    }

    override suspend fun hideProgressBar() {
    }

    override suspend fun showQuestion() {
    }

    override fun setPresenter(presenter: QuestionContract.Presenter) {
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            QuestionFragment().apply {
                //                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
    }
}
