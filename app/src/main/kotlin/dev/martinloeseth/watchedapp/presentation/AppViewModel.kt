package dev.martinloeseth.watchedapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.martinloeseth.watchedapp.domain.models.ThemeMode
import dev.martinloeseth.watchedapp.domain.usecases.GetThemeModeUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    getThemeModeUseCase: GetThemeModeUseCase,
) : ViewModel() {

    val themeMode: StateFlow<ThemeMode> = getThemeModeUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ThemeMode.SYSTEM,
        )
}
