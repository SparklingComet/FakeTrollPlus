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

package org.shanerx.faketrollplus.utils;

import org.bstats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class MetricsManager {
	
	private JavaPlugin plugin;
	private Metrics metrics;
	
	public MetricsManager(JavaPlugin plugin) {
		this.plugin = plugin;
		this.metrics = new Metrics(plugin);
		quickSetup();
	}
	
	public void quickSetup() {
		metrics.addCustomChart(new Metrics.MultiLineChart("players_and_servers") {
			@Override
			public HashMap<String, Integer> getValues(HashMap<String, Integer> valueMap) {
				valueMap.put("servers", 1);
				valueMap.put("players", Bukkit.getOnlinePlayers().size());
				return valueMap;
			}
		});
		metrics.addCustomChart(new Metrics.AdvancedPie("servers_using_the_updater") {
			@Override
			public HashMap<String, Integer> getValues(HashMap<String, Integer> values) {
				values.put("servers", plugin.getConfig().getBoolean("check-updates") ? 1 : 0);
				return values;
			}
		});
	}
}
