package com.end.mvi.viewmodels


import androidx.lifecycle.ViewModel
import com.end.mvi.models.BaseEndResponse
import com.end.mvi.repos.EndRepository
import com.end.mvi.utils.EndAction
import com.end.mvi.utils.EndMainPageUIState
import io.reactivex.rxjava3.subjects.PublishSubject

class EndIntention(
    private val endRepository: EndRepository
) : ViewModel() {

    fun endAction(action: EndAction): PublishSubject<EndMainPageUIState<BaseEndResponse>> {
        val observable = PublishSubject.create<EndMainPageUIState<BaseEndResponse>>()
        when (action) {
            is EndAction.LoadEndClothesItems -> {
                endRepository.getClothesAndShoes().doOnSubscribe {
                    observable.onNext(EndMainPageUIState.Loading)
                }
                    .doOnError {
                        observable.onNext(
                            EndMainPageUIState.Error(
                                it.localizedMessage ?: ""
                            )
                        )
                    }
                    .subscribe {
                        observable.onNext(
                            EndMainPageUIState.SucceedEndClothes(it)
                        )
                    }

            }
        }
        return observable
    }

}