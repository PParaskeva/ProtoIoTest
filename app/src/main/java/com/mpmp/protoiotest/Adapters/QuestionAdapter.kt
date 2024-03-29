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
import com.mpmp.protoiotest.Adapters.QuestionAdapter.*
import com.mpmp.protoiotest.Data.Responses.Question
import com.mpmp.protoiotest.Enum.QuestionType
import com.mpmp.protoiotest.R
import kotlinx.android.synthetic.main.questions_item_view.view.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import coil.Coil
import coil.api.get
import kotlinx.coroutines.*
import java.net.URL


class QuestionAdapter(
    val mQuestion: Question?
) : RecyclerView.Adapter<ViewHolder>() {

    var mSelectedAnswerPositions: MutableList<Int?> = mutableListOf()
    var isTheAnswerCorrect: Boolean? = null
    var mDrawable: Drawable? = null

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
            multipleAnswerMsg?.visibility = View.GONE
            answerButton?.visibility = View.GONE
        }
    }

    private fun setUpHeader(holder: ViewHolder) {
        holder.apply {
            questionHeaderLayout?.visibility = View.VISIBLE

            if (mQuestion?.questionType == QuestionType.MUTIPLECHOICE_MULTIPLE.type) {
                multipleAnswerMsg?.visibility = View.VISIBLE
            }
            if (mDrawable == null) {
                questionHeaderImage?.setImageResource(R.drawable.placeholder)
                CoroutineScope(Dispatchers.IO).launch {
                    async { mDrawable = mQuestion?.img?.let { Coil.get(it) } }.await()
                    runBlocking(Dispatchers.Main) {
                        questionHeaderImage?.setImageDrawable(mDrawable)
                    }
                }
            } else {
                questionHeaderImage?.setImageDrawable(mDrawable)
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

                background = if (mSelectedAnswerPositions.contains(positions)) {
                    roundedButtonWaitingDrawable
                } else {
                    roundedButtonDrawable
                }

                isTheAnswerCorrect?.let {
                    isEnabled = false

                    val isThePositionSelectedAsAnswer = mSelectedAnswerPositions.contains(positions)
                    val isThePositionACorrectAnswer = correctAnswers?.contains(possibleAnswer?.aId)

                    if (isThePositionSelectedAsAnswer &&
                        isThePositionACorrectAnswer == false) {
                        isTheAnswerCorrect = false
                        background = roundedButtonWrongDrawable
                    }

                    if (isThePositionACorrectAnswer == true) {
                        background = if (mSelectedAnswerPositions.isEmpty()) {
                            isTheAnswerCorrect = false
                            roundedButtonWrongDrawable
                        } else {
                            if (isThePositionSelectedAsAnswer) {
                                roundedButtonCorrectDrawable
                            } else {
                                isTheAnswerCorrect = false
                                roundedButtonWrongDrawable
                            }
                        }
                    }
                }

                setOnClickListener {
                    if (mQuestion?.questionType == QuestionType.MUTIPLECHOICE_MULTIPLE.type) {
                        onMultipleAnswerSelection(positions)
                    } else {
                        onSingleAnswerSelection(positions)
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun onSingleAnswerSelection(positions: Int) {
        if (mSelectedAnswerPositions.contains(positions)) {
            mSelectedAnswerPositions.remove(positions)
        } else {
            mSelectedAnswerPositions.clear()
            mSelectedAnswerPositions.add(positions)
        }
    }

    private fun onMultipleAnswerSelection(
        positions: Int
    ) {
        if (mSelectedAnswerPositions.contains(positions)) {
            mSelectedAnswerPositions.remove(positions)
        } else {
            mSelectedAnswerPositions.add(positions)
        }
    }

    override fun getItemCount(): Int {
        return (mQuestion?.possibleAnswers?.size ?: 0) + 1
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionHeaderLayout: LinearLayout? = itemView.questionHeaderLayout
        val questionHeaderImage: ImageView? = itemView.questionHeaderImage
        val questionTextView: TextView? = itemView.questionTextView
        val multipleAnswerMsg: TextView? = itemView.multipleAnswerMsg
        val answerButton: Button? = itemView.answerButton

        val context = itemView.context
        val roundedButtonDrawable = ContextCompat.getDrawable(context, R.drawable.roundedbutton)
        val roundedButtonWrongDrawable =
            ContextCompat.getDrawable(context, R.drawable.rounded_button_wrong)
        val roundedButtonWaitingDrawable =
            ContextCompat.getDrawable(context, R.drawable.rounded_button_waiting)
        val roundedButtonCorrectDrawable =
            ContextCompat.getDrawable(context, R.drawable.rounded_button_correct)
    }
}
