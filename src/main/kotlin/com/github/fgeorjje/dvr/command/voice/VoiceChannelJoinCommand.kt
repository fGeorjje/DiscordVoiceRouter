/*
 *     Discord Voice Router
 *     Copyright (C) 2021  Paul S.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.fgeorjje.dvr.command.voice

import com.github.fgeorjje.dvr.AudioRouter
import com.github.fgeorjje.dvr.command.CommandExecutor
import net.dv8tion.jda.api.JDA

class VoiceChannelJoinCommand(private val jda: JDA, private val audioRouter: AudioRouter) : CommandExecutor {
    override fun execute(args: List<String>) {
        val channelId = args[0]
        val channel = jda.getVoiceChannelById(channelId) ?: return println("No voice channel with id $channelId")
        val connected = jda.audioManagers.filter { it.isConnected }.map { it.connectedChannel }
        if (connected.isNotEmpty()) {
            return println("Already connected to $connected")
        }
        val audioManager = channel.guild.audioManager
        audioManager.receivingHandler = audioRouter
        audioManager.openAudioConnection(channel)
        println("Joined $channel")
    }
}