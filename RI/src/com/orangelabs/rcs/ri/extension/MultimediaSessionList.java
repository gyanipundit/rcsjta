package com.orangelabs.rcs.ri.extension;

import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.gsma.services.rcs.JoynService;
import com.gsma.services.rcs.JoynServiceListener;
import com.gsma.services.rcs.extension.MultimediaSessionService;
import com.orangelabs.rcs.ri.R;
import com.orangelabs.rcs.ri.utils.Utils;

/**
 * Abstract list of multimedia sessions
 * 
 * @author Jean-Marc AUFFRET
 */
public abstract class MultimediaSessionList extends ListActivity implements JoynServiceListener {
	/**
	 * MM session API
	 */
	protected MultimediaSessionService sessionApi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // Set layout
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.extension_session_list);

        // Instanciate API
        sessionApi = new MultimediaSessionService(getApplicationContext(), this);
        
        // Connect API
        sessionApi.connect();
	}
	
	@Override
	protected void onResume() {
		super.onResume();

    	// Update the list of sessions
		updateList();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();

        // Disconnect API
        sessionApi.disconnect();
	}	
	
    /**
     * Callback called when service is connected. This method is called when the
     * service is well connected to the RCS service (binding procedure successfull):
     * this means the methods of the API may be used.
     */
    public void onServiceConnected() {
		// Display the list of sessions
		updateList();
    }
    
    /**
     * Callback called when service has been disconnected. This method is called when
     * the service is disconnected from the RCS service (e.g. service deactivated).
     * 
     * @param error Error
     * @see JoynService.Error
     */
    public void onServiceDisconnected(int error) {
		Utils.showMessageAndExit(MultimediaSessionList.this, getString(R.string.label_api_disabled));
    }    
    
    /**
     * Callback called when service is registered to the RCS/IMS platform
     */
    public void onServiceRegistered() {
    	// Nothing to do here
    }
    
    /**
     * Callback called when service is unregistered from the RCS/IMS platform
     */
    public void onServiceUnregistered() {
    	// Update the list of sessions
		updateList();
    }      
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		// Display the selected session
		displaySession(position);
	}

	/**
	 * Display a session
	 * 
	 * @param sessionId Session ID
	 */
	public abstract void displaySession(int position);
	
    /**
     * Update the displayed list
     */
	public abstract void updateList();
}