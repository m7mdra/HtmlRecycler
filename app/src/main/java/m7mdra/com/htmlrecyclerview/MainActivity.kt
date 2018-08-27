package m7mdra.com.htmlrecyclerview

import android.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import m7mdra.com.htmlrecycler.*
import m7mdra.com.htmlrecycler.adapter.DefaultElementsAdapter
import m7mdra.com.htmlrecycler.elements.ImageElement
import m7mdra.com.htmlrecycler.source.StringSource

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        HtmlRecycler.Builder(this)
                .setSource(StringSource(Data.data))
                .setAdapter(DefaultElementsAdapter(this) { element, i, view ->
                    if (element is ImageElement)
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.fragment_layout, ImageFragment.newInstance(element.ImageUrl))
                            setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            commit()
                            addToBackStack("")
                        }
                })
                .setRecyclerView(recyclerView)
                .build()
    }


}
