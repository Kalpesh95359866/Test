package com.example.practicalapp.fragment.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.practicalapp.R
import com.example.practicalapp.databinding.ItemviewHomeListBinding




class NewAdapter(var mapList: HashMap<String, String>) :
    RecyclerView.Adapter<NewAdapter.ViewHolder>() {
   // lateinit var listener: OnSelectEarning
   lateinit var bindind: ItemviewHomeListBinding
   lateinit var mContext:Context
    lateinit var listener: OnSelect

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext=parent.context
        bindind = ItemviewHomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bindind)
    }

    override fun getItemCount(): Int = mapList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(mapList)

        if(position==0)
        {
            bindind.cardHome.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.colorC58A72))
        }else if(position==1){
            bindind.cardHome.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.color2D31AC))
        }else
        {
            bindind.cardHome.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.color44AFE3))
        }
        holder.itemView.setOnClickListener {
            listener.click(position,mapList)

        }

    }
    inner class ViewHolder(bindind: ItemviewHomeListBinding) : RecyclerView.ViewHolder(bindind.root) {
        fun bind(mapList: HashMap<String, String>) {
            val key: String = mapList.keys.toString()
            val value: String? = mapList.get(key)

            bindind.tvTitle.text=mapList.getValue("Title")
            bindind.tvDiscription.text=mapList.getValue("Description")
            bindind.tvDays.text=mapList.getValue("Days")
            bindind.tvTime.text=mapList.getValue("Time")


        }


    }

    fun setClickListener(listener: OnSelect) {
        this.listener = listener
    }

    public interface OnSelect {
        public fun click(position: Int, items: HashMap<String, String>)
    }
}