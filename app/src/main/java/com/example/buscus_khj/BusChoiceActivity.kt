package com.example.buscus_khj

import android.bluetooth.BluetoothAdapter
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import mobi.inthepocket.android.beacons.ibeaconscanner.Beacon
import org.altbeacon.beacon.BeaconConsumer
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.Region

class BusChoiceActivity : BaseActivity , BeaconConsumer {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_choice)

}

// 블루투스 권한을 얻는 코드입니다.
val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

if (!mBluetoothAdapter.isEnabled()) {
    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
} else {
    mBeaconManager = BeaconManager.getInstanceForApplication(this)
    mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BEACON_PARSER))
    // BEACON_PARSER 는 문자열인데요. iBeacon 이냐 EddyStone 이냐에 따라 다른 문자열을 필요로합니다.

    //BeaconManager.setRssiFilterImplClass(ArmaRssiFilter.class)
}

@Override
public onBeaconServiceConnect() {
    mBeaconManager.addRangeNotifier(new RangeNotifier() {
        @Override
        override fun void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
            if (beacons.size() > 0) {
                Iterator<Beacon> iterator = beacons.iterator()
                items = new Vector<>()
                while (iterator.hasNext()) {
                    Beacon beacon = iterator.next()
                    String address = beacon.getBluetoothAddress()
                    if(address.equals(ble1) || address.equals(ble2)) {
                        double rssi = beacon.getRssi();
                        int txPower = beacon.getTxPower();
                        double distance = Double.parseDouble(decimalFormat.format(beacon.getDistance()))
                        int major = beacon.getId2().toInt();
                        int minor = beacon.getId3().toInt();
                        items.add(new Item(address, rssi, txPower, distance, major, minor))
                        /*Log.d("major ", beacon.getId2().toString());
                        Log.d("minor ", beacon.getId3().toString());*/
                    }
                }Å
                runOnUiThread(new Runnable() {
                    @Override
                    override fun run() {
                        beaconAdapter = new BeaconAdapter(items, MainActivity.this)
                        binding.beaconListView.setAdapter(beaconAdapter)
                        beaconAdapter.notifyDataSetChanged()
                    }
                });
            }
        }
    });
    try {
        mBeaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
    } catch (RemoteException e) {
        e.printStackTrace();
    }
    mBeaconManager.addMonitorNotifier(new MonitorNotifier() {

        @Override
        override fun didEnterRegion(Region region) {
            Log.i(TAG, "I just saw an beacon for the first time!")
        }

        @Override
        override fun void didExitRegion(Region region) {
            Log.i(TAG, "I no longer see an beacon")
        }


        @Override
        override fun didDetermineStateForRegion(int state, Region region) {
            Log.i(TAG, "I have just switched from seeing/not seeing beacons: "+state);
        }
    });
    try {
        mBeaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
    } catch (RemoteException e) {
        e.printStackTrace();
    }
}



