package com.tyczj.extendedcalendarview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.os.Build;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalendarAdapter extends BaseAdapter {

    static final int FIRST_DAY_OF_WEEK = 0;
    Context context;
    Calendar cal;
    public String[] days;

//	OnAddNewEventClick mAddEvent;

    ArrayList<Day> dayList = new ArrayList<Day>();

    public CalendarAdapter(Context context, Calendar cal) {
        this.cal = cal;
        this.context = context;
        cal.set(Calendar.DAY_OF_MONTH, 1);
        refreshDays();
    }

    @Override
    public int getCount() {
        return days.length;
    }

    @Override
    public Object getItem(int position) {
        return dayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getPrevMonth() {
        if (cal.get(Calendar.MONTH) == cal.getActualMinimum(Calendar.MONTH)) {
            cal.set(Calendar.YEAR, cal.get(Calendar.YEAR - 1));
        } else {

        }
        int month = cal.get(Calendar.MONTH);
        if (month == 0) {
            return month = 11;
        }

        return month - 1;
    }

    public int getMonth() {
        return cal.get(Calendar.MONTH);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (position >= 0 && position < 7) {
            v = vi.inflate(R.layout.day_of_week, null);
            TextView day = (TextView) v.findViewById(R.id.textView1);

            if (position == 0) {
                day.setText(R.string.sunday);
            } else if (position == 1) {
                day.setText(R.string.monday);
            } else if (position == 2) {
                day.setText(R.string.tuesday);
            } else if (position == 3) {
                day.setText(R.string.wednesday);
            } else if (position == 4) {
                day.setText(R.string.thursday);
            } else if (position == 5) {
                day.setText(R.string.friday);
            } else if (position == 6) {
                day.setText(R.string.saturday);
            }

        } else {

            v = vi.inflate(R.layout.day_view, null);
            TextView dayTV = (TextView) v.findViewById(R.id.textView1);
            TextView quantitiTV = (TextView) v.findViewById(R.id.milk_quantity);
            FrameLayout today = (FrameLayout) v.findViewById(R.id.today_frame);
            Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
            int month = cal.get(Calendar.DAY_OF_MONTH);
            Day d = dayList.get(position);

            if (d.getYear() == cal.get(Calendar.YEAR) && d.getMonth() == cal.get(Calendar.MONTH) && d.getDay() == cal.get(Calendar.DAY_OF_MONTH)) {
                today.setVisibility(View.VISIBLE);
                quantitiTV.setText(String.valueOf(getQuantity(d)) + "L");


            } else if (d.getYear() == cal.get(Calendar.YEAR) && d.getMonth() == cal.get(Calendar.MONTH) && d.getDay() < cal.get(Calendar.DAY_OF_MONTH)
                    && d.getDay() >= registrationDate) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    today.setBackground(context.getResources().getDrawable(R.drawable.past_days));
                }
                quantitiTV.setText(String.valueOf(getQuantity(d)) + "L");

                today.setVisibility(View.VISIBLE);
            } else if (d.getYear() == cal.get(Calendar.YEAR) && d.getMonth() == cal.get(Calendar.MONTH) && d.getDay() < cal.get(Calendar.DAY_OF_MONTH)
                    && d.getDay() < registrationDate) {
                today.setVisibility(View.GONE);
                quantitiTV.setText("");
            } else if (d.getYear() == cal.get(Calendar.YEAR) && d.getMonth() < cal.get(Calendar.MONTH)) {
                quantitiTV.setText("");
                today.setVisibility(View.GONE);
            } else if (d.getYear() < cal.get(Calendar.YEAR)) {
                quantitiTV.setText("");
                today.setVisibility(View.GONE);
            } else {
                quantitiTV.setText(String.valueOf(getQuantity(d)) + "L");
                today.setVisibility(View.GONE);
            }

            RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.rl);
            ImageView iv = (ImageView) v.findViewById(R.id.imageView1);
            ImageView blue = (ImageView) v.findViewById(R.id.imageView2);
            ImageView purple = (ImageView) v.findViewById(R.id.imageView3);
            ImageView green = (ImageView) v.findViewById(R.id.imageView4);
            ImageView orange = (ImageView) v.findViewById(R.id.imageView5);
            ImageView red = (ImageView) v.findViewById(R.id.imageView6);

            blue.setVisibility(View.VISIBLE);
            purple.setVisibility(View.VISIBLE);
            green.setVisibility(View.VISIBLE);
            purple.setVisibility(View.VISIBLE);
            orange.setVisibility(View.VISIBLE);
            red.setVisibility(View.VISIBLE);

            iv.setVisibility(View.VISIBLE);
            dayTV.setVisibility(View.VISIBLE);
            rl.setVisibility(View.VISIBLE);

            Day day = dayList.get(position);

            if (day.getNumOfEvenets() > 0) {
                Set<Integer> colors = day.getColors();

                iv.setVisibility(View.INVISIBLE);
                blue.setVisibility(View.INVISIBLE);
                purple.setVisibility(View.INVISIBLE);
                green.setVisibility(View.INVISIBLE);
                purple.setVisibility(View.INVISIBLE);
                orange.setVisibility(View.INVISIBLE);
                red.setVisibility(View.INVISIBLE);

                if (colors.contains(0)) {
                    iv.setVisibility(View.VISIBLE);
                }
                if (colors.contains(2)) {
                    blue.setVisibility(View.VISIBLE);
                }
                if (colors.contains(4)) {
                    purple.setVisibility(View.VISIBLE);
                }
                if (colors.contains(5)) {
                    green.setVisibility(View.VISIBLE);
                }
                if (colors.contains(3)) {
                    orange.setVisibility(View.VISIBLE);
                }
                if (colors.contains(1)) {
                    red.setVisibility(View.VISIBLE);
                }

            } else {
                iv.setVisibility(View.INVISIBLE);
                blue.setVisibility(View.INVISIBLE);
                purple.setVisibility(View.INVISIBLE);
                green.setVisibility(View.INVISIBLE);
                purple.setVisibility(View.INVISIBLE);
                orange.setVisibility(View.INVISIBLE);
                red.setVisibility(View.INVISIBLE);
            }

            if (day.getDay() == 0) {
                rl.setVisibility(View.GONE);
            } else {
                dayTV.setVisibility(View.VISIBLE);
                dayTV.setText(String.valueOf(day.getDay()));
            }
        }

        return v;
    }

    public void refreshDays() {
        // clear items
        dayList.clear();

        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH) + 7;
        int firstDay = (int) cal.get(Calendar.DAY_OF_WEEK);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        TimeZone tz = TimeZone.getDefault();

        // figure size of the array
        if (firstDay == 1) {
            days = new String[lastDay + (FIRST_DAY_OF_WEEK * 6)];
        } else {
            days = new String[lastDay + firstDay - (FIRST_DAY_OF_WEEK + 1)];
        }

        int j = FIRST_DAY_OF_WEEK;

        // populate empty days before first real day
        if (firstDay > 1) {
            for (j = 0; j < (firstDay - FIRST_DAY_OF_WEEK) + 7; j++) {
                days[j] = "";
                Day d = new Day(context, 0, 0, 0);
                dayList.add(d);
            }
        } else {
            for (j = 0; j < (FIRST_DAY_OF_WEEK * 6) + 7; j++) {
                days[j] = "";
                Day d = new Day(context, 0, 0, 0);
                dayList.add(d);
            }
            j = FIRST_DAY_OF_WEEK * 6 + 1; // sunday => 1, monday => 7
        }

        // populate days
        int dayNumber = 1;

        if (j > 0 && dayList.size() > 0 && j != 1) {
            dayList.remove(j - 1);
        }

        for (int i = j - 1; i < days.length; i++) {
            Day d = new Day(context, dayNumber, year, month);

            Calendar cTemp = Calendar.getInstance();
            cTemp.set(year, month, dayNumber);
            int startDay = Time.getJulianDay(cTemp.getTimeInMillis(), TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cTemp.getTimeInMillis())));

            d.setAdapter(this);
            d.setStartDay(startDay);

            days[i] = "" + dayNumber;
            dayNumber++;
            dayList.add(d);
        }
    }

    //	public abstract static class OnAddNewEventClick{
//		public abstract void onAddNewEventClick();
//	}
    private static String quantityText;
    private static int registrationDate = 0, registeredMonth = 0, registeredYear = 0;

    public static void setQuantity(final String value) {
        quantityText = value;
    }

    public static void setRegistrationTime(final int value) {
        registrationDate = value;
    }

    static int totalQuantity;
    static boolean isForCustomers = false;
    static ArrayList<DateQuantityModel> quantityByDateList, totalData;

    public static void totalData(final int totalList) {
        totalQuantity = totalList;
    }

    public static void quantityByDate(final ArrayList<DateQuantityModel> totalList) {
        quantityByDateList = totalList;
    }

    public static void totalDataList(final ArrayList<DateQuantityModel> totalList) {
        totalData = totalList;
    }

    public static void isForCustomerDelivery(final boolean customer_delivery) {
        isForCustomers = customer_delivery;
    }

    public static void setRegisteredMonth(final int month) {
        registeredMonth = month;
    }

    static ArrayList<DateQuantityModel> customerMillkQuantity;

    public static void customersMilkQuantity(ArrayList<DateQuantityModel> list) {
        customerMillkQuantity = list;
    }

    public static void setRegisteredYear(final int year) {
        registeredYear = year;
    }

    ArrayList<String> array;
    List<Day> day = new ArrayList<>();

    private int getQuantity(Day d) {
        int quantity = 0;
        int position = 0;
        array = new ArrayList<>();
        List<String> custId = new ArrayList<>();
        if (!isForCustomers) {
            // if calendar date matches with delivery table data
            //if delivery table data is not empty get data from delivery

            if (quantityByDateList != null) {
                for (int i = 0; i < quantityByDateList.size(); i++) {
                    // Date matches
                    if (d.getDay() == Integer.parseInt(quantityByDateList.get(i).getDay())
                            && d.getMonth() == Integer.parseInt(quantityByDateList.get(i).getMonth())
                            && d.getYear() == Integer.parseInt(quantityByDateList.get(i).getYear())) {
                        quantity = Integer.parseInt(quantityByDateList.get(i).getQuantity()) + quantity;
                        custId.add(quantityByDateList.get(i).getCustomerId());
                        day.add(d);

                    } else {
                        for (int x = 0; x < totalData.size(); x++) {
                            if (custId.size() > 0) {

                                if (!custId.contains(totalData.get(x).getCustomerId()))
                                    quantity = Integer.parseInt(totalData.get(x).getQuantity()) + quantity;
                            } else {

                                quantity = Integer.parseInt(totalData.get(x).getQuantity()) + quantity;
                            }


                        }
                        return quantity;
                    }
                }
            } else {
                if (totalData != null)
                    for (int i = 0; i < totalData.size(); i++) {
                        quantity = Integer.parseInt(totalData.get(i).getQuantity()) + quantity;
                    }
                else {
                    quantity = 0;
                }
            }

            return quantity;

        } else {

            // Show quantity for customer only

            if (customerMillkQuantity != null && customerMillkQuantity.size()>0) {

                for (int i = 0; i < customerMillkQuantity.size(); i++) {
                    // Date matches
                    if (d.getDay() == Integer.parseInt(customerMillkQuantity.get(i).getDay())
                            && d.getMonth() == Integer.parseInt(customerMillkQuantity.get(i).getMonth())
                            && d.getYear() == Integer.parseInt(customerMillkQuantity.get(i).getYear())) {
                        quantity = Integer.parseInt(customerMillkQuantity.get(i).getQuantity());


                    } else {
                        quantity = totalQuantity;
                    }
                }

            }
            else {
                quantity = totalQuantity;
            }
            return quantity;

        }
    }
}
