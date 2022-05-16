package com.kosmokamikaze.intervalapp

import com.kosmokamikaze.intervalapp.musical.MusicTheoryHandler
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun chordBuilderTest() {
        val chord = MusicTheoryHandler.buildChord(0, 2726)
        print(chord)
    }

}