package com.mpmp.protoiotest.Data

import com.mpmp.protoiotest.Data.Responses.GetQuestionsResponse
import com.mpmp.protoiotest.Data.Responses.GetResultResponse

object Data {
    val userData: UserData? = UserData()
    var getQuestionsResponse: GetQuestionsResponse? = null
    var getResultsResponse: GetResultResponse? = null
}