package com.example.cubit.di

import android.util.Patterns
import com.example.core.util.InputLinter
import com.example.feature_auth.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InputLinterModule {

    @Provides
    @Singleton
    @Named("emailLinter")
    fun provideEmailLinter() = InputLinter()
        .addRule(errorMessage = R.string.invalid_email_format) { input ->
            Patterns.EMAIL_ADDRESS.matcher(input).matches()
        }

    @Provides
    @Singleton
    @Named("passwordLinter")
    fun providePasswordLinter() = InputLinter()
        .addRule(errorMessage = R.string.short_password) { input -> input.length > 6}

}