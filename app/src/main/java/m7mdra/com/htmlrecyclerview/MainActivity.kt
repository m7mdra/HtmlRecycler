package m7mdra.com.htmlrecyclerview

import android.annotation.SuppressLint
import android.app.FragmentTransaction
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import m7mdra.com.htmlrecycler.*
import m7mdra.com.htmlrecycler.adapter.DefaultElementsAdapter
import m7mdra.com.htmlrecycler.elements.ImageElement
import m7mdra.com.htmlrecycler.source.NetworkSource
import m7mdra.com.htmlrecycler.source.Source
import m7mdra.com.htmlrecycler.source.StringSource
import org.jsoup.nodes.Document

class MainActivity : AppCompatActivity() {

    @SuppressLint("StaticFieldLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        object : AsyncTask<Unit, Unit, Document>() {
            override fun doInBackground(vararg params: Unit?): Document {
                return StringSource(Data.data2).get()

            }

            override fun onPostExecute(result: Document) {
                super.onPostExecute(result)
                HtmlRecycler.Builder(this@MainActivity)
                        .setSource(object:Source{
                            override fun get(): Document {
                                return result
                            }
                        })
                        .setAdapter(DefaultElementsAdapter(this@MainActivity) { element, i, view ->
                            if (element is ImageElement)
                                supportFragmentManager.beginTransaction().apply {
                                    replace(R.id.fragment_layout, ImageFragment.newInstance(element.ImageUrl))
                                    setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    commit()
                                    addToBackStack("")
                                }
                        }).setRecyclerView(recyclerView)
                        .build()
            }

        }.execute().get()

    }


}
