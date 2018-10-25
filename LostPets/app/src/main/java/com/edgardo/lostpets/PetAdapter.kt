package com.edgardo.lostpets

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edgardo.database.Converters
import com.edgardo.database.Pet
import kotlinx.android.synthetic.main.row.view.*


class PetAdapter (val pets: List<Pet>, var listener: ((Pet) -> Unit)?, val context : Context) : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    lateinit var pet: Pet
    private var numberOfItems = pets.size

    override fun onCreateViewHolder(ViewGroup: ViewGroup, p1: Int): PetViewHolder {
        val bookViewHolder: PetViewHolder

        val rowView = LayoutInflater.from(ViewGroup.context).inflate(R.layout.row, ViewGroup, false)

        bookViewHolder = PetViewHolder(rowView)

        return bookViewHolder


    }

    override fun getItemCount(): Int {
        return numberOfItems
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        //Assign data

        fun bind(index: Int) {
            pet = pets[index]

            itemView.text_name.text = pet.Name
            val dogRace =  context.resources.getStringArray(R.array.races_array)[pet.Race]
            itemView.text_race.text = dogRace
            itemView.text_date_added.text = pet.DateFound
            itemView.text_location_found.text = pet.LocationFound
            itemView.image_pet.setImageBitmap(Converters.toBitmap(pet.ImagePet!!))

        }

        override fun onClick(p0: View?) {
            val pet = pets[adapterPosition]
            listener?.invoke(pet)
        }
    }

}