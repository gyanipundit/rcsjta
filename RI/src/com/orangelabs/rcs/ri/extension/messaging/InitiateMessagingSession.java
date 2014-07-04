/*******************************************************************************
 * Software Name : RCS IMS Stack
 *
 * Copyright (C) 2010 France Telecom S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.orangelabs.rcs.ri.extension.messaging;

import android.content.Intent;
import android.os.Bundle;

import com.orangelabs.rcs.ri.R;
import com.orangelabs.rcs.ri.extension.InitiateMultimediaSession;

/**
 * Initiate messaging session
 *  
 * @author Jean-Marc AUFFRET
 */
public class InitiateMessagingSession extends InitiateMultimediaSession {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set title
		setTitle(R.string.menu_initiate_messaging_session);
	}

	/**
	 * Initiate session
	 * 
	 * @param contact Remote contact
	 */
	public void initiateSession(String contact) {
		// Display session view
		Intent intent = new Intent(InitiateMessagingSession.this, MessagingSessionView.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	intent.putExtra(MessagingSessionView.EXTRA_MODE, MessagingSessionView.MODE_OUTGOING);
    	intent.putExtra(MessagingSessionView.EXTRA_CONTACT, contact);
		startActivity(intent);
	}
}