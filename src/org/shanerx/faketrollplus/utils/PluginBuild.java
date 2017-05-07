/*
 *     Copyright 2016-2017 ShanerX @ http://shanerx.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.shanerx.faketrollplus.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.shanerx.faketrollplus.FakeTrollPlus;

public enum PluginBuild {

	DEV, BETA, STABLE, FINAL;

	public static String getVersion() {
		return FakeTrollPlus.version + "-" + PluginBuild.DEV;
	}
	
	public static void checkCurrentVersion() {
        Logger log = FakeTrollPlus.console;
		try {
			URL url = new URL("https://pastebin.com/raw/UuUcn7SM");
			
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(url.openStream()));

	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	        	System.out.println(inputLine);
	        	if (!inputLine.equalsIgnoreCase(FakeTrollPlus.version)) {
	    			log.log(Level.WARNING, "[Updater] ------------------------------------------------------");
	    			log.log(Level.WARNING, "[Updater] You are running an outdated version of the plugin!");
	    			log.log(Level.WARNING, "[Updater] Most recent stable version: " + inputLine);
	    			log.log(Level.WARNING, "[Updater] Please update asap from: https://goo.gl/Qv2iVZ");
	    			log.log(Level.WARNING, "[Updater] ------------------------------------------------------");
	    			in.close();
	    			return;
	        	}
	        	log.log(Level.WARNING, "[Updater] ------------------------------------------------------");
	        	log.log(Level.WARNING, "[Updater] You are running the latest version of the plugin!");
	        	log.log(Level.WARNING, "[Updater] ------------------------------------------------------");	        }
	        in.close();
		} catch (Exception e) {
			log.log(Level.WARNING, "[Updater] ------------------------------------------------------");
			log.log(Level.WARNING, "[Updater] Could not establish a connection to check for updates!");
			log.log(Level.WARNING, "[Updater] ------------------------------------------------------");
		}
	}

}