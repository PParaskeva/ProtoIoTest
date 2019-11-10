package com.mpmp.protoiotest.Data

data class UserData(
    var correctAnswersList: MutableList<Int> = mutableListOf(),
    var wrongAnswersList: MutableList<Int> = mutableListOf(),
    var listOfQuestionsId: MutableList<Int> = mutableListOf(),
    var totalPoints: Int = 0
)