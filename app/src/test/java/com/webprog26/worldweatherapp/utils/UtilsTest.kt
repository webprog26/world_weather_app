package com.webprog26.worldweatherapp.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UtilsTest {

    @Test
    fun `dateTime 1652198400 will be converted to 19-00 HhMm time`() {
        val dateTime: Long = 1652198400
        Assert.assertEquals("19:00", convertMillisToHhMm(dateTime))
    }

    @Test
    fun `dateTime will be converted to Tue, May 10 date string`() {
        val dateTime: Long = 1652212800
        Assert.assertEquals("Tue, May 10", convertMillisToDateString(dateTime))
    }
}