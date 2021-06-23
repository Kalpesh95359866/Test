package com.example.practicalapp.fragment.popular.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.practicalapp.R
import com.example.practicalapp.databinding.ItemviewPopuerListBinding
import com.example.practicalapp.model.PopulerListModel


class PopulerListAdapter(val popularListMap: ArrayList<PopulerListModel>) :
    RecyclerView.Adapter<PopulerListAdapter.ViewHolder>() {

    lateinit var bindind: ItemviewPopuerListBinding
    lateinit var mContext: Context

    var pos=0
    // lateinit var listener: OnSelectEarning

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        bindind = ItemviewPopuerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(bindind)
    }

    override fun getItemCount(): Int = popularListMap.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(popularListMap[position])

        if (position == 0) {
            bindind.imageIcon.setBackgroundResource(R.drawable.ic_populer1);
            bindind.imgFavorite.setImageResource(R.drawable.ic_like1)
        } else if (position == 1) {

            bindind.imageIcon.setBackgroundResource(R.drawable.ic_populer3);
        } else if (position == 2) {
            bindind.imageIcon.setBackgroundResource(R.drawable.ic_populer1);

        } else if (position == 3) {
            bindind.imageIcon.setBackgroundResource(R.drawable.ic_populer4);
        }else{
            bindind.imageIcon.setBackgroundResource(R.drawable.ic_populer3);
        }

        holder.itemView.setOnClickListener {
         /*   pos=position
            if(pos==0) {
                bindind.imgFavorite.setImageResource(R.drawable.ic_like1)
                notifyItemChanged(position)
            }else{
                bindind.imgFavorite.setImageResource(R.drawable.ic_like)
                notifyItemChanged(position)
            }*/



        }


    }

    inner class ViewHolder(bindind: ItemviewPopuerListBinding) :
        RecyclerView.ViewHolder(bindind.root) {
        fun bind(populerListModel: PopulerListModel) {

            if (populerListModel.equals("Title")) {
                bindind.tvTitle.text = populerListModel.Title.toString()
                bindind.tvDiscription.text = populerListModel.Description

            } else {
                bindind.tvDiscription.text = populerListModel.Description
            }


        }

    }

    /* fun setClickListenerEarning(listener: OnSelectEarning) {
         this.listener = listener
     }

     public interface OnSelectEarning {
         public fun clickAtEarning(position: Int, get: EarningHistoryResponse.Payload.Log)
     }*/
}


