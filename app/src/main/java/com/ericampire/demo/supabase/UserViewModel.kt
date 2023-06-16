package com.ericampire.demo.supabase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericampire.demo.supabase.datasource.ApiResult
import com.ericampire.demo.supabase.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
  private val repository: UserRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow<ApiResult<*>>(ApiResult.Loading)
  val uiState: StateFlow<ApiResult<*>>
    get() = _uiState

  fun deleteById(id: Int) {
    viewModelScope.launch {
      repository.deleteById(id).collectLatest { data ->
        _uiState.update { data }
      }
    }
  }

  fun findAll() {
    viewModelScope.launch {
      repository.findAll().collectLatest { data ->
        _uiState.update { data }
      }
    }
  }

  fun update(id: Int, user: User) {
    viewModelScope.launch {
      repository.updateById(id, user).collectLatest { data ->
        _uiState.update { data }
      }
    }
  }

  fun insert(user: User) {
    viewModelScope.launch {
      repository.insert(user).collectLatest { data ->
        _uiState.update { data }
      }
    }
  }
}