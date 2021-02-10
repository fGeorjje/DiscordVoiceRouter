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

import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Mixer
import javax.sound.sampled.SourceDataLine

class MixerOutput(private val mixer: Mixer.Info, private val format: AudioFormat) {
    private var line: SourceDataLine? = createLine()
    private var running = true

    private fun createLine(): SourceDataLine {
        return AudioSystem.getSourceDataLine(format, mixer)
            .also { it.open(format) }
            .also { it.start() }
    }

    @Synchronized
    fun restart() {
        line?.close()
        line = createLine()
    }

    fun write(b: ByteArray) = line?.write(b, 0, b.size)

    @Synchronized
    fun close() {
        line?.close()
        running = false
    }
}