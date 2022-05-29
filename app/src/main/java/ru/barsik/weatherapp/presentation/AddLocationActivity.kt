package ru.barsik.weatherapp.presentation

import android.Manifest
import android.R
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import ru.barsik.weatherapp.databinding.ActivityAddLocationBinding
import ru.barsik.weatherapp.di.component.ViewModelComponent
import ru.barsik.weatherapp.domain.AddLocationViewModel
import javax.inject.Inject

class AddLocationActivity : BaseActivity() {
    private val TAG = "AddLocationActivity"
    private lateinit var binding: ActivityAddLocationBinding

    var viewModel: AddLocationViewModel? = null
        @Inject set

    lateinit var arrayAdapter : ArrayAdapter<String>
    lateinit var results : ArrayList<String>

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putString("query", binding.editQuery.text.toString())
        outState.putStringArrayList("results", results)
        Log.d(TAG, "onSaveInstanceState: dfghjkljhgfdghjk")
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        results = savedInstanceState.getStringArrayList("results")?: ArrayList<String>()
        binding.editQuery.setText(savedInstanceState.getString("query") ?: "")
        arrayAdapter.notifyDataSetChanged()
    }

    override fun injectDependency(component: ViewModelComponent){
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val permissionCheck = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        }

        binding = ActivityAddLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        viewModel?.getSuggestListLD()?.observe(this) {
            results.clear()
            for(i in it) results.add(i)
            arrayAdapter.notifyDataSetChanged()
        }
        viewModel?.getSaveStatusLD()?.observe(this){
            when(it){
                0L -> Log.d(TAG, "statusObserve: $it")
                else->{
                    Snackbar.make(binding.root, "Сохранено", Snackbar.LENGTH_LONG).show()
                    Log.d(TAG, "statusObserve: $it")
                    var intent = Intent(this, MainActivity::class.java)
                     setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }

        }
        results = ArrayList<String>()
        arrayAdapter = ArrayAdapter<String>(
            this,
            R.layout.simple_list_item_2,
            R.id.text1,
            results
        )
        binding.resList.adapter = arrayAdapter

        binding.editQuery.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                viewModel?.findSuggestions(editable.toString())
            }
        })

        binding.resList.setOnItemClickListener { parent, view, position, id ->
            Log.d(TAG, "Click ${arrayAdapter.getItem(position)}")
            viewModel?.addLocation(position)
        }
    }

}