package com.ericampire.demo.supabase.repository

import com.ericampire.demo.supabase.User
import com.ericampire.demo.supabase.datasource.ApiResult
import com.ericampire.demo.supabase.datasource.UserDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserRepository {
  fun findAll(): Flow<ApiResult<List<User>>>
  fun updateById(id: Int, user: User): Flow<ApiResult<Unit>>
  fun insert(user: User): Flow<ApiResult<Unit>>
  fun deleteById(id: Int): Flow<ApiResult<Unit>>
}


class DefaultUserRepository @Inject constructor(
  private val dataSource: UserDataSource,
) : UserRepository {

  override fun findAll(): Flow<ApiResult<List<User>>> {
    return dataSource.findAll()
  }

  override fun updateById(id: Int, user: User): Flow<ApiResult<Unit>> {
    return dataSource.updateById(id, user)
  }

  override fun insert(user: User): Flow<ApiResult<Unit>> {
    return dataSource.insert(user)
  }

  override fun deleteById(id: Int): Flow<ApiResult<Unit>> {
    return dataSource.deleteById(id)
  }
}
