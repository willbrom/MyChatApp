package com.wilbrom.mychatapp.utils;

import java.util.ArrayList;

public class FakeData {

    private static final ArrayList dataList = new ArrayList();

    private static void addData() {
        dataList.add("Lupin Wolf");
        dataList.add("Jack Nutella");
        dataList.add("Lilly Potter");
        dataList.add("Jinny Granger");
        dataList.add("Ron Weasly");
        dataList.add("Ronald Ray");
        dataList.add("Zed Axe");
        dataList.add("Walker");
    }

    public static ArrayList getFakeData() {
        addData();
        return dataList;
    }
}
