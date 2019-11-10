package com.mpmp.protoiotest.Fragments

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.Coil
import coil.api.get
import coil.api.load
import com.mpmp.protoiotest.Data.Data
import com.mpmp.protoiotest.MainActivity

import com.mpmp.protoiotest.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_results.*
import kotlinx.coroutines.*

class ResultsFragment : Fragment() {

    var mMessage: String? = ""
    var mImageUrl: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    override fun onResume() {
        super.onResume()
        calculateResults()
        setUpResultImageView()
        setUpMessageText()
        setScoreValue()
        setUpCorrectAnswerValue()
        setUpWrongAnswerValue()
        setUpRestartButton()
    }

    private fun calculateResults() {
        val userScore = Data.userData?.totalPoints ?: 0
        Data.getResultsResponse?.results?.find {
            userScore >= it?.minpoints ?: 0 && userScore <= it?.maxpoints ?: 0
        }?.let {
            mImageUrl = it.img
            mMessage = it.message
        }
    }

    private fun setUpResultImageView() {
        resultsImageView?.setImageResource(R.drawable.placeholder)
        CoroutineScope(Dispatchers.IO).launch {
            var drawable: Drawable? = null
            async { drawable = mImageUrl?.let { Coil.get(it) } }.await()
            runBlocking(Dispatchers.Main) {
                resultsImageView?.setImageDrawable(drawable)
            }
        }
    }

    private fun setUpMessageText() {
        resultsMsg?.text = mMessage ?: ""
    }

    private fun setScoreValue() {
        scoreValue?.text = Data.userData?.totalPoints?.toString() ?: "0"
    }

    private fun setUpCorrectAnswerValue() {
        correctAnswerValue?.text = Data.userData?.correctAnswersList?.size?.toString() ?: "0"
    }

    private fun setUpWrongAnswerValue() {
        wrongAnswerValue?.text = Data.userData?.wrongAnswersList?.size?.toString() ?: "0"
    }

    private fun setUpRestartButton() {
        restartButton?.setOnClickListener {
            Intent(activity, MainActivity::class.java)?.let {
                activity?.startActivity(it)
                activity?.finish()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ResultsFragment().apply {
                //                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
    }
}
