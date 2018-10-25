package com.edgardo.lostpets

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edgardo.database.Pet

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ItemFragment.OnListFragmentInteractionListener] interface.
 */
class PetListFragment : Fragment() {

    var onPetClick: ((Pet) -> Unit)? = null
        set(value) {
            field = value
            petAdapter?.listener = value
        }

    var pets: List<Pet>
        get() = petAdapter?.pets ?: emptyList()
        set(value) {
            val context = context

            if (context != null) {
                petAdapter = PetAdapter(value, petAdapter?.listener, context)

                with(view as RecyclerView) {
                    adapter = petAdapter
                }
            }
        }

    private var petAdapter: PetAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            pets = it.getParcelableArrayList(PET_LIST)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        val pets = pets

        petAdapter = PetAdapter(pets, onPetClick, context!!).apply {
            listener = onPetClick
        }

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = petAdapter
            }
        }

        return view
    }

    companion object {
        const val PET_LIST = "pet-list"

        @JvmStatic
        fun newInstance(pets: ArrayList<Pet>) =
            PetListFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(PET_LIST, pets)
                }
            }
    }
}
