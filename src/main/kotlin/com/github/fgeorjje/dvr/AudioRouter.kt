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

package com.github.fgeorjje.dvr

import net.dv8tion.jda.api.audio.AudioReceiveHandler
import net.dv8tion.jda.api.audio.UserAudio
import javax.sound.sampled.Mixer

class AudioRouter : AudioReceiveHandler {
    data class Route(val userId: String, val mixerOutputStream: MixerOutput)

    override fun canReceiveUser(): Boolean = true
    override fun handleUserAudio(userAudio: UserAudio) {
        val data = userAudio.getAudioData(1.0)
        routes.filter { it.userId == userAudio.user.id }
            .forEach { it.mixerOutputStream.write(data) }
    }

    private var routes = listOf<Route>()

    @Synchronized
    fun restart() {
        routes.forEach { it.mixerOutputStream.restart() }
    }

    @Synchronized
    fun stop() {
        routes.forEach { it.mixerOutputStream.close() }
        routes = emptyList()
    }

    @Synchronized
    fun addRoute(userId: String, mixer: Mixer.Info) {
        routes = routes + Route(userId, MixerOutput(mixer, AudioReceiveHandler.OUTPUT_FORMAT))
    }

    @Synchronized
    fun removeRoutes(userId: String) {
        val remove = routes.filter { it.userId == userId }
        remove.forEach { it.mixerOutputStream.close() }
        routes = routes.minus(remove)
    }
}