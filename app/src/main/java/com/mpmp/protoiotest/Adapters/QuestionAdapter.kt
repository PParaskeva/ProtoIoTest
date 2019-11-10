package com.mpmp.protoiotest.Adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.api.load
import com.mpmp.protoiotest.Adapters.QuestionAdapter.*
import com.mpmp.protoiotest.Data.Responses.Question
import com.mpmp.protoiotest.R
import kotlinx.android.synthetic.main.questions_item_view.view.*

class QuestionAdapter(
    val mQuestion: Question?) : RecyclerView.Adapter<ViewHolder>() {

    var mSelectedAnswerPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.questions_item_view,
            parent, false
        )
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            initXml(this)
            if (position == 0) {
                setUpHeader(this)
            }
            if (position > 0) {
                setUpPossibleAnswers(this, position - 1)
            }
        }
    }

    private fun initXml(holder: ViewHolder) {
        holder.apply {
            questionHeaderLayout?.visibility = View.GONE
            answerButton?.visibility = View.GONE
        }
    }

    private fun setUpHeader(holder: ViewHolder) {
        holder.apply {
            questionHeaderLayout?.visibility = View.VISIBLE

            questionHeaderImage?.load(mQuestion?.img) {
                crossfade(true)
                placeholder(R.drawable.placeholder)
                error(R.drawable.placeholder)
            }

            questionTextView?.text = mQuestion?.title
        }
    }


    private fun setUpPossibleAnswers(holder: ViewHolder, positions: Int) {
        holder.apply {
            answerButton?.apply {
                visibility = View.VISIBLE
                val possibleAnswer = mQuestion?.possibleAnswers?.get(positions)
                val correctAnswers = mQuestion?.correctAnswerList
                text = possibleAnswer?.caption ?: ""

                mSelectedAnswerPosition?.let { selectedAnswerPosition ->

                    isEnabled = false

                    if (selectedAnswerPosition == positions) {
                        if (correctAnswers?.contains(possibleAnswer?.aId) == false) {
                            background = ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.rounded_button_wrong
                            )
                        }
                    }

                    if (correctAnswers?.contains(possibleAnswer?.aId) == true) {
                        background = ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.rounded_button_correct
                        )
                    }
                }

                setOnClickListener {
                    background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.rounded_button_waiting
                    )
                    possibleAnswer?.aId?.let { answerId ->
                        mSelectedAnswerPosition = positions
                    }
                }
            }


        }
    }

    override fun getItemCount(): Int {
        return (mQuestion?.possibleAnswers?.size ?: 0) + 1
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionHeaderLayout: LinearLayout? = itemView.questionHeaderLayout
        val questionHeaderImage: ImageView? = itemView.questionHeaderImage
        val questionTextView: TextView? = itemView.questionTextView
        val answerButton: Button? = itemView.answerButton
    }
}
