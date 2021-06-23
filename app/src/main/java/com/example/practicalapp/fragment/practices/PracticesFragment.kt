package com.example.practicalapp.fragment.practices

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicalapp.R
import com.example.practicalapp.base.BaseFragment
import com.example.practicalapp.databinding.FragmentPracticesBinding
import com.example.practicalapp.fragment.popular.adapter.PopulerListAdapter
import com.example.practicalapp.fragment.practices.adapter.PrecticesAdapter
import com.example.practicalapp.model.PopulerListModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class PracticesFragment : BaseFragment(), View.OnClickListener, SeekBar.OnSeekBarChangeListener,
    MediaPlayer.OnCompletionListener {

    lateinit var binding: FragmentPracticesBinding

    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null

    var mapPopulerList1 = HashMap<String, String>()
    var mapPopulerList2 = HashMap<String, String>()
    var mapPopulerList3 = HashMap<String, String>()

    val arrayListMain = ArrayList<PopulerListModel>()

    private lateinit var databaseReference_Image: DatabaseReference
    private lateinit var databaseReference1: DatabaseReference

    var map = HashMap<String, String>()

    lateinit var sheetBehavior: BottomSheetBehavior<RelativeLayout>
    private lateinit var bottom_sheet: RelativeLayout

    private lateinit var mp: MediaPlayer
    private   val handler = Handler()


    private var currentSongIndex = 0
    private var progressStatus = 0

    private var isShuffle = false;
    private var isRepeat = false
    var ischeck: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPracticesBinding.inflate(layoutInflater)


        return binding.root // inflater.inflate(R.layout.fragment_practices, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // val rawUri =requireContext().getRawUri("myFile.filetype")

        initView()
        setHeader()
        setDialog()


        //set Time

        val currentTime: String = SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(Date())
        // Displaying time completed playing
        binding.bottomSheet.songCurrentDurationLabel.text = currentTime
        binding.bottomSheet.songTotalDurationLabel.text = "00:00"

    }


    @SuppressLint("RestrictedApi", "SetTextI18n")
    private fun setDialog() {
        bottom_sheet = binding.bottomSheet.dialogSelectColor
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        sheetBehavior.isHideable = false

        sheetBehavior.peekHeight = requireActivity().resources.getDimension(R.dimen._150sdp).toInt()


        // callback for do something
        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(@NonNull view: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(@NonNull view: View, v: Float) {

            }
        })
        // set listener on button click
        binding.bottomSheet.imgUpDown.setOnClickListener(View.OnClickListener {
            if (sheetBehavior.state !== BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)

            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)

            }
        })
        binding.bottomSheet.imgclose.setOnClickListener {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        }


        binding.bottomSheet.imgPlay.setOnClickListener {

            binding.bottomSheet.LLStop.visibility = View.VISIBLE
            binding.bottomSheet.LLPlay.visibility = View.GONE

            binding.bottomSheet.imgStop.setImageResource(R.drawable.ic_stop)

            mp = MediaPlayer()
            mp = MediaPlayer.create(requireContext(), R.raw.shanti);

            mp.setOnCompletionListener(this);

            if (mp.isPlaying()) {
                mp.pause();
                ischeck = true

            } else {
                // Resume song
                if (mp != null) {
                    ischeck = false
                    mp.start();
                    // Changing button image to pause button
                    //  binding.bottomSheet.imgPlay.setImageResource(R.drawable.ic_stop)

                    Thread {
                        while (progressStatus < 100) {
                            progressStatus += 1
                            // Update the progress bar and display the
                            //current value in the text view
                            handler.post(Runnable {
                                binding.bottomSheet.songProgressBar.setProgress(progressStatus)
                                binding.bottomSheet.songProgressBar.max
                                //textView.setText(progressStatus.toString() + "/" + progressBar.getMax())

                                val currentTime: String =
                                    SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(Date())
                                // Displaying time completed playing
                                binding.bottomSheet.songCurrentDurationLabel.text = currentTime


                            })
                            try {
                                val totalDuration = mp.duration.toLong()
                                // Sleep for 200 milliseconds

                                binding.bottomSheet.songTotalDurationLabel.setText(
                                    "" + milliSecondsToTimer(
                                        totalDuration
                                    )
                                )


                                Thread.sleep(totalDuration)
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }
                        }
                    }.start()


                }
            }
        }

        binding.bottomSheet.imgStop.setOnClickListener {

            binding.bottomSheet.imgPlay.setImageResource(R.drawable.ic_play)
            binding.bottomSheet.LLStop.visibility = View.GONE
            binding.bottomSheet.LLPlay.visibility = View.VISIBLE

            mp.stop()

        }

    }

    private fun playSong(currentSongIndex: Int) {
        updateProgressBar()

    }

    private fun initView() {
        getDataPopular()

    }

    private fun setHeader() {
        binding.toolbar.imgLeftIcon.visibility=View.INVISIBLE
        binding.toolbar.tvToolbarTitle.text = "Practices"
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgLeftIcon -> {
                baseActivity!!.onBackPressed()
            }
        }
    }

    private fun getDataPopular() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase!!.getReference("populer");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference1 = firebaseDatabase!!.getReference("PopulerList");


        databaseReference_Image = firebaseDatabase!!.getReference("Images");

        getData()
        getPrecticesData()


    }

    private fun getData() {
        showLoading()
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NewApi")
            override fun onDataChange(snapshot: DataSnapshot) {

                for (suppSnapshot in snapshot.child("Popular1").getChildren()) {
                    map.put(suppSnapshot.key.toString(), suppSnapshot.value.toString())
                }

                setPopularAdapter(map)

                hideLoading()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setPopularAdapter(popularMap: HashMap<String, String>) {
        binding.rvPrecties.setHasFixedSize(true)
        val adapter = PrecticesAdapter(popularMap)
        binding.rvPrecties.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        binding.rvPrecties.adapter = adapter
        binding.rvPrecties.setHasTransientState(true)

        //Onclick of Adapter With  Data
        adapter.listener = object : PrecticesAdapter.OnSelect {
            override fun click(position: Int, items: HashMap<String, String>) {

                //  replaceFragment(PopularFragment(),true)
            }

        }

    }

    private fun getPrecticesData() {
        showLoading()
        databaseReference1!!.addValueEventListener(object : ValueEventListener {
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
        binding.rvPrectiesList.adapter = adapter
        binding.rvPrectiesList.setHasFixedSize(true)
        binding.rvPrectiesList.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvPrectiesList.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        // binding.rvPopularList.setHasTransientState(true)
    }

    //Media Player
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {


    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        // mHandler.removeCallbacks(mUpdateTimeTask)
        val totalDuration = mp.duration
        val currentPosition: Int = progressToTimer(seekBar!!.progress, totalDuration)

        mp.seekTo(currentPosition)

        updateProgressBar()

    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (isRepeat) {
        } else if (isShuffle) {
            val rand = Random()
            currentSongIndex = rand.nextInt(-1 - 0 + 1) + 0
            playSong(currentSongIndex)
        } else {
            currentSongIndex = if (currentSongIndex < 1) {
                playSong(currentSongIndex + 1)
                currentSongIndex + 1
            } else {
                // play first song
                playSong(0)
                0
            }
        }
    }

 /*   override fun onDestroy() {
        super.onDestroy()
        if(mp!=null)
        {
            mp.release()
        }

    }*/

    fun updateProgressBar() {
    }

    fun progressToTimer(progress: Int, totalDuration: Int): Int {
        var totalDuration = totalDuration
        var currentDuration = 0
        totalDuration = (totalDuration / 1000)
        currentDuration = (progress.toDouble() / 100 * totalDuration).toInt()

        // return current duration in milliseconds
        return currentDuration * 1000
    }

    fun milliSecondsToTimer(milliseconds: Long): String? {
        var finalTimerString = ""
        var secondsString = ""

        // Convert total duration into time
        val hours = (milliseconds / (1000 * 60 * 60)).toInt()
        val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
        val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
        // Add hours if there
        if (hours > 0) {
            finalTimerString = "$hours:"
        }

        // Prepending 0 to seconds if it is one digit
        secondsString = if (seconds < 10) {
            "0$seconds"
        } else {
            "" + seconds
        }
        finalTimerString = "$finalTimerString$minutes:$secondsString"

        // return timer string
        return finalTimerString
    }



}