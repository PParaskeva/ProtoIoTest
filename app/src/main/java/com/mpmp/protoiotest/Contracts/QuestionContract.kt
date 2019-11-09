package com.mpmp.protoiotest.Contracts

import com.mpmp.protoiotest.MVP.BasePresenter
import com.mpmp.protoiotest.MVP.BaseView

interface QuestionContract {
    interface Presenter : BasePresenter {}
    interface View : BaseView<Presenter> {}
}