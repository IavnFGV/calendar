package gui.calendar;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by GFH on 01.12.2015.
 * <p/>
 * in order to implement #2 and #3 - Cleanable calendar fields
 * we faced with the problem that current date is not correctly painted
 * because by default today date is used in constructor
 * so as a temporary workaround (# 7 - Calendar change and big plan to migrate to JavaFX)
 * we use dirty trick with reflection... SORRY
 */
public class EveryValueSpinnerDateModel extends SpinnerDateModel {
    public EveryValueSpinnerDateModel() {
        super();
    }

    public EveryValueSpinnerDateModel(Date initialDate) {
        super(initialDate == null ? new Date() : initialDate, null, null, Calendar.DAY_OF_MONTH);
    }


    public EveryValueSpinnerDateModel(Date value, Comparable start, Comparable end, int calendarField) {
        super(value, start, end, calendarField);
    }

    @Override
    public void setValue(Object value) {
        if ((value == null) || !(value instanceof Date)) {
            throw new IllegalArgumentException("illegal value");
        }
        try {
            Field calendarValueField = SpinnerDateModel.class.getDeclaredField("value");
            calendarValueField.setAccessible(true);
            Calendar calendarValue = (Calendar) calendarValueField.get(this);
//            if (!value.equals(this.value.getTime())) {
            calendarValue.setTime((Date) value);
//            this.value.setTime((Date)value);
            fireStateChanged();
//            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
