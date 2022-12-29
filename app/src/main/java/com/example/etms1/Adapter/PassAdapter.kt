package com.example.etms1.Adapter

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.etms1.Models.Pass
import com.example.etms1.Models.PassViewModel
import com.example.etms1.R

private lateinit var viewModel: PassViewModel

class PassAdapter(val context: Context,val listener: PassItemClickListener) : RecyclerView.Adapter<PassAdapter.PassViewHolder>() {

    val passList = ArrayList<Pass>()


    inner class PassViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val passLayout = itemView.findViewById<CardView>(R.id.list_Item1)!!
        val from: TextView = itemView.findViewById<TextView>(R.id.list_from)
        val too: TextView = itemView.findViewById<TextView>(R.id.list_to)
        val startDate: TextView = itemView.findViewById<TextView>(R.id.list_startDate)
        val endDate: TextView = itemView.findViewById<TextView>(R.id.list_endDate)
        val pickuptime: TextView = itemView.findViewById(R.id.list_pickuptime)
        val droptime: TextView = itemView.findViewById(R.id.list_dropTime)
        val stopName: TextView = itemView.findViewById(R.id.list_busStop)
        val delete_btn: Button = itemView.findViewById(R.id.list_delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassViewHolder {

           val viewholder = PassViewHolder( LayoutInflater.from(context).inflate(R.layout.list_item1,parent,false))
            return viewholder

    }

    override fun onBindViewHolder(holder: PassViewHolder, position: Int) {
        val currentPass = passList[position]
        holder.from.text = currentPass.from
        holder.too.text = currentPass.to
        holder.startDate.text = currentPass.start_date
        holder.endDate.text = currentPass.end_date
        holder.stopName.text = currentPass.from
        holder.pickuptime.text = currentPass.Pickup
        holder.droptime.text = currentPass.Drop
        holder.passLayout.setOnClickListener{
            listener.onItemClicked(passList[holder.adapterPosition])
        }
        holder.passLayout.setOnLongClickListener {
            listener.onLongItemClicked(passList[holder.adapterPosition],holder.passLayout)
            true
        }

        holder.delete_btn.setOnClickListener {
                removeAt(position)
        }


    }
    private fun removeAt(position: Int) {
        passList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, passList.size)
        notifyDataSetChanged()
    }

    fun updateList(newList: ArrayList<Pass>){
        passList.clear()
        passList.addAll(newList)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return passList.size

    }


    interface OnClickListener {
        fun onClick(position: Int)

    }


    interface PassItemClickListener{

        fun onItemClicked(pass: Pass)
        fun onLongItemClicked(pass: Pass,cardView: CardView)
        fun onBtnClicked(pass: Pass)
    }
}


