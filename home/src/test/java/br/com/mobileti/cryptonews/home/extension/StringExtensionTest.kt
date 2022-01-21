package br.com.mobileti.cryptonews.home.extension

import org.junit.Assert.*
import org.junit.Test

class StringExtensionTest {

    @Test
    fun `GIVEN full date format WHEN month day year format THEN date format success`() {
        val dateBarFormat = FULL_DATE.toFormattedDateString(
            oldFormat = FULL_DATE_FORMAT,
            newFormat = MONTH_DAY_YEAR_BAR_FORMAT
        )

        val dateDashFormat = FULL_DATE.toFormattedDateString(
            oldFormat = FULL_DATE_FORMAT,
            newFormat = MONTH_DAY_YEAR_DASH_FORMAT
        )

        assertEquals(dateBarFormat, "01/06/2022")
        assertEquals(dateDashFormat, "01-06-2022")
    }

    @Test
    fun `GIVEN full date format WHEN day month year format THEN date format success`() {
        val dateBarFormat = FULL_DATE.toFormattedDateString(
            oldFormat = FULL_DATE_FORMAT,
            newFormat = DAY_MONTH_YEAR_BAR_FORMAT
        )

        val dateDashFormat = FULL_DATE.toFormattedDateString(
            oldFormat = FULL_DATE_FORMAT,
            newFormat = DAY_MONTH_YEAR_DASH_FORMAT
        )

        assertEquals(dateBarFormat, "06/01/2022")
        assertEquals(dateDashFormat, "06-01-2022")
    }

    @Test
    fun `GIVEN full date format WHEN year month day THEN date format success`() {
        val dateBarFormat = FULL_DATE.toFormattedDateString(
            oldFormat = FULL_DATE_FORMAT,
            newFormat = YEAR_MONTH_DAY_BAR_FORMAT
        )

        val dateDashFormat = FULL_DATE.toFormattedDateString(
            oldFormat = FULL_DATE_FORMAT,
            newFormat = YEAR_MONTH_DAY_DASH_FORMAT
        )

        assertEquals(dateBarFormat, "2022/01/06")
        assertEquals(dateDashFormat, "2022-01-06")
    }

    @Test
    fun `GIVEN full date format WHEN wrong format THEN date format success`() {
        val wrongDateFormat = FULL_DATE.toFormattedDateString(
            oldFormat = FULL_DATE_FORMAT,
            newFormat = WRONG_FORMAT
        )

        assertEquals(wrongDateFormat, "")
    }

    @Test
    fun `GIVEN full date format WHEN date time format THEN date time format success`() {
        val dateTimeFormat = FULL_DATE.toFormattedDateString(
            oldFormat = FULL_DATE_FORMAT,
            newFormat = DATE_TIME_FORMAT
        )

        assertEquals(dateTimeFormat, "06/01/2022 16:00")
    }

    @Test
    fun `GIVEN full date format WHEN time format THEN time format success`() {
        val dateTimeFormat = FULL_DATE.toFormattedDateString(
            oldFormat = FULL_DATE_FORMAT,
            newFormat = TIME_FORMAT
        )

        assertEquals(dateTimeFormat, "16:00")
    }

    companion object {
        const val FULL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        const val MONTH_DAY_YEAR_BAR_FORMAT = "MM/dd/yyyy"
        const val MONTH_DAY_YEAR_DASH_FORMAT = "MM-dd-yyyy"
        const val DAY_MONTH_YEAR_BAR_FORMAT = "dd/MM/yyyy"
        const val DAY_MONTH_YEAR_DASH_FORMAT = "dd-MM-yyyy"
        const val YEAR_MONTH_DAY_BAR_FORMAT = "yyyy/MM/dd"
        const val YEAR_MONTH_DAY_DASH_FORMAT = "yyyy-MM-dd"
        const val DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm"
        const val TIME_FORMAT = "HH:mm"
        const val WRONG_FORMAT = "wrong"

        const val FULL_DATE = "2022-01-06T16:00:06Z"

    }
}