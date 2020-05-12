package com.example.Clublife

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


/**
 * A simple [Fragment] subclass.
 */
class FragmentMap : Fragment(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment mapView = (MapView) view.findViewById(R.id.map_view);
        //mapView = view.findViewById(R.id.map) as MapView
        //mapView!!.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_map, container, false)


    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

    }

    override fun onResume() {
        super.onResume()

            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(this)
            activity?.let {
                LocationHelper().startListeningUserLocation(
                    activity!!,
                    object : LocationHelper.MyLocationListener {
                        override fun onLocationChanged(location: Location) {
                            // Add a marker in current location and move the camera.
                            val current = LatLng(location.latitude, location.longitude)
                            mMap?.addMarker(MarkerOptions().position(current).title("Marker is your current location"))
                            mMap?.moveCamera(CameraUpdateFactory.newLatLng(current))

                            Log.d("======================", "" + location.latitude + "," + location.longitude)

                        }
                    })
            }



    }
}


//    override fun onResume() {
//        super.onResume()
//        activity?.let {
//            LocationHelper().startListeningUserLocation(it, object : LocationHelper.MyLocationListener {
//                override fun onLocationChanged(location: Location) {
//                    // Here you got user location :)
//                    //latTextView.setText(location.latitude.toString())
//                    //lonTextView.setText(location.longitude.toString())
//                    Log.d("Location","" + location.latitude + "," + location.longitude)
//                }
//            })
//        }
//    }





