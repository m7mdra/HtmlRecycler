package m7mdra.com.htmlrecyclerview

import android.annotation.SuppressLint
import android.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.accessibility.AccessibilityRecordCompat.setSource
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import m7mdra.com.htmlrecycler.*
import m7mdra.com.htmlrecycler.adapter.DefaultElementsAdapter
import m7mdra.com.htmlrecycler.elements.ImageElement
import m7mdra.com.htmlrecycler.source.NetworkSource
import org.jsoup.nodes.Document


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val networkSource = NetworkSource("https://gist.githubusercontent.com/m7mdra/f22c62bc6941e08064b4fbceb4832a90/raw/ea8574d986635cf214541f1f5702ef37cc731aaf/article.html")

        HtmlRecycler.Builder(this@MainActivity)
                .setSource(networkSource)
                .setAdapter(DefaultElementsAdapter(this@MainActivity) { element, i, view ->

                    Toast.makeText(this, "you clicked ${element::class.java.simpleName} element", Toast.LENGTH_SHORT).show()
                    if (element is ImageElement)
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.fragment_layout, ImageFragment.newInstance(element.ImageUrl))
                            setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            commit()
                            addToBackStack("")
                        }
                }).setRecyclerView(recyclerView)
                .setLoadingCallback(object : HtmlRecycler.LoadCallback {
                    override fun onLoadingStart() {
                        progressBar.visibility = View.VISIBLE
                    }

                    override fun onLoaded(document: Document?) {
                        progressBar.visibility = View.GONE

                    }
                })
                .build()

    }


}
