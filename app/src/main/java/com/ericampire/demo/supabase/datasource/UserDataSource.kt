package com.ericampire.demo.supabase.datasource

import android.util.Log
import com.ericampire.demo.supabase.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.FilterOperator
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.createChannel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.presenceChangeFlow
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.Result

class UserDataSource @Inject constructor(
  private val supabaseClient: SupabaseClient
) {

  fun deleteById(userId: Int): Flow<ApiResult<Unit>> {
    return flow {
      emit(ApiResult.Loading)
      try {
        supabaseClient.postgrest["users-table"].delete {
          filter("id", FilterOperator.EQ, userId)
        }
        emit(ApiResult.Success(Unit))
      } catch (e: Exception) {
        emit(ApiResult.Error(e.message))
      }
    }
  }

  fun findAll(): Flow<ApiResult<List<User>>> {
    return flow {
      //emit(ApiResult.Loading)
      supabaseClient.realtime.connect()
      val channel = supabaseClient.realtime.createChannel("channelId") {
        //optional config
      }
      val changeFlow = channel.postgresChangeFlow<PostgresAction>(schema = "public")
      changeFlow.collect {
        when(it) {
          is PostgresAction.Delete -> Log.e("ericampire", "Deleted: ${it.oldRecord}")
          is PostgresAction.Insert -> Log.e("ericampire", "Inserted: ${it.record}")
          is PostgresAction.Select -> Log.e("ericampire", "Selected: ${it.record}")
          is PostgresAction.Update -> Log.e("ericampire", "Updated: ${it.oldRecord} with ${it.record}")
        }
      }

      channel.join()


//      try {
//        val res = supabaseClient.postgrest["users-table"].select()
//        val users = res.decodeList<User>()
//        emit(ApiResult.Success(users))
//      } catch (e: Exception) {
//        emit(ApiResult.Error(e.message))
//      }
    }
  }

  fun insert(user: User): Flow<ApiResult<Unit>> {
    return flow {
      emit(ApiResult.Loading)
      try {
        supabaseClient.postgrest["users-table"].insert(user)
        emit(ApiResult.Success(Unit))
      } catch (e: Exception) {
        emit(ApiResult.Error(e.message))
      }
    }
  }

  fun updateById(userId: Int, user: User): Flow<ApiResult<Unit>> {
    return flow {
      emit(ApiResult.Loading)
      try {
        supabaseClient.postgrest["users-table"].update(user) {
          filter("id", FilterOperator.EQ, userId)
        }
        emit(ApiResult.Success(Unit))
      } catch (e: Exception) {
        emit(ApiResult.Error(e.message))
      }
    }
  }
}


sealed class ApiResult<out R> {
  data class Success<out R>(val data: R): ApiResult<R>()
  data class Error(val message: String?): ApiResult<Nothing>()
  object Loading : ApiResult<Nothing>()
}