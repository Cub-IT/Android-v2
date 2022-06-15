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
        .addRule(errorMessage = R.string.invalid_email_format) { input -> Patterns.EMAIL_ADDRESS.matcher(input).matches() }
        .addRule(errorMessage = R.string.long_email) { input -> input.length < 45 }

    @Provides
    @Singleton
    @Named("passwordLinter")
    fun providePasswordLinter() = InputLinter()
        .addRule(errorMessage = R.string.short_password) { input -> input.length > 5 }
        .addRule(errorMessage = R.string.long_password) { input -> input.length < 45 }
        .addRule(errorMessage = R.string.password_error_uppercase) { input -> input.hasUpperCase() }
        .addRule(errorMessage = R.string.password_error_number) { input -> input.hasDigit() }
        .addRule(errorMessage = R.string.password_error_lowercase) { input -> input.hasLowerCase() }
        .addRule(errorMessage = R.string.password_error_prohibited) { input -> input.doesNotContain("\"\'.,") }

    @Provides
    @Singleton
    @Named("nameLinter")
    fun provideNameLinter() = InputLinter()
        .addRule(errorMessage = R.string.short_name) { input -> input.isNotEmpty() }
        .addRule(errorMessage = R.string.long_name) { input -> input.length < 45 }

    private fun String.hasDigit() = this.any { it.isDigit() }

    private fun String.hasUpperCase() = this.any { it.isUpperCase() }

    private fun String.hasLowerCase() = this.any { it.isLowerCase() }

    private fun String.isFitted() = this.all { it.isLetterOrDigit() or "".contains(it) }

    private fun String.doesNotContain(prohibited: String) = this.none { prohibited.contains(it) }

}