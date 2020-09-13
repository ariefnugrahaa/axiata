package works.newsapi.arief.dependencies

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.grapesnberries.curllogger.CurlLoggerInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import works.newsapi.arief.common.extension.enableTls12OnPreLollipop
import works.newsapi.arief.common.utils.Logger
import works.newsapi.arief.data.server.ApiService
import works.newsapi.arief.data.server.ApiServiceManager
import works.newsapi.arief.BuildConfig
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class AriefNetworkModule {

    @Singleton
    @Provides
    fun provideApiServiceManager(apiService: ApiService): ApiServiceManager {
        return ApiServiceManager(apiService)
    }

    @Singleton
    @Provides
    fun provideApiService(@Named("retrofit_api") retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    @Named("retrofit_api")
    fun provideServerRetrofit(okHttpClient: OkHttpClient,
                              @Named("base_url") baseUrl: String,
                              callAdapterFactory: CallAdapter.Factory,
                              @Named("gson_converter") gsonConverter: Converter.Factory,
                              @Named("any_on_empty_converter") anyOnEmptyConverter: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(anyOnEmptyConverter)
            .addConverterFactory(gsonConverter)
            .build()
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(@Named("header_interceptor") headerInterceptor: Interceptor,
                            @Named("http_logging_interceptor") loggingInterceptor: Interceptor,
                            chuckInterceptor: ChuckInterceptor,
                            curlLoggerInterceptor: CurlLoggerInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(headerInterceptor)
            addInterceptor(loggingInterceptor)
            addInterceptor(curlLoggerInterceptor)
            if (BuildConfig.DEBUG) {
                addInterceptor(chuckInterceptor)
            }
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            enableTls12OnPreLollipop()
        }.build()
    }

    @Singleton
    @Provides
    @Named("header_interceptor")
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val headerInterceptedRequest = request.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("X-Api-Key", "784d0bccae2e4ad3b227f52e60f69926")
                .method(request.method(), request.body())
                .build()

            chain.proceed(headerInterceptedRequest)
        }
    }

    @Singleton
    @Provides
    fun provideChuckInterceptor(context: Context): ChuckInterceptor {
        return ChuckInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideCurlLoggerInterceptor(): CurlLoggerInterceptor {
        return CurlLoggerInterceptor()
    }

    @Singleton
    @Provides
    @Named("http_logging_interceptor")
    fun provideHttpLoggingInterceptor(@Named("log_enabled") logEnabled: Boolean): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (logEnabled) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }

    @Provides
    @Named("log_enabled")
    fun provideLogEnabled(): Boolean {
        return Logger.logEnabled
    }

    @Provides
    @Named("base_url")
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @Singleton
    @Provides
    fun provideCallAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    @Singleton
    @Provides
    @Named("gson_converter")
    fun provideConverterFactory(@Named("lower_case_with_underscores_gson") gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    @Named("lower_case_with_underscores_gson")
    fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    @Singleton
    @Provides
    @Named("any_on_empty_converter")
    fun provideAnyOnEmptyConverter(): Converter.Factory {
        return object : Converter.Factory() {
            override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, Any> {
                val delegate: Converter<ResponseBody, Any> = retrofit.nextResponseBodyConverter(this, type, annotations)
                return Converter { body ->
                    if (body.contentLength() == 0L) return@Converter null
                    delegate.convert(body)
                }
            }
        }
    }
}