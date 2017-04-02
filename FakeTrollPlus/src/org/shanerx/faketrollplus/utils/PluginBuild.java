package org.shanerx.faketrollplus.utils;

import org.shanerx.faketrollplus.FakeTrollPlus;

public enum PluginBuild {
	
	DEV, BETA, STABLE, FINAL;
	
	public static String getVersion() {
		return FakeTrollPlus.version + "-" + PluginBuild.STABLE;
	}

}
