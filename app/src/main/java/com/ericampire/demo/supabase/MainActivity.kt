package com.ericampire.demo.supabase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.ericampire.demo.supabase.ui.theme.SupaandroidTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val userViewModel by viewModels<UserViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    val user = User(name = "Tim Cook", profile_url = "I don't know")
//    userViewModel.update(1, user)
    userViewModel.findAll()
    setContent {
      SupaandroidTheme {
        ListUserScreen(viewModel = userViewModel)
      }
    }
  }
}



@Serializable
data class User(
  val id: Int = 0,
  val name: String = "",
  val created_at: String = "",
  val profile_url: String = ""
)

