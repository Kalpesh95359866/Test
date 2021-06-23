package com.example.practicalapp.fragment.popular

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.practicalapp.R
import com.example.practicalapp.base.BaseFragment
import com.example.practicalapp.databinding.FragmentPopularBinding
import com.example.practicalapp.fragment.popular.adapter.PopulerListAdapter
import com.example.practicalapp.model.PopulerListModel
import com.google.firebase.database.*


class PopularFragment : BaseFragment(), View.OnClickListener {

    lateinit var binding: FragmentPopularBinding

    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null

    var mapPopulerList1 = HashMap<String, String>()
    var mapPopulerList2 = HashMap<String, String>()
    var mapPopulerList3 = HashMap<String, String>()

    val arrayListMain=ArrayList<PopulerListModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPopularBinding.inflate(layoutInflater)

        return binding.root // inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setHeader()

    }

    private fun initView() {


        getDataPopular()
    }

    private fun getDataPopular() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase!!.getReference("PopulerList");

        getData()


    }
    private fun setHeader() {
        binding.toolbar.imgLeftIcon.setOnClickListener(this)
       binding.toolbar.tvToolbarTitle.text="Populer"
    }

    override fun onClick(v: View?) {
       when(v!!.id)
       {
           R.id.imgLeftIcon->
            baseActivity!!.onBackPressed()

       }
    }
    private fun getData() {
        showLoading()
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NewApi")
            override fun onDataChange(snapshot: DataSnapshot) {
                for (suppSnapshot in snapshot.child("PopulerName1").getChildren()) {
                    mapPopulerList1.put(suppSnapshot.key.toString(), suppSnapshot.value.toString())

                }
                for (suppSnapshot in snapshot.child("PopulerName2").getChildren()) {
                    mapPopulerList2.put(suppSnapshot.key.toString(), suppSnapshot.value.toString())
                }
                for (suppSnapshot in snapshot.child("PopulerName3").getChildren()) {
                    mapPopulerList3.put(suppSnapshot.key.toString(), suppSnapshot.value.toString())

                }

              /*  mapPopulerMainList.put("PopulerName1", mapPopulerList1.toString());
                mapPopulerMainList.put("PopulerName2", mapPopulerList2.toString());
                mapPopulerMainList.put("PopulerName3", mapPopulerList3.toString());
*/

                val iterator1: Iterator<Map.Entry<String, Any>> = mapPopulerList1.entries.iterator()
                while (iterator1.hasNext()) {
                    val getkey = iterator1.next()
                    val getvalue = getkey.value
                    arrayListMain.add(PopulerListModel(getkey, getvalue as String))
                }

                val iterator2: Iterator<Map.Entry<String, Any>> = mapPopulerList2.entries.iterator()
                while (iterator2.hasNext()) {
                    val getkey = iterator2.next()
                    val getvalue = getkey.value
                    arrayListMain.add(PopulerListModel(getkey, getvalue as String))
                }

                val iterator3: Iterator<Map.Entry<String, Any>> = mapPopulerList3.entries.iterator()
                while (iterator3.hasNext()) {
                    val getkey = iterator3.next()
                    val getvalue = getkey.value
                    arrayListMain.add(PopulerListModel(getkey, getvalue as String))
                }
                setPopularListAdapter(arrayListMain)
                hideLoading()

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setPopularListAdapter(popularListMap: ArrayList<PopulerListModel>) {

        val adapter = PopulerListAdapter(popularListMap)
        binding.rvPopularList.adapter = adapter
        binding.rvPopularList.setHasFixedSize(true)
        binding.rvPopularList.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
       // binding.rvPopularList.setHasTransientState(true)
    }



}

