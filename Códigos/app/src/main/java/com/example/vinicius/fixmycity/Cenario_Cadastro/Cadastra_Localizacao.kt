package com.example.vinicius.fixmycity.Cenario_Cadastro

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.constraint.Constraints.TAG
import android.util.Log
import android.widget.Toast
import com.example.vinicius.fixmycity.R
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.android.synthetic.main.activity_cadastra_localizacao.*
import java.util.*

class Cadastra_Localizacao : Activity() {

    companion object {
        const val ENDERECO: String = "endereço"
        const val LOCX: String = "locX"
        const val LOCY: String = "locY"
        const val PERMISSAO: Int = 1
        const val PLACE_PICKER_REQUEST = 2
    }

    var locX = 0.0
    var locY = 0.0
    var endereco = ""

    private var permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.INTERNET, android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)

    private fun checkPermission(permissionArray: Array<String>):Boolean{
        var allSucess = true
            for (i in permissionArray.indices){
                if(checkCallingOrSelfPermission(permissionArray[i])==PackageManager.PERMISSION_DENIED){
                    allSucess = false
                }
            }
        return allSucess
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode==PERMISSAO && (grantResults.isEmpty() || grantResults[0]!= PackageManager.PERMISSION_GRANTED)){
            Toast.makeText(this,"Permissões não aceitas", Toast.LENGTH_LONG).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_localizacao)



        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(checkPermission(permissions) == false){
                requestPermissions(permissions,PERMISSAO)
            }
        }

        ConfirmaEnd.setOnClickListener {
            if (endereco.isBlank()){
                Toast.makeText(this,"Selecione um endereço.", Toast.LENGTH_LONG).show()
            }else {
                val cadastraDificuldade = Intent(this, Cadastro_Dificuldades::class.java)
                cadastraDificuldade.putExtra(ENDERECO, endereco)
                cadastraDificuldade.putExtra(LOCX, locX)
                cadastraDificuldade.putExtra(LOCY, locY)
                startActivity(cadastraDificuldade)
                finish()
            }
        }

        LocAtual.setOnClickListener {
            try {
                val builder = PlacePicker.IntentBuilder()
                val i = builder.build(this) as Intent
                startActivityForResult(i, PLACE_PICKER_REQUEST)
            }catch (e : GooglePlayServicesRepairableException){
                Log.e(TAG,  String.format("GooglePlayServices Not avaiable [%s]", e.message))
            }catch (e : GooglePlayServicesNotAvailableException){
                Log.e(TAG,  String.format("GooglePlayServices Not avaiable [%s]", e.message))
            }catch (e : java.lang.Exception){
                Log.e(TAG,  String.format("PlacePicker Exception : %s", e.message))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode : Int, data : Intent?){
        if(requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK){
            var place = PlacePicker.getPlace(this, data)
            if(place == null){
                Log.i(TAG, "Nenhum lugar selecionado.")
                return
            }
            else{
                val place = PlacePicker.getPlace(data, this)
                endereco = place.address.toString()
                Endereco.text = endereco
                locX = place.latLng.latitude
                locY = place.latLng.longitude
            }

        }
    }


}
