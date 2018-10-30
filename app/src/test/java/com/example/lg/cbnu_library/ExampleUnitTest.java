package com.example.lg.cbnu_library;

import com.example.lg.cbnu_library.model.Seat;
import com.example.lg.cbnu_library.model.User;
import com.example.lg.cbnu_library.util.Global;
import com.example.lg.cbnu_library.util.NetworkConnector;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void seat_test() {
        try {
            String url = Global.getInstance().getIpAddr() + "rest/seats?library_num=" + 1 + "&format=json";
            System.out.println(url);
            String result = NetworkConnector.getInstance().get(url);
            System.out.println(result);
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(result).getAsJsonArray();

            List<Seat> seats = new ArrayList<>(array.size());
            for (JsonElement element : array) {
                Seat seat = gson.fromJson(element, Seat.class);
                seats.add(seat);
            }

            for(Seat arg : seats) {
                assertEquals(1, arg.getLibraryNum());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void user_seat_test() {
        User user = Global.getInstance().getUser();
        Seat seat = new Seat(1, 1, 1, true);
        seat.setEmpty(false);

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        String seatUrl = Global.getInstance().getIpAddr() + "rest/seats/" + seat.getId() +"/";
        String seatData = gson.toJson(seat);

        System.out.println(seatUrl);
        System.out.println(seatData);
        assertEquals(true, NetworkConnector.getInstance().put(seatUrl, seatData));

        Date startTime = new Date();
        Date endTime = new Date(System.currentTimeMillis() + 3600 * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String userSeatUrl = Global.getInstance().getIpAddr() + "rest/userseats/";
        String userSeatData = "{\"seat_id\":" + seat.getId() +
                ", \"user_id\":" + user.getId() +
                ", \"start_time\":\"" + format.format(startTime) +
                "\", \"end_time\":\"" + format.format(endTime) +
                "\"}";

        System.out.println(userSeatUrl);
        System.out.println(userSeatData);
        assertEquals(true, NetworkConnector.getInstance().post(userSeatUrl, userSeatData));
    }
}