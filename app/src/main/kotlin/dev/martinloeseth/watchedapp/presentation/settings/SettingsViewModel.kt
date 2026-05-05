package dev.martinloeseth.watchedapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.martinloeseth.watchedapp.data.preferences.ThemeMode
import dev.martinloeseth.watchedapp.domain.usecases.GetThemeModeUseCase
import dev.martinloeseth.watchedapp.domain.usecases.SetThemeModeUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getThemeModeUseCase: GetThemeModeUseCase,
    private val setThemeModeUseCase: SetThemeModeUseCase,
) : ViewModel() {

    val themeMode: StateFlow<ThemeMode> = getThemeModeUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ThemeMode.SYSTEM,
        )

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch { setThemeModeUseCase(mode) }
    }
}
