package com.example.practicalapp.fragment.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicalapp.base.BaseFragment
import com.example.practicalapp.databinding.FragmentHomeBinding
import com.example.practicalapp.fragment.home.adapter.NewAdapter
import com.example.practicalapp.fragment.home.adapter.PopularAdapter
import com.example.practicalapp.fragment.popular.PopularFragment
import com.example.practicalapp.model.PopulerModel
import com.google.firebase.database.*
import com.google.firebase.database.ValueEventListener as DatabaseValueEventListener


class HomeFragment : BaseFragment() {

    lateinit var binding: FragmentHomeBinding

    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null


    private lateinit var databaseReference_Image: DatabaseReference

    var map = HashMap<String, String>()

    var arrayLis = ArrayList<PopulerModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root // inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        getDataPopular()
    }

    private fun setPopularAdapter(popularMap: HashMap<String, String>) {
        binding.rvPopular.setHasFixedSize(true)
        val adapter = PopularAdapter(popularMap)
        binding.rvPopular.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        binding.rvPopular.adapter = adapter

        //Onclick of Adapter With  Data
        adapter.listener=object : PopularAdapter.OnSelect{
            override fun click(position: Int, items: HashMap<String, String>) {

                replaceFragment(PopularFragment(),true)
            }

        }

    }

    private fun setNewAdapter(popularMap: HashMap<String, String>) {
        binding.rvNew.setHasFixedSize(true)
        val adapter = NewAdapter(popularMap)
        binding.rvNew.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        binding.rvNew.adapter = adapter

        //Onclick of Adapter With  Data
        adapter.listener=object : NewAdapter.OnSelect{
            override fun click(position: Int, items: HashMap<String, String>) {

                replaceFragment(PopularFragment(),true)
            }

        }
    }

    private fun getDataPopular() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase!!.getReference("populer");

        databaseReference_Image = firebaseDatabase!!.getReference("Images");

        getData()
        getImage()

    }
    private fun getData() {
        showLoading()
        databaseReference!!.addValueEventListener(object : DatabaseValueEventListener {
            @SuppressLint("NewApi")
            override fun onDataChange(snapshot: DataSnapshot) {

                for (suppSnapshot in snapshot.child("Popular1").getChildren()) {
                    map.put(suppSnapshot.key.toString(), suppSnapshot.value.toString())
                }

                setPopularAdapter(map)
                setNewAdapter(map)
                hideLoading()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getImage() {
        databaseReference_Image.addValueEventListener(object : DatabaseValueEventListener {
            override fun onDataChange(snapshots: DataSnapshot) {
                Log.d("Iamges", snapshots.key.toString())
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }




}


