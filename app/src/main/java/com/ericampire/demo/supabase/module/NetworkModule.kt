package com.ericampire.demo.supabase.module

import com.ericampire.demo.supabase.datasource.UserDataSource
import com.ericampire.demo.supabase.repository.DefaultUserRepository
import com.ericampire.demo.supabase.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideSupabaseClient(): SupabaseClient {
    return createSupabaseClient(
      supabaseUrl = "https://anqelvcebqnrppmtnzwl.supabase.co",
      supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYW5vbiIsImlhdCI6MTYzNTUzMjczMCwiZXhwIjoxOTUxMTA4NzMwfQ.KESuYGeVClU1mdufNncGOKeyQPpkfEsHNhXI-fYvLCk"
    ) {
      install(Postgrest)
      install(GoTrue) {
        //platformGoTrueConfig()
      }
      install(Realtime)
    }
  }

  @Provides
  @Singleton
  fun provideUserDataSource(client: SupabaseClient): UserDataSource {
    return UserDataSource(client)
  }

  @Provides
  @Singleton
  fun provideUserRepository(dataSource: UserDataSource) : UserRepository {
    return DefaultUserRepository(dataSource)
  }
}