package com.example.core.data.remote

import com.example.core.util.Result
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

internal class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, Result<T, Exception>>(proxy) {

    override fun enqueueImpl(callback: Callback<Result<T, Exception>>) {
        proxy.enqueue(ResultCallback(this, callback))
    }

    override fun cloneImpl(): ResultCall<T> {
        return ResultCall(proxy.clone())
    }

    private class ResultCallback<T>(
        private val proxy: ResultCall<T>,
        private val callback: Callback<Result<T, Exception>>
    ) : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val result: Result<T, Exception> = if (response.isSuccessful) {
                Result.Success(
                    value = response.body() as T
                )
            } else {
                Result.Failure(
                    error = HttpException(response)
                )
            }
            callback.onResponse(proxy, Response.success(result))
        }

        override fun onFailure(call: Call<T>, error: Throwable) {
            val result = when (error) {
                is HttpException -> Result.Failure(error = error)
                is IOException -> Result.Failure(error = error)

                else -> Result.Failure(error = Exception(null, error))
            }
            callback.onResponse(proxy, Response.success(result))
        }
    }

    override fun timeout(): Timeout {
        return proxy.timeout()
    }

}