package com.example.practicalapp.fragment.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practicalapp.R
import android.os.Bundle
import android.util.Log
import com.example.practicalapp.base.BaseFragment
import com.example.practicalapp.databinding.FragmentProfileBinding
import com.example.practicalapp.fragment.profile.adapter.ProfileAdapter
import com.example.practicalapp.fragment.profile.model.ProfileModel
import com.example.practicalapp.util.ImagePicker
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.squareup.picasso.Picasso
import java.io.File
import kotlin.collections.ArrayList


class ProfileFragment : BaseFragment(), View.OnClickListener {

    lateinit var binding: FragmentProfileBinding
    val arrayList = ArrayList<ProfileModel>()

    lateinit var adapter: ProfileAdapter

    lateinit var picker: ImagePicker
    var filePathUrl = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)

        return binding.root // inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        picker = ImagePicker()

        setOnclickLis()
        setAdapter()
        barChart()

        picker.setListener { imagePath, uri ->
            showLoading()

            val file = File(imagePath!!)
            val compressFile = picker.compressImage(file.absolutePath)
            Picasso.with(context).load(uri).into(binding.imgProfile);
            //   setAllPermission(compressFile)
            hideLoading()
        }

    }

    private fun barChart() {
        val barChar1: ArrayList<BarEntry> = ArrayList()
        barChar1.add(BarEntry(8f, 0f))
        barChar1.add(BarEntry(2f, 1f))
        barChar1.add(BarEntry(5f, 2f))
        barChar1.add(BarEntry(20f, 3f))
        barChar1.add(BarEntry(15f, 4f))
        barChar1.add(BarEntry(19f, 5f))
        barChar1.add(BarEntry(8f, 6f))
        barChar1.add(BarEntry(2f, 7f))
        barChar1.add(BarEntry(5f, 8f))
        barChar1.add(BarEntry(20f, 9f))
        barChar1.add(BarEntry(15f, 10f))


        val barChar2: ArrayList<BarEntry> = ArrayList()
        barChar2.add(BarEntry(8f, 0f))
        barChar2.add(BarEntry(2f, 1f))
        barChar2.add(BarEntry(5f, 2f))
        barChar2.add(BarEntry(20f, 3f))
        barChar2.add(BarEntry(15f, 4f))
        barChar2.add(BarEntry(19f, 5f))
        barChar2.add(BarEntry(8f, 6f))
        barChar2.add(BarEntry(2f, 7f))
        barChar2.add(BarEntry(5f, 8f))
        barChar2.add(BarEntry(20f, 9f))
        barChar2.add(BarEntry(15f, 10f))


        val barDataSet1 = BarDataSet(barChar1, "Practices")
        barDataSet1.color = getResources().getColor(R.color.color2D31AC);

        val barDataSet2 = BarDataSet(barChar2, "Meditations")
        barDataSet2.color = getResources().getColor(R.color.colorFB9B9C)

        val months = arrayOf(
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
        )
        val data = BarData(barDataSet1, barDataSet2)
        binding.barChart.setData(data)

        val xAxis: XAxis = binding.barChart.getXAxis()
        xAxis.valueFormatter = IndexAxisValueFormatter(months)
        binding.barChart.getAxisLeft().setAxisMinimum(0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularityEnabled(true);
        binding.barChart.getAxisLeft().setDrawGridLines(false);
        binding.barChart.getXAxis().setDrawGridLines(false)


    }

    private fun setAdapter() {
        arrayList.clear()
        arrayList.add(ProfileModel("Practices", "13", "4:23:04"))
        arrayList.add(ProfileModel("Meditations", "6", "0:55:04"))


        adapter = ProfileAdapter(arrayList)
        binding.rvProfile.adapter = adapter
    }

    private fun setOnclickLis() {
        binding.imgProfile.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgProfile -> {
                picker.show(baseActivity!!.supportFragmentManager, "")
            }
        }


    }
}