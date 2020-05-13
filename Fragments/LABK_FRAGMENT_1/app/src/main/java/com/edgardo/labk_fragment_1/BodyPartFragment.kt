package com.edgardo.labk_fragment_1


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_body_part.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BodyPartFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class BodyPartFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var bodyPartList: List<Int>? = null
    var index: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bodyPartList = it.getIntegerArrayList(IMAGE_LIST_LABEL)
            index = it.getInt(INDEX_LABEL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_body_part, container,false)

        if(bodyPartList != null){
            if (rootView.image_body_part != null){
                rootView.image_body_part.setImageResource(bodyPartList!![index])
                rootView.image_body_part.setOnClickListener(this)
            }
        }

        return rootView
    }

    override fun onClick(v: View?) {
        if (index < bodyPartList?.size!! - 1){
            index++
        }else{
            index = 0
        }
        view?.image_body_part?.setImageResource(bodyPartList!![index])
    }


    companion object {
        const val IMAGE_LIST_LABEL = "images"
        const val INDEX_LABEL = "index"

        @JvmStatic
        fun newInstance(bodyPartList: List<Int>, index: Int) =
                BodyPartFragment().apply {
                    arguments = Bundle().apply {
                        bodyPartList as ArrayList<Int>
                        putIntegerArrayList(Companion.IMAGE_LIST_LABEL, bodyPartList)
                        putInt(Companion.INDEX_LABEL, index)
                    }
                }
    }
}
