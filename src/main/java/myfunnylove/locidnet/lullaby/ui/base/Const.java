package myfunnylove.locidnet.lullaby.ui.base;

import java.util.ArrayList;

import myfunnylove.locidnet.lullaby.M;
import myfunnylove.locidnet.lullaby.R;

/**
 * Created by Michaelan on 12/7/2016.
 */

public class Const {


    public static ArrayList<String> songNames = new ArrayList<>();
    static {
        songNames.add(M.getInstance().getResources().getString(R.string.name13));
        songNames.add(M.getInstance().getResources().getString(R.string.name12));
        songNames.add(M.getInstance().getResources().getString(R.string.name1));
        songNames.add(M.getInstance().getResources().getString(R.string.name2));
        songNames.add(M.getInstance().getResources().getString(R.string.name4));
        songNames.add(M.getInstance().getResources().getString(R.string.name5));
        songNames.add(M.getInstance().getResources().getString(R.string.name6));
        songNames.add(M.getInstance().getResources().getString(R.string.name7));
        songNames.add(M.getInstance().getResources().getString(R.string.name8));
        songNames.add(M.getInstance().getResources().getString(R.string.name9));
        songNames.add(M.getInstance().getResources().getString(R.string.name10));
        songNames.add(M.getInstance().getResources().getString(R.string.name11));
        songNames.add(M.getInstance().getResources().getString(R.string.name3));
    }

    public static ArrayList<String> songs = new ArrayList<>();

    static {
        songs.add("songs/g1.mp3");
        songs.add("songs/g2.mp3");
        songs.add("songs/g3.mp3");
        songs.add("songs/g4.mp3");
        songs.add("songs/g5.mp3");
        songs.add("songs/g6.mp3");
        songs.add("songs/g7.mp3");
        songs.add("songs/g8.mp3");
        songs.add("songs/g9.mp3");
        songs.add("songs/g10.mp3");
        songs.add("songs/g11.mp3");
        songs.add("songs/g12.mp3");
        songs.add("songs/g13.mp3");
    }


    public static ArrayList<String> duration = new ArrayList<>();

    static {
        duration.add("03:33");
        duration.add("04:01");
        duration.add("02:49");
        duration.add("03:01");
        duration.add("03:12");
        duration.add("02:21");
        duration.add("02:53");
        duration.add("03:06");
        duration.add("02:32");
        duration.add("03:20");
        duration.add("03:34");
        duration.add("03:36");
        duration.add("04:01");
    }

    public static ArrayList<Integer> songsPic = new ArrayList<>();

    static {
        songsPic.add(R.drawable.g1);
        songsPic.add(R.drawable.g2);
        songsPic.add(R.drawable.g3);
        songsPic.add(R.drawable.g4);
        songsPic.add(R.drawable.g5);
        songsPic.add(R.drawable.g6);
        songsPic.add(R.drawable.g7);
        songsPic.add(R.drawable.g8);
        songsPic.add(R.drawable.g9);
        songsPic.add(R.drawable.g10);
        songsPic.add(R.drawable.g11);
        songsPic.add(R.drawable.g12);
        songsPic.add(R.drawable.g13);
    }

    private static final long milHour = 3600000L;
    private static final long milMinute = 60000L;
    public static ArrayList<Long> timer = new ArrayList<>();
    static {
        timer.add(0L);
        timer.add(milMinute);/*15 minut*/
        timer.add(milMinute * 30);/*30 minut*/
        timer.add(milHour);/*1 soat*/
        timer.add(milHour + (milMinute * 30)); /*1 yarim soat*/
        timer.add(milHour * 2);/*2 soat*/
    }

}
