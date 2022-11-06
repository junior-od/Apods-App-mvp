package com.example.mvpplayaround.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DateConverterTest {

    /**
     * test that a empty string
     * returns empty result
     */

    @Test
    fun `empty date value return empty result`(){
        val result = DateConverter.formatDateToDdMmYyyy("")
        assertThat(result).isEmpty()
    }

    /**
     * test that a invalid value string
     * returns empty result
     */

    @Test
    fun `invalid date value format return empty result`(){
        val result = DateConverter.formatDateToDdMmYyyy("2012-01-")
        assertThat(result).isEmpty()
    }

    /**
     * test that a date value yyyy-MM-dd format returns
     * dd/MM/yyyy result
     * */

    @Test
    fun `date value is in yyyy-MM-dd format returns correct result format`(){
        val result = DateConverter.formatDateToDdMmYyyy("2012-01-09")
        assertThat(result).isEqualTo("09/01/2012")
    }

    /**
     * test that date value is
     * a valid yyyy-MM-dd format return true
     * */

    @Test
    fun `date value is valid yyyy-MM-dd format`(){
        val result = DateConverter.validateddMMyyyyFormat("2000-12-01")
        assertThat(result).isTrue()
    }

    /**
     * test that date value is
     * an  invalid yyyy-MM-dd format return false
     * */

    @Test
    fun `date value is an invalid yyyy-MM-dd format`(){
        val result = DateConverter.validateddMMyyyyFormat("2002-13-01")
        assertThat(result).isFalse()
    }



}