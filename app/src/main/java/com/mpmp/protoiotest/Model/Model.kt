package com.mpmp.protoiotest.Model

import com.mpmp.protoiotest.Data.Responses.GetQuestionsResponse
import com.mpmp.protoiotest.Retrofit.RetrofitConnection

class Model {

    suspend fun getQuestions(): GetQuestionsResponse? {
        RetrofitConnection().getConnection().getQuestionsCall().let { reponse ->
            if (reponse.isSuccessful) {
                return reponse.body()
            }
        }
        return null
    }
}