package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    override fun compareTo(other: MyDate): Int {
        year.compareTo(other.year).let {
           if (it != 0) return it
           else month.compareTo(other.month).let {
               if(it != 0) return it
               else return dayOfMonth.compareTo(other.dayOfMonth)
           }
        }
    }
    operator fun rangeTo(other : MyDate) : DateRange = DateRange(this, other)
    operator fun plus(timeInterval: TimeInterval) : MyDate = addTimeIntervals(timeInterval, 1)
    operator fun plus(timeQuantity: TimeQuantity) : MyDate {
       return addTimeIntervals(timeQuantity.timeInterval, timeQuantity.quantity)
    }
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(q : Int) = TimeQuantity(this, q)
}

class TimeQuantity(val timeInterval: TimeInterval, val quantity: Int)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var cursor = start;

            override fun hasNext(): Boolean = endInclusive.compareTo(cursor) >= 0

            override fun next(): MyDate {
                if (hasNext()) {
                    val current = cursor;
                    cursor = cursor.nextDay();
                    return current;
                } else {
                    throw IndexOutOfBoundsException();
                }
            }

        }
    }

    fun contains(d: MyDate) : Boolean {
        return start.compareTo(d) <= 0 && d.compareTo(endInclusive) <= 0
    }
}
