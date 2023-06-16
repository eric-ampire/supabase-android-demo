package com.ericampire.demo.supabase

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ericampire.demo.supabase.datasource.ApiResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListUserScreen(
  modifier: Modifier = Modifier,
  viewModel: UserViewModel
) {
  val uiState = viewModel.uiState.collectAsState()

  Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {},
    content = {
      when(uiState.value) {
        is ApiResult.Error -> Text(text = "Error")
        ApiResult.Loading -> {
          CircularProgressIndicator()
        }
        is ApiResult.Success -> {
          val users = (uiState.value as? ApiResult.Success<*>)?.data as? List<User>
          LazyColumn(
            modifier = Modifier
              .padding(it)
              .fillMaxSize(),
            content = {
              items(users ?: listOf()) { user ->
                Text(
                  modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                  text = user.name
                )
              }
            }
          )
        }
      }
    }
  )
}