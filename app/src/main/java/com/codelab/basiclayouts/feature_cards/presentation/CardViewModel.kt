package com.codelab.basiclayouts.feature_cards.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelab.basiclayouts.core.domain.use_case.AccountingUseCases
import com.codelab.basiclayouts.feature_account.domain.model.PetCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val accountingUseCases: AccountingUseCases
) : ViewModel() {

    private var _petCardData by mutableStateOf(listOf<PetCard>())
    val petCardData: List<PetCard>
        get() = _petCardData

    fun getCardsFromDatabase(){
        viewModelScope.launch {
            _petCardData = accountingUseCases.getCardsList().toMutableStateList()
        }
    }
}