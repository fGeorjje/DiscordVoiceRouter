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

class AudioRouterRemoveRouteCommand(jda: JDA, private val audioRouter: AudioRouter): AudioRouterUserCommand(jda) {
    override fun execute(user: User, args: List<String>) {
        audioRouter.removeRoutes(user.id)
        println("Removed routes for $user")
    }
}