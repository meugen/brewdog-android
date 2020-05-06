package com.brewdog.android.model.repositories

sealed class RepositoryResult<T> {
    class Success<T>(val data: T): RepositoryResult<T>()
    class Error<T>(val error: Throwable): RepositoryResult<T>()
    class Loading<T>(val data: T?): RepositoryResult<T>()
}