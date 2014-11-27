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
package com.gsma.service.rcs.chat;

import java.util.Random;

import android.os.Parcel;
import android.test.AndroidTestCase;

import com.gsma.services.rcs.chat.ChatMessage;
import com.gsma.services.rcs.contacts.ContactId;
import com.gsma.services.rcs.contacts.ContactUtils;

public class ChatMessageTest extends AndroidTestCase {

	private String messageId;
	private long receiptAt;
	private long sentAt;
	private String message;
	private ContactId remote;
	
	protected void setUp() throws Exception {
		super.setUp();
		Random random = new Random();
		messageId = String.valueOf (random.nextInt(96) + 32);
		message = String.valueOf (random.nextInt(96) + 32);
		receiptAt = random.nextLong();
		sentAt = random.nextLong();
		
		ContactUtils contactUtils = ContactUtils.getInstance(getContext());
		remote = contactUtils.formatContact("+33123456789");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	private boolean chatMessageIsEqual(ChatMessage chatMessage1, ChatMessage chatMessage2) {
		if (!chatMessage1.getId().equals(chatMessage2.getId()) ) {
			return false;
		}
		if (!chatMessage1.getContent().equals(chatMessage2.getContent())) {
			return false;
		}
		if (chatMessage1.getTimestamp()!=chatMessage2.getTimestamp()) {
			return false;
		}
		if (chatMessage1.getTimestampSent()!=chatMessage2.getTimestampSent()) {
			return false;
		}
		if (chatMessage1.getRemoteContact() != null) {
			if (!chatMessage1.getRemoteContact().equals(chatMessage2.getRemoteContact())) {
				return false;
			}
		} else {
			if (chatMessage2.getRemoteContact() != null) {
				return false;
			}
		}
		return true;
	}

	public void testChatMessageContactNull() {
		ChatMessage chatMessage = new ChatMessage(messageId, null, message, receiptAt, sentAt);
		Parcel parcel = Parcel.obtain();
		chatMessage.writeToParcel(parcel, 0);
		// done writing, now reset parcel for reading
		parcel.setDataPosition(0);
		// finish round trip
		ChatMessage createFromParcel = ChatMessage.CREATOR.createFromParcel(parcel);
		assertTrue(chatMessageIsEqual(createFromParcel, chatMessage));
	}
	
	public void testChatMessageContact() {
		ChatMessage chatMessage = new ChatMessage(messageId, remote, message, receiptAt, sentAt);
		Parcel parcel = Parcel.obtain();
		chatMessage.writeToParcel(parcel, 0);
		// done writing, now reset parcel for reading
		parcel.setDataPosition(0);
		// finish round trip
		ChatMessage createFromParcel = ChatMessage.CREATOR.createFromParcel(parcel);
		assertTrue(chatMessageIsEqual(createFromParcel, chatMessage));
	}
}