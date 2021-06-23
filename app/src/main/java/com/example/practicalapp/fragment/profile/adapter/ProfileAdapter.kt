package com.example.practicalapp.fragment.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.practicalapp.R
import com.example.practicalapp.databinding.ItemviewProfileBinding
import com.example.practicalapp.fragment.profile.model.ProfileModel


class ProfileAdapter(var arrayList: ArrayList<ProfileModel>) :
    RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

   lateinit var bindind: ItemviewProfileBinding
    lateinit var mContext: Context
    lateinit var listener: OnSelect

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext=parent.context
        bindind = ItemviewProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bindind.root)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(arrayList[position])

        if(position==0)
        {
            bindind.imgFavorite.setImageResource(R.drawable.ic_book)
        }else if(position==1){
            bindind.imgFavorite.setImageResource(R.drawable.ic_meditations)
        }else
        {
            bindind.imgFavorite.setImageResource(R.drawable.ic_book)

        }

        holder.itemView.setOnClickListener {

        }

    }
    inner class ViewHolder(bindind: FrameLayout) : RecyclerView.ViewHolder(bindind.rootView) {
        fun bind(items: ProfileModel) {

            bindind.tvTitle.text=items.title
            bindind.tvSession  .text=items.session
            bindind.tvTime.text=items.strTime




        }
    }

    fun setClickListener(listener: OnSelect) {
        this.listener = listener
    }

    public interface OnSelect {
        public fun click(position: Int, items: HashMap<String, String>)
    }
}