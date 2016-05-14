package com.tyler.flagbotremote;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast; //showing text box

import java.util.ArrayList;
import java.util.Set;

/*
 * Created by Lambda Class
 */
public class MainActivity extends AppCompatActivity {

    // Phone bluetooth adapter used to connect to surrounding bluetooth devices
    private BluetoothAdapter btAdapter;
    //List of bluetooth devices to connect to.
    private Set<BluetoothDevice> pairedDevices;

    private Button bList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    protected void init(){
        bList = (Button)findViewById(R.id.bList);
        listView = (ListView)findViewById(R.id.listView);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

		/*
		 * Called by Bluetooth On button
		 */
    public void on(View view){
        if(!btAdapter.isEnabled()){
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Turned on"
                    , Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
        }
    }

		/*
		 * Called by Bluetooth Off button	 
		 */
    public void off(View view){
        btAdapter.disable();
        Toast.makeText(getApplicationContext(), "Bluetooth Off", Toast.LENGTH_LONG).show();
    }

		/*
		 * Called by List Bluetooth Devices button
		 * Generates new Intent when bluetooth device is selected
		 */
    public void list(View view){
        //Assign set reference to set of bluetooth devices connected to adapter
        pairedDevices = btAdapter.getBondedDevices();

        //I guess populating array with strings of device names.
        ArrayList list = new ArrayList();
        for(BluetoothDevice bt : pairedDevices)
            list.add(bt.getName() + "\n" + bt.getAddress());

        final Object[] btArray = pairedDevices.toArray();

        Toast.makeText(getApplicationContext(),"Showing Paired Devices", Toast.LENGTH_SHORT).show();
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(((BluetoothDevice)btArray[position]).getName());
                Intent openController = new Intent(MainActivity.this, ControllerActivity.class);
                openController.putExtra("DESIRED_DEVICE", (BluetoothDevice) btArray[position]);
                MainActivity.this.startActivity(openController);
            }
        });

    }
}
