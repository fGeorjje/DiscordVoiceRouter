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

package com.github.fgeorjje.dvr.command

import net.dv8tion.jda.api.JDA

class ConsoleHandler(private val jda: JDA, private val executor: CommandExecutor) {
    fun loopRead() {
        while (true) {
            try {
                val line = readLine()!!
                jda.callbackPool.execute { parse(line) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun parse(command: String) {
        try {
            executor.execute(command.split(" "))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}