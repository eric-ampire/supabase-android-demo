package com.ericampire.demo.supabase.module

import com.ericampire.demo.supabase.UserViewModel
import com.ericampire.demo.supabase.datasource.UserDataSource
import com.ericampire.demo.supabase.repository.DefaultUserRepository
import com.ericampire.demo.supabase.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {

  @Provides
  fun provideUserViewModel(repo: DefaultUserRepository): UserViewModel {
    return UserViewModel(repo)
  }
}