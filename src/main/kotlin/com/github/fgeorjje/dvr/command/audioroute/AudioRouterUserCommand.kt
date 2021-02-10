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

import com.github.fgeorjje.dvr.command.CommandExecutor
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.User

abstract class AudioRouterUserCommand(private val jda: JDA) : CommandExecutor {
    abstract fun execute(user: User, args: List<String>)

    override fun execute(args: List<String>) {
        val userInput = args[0]
        val user: User = if (userInput.matches(SNOWFLAKE_REGEX)) {
            jda.getUserById(userInput) ?: return println("User not found: $userInput")
        } else {
            val usersByName = jda.getUsersByName(userInput, true)
            if (usersByName.isEmpty()) return println("User not found: $userInput")
            if (usersByName.size > 1) return println("Multiple users found: $usersByName")
            usersByName[0]
        }

        execute(user, args.drop(1))
    }

    companion object {
        private val SNOWFLAKE_REGEX = Regex("-?\\d+")
    }
}