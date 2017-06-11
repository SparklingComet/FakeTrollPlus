/*
 *
 *  *     Copyright 2016-2017 SparklingComet @ http://shanerx.org
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *          http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package org.shanerx.faketrollplus.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.shanerx.faketrollplus.FakeTrollPlus;
import org.shanerx.faketrollplus.core.TrollPlayer;

public class ChatPacketListener extends PacketAdapter {
	
	private FakeTrollPlus plugin;
	
	public ChatPacketListener(FakeTrollPlus plugin) {
		super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.CHAT);
		this.plugin = plugin;
	}
	
	@Override
	public void onPacketSending(PacketEvent event) {
		if (event.getPacketType() == PacketType.Play.Server.CHAT) {
			TrollPlayer tp = plugin.getUserCache().getTrollPlayer(event.getPlayer());
			if (tp.chatIsFrozen()) {
				event.setCancelled(true);
			}
		}
	}
}