package m7mdra.com.htmlrecycler.source

import android.os.HandlerThread
import android.os.Looper
import android.os.NetworkOnMainThreadException
import android.support.annotation.WorkerThread
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.logging.Handler

@WorkerThread
class NetworkSource(private val url: String) : Source {
    override fun get(): Document {
        if (Looper.getMainLooper().thread== Thread.currentThread())
            throw Exception("'NetworkSource' should be called of Main Thread (UI Thread)")
        return Jsoup.connect(url).get()
    }
}