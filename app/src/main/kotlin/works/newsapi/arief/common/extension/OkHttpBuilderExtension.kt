package works.newsapi.arief.common.extension

import android.os.Build
import works.newsapi.arief.data.server.tls.Tls12SocketFactory
import works.newsapi.arief.common.utils.Logger
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import java.security.KeyStore
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

fun OkHttpClient.Builder.enableTls12OnPreLollipop(): OkHttpClient.Builder {
    if (Build.VERSION.SDK_INT in Build.VERSION_CODES.JELLY_BEAN..Build.VERSION_CODES.LOLLIPOP) {
        try {
            val sslContext = SSLContext.getInstance("TLSv1.2")
            sslContext.init(null, null, null)
            this.sslSocketFactory(Tls12SocketFactory(sslContext.socketFactory), getTrustManager())

            val connectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .build()

            val specs = mutableListOf<ConnectionSpec>()
            specs.add(connectionSpec)
            specs.add(ConnectionSpec.COMPATIBLE_TLS)
            specs.add(ConnectionSpec.CLEARTEXT)

            this.connectionSpecs(specs)
        } catch (e: Exception) {
            Logger.error("OkHttpTLSCompat", "Error while setting TLS 1.2", e)
        }
    }

    return this
}

private fun OkHttpClient.Builder.getTrustManager(): X509TrustManager {
    val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    trustManagerFactory.init(null as KeyStore?)
    val trustManagers = trustManagerFactory.trustManagers
    if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
        throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
    }
    return trustManagers[0] as X509TrustManager
}