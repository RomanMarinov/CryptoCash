package com.dev_marinov.cryptocash.domain

import com.dev_marinov.cryptocash.data.ConvertDate

class DateConverter {

   fun getName(convertDate: ConvertDate) : String {

        var dateResult = ""

        // i1 - год, i2 - месяц(январь с нуля идет отсчет) ПЛЮС ОДИН, i3 - день
        val i1 = convertDate.year
        val i2 = convertDate.month?.let { it + 1 }
        val i3 = convertDate.day

        // если месяц не входит в диапазон от 10 до 12, а день входит от 10 до 31
        if (i2 !in 10..12 && i3 in 10..31) dateResult = String.format("$i1" + "-" + "0$i2" + "-" + "$i3")
        // если месяц входит в диапазон от 10 до 12, а день не входит от 10 до 31
        if (i2 in 10..12 && i3 !in 10..31) dateResult = String.format("$i1" + "-" + "$i2" + "-" + "0$i3")
        // если месяц входит в диапазон от 10 до 12, и день входит от 10 до 31
        if (i2 in 10..12 && i3 in 10..31) dateResult = String.format("$i1" + "-" + "$i2" + "-" + "$i3")
        // если месяц не входит в диапазон от 10 до 12, и день не входит от 10 до 31
        if (i2 !in 10..12 && i3 !in 10..31) dateResult = String.format("$i1" + "-" + "0$i2" + "-" + "0$i3")

        return dateResult

    }

}