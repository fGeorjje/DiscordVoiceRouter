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

package com.github.fgeorjje.dvr.command.audioroute

import com.github.fgeorjje.dvr.AudioRouter
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.User
import javax.sound.sampled.AudioSystem

class AudioRouterAddRouteCommand(jda: JDA, private val audioRouter: AudioRouter) : AudioRouterUserCommand(jda) {
    override fun execute(user: User, args: List<String>) {
        val device = args.joinToString(" ")
        val matching = AudioSystem.getMixerInfo().filter { it.name.startsWith(device) }
        if (matching.isEmpty()) return println("Device not found: $device")
        if (matching.size > 1) return println("Multiple devices found: $matching")
        val selected = matching[0]
        audioRouter.addRoute(user.id, selected)
        println("Routed $user to $selected")
    }
}