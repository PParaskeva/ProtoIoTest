package com.mpmp.protoiotest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mpmp.protoiotest.Contracts.MainContract
import com.mpmp.protoiotest.Fragments.QuestionFragment
import com.mpmp.protoiotest.Presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity(), MainContract.View {

    var mPresenter: MainContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter(this)
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

    override fun setPresenter(presenter: MainContract.Presenter) {
        mPresenter = presenter
    }

    override suspend fun moveToQuestionFragment() {
        println("moveToQuestionFragment")
//        runBlocking(Dispatchers.Main) {
            fragmentTransfer(QuestionFragment.newInstance(1))
//        }
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
