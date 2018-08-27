package m7mdra.com.htmlrecyclerview


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image.*


class ImageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get().load(arguments?.getString(IMAGE)).into(image)

    }

    companion object {
        const val IMAGE = "img"
        @JvmStatic
        fun newInstance(imageUrl: String): ImageFragment {
            val imageFragment = ImageFragment()
            val bundle = Bundle()
            bundle.putString(IMAGE, imageUrl)
            imageFragment.arguments = bundle
            return imageFragment
        }
    }

}
