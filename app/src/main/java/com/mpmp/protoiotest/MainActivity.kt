package com.mpmp.protoiotest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mpmp.protoiotest.Contracts.MainContract
import com.mpmp.protoiotest.Data.Data
import com.mpmp.protoiotest.Fragments.QuestionFragment
import com.mpmp.protoiotest.Fragments.ResultsFragment
import com.mpmp.protoiotest.Presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), MainContract.View {

    var mPresenter: MainContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter = MainPresenter(this)
        mPresenter?.start()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                coroutineScope {
                    showProgressBar()
                    async { mPresenter?.getQuestions() }.await()
                    async { mPresenter?.getResults() }.await()
                    hideProgressBar()
                    Data.getQuestionsResponse?.let {
                        moveToQuestionFragment()
                    }
                }

            } catch (t: Throwable) {
                hideProgressBar()
            }
        }
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        mPresenter = presenter
    }

    override suspend fun moveToQuestionFragment() {
        val listOfQuestionsId = Data.userData?.listOfQuestionsId ?: mutableListOf()
        val questions = Data.getQuestionsResponse?.questions?.filter {
            !listOfQuestionsId.contains(it?.qId)
        }
        if (questions?.size ?: 0 > 0) {
            val randomNum = Random.nextInt(0, questions?.size ?: 0)
            questions?.get(randomNum)?.let { randomQuestionSelection ->
                randomQuestionSelection.qId?.let { questionId ->
                    Data.userData?.listOfQuestionsId?.add(questionId)
                    fragmentTransfer(QuestionFragment.newInstance(questionId))
                }
            }
        } else {
            fragmentTransfer(ResultsFragment.newInstance())
        }
    }

    override suspend fun showProgressBar() {
        runBlocking(Dispatchers.Main) {
            mainActivityProgressBar?.visibility = View.VISIBLE
        }
    }

    override suspend fun hideProgressBar() {
        runBlocking(Dispatchers.Main) {
            mainActivityProgressBar?.visibility = View.GONE
        }
    }

    fun fragmentTransfer(fragment: Fragment) {
        runBlocking(Dispatchers.Main) {
            supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
        }
    }
}
