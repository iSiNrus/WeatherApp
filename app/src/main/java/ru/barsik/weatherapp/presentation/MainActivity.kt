package ru.barsik.weatherapp.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import ru.barsik.weatherapp.R
import ru.barsik.weatherapp.databinding.ActivityMainBinding
import ru.barsik.weatherapp.di.component.ViewModelComponent
import ru.barsik.weatherapp.domain.MainViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    var viewModel: MainViewModel? = null
        @Inject set

    private var locationsArray = ArrayList<String>()

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        Log.d(TAG, "Comes result: ")
        if (it.resultCode == Activity.RESULT_OK) {
            Snackbar.make(binding.root, "Сохранено", Snackbar.LENGTH_SHORT).show()
            viewModel?.getLocations()
        } else {
            Snackbar.make(binding.root, "Ошибка сохранения", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState: spinner: ${binding.spinner.selectedItemPosition}")
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (viewModel?.getSelectedLocationPos() != null)
            binding.spinner.setSelection(viewModel?.getSelectedLocationPos()!!)
        Log.d(TAG, "onRestoreInstanceState: spinner ${binding.spinner.selectedItemPosition}")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnAddLocation.setOnClickListener {
            getResult.launch(Intent(this, AddLocationActivity::class.java))
        }
        binding.spinner.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_2,
            android.R.id.text1,
            locationsArray
        )
        viewModel?.getLocationsLD()?.observe(this) { res ->
            locationsArray.clear()
            if (res.size != 0) {
                res.forEach {
                    locationsArray.add(it.toString())
                }
                (binding.spinner.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                Log.d(TAG, "locat observe: ${binding.spinner.selectedItemPosition}")
                binding.emptyScreen.visibility = View.GONE
                binding.mainContent.visibility = View.VISIBLE
                binding.spinner.isEnabled = true
                if (binding.spinner.selectedItemPosition == -1) {
                    binding.emptyScreen.visibility = View.VISIBLE
                    binding.mainContent.visibility = View.GONE
                } else {
                    binding.emptyScreen.visibility = View.GONE
                    binding.mainContent.visibility = View.VISIBLE
                    if (viewModel?.getSelectedLocation() == null) {
                        viewModel?.setSelectedLocation(binding.spinner.selectedItemPosition)
                    } else viewModel?.getOneReqWeatherLD()
                }
            } else {
                Log.d(TAG, "locations: empty")
                binding.emptyScreen.visibility = View.VISIBLE
                binding.mainContent.visibility = View.GONE
                binding.processBarContainer.visibility = View.GONE
                (binding.spinner.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                binding.spinner.isEnabled = false
            }
        }

        viewModel?.getCurrentWeatherLD()?.observe(this) {
            binding.processBarContainer.visibility = View.GONE
            binding.emptyScreen.visibility = View.GONE
            binding.mainContent.visibility = View.VISIBLE

            binding.tvTempNow.text = it.main?.temp.toString() + "˚C"
            binding.tvTempDayNow.text =
                it.main?.temp_max.toString() + "˚C / " + it.main?.temp_min.toString() + "˚C"
            binding.tvStateNow.text = it.weather?.get(0)?.main ?: "Unknown"
        }
        viewModel?.getOneReqWeatherLD()?.observe(this) {
            binding.tvLocation.text = viewModel?.getSelectedLocation().toString()
            TimeZone.setDefault(TimeZone.getTimeZone(it.timezone))
            val inst = Instant.ofEpochSecond((it.current?.dt ?: 0))
            val calendar = Date.from(inst)
            val dateFormatter = SimpleDateFormat("dd MMM, EEE", Locale("ru"))
            Log.d(TAG, "date: ${dateFormatter.format(calendar)}")
            binding.tvDateNow.text = dateFormatter.format(calendar)
            binding.ivIcon.setImageBitmap(
                BitmapFactory.decodeStream(
                    resources.assets.open("icons/" + it.current?.weather?.get(0)?.icon + "@2x.png")
                )
            )
            binding.tvTempNow.text = it.current?.temp?.roundToInt().toString() + "˚C"
            binding.tvTempDayNow.text =
                it.daily?.get(0)?.temp?.min?.roundToInt().toString() + "˚C / " +
                        it.daily?.get(0)?.temp?.max?.roundToInt().toString() + "˚C"
            binding.tvStateNow.text = it.current?.weather?.get(0)?.description ?: "Unknown"

            binding.tvTempFeel.text = it.current?.feels_like?.roundToInt().toString() + "˚C"
            binding.tvWet.text = it.current?.humidity.toString() + "%"

            binding.tvWindSpeed.text = it.current?.wind_speed.toString() + " км/ч"
            binding.tvWindVector.text = when (it.current?.wind_deg) {
                in 0..22 -> {
                    "Северный"
                } //nord
                in 23..67 -> {
                    "Северо-восточный"
                } //nord - ost
                in 68..112 -> {
                    "Восточный"
                } //ost
                in 113..157 -> {
                    "Юго-восточный"
                } //so
                in 158..202 -> {
                    "Южный"
                } //source
                in 203..247 -> {
                    "Юго-западный"
                } //ws
                in 248..292 -> {
                    "Западный"
                } //west
                in 293..337 -> {
                    "Северо-западный"
                } //wn
                in 338..360 -> {
                    "Северный"
                } //n
                else -> {
                    "ХЗ"
                }
            }

            binding.tvVisible.text = it.current?.visibility.toString() + "м"
            binding.tvPressure.text = it.current?.pressure.toString() + "гПа"
            binding.llHourWeatherList.removeAllViews()
            binding.llDailyList.removeAllViews()
            val inflater: LayoutInflater =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            for (i in 0 until 24) {
                var hourWeather = it.hourly?.get(i)
                var hourItem = inflater.inflate(
                    R.layout.hour_weather_item,
                    binding.llHourWeatherList,
                    false
                ) as LinearLayout
                TimeZone.setDefault(TimeZone.getTimeZone(it.timezone))
                val instH = Instant.ofEpochSecond(hourWeather?.dt ?: 0)
                val calH = Date.from(instH)
                val dateFormatH = SimpleDateFormat("HH:mm", Locale.UK)

                hourItem.findViewById<TextView>(R.id.tv_hour).text = dateFormatH.format(calH)
                hourItem.findViewById<TextView>(R.id.tv_hour_temp).text =
                    hourWeather?.temp?.roundToInt().toString() + "˚C"
                hourItem.findViewById<ImageView>(R.id.iv_hour_icon).setImageBitmap(
                    BitmapFactory.decodeStream(
                        resources.assets.open("icons/" + hourWeather?.weather?.get(0)?.icon + "@2x.png")
                    )
                )
                binding.llHourWeatherList.addView(hourItem)
            }

            it.daily?.forEachIndexed { index, daily ->
                TimeZone.setDefault(TimeZone.getTimeZone(it.timezone))
                val instD = Instant.ofEpochSecond((daily.dt))
                val calD = Date.from(instD)
                val dateFormatD = SimpleDateFormat("dd MMM, EEE", Locale("ru"))

                var dailyItem =
                    inflater.inflate(R.layout.daily_weather_item, binding.llDailyList, false)
                dailyItem.findViewById<TextView>(R.id.tv_daily_day).text = dateFormatD.format(calD)
                dailyItem.findViewById<TextView>(R.id.tv_daily_weather).text =
                    daily?.weather?.get(0)?.description ?: "UNKNOWN"
                dailyItem.findViewById<TextView>(R.id.tv_daily_temp).text =
                    it.daily?.get(index)?.temp?.min?.roundToInt().toString() + "˚C / " +
                            it.daily?.get(index)?.temp?.max?.roundToInt().toString() + "˚C"
                dailyItem.findViewById<ImageView>(R.id.iv_daily_icon).setImageBitmap(
                    BitmapFactory.decodeStream(
                        resources.assets.open("icons/" + it.daily?.get(index)?.weather?.get(0)?.icon + "@2x.png")
                    )
                )
                binding.llDailyList.addView(dailyItem)
            }

            binding.swipe.isRefreshing = false
            binding.processBarContainer.visibility = View.GONE
            binding.emptyScreen.visibility = View.GONE
            binding.mainContent.visibility = View.VISIBLE
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == -1) {
                    binding.emptyScreen.visibility = View.VISIBLE
                    binding.mainContent.visibility = View.GONE
                } else {
                    binding.emptyScreen.visibility = View.GONE
                    binding.mainContent.visibility = View.GONE
                    binding.processBarContainer.visibility = View.VISIBLE
                    viewModel?.setSelectedLocation(p2)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.swipe.setOnRefreshListener {
            if (viewModel?.getSelectedLocation() == null) {
                viewModel?.setSelectedLocation(binding.spinner.selectedItemPosition)
            } else viewModel?.oneReqWeather()
        }

        binding.btnDelete.setOnClickListener {
            viewModel?.deleteLocation(binding.spinner.selectedItemPosition)
            with(binding.spinner) {
                if (adapter.count != 1) setSelection(0)
            }
        }
        viewModel?.getLocations()
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
        binding.emptyScreen.visibility = View.GONE
        binding.mainContent.visibility = View.GONE
        binding.processBarContainer.visibility = View.VISIBLE
        viewModel?.oneReqWeather()
    }

    override fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }
}