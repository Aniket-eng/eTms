package com.example.etms1.Adapter

import android.content.Context
import android.location.GnssAntennaInfo.Listener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.etms1.MainActivity
import com.example.etms1.Models.Pass
import com.example.etms1.R

class PassAdapter(private val context: Context, private val listener: Listener) : RecyclerView.Adapter<PassAdapter.PassViewHolder>() {

    private val passList = ArrayList<Pass>()
    private val  fullList = ArrayList<Pass>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassViewHolder {
        return PassViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item1,parent,false)
        )
    }

    override fun onBindViewHolder(holder: PassViewHolder, position: Int) {
        val currentPass = passList[position]
        holder.from.text = currentPass.from
        holder.too.text = currentPass.to
        holder.startDate.text = currentPass.start_date
        holder.endDate.text = currentPass.end_date
       // holder.pickup.text = currentPass.Pickup
       // holder.drop.text = currentPass.Drop
        holder.stopName.text = currentPass.from
        holder.passLayout.setOnClickListener{
            listener.onItemClicked(passList[holder.adapterPosition])
        }
        holder.passLayout.setOnLongClickListener {
            listener.onLongItemClicked(passList[holder.adapterPosition],holder.passLayout)
            true
        }
    }

    override fun getItemCount(): Int {
        return passList.size
    }

    inner class PassViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val passLayout = itemView.findViewById<CardView>(R.id.list_Item1)!!
        val from: TextView = itemView.findViewById<TextView>(R.id.list_from)
        val too: TextView = itemView.findViewById<TextView>(R.id.list_to)
        val startDate: TextView = itemView.findViewById<TextView>(R.id.list_startDate)
        val endDate: TextView = itemView.findViewById<TextView>(R.id.list_endDate)
       // val pickup: TextView = itemView.findViewById<TextView>(R.id.list_pickup)
       //val drop: TextView = itemView.findViewById<TextView>(R.id.list_drop)
        val stopName: TextView = itemView.findViewById(R.id.list_busStop)

    }

    interface PassItemClickListener{

        fun onItemClicked(pass: Pass)
        fun onLongItemClicked(pass: Pass,cardView: CardView)
    }
}


