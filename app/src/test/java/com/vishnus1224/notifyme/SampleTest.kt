package com.vishnus1224.notifyme

import org.junit.Assert.*
import org.junit.Test

class SampleTest {

    @Test
    fun checkAddition() {
        val result = Sample().doAddition(1, 2)
        
        assertEquals(3, result)
    }
}