package uz.gita.quizzbyme.presenter

import android.content.Context
import uz.gita.quizzbyme.ContractForStatistics
import uz.gita.quizzbyme.model.ModelStatistics

class PresenterImplForStatistics(context: Context, view: ContractForStatistics.View) :
    ContractForStatistics.Presenter {
    private var model: ContractForStatistics.Model
    private val view: ContractForStatistics.View = view

    init {
        model = ModelStatistics(context)
    }

    override fun reload() {
        view.showList(model.getStatistics())
    }
}