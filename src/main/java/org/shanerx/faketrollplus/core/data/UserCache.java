/*
 *
 *      Copyright (c) 2016-2017 SparklingComet @ http://shanerx.org
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.shanerx.faketrollplus.core.data;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.shanerx.faketrollplus.FakeTrollPlus;

import java.util.UUID;

public interface UserCache {
    
    /**
     * Gets the {@link org.shanerx.faketrollplus.FakeTrollPlus} instance, which
     * can be casted to {@link org.bukkit.plugin.java.JavaPlugin} and
     * {@link org.bukkit.plugin.Plugin}.
     *
     * @return the {@link org.shanerx.faketrollplus.FakeTrollPlus} instance.
     */
    FakeTrollPlus getPlugin();
    
    /**
     * Returns an instance of {@link org.json.simple.JSONObject}, which contains
     * all the user data collected by the plugin.
     *
     * @return the {@link org.json.simple.JSONObject}.
     */
    JSONObject getJSONConfiguration();
    
    /**
     * Returns an instance of {@link org.json.simple.JSONObject}, containing the
     * specific data related to that {@link java.util.UUID}. <br>
     * {@code null} if there are no records about that player. If the value
     * {@code null} is returned, the player may still have joined once, they
     * have never been trolled.
     *
     * @param uuid
     *            the uuid of the player.
     * @return the {@link org.json.simple.JSONObject} representing the data
     *         related to the UUID.
     */
    JSONObject getPlayerInfo(UUID uuid);
    
    /**
     * Returns an instance of {@link org.json.simple.JSONObject}, containing the
     * specific data related to that {@link java.util.UUID}. <br>
     * {@code null} if there are no records about that player. If the value
     * {@code null} is returned, the player may still have joined once, they
     * have never been trolled.
     *
     * @param op the {@link org.bukkit.OfflinePlayer} instance
     * @return the {@link org.json.simple.JSONObject} representing the data
     *         related to the {@link org.bukkit.OfflinePlayer}.
     */
    JSONObject getPlayerInfo(OfflinePlayer op);
    
    /**
     * Returns an instance of {@link org.json.simple.JSONObject}, containing the
     * specific data related to that {@link java.util.UUID}. <br>
     * {@code null} if there are no records about that player. If the value
     * {@code null} is returned, the player may still have joined once, they
     * have never been trolled.
     *
     * @param p
     *            the {@link org.bukkit.entity.Player} instance
     * @return the {@link org.json.simple.JSONObject} representing the data
     *         related to the {@link org.bukkit.entity.Player}.
     */
    JSONObject getPlayerInfo(Player p);
    
    /**
     * Gets the instance of {@link org.shanerx.faketrollplus.core.data.TrollPlayer}
     * which represents the player known by that {@link java.util.UUID}.
     *
     * @param uuid
     *            the UUID of the player.
     * @return the {@link org.shanerx.faketrollplus.core.data.TrollPlayer} known by
     *         that UUID.
     */
    TrollPlayer getTrollPlayer(UUID uuid);
    
    /**
     * Gets the instance of {@link org.shanerx.faketrollplus.core.data.TrollPlayer}
     * which represents the player known by the {@link java.util.UUID} of the
     * {@link org.bukkit.OfflinePlayer}.
     *
     * @param op
     *            the player.
     * @return the {@link org.shanerx.faketrollplus.core.data.TrollPlayer}.
     */
    TrollPlayer getTrollPlayer(OfflinePlayer op);
    
    /**
     * Gets the instance of {@link org.shanerx.faketrollplus.core.data.TrollPlayer}
     * which represents the player known by the {@link java.util.UUID} of the
     * {@link org.bukkit.entity.Player}.
     *
     * @param p
     *            the player.
     * @return the {@link org.shanerx.faketrollplus.core.data.TrollPlayer}.
     */
    TrollPlayer getTrollPlayer(Player p);
}