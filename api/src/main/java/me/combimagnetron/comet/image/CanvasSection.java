package me.combimagnetron.comet.image;

import me.combimagnetron.comet.game.menu.Pos2D;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public interface CanvasSection {

    CanvasSection R00_00 = of(new String[] {"000", "000", "000"},0, 0, Pos2D.of(0, 0), '');

    CanvasSection R00_01 = of(new String[] {"100", "000", "000"},1, 1, Pos2D.of(1, 0), '');

    CanvasSection R00_02 = of(new String[] {"010", "000", "000"},1, 1, Pos2D.of(2, 0), '');

    CanvasSection R00_03 = of(new String[] {"110", "000", "000"},2, 2, Pos2D.of(3, 0), '');

    CanvasSection R00_04 = of(new String[] {"001", "000", "000"},1, 1, Pos2D.of(4, 0), '');

    CanvasSection R00_05 = of(new String[] {"101", "000", "000"},2, 3, Pos2D.of(5, 0), '');

    CanvasSection R00_06 = of(new String[] {"011", "000", "000"},2, 2, Pos2D.of(6, 0), '');

    CanvasSection R00_07 = of(new String[] {"111", "000", "000"},3, 3, Pos2D.of(7, 0), '');

    CanvasSection R00_08 = of(new String[] {"000", "100", "000"},1, 1, Pos2D.of(8, 0), '');

    CanvasSection R00_09 = of(new String[] {"100", "100", "000"},2, 1, Pos2D.of(9, 0), '');

    CanvasSection R00_10 = of(new String[] {"010", "100", "000"},2, 2, Pos2D.of(10, 0), '');

    CanvasSection R00_11 = of(new String[] {"110", "100", "000"},3, 2, Pos2D.of(11, 0), '');

    CanvasSection R00_12 = of(new String[] {"001", "100", "000"},2, 3, Pos2D.of(12, 0), '');

    CanvasSection R00_13 = of(new String[] {"101", "100", "000"},3, 3, Pos2D.of(13, 0), '');

    CanvasSection R00_14 = of(new String[] {"011", "100", "000"},3, 3, Pos2D.of(14, 0), '');

    CanvasSection R00_15 = of(new String[] {"111", "100", "000"},4, 3, Pos2D.of(15, 0), '');

    CanvasSection R01_00 = of(new String[] {"000", "010", "000"},1, 1, Pos2D.of(0, 1), '');

    CanvasSection R01_01 = of(new String[] {"100", "010", "000"},2, 2, Pos2D.of(1, 1), '');

    CanvasSection R01_02 = of(new String[] {"010", "010", "000"},2, 1, Pos2D.of(2, 1), '');

    CanvasSection R01_03 = of(new String[] {"110", "010", "000"},3, 2, Pos2D.of(3, 1), '');

    CanvasSection R01_04 = of(new String[] {"001", "010", "000"},2, 2, Pos2D.of(4, 1), '');

    CanvasSection R01_05 = of(new String[] {"101", "010", "000"},3, 3, Pos2D.of(5, 1), '');

    CanvasSection R01_06 = of(new String[] {"011", "010", "000"},3, 2, Pos2D.of(6, 1), '');

    CanvasSection R01_07 = of(new String[] {"111", "010", "000"},4, 3, Pos2D.of(7, 1), '');

    CanvasSection R01_08 = of(new String[] {"000", "110", "000"},2, 2, Pos2D.of(8, 1), '');

    CanvasSection R01_09 = of(new String[] {"100", "110", "000"},3, 2, Pos2D.of(9, 1), '');

    CanvasSection R01_10 = of(new String[] {"010", "110", "000"},3, 2, Pos2D.of(10, 1), '');

    CanvasSection R01_11 = of(new String[] {"110", "110", "000"},4, 2, Pos2D.of(11, 1), '');

    CanvasSection R01_12 = of(new String[] {"001", "110", "000"},3, 3, Pos2D.of(12, 1), '');

    CanvasSection R01_13 = of(new String[] {"101", "110", "000"},4, 3, Pos2D.of(13, 1), '');

    CanvasSection R01_14 = of(new String[] {"011", "110", "000"},4, 3, Pos2D.of(14, 1), '');

    CanvasSection R01_15 = of(new String[] {"111", "110", "000"},5, 3, Pos2D.of(15, 1), '');

    CanvasSection R02_00 = of(new String[] {"000", "001", "000"},1, 1, Pos2D.of(0, 2), '');

    CanvasSection R02_01 = of(new String[] {"100", "001", "000"},2, 3, Pos2D.of(1, 2), '');

    CanvasSection R02_02 = of(new String[] {"010", "001", "000"},2, 2, Pos2D.of(2, 2), '');

    CanvasSection R02_03 = of(new String[] {"110", "001", "000"},3, 3, Pos2D.of(3, 2), '');

    CanvasSection R02_04 = of(new String[] {"001", "001", "000"},2, 1, Pos2D.of(4, 2), '');

    CanvasSection R02_05 = of(new String[] {"101", "001", "000"},3, 3, Pos2D.of(5, 2), '');

    CanvasSection R02_06 = of(new String[] {"011", "001", "000"},3, 2, Pos2D.of(6, 2), '');

    CanvasSection R02_07 = of(new String[] {"111", "001", "000"},4, 3, Pos2D.of(7, 2), '');

    CanvasSection R02_08 = of(new String[] {"000", "101", "000"},2, 3, Pos2D.of(8, 2), '');

    CanvasSection R02_09 = of(new String[] {"100", "101", "000"},3, 3, Pos2D.of(9, 2), '');

    CanvasSection R02_10 = of(new String[] {"010", "101", "000"},3, 3, Pos2D.of(10, 2), '');

    CanvasSection R02_11 = of(new String[] {"110", "101", "000"},4, 3, Pos2D.of(11, 2), '');

    CanvasSection R02_12 = of(new String[] {"001", "101", "000"},3, 3, Pos2D.of(12, 2), '');

    CanvasSection R02_13 = of(new String[] {"101", "101", "000"},4, 3, Pos2D.of(13, 2), '');

    CanvasSection R02_14 = of(new String[] {"011", "101", "000"},4, 3, Pos2D.of(14, 2), '');

    CanvasSection R02_15 = of(new String[] {"111", "101", "000"},5, 3, Pos2D.of(15, 2), '');

    CanvasSection R03_00 = of(new String[] {"000", "011", "000"},2, 2, Pos2D.of(0, 3), '');

    CanvasSection R03_01 = of(new String[] {"100", "011", "000"},3, 3, Pos2D.of(1, 3), '');

    CanvasSection R03_02 = of(new String[] {"010", "011", "000"},3, 2, Pos2D.of(2, 3), '');

    CanvasSection R03_03 = of(new String[] {"110", "011", "000"},4, 3, Pos2D.of(3, 3), '');

    CanvasSection R03_04 = of(new String[] {"001", "011", "000"},3, 2, Pos2D.of(4, 3), '');

    CanvasSection R03_05 = of(new String[] {"101", "011", "000"},4, 3, Pos2D.of(5, 3), '');

    CanvasSection R03_06 = of(new String[] {"011", "011", "000"},4, 2, Pos2D.of(6, 3), '');

    CanvasSection R03_07 = of(new String[] {"111", "011", "000"},5, 3, Pos2D.of(7, 3), '');

    CanvasSection R03_08 = of(new String[] {"000", "111", "000"},3, 3, Pos2D.of(8, 3), '');

    CanvasSection R03_09 = of(new String[] {"100", "111", "000"},4, 3, Pos2D.of(9, 3), '');

    CanvasSection R03_10 = of(new String[] {"010", "111", "000"},4, 3, Pos2D.of(10, 3), '');

    CanvasSection R03_11 = of(new String[] {"110", "111", "000"},5, 3, Pos2D.of(11, 3), '');

    CanvasSection R03_12 = of(new String[] {"001", "111", "000"},4, 3, Pos2D.of(12, 3), '');

    CanvasSection R03_13 = of(new String[] {"101", "111", "000"},5, 3, Pos2D.of(13, 3), '');

    CanvasSection R03_14 = of(new String[] {"011", "111", "000"},5, 3, Pos2D.of(14, 3), '');

    CanvasSection R03_15 = of(new String[] {"111", "111", "000"},6, 3, Pos2D.of(15, 3), '');

    CanvasSection R04_00 = of(new String[] {"000", "000", "100"},1, 1, Pos2D.of(0, 4), '');

    CanvasSection R04_01 = of(new String[] {"100", "000", "100"},2, 1, Pos2D.of(1, 4), '');

    CanvasSection R04_02 = of(new String[] {"010", "000", "100"},2, 2, Pos2D.of(2, 4), '');

    CanvasSection R04_03 = of(new String[] {"110", "000", "100"},3, 2, Pos2D.of(3, 4), '');

    CanvasSection R04_04 = of(new String[] {"001", "000", "100"},2, 3, Pos2D.of(4, 4), '');

    CanvasSection R04_05 = of(new String[] {"101", "000", "100"},3, 3, Pos2D.of(5, 4), '');

    CanvasSection R04_06 = of(new String[] {"011", "000", "100"},3, 3, Pos2D.of(6, 4), '');

    CanvasSection R04_07 = of(new String[] {"111", "000", "100"},4, 3, Pos2D.of(7, 4), '');

    CanvasSection R04_08 = of(new String[] {"000", "100", "100"},2, 1, Pos2D.of(8, 4), '');

    CanvasSection R04_09 = of(new String[] {"100", "100", "100"},3, 1, Pos2D.of(9, 4), '');

    CanvasSection R04_10 = of(new String[] {"010", "100", "100"},3, 2, Pos2D.of(10, 4), '');

    CanvasSection R04_11 = of(new String[] {"110", "100", "100"},4, 2, Pos2D.of(11, 4), '');

    CanvasSection R04_12 = of(new String[] {"001", "100", "100"},3, 3, Pos2D.of(12, 4), '');

    CanvasSection R04_13 = of(new String[] {"101", "100", "100"},4, 3, Pos2D.of(13, 4), '');

    CanvasSection R04_14 = of(new String[] {"011", "100", "100"},4, 3, Pos2D.of(14, 4), '');

    CanvasSection R04_15 = of(new String[] {"111", "100", "100"},5, 3, Pos2D.of(15, 4), '');

    CanvasSection R05_00 = of(new String[] {"000", "010", "100"},2, 2, Pos2D.of(0, 5), '');

    CanvasSection R05_01 = of(new String[] {"100", "010", "100"},3, 2, Pos2D.of(1, 5), '');

    CanvasSection R05_02 = of(new String[] {"010", "010", "100"},3, 2, Pos2D.of(2, 5), '');

    CanvasSection R05_03 = of(new String[] {"110", "010", "100"},4, 2, Pos2D.of(3, 5), '');

    CanvasSection R05_04 = of(new String[] {"001", "010", "100"},3, 3, Pos2D.of(4, 5), '');

    CanvasSection R05_05 = of(new String[] {"101", "010", "100"},4, 3, Pos2D.of(5, 5), '');

    CanvasSection R05_06 = of(new String[] {"011", "010", "100"},4, 3, Pos2D.of(6, 5), '');

    CanvasSection R05_07 = of(new String[] {"111", "010", "100"},5, 3, Pos2D.of(7, 5), '');

    CanvasSection R05_08 = of(new String[] {"000", "110", "100"},3, 2, Pos2D.of(8, 5), '');

    CanvasSection R05_09 = of(new String[] {"100", "110", "100"},4, 2, Pos2D.of(9, 5), '');

    CanvasSection R05_10 = of(new String[] {"010", "110", "100"},4, 2, Pos2D.of(10, 5), '');

    CanvasSection R05_11 = of(new String[] {"110", "110", "100"},5, 2, Pos2D.of(11, 5), '');

    CanvasSection R05_12 = of(new String[] {"001", "110", "100"},4, 3, Pos2D.of(12, 5), '');

    CanvasSection R05_13 = of(new String[] {"101", "110", "100"},5, 3, Pos2D.of(13, 5), '');

    CanvasSection R05_14 = of(new String[] {"011", "110", "100"},5, 3, Pos2D.of(14, 5), '');

    CanvasSection R05_15 = of(new String[] {"111", "110", "100"},6, 3, Pos2D.of(15, 5), '');

    CanvasSection R06_00 = of(new String[] {"000", "001", "100"},2, 3, Pos2D.of(0, 6), '');

    CanvasSection R06_01 = of(new String[] {"100", "001", "100"},3, 3, Pos2D.of(1, 6), '');

    CanvasSection R06_02 = of(new String[] {"010", "001", "100"},3, 3, Pos2D.of(2, 6), '');

    CanvasSection R06_03 = of(new String[] {"110", "001", "100"},4, 3, Pos2D.of(3, 6), '');

    CanvasSection R06_04 = of(new String[] {"001", "001", "100"},3, 3, Pos2D.of(4, 6), '');

    CanvasSection R06_05 = of(new String[] {"101", "001", "100"},4, 3, Pos2D.of(5, 6), '');

    CanvasSection R06_06 = of(new String[] {"011", "001", "100"},4, 3, Pos2D.of(6, 6), '');

    CanvasSection R06_07 = of(new String[] {"111", "001", "100"},5, 3, Pos2D.of(7, 6), '');

    CanvasSection R06_08 = of(new String[] {"000", "101", "100"},3, 3, Pos2D.of(8, 6), '');

    CanvasSection R06_09 = of(new String[] {"100", "101", "100"},4, 3, Pos2D.of(9, 6), '');

    CanvasSection R06_10 = of(new String[] {"010", "101", "100"},4, 3, Pos2D.of(10, 6), '');

    CanvasSection R06_11 = of(new String[] {"110", "101", "100"},5, 3, Pos2D.of(11, 6), '');

    CanvasSection R06_12 = of(new String[] {"001", "101", "100"},4, 3, Pos2D.of(12, 6), '');

    CanvasSection R06_13 = of(new String[] {"101", "101", "100"},5, 3, Pos2D.of(13, 6), '');

    CanvasSection R06_14 = of(new String[] {"011", "101", "100"},5, 3, Pos2D.of(14, 6), '');

    CanvasSection R06_15 = of(new String[] {"111", "101", "100"},6, 3, Pos2D.of(15, 6), '');

    CanvasSection R07_00 = of(new String[] {"000", "011", "100"},3, 3, Pos2D.of(0, 7), '');

    CanvasSection R07_01 = of(new String[] {"100", "011", "100"},4, 3, Pos2D.of(1, 7), '');

    CanvasSection R07_02 = of(new String[] {"010", "011", "100"},4, 3, Pos2D.of(2, 7), '');

    CanvasSection R07_03 = of(new String[] {"110", "011", "100"},5, 3, Pos2D.of(3, 7), '');

    CanvasSection R07_04 = of(new String[] {"001", "011", "100"},4, 3, Pos2D.of(4, 7), '');

    CanvasSection R07_05 = of(new String[] {"101", "011", "100"},5, 3, Pos2D.of(5, 7), '');

    CanvasSection R07_06 = of(new String[] {"011", "011", "100"},5, 3, Pos2D.of(6, 7), '');

    CanvasSection R07_07 = of(new String[] {"111", "011", "100"},6, 3, Pos2D.of(7, 7), '');

    CanvasSection R07_08 = of(new String[] {"000", "111", "100"},4, 3, Pos2D.of(8, 7), '');

    CanvasSection R07_09 = of(new String[] {"100", "111", "100"},5, 3, Pos2D.of(9, 7), '');

    CanvasSection R07_10 = of(new String[] {"010", "111", "100"},5, 3, Pos2D.of(10, 7), '');

    CanvasSection R07_11 = of(new String[] {"110", "111", "100"},6, 3, Pos2D.of(11, 7), '');

    CanvasSection R07_12 = of(new String[] {"001", "111", "100"},5, 3, Pos2D.of(12, 7), '');

    CanvasSection R07_13 = of(new String[] {"101", "111", "100"},6, 3, Pos2D.of(13, 7), '');

    CanvasSection R07_14 = of(new String[] {"011", "111", "100"},6, 3, Pos2D.of(14, 7), '');

    CanvasSection R07_15 = of(new String[] {"111", "111", "100"},7, 3, Pos2D.of(15, 7), '');

    CanvasSection R08_00 = of(new String[] {"000", "000", "010"},1, 1, Pos2D.of(0, 8), '');

    CanvasSection R08_01 = of(new String[] {"100", "000", "010"},2, 2, Pos2D.of(1, 8), '');

    CanvasSection R08_02 = of(new String[] {"010", "000", "010"},2, 1, Pos2D.of(2, 8), '');

    CanvasSection R08_03 = of(new String[] {"110", "000", "010"},3, 2, Pos2D.of(3, 8), '');

    CanvasSection R08_04 = of(new String[] {"001", "000", "010"},2, 2, Pos2D.of(4, 8), '');

    CanvasSection R08_05 = of(new String[] {"101", "000", "010"},3, 3, Pos2D.of(5, 8), '');

    CanvasSection R08_06 = of(new String[] {"011", "000", "010"},3, 2, Pos2D.of(6, 8), '');

    CanvasSection R08_07 = of(new String[] {"111", "000", "010"},4, 3, Pos2D.of(7, 8), '');

    CanvasSection R08_08 = of(new String[] {"000", "100", "010"},2, 2, Pos2D.of(8, 8), '');

    CanvasSection R08_09 = of(new String[] {"100", "100", "010"},3, 2, Pos2D.of(9, 8), '');

    CanvasSection R08_10 = of(new String[] {"010", "100", "010"},3, 2, Pos2D.of(10, 8), '');

    CanvasSection R08_11 = of(new String[] {"110", "100", "010"},4, 2, Pos2D.of(11, 8), '');

    CanvasSection R08_12 = of(new String[] {"001", "100", "010"},3, 3, Pos2D.of(12, 8), '');

    CanvasSection R08_13 = of(new String[] {"101", "100", "010"},4, 3, Pos2D.of(13, 8), '');

    CanvasSection R08_14 = of(new String[] {"011", "100", "010"},4, 3, Pos2D.of(14, 8), '');

    CanvasSection R08_15 = of(new String[] {"111", "100", "010"},5, 3, Pos2D.of(15, 8), '');

    CanvasSection R09_00 = of(new String[] {"000", "010", "010"},2, 1, Pos2D.of(0, 9), '');

    CanvasSection R09_01 = of(new String[] {"100", "010", "010"},3, 2, Pos2D.of(1, 9), '');

    CanvasSection R09_02 = of(new String[] {"010", "010", "010"},3, 1, Pos2D.of(2, 9), '');

    CanvasSection R09_03 = of(new String[] {"110", "010", "010"},4, 2, Pos2D.of(3, 9), '');

    CanvasSection R09_04 = of(new String[] {"001", "010", "010"},3, 2, Pos2D.of(4, 9), '');

    CanvasSection R09_05 = of(new String[] {"101", "010", "010"},4, 3, Pos2D.of(5, 9), '');

    CanvasSection R09_06 = of(new String[] {"011", "010", "010"},4, 2, Pos2D.of(6, 9), '');

    CanvasSection R09_07 = of(new String[] {"111", "010", "010"},5, 3, Pos2D.of(7, 9), '');

    CanvasSection R09_08 = of(new String[] {"000", "110", "010"},3, 2, Pos2D.of(8, 9), '');

    CanvasSection R09_09 = of(new String[] {"100", "110", "010"},4, 2, Pos2D.of(9, 9), '');

    CanvasSection R09_10 = of(new String[] {"010", "110", "010"},4, 2, Pos2D.of(10, 9), '');

    CanvasSection R09_11 = of(new String[] {"110", "110", "010"},5, 2, Pos2D.of(11, 9), '');

    CanvasSection R09_12 = of(new String[] {"001", "110", "010"},4, 3, Pos2D.of(12, 9), '');

    CanvasSection R09_13 = of(new String[] {"101", "110", "010"},5, 3, Pos2D.of(13, 9), '');

    CanvasSection R09_14 = of(new String[] {"011", "110", "010"},5, 3, Pos2D.of(14, 9), '');

    CanvasSection R09_15 = of(new String[] {"111", "110", "010"},6, 3, Pos2D.of(15, 9), '');

    CanvasSection R10_00 = of(new String[] {"000", "001", "010"},2, 2, Pos2D.of(0, 10), '');

    CanvasSection R10_01 = of(new String[] {"100", "001", "010"},3, 3, Pos2D.of(1, 10), '');

    CanvasSection R10_02 = of(new String[] {"010", "001", "010"},3, 2, Pos2D.of(2, 10), '');

    CanvasSection R10_03 = of(new String[] {"110", "001", "010"},4, 3, Pos2D.of(3, 10), '');

    CanvasSection R10_04 = of(new String[] {"001", "001", "010"},3, 2, Pos2D.of(4, 10), '');

    CanvasSection R10_05 = of(new String[] {"101", "001", "010"},4, 3, Pos2D.of(5, 10), '');

    CanvasSection R10_06 = of(new String[] {"011", "001", "010"},4, 2, Pos2D.of(6, 10), '');

    CanvasSection R10_07 = of(new String[] {"111", "001", "010"},5, 3, Pos2D.of(7, 10), '');

    CanvasSection R10_08 = of(new String[] {"000", "101", "010"},3, 3, Pos2D.of(8, 10), '');

    CanvasSection R10_09 = of(new String[] {"100", "101", "010"},4, 3, Pos2D.of(9, 10), '');

    CanvasSection R10_10 = of(new String[] {"010", "101", "010"},4, 3, Pos2D.of(10, 10), '');

    CanvasSection R10_11 = of(new String[] {"110", "101", "010"},5, 3, Pos2D.of(11, 10), '');

    CanvasSection R10_12 = of(new String[] {"001", "101", "010"},4, 3, Pos2D.of(12, 10), '');

    CanvasSection R10_13 = of(new String[] {"101", "101", "010"},5, 3, Pos2D.of(13, 10), '');

    CanvasSection R10_14 = of(new String[] {"011", "101", "010"},5, 3, Pos2D.of(14, 10), '');

    CanvasSection R10_15 = of(new String[] {"111", "101", "010"},6, 3, Pos2D.of(15, 10), '');

    CanvasSection R11_00 = of(new String[] {"000", "011", "010"},3, 2, Pos2D.of(0, 11), '');

    CanvasSection R11_01 = of(new String[] {"100", "011", "010"},4, 3, Pos2D.of(1, 11), '');

    CanvasSection R11_02 = of(new String[] {"010", "011", "010"},4, 2, Pos2D.of(2, 11), '');

    CanvasSection R11_03 = of(new String[] {"110", "011", "010"},5, 3, Pos2D.of(3, 11), '');

    CanvasSection R11_04 = of(new String[] {"001", "011", "010"},4, 2, Pos2D.of(4, 11), '');

    CanvasSection R11_05 = of(new String[] {"101", "011", "010"},5, 3, Pos2D.of(5, 11), '');

    CanvasSection R11_06 = of(new String[] {"011", "011", "010"},5, 2, Pos2D.of(6, 11), '');

    CanvasSection R11_07 = of(new String[] {"111", "011", "010"},6, 3, Pos2D.of(7, 11), '');

    CanvasSection R11_08 = of(new String[] {"000", "111", "010"},4, 3, Pos2D.of(8, 11), '');

    CanvasSection R11_09 = of(new String[] {"100", "111", "010"},5, 3, Pos2D.of(9, 11), '');

    CanvasSection R11_10 = of(new String[] {"010", "111", "010"},5, 3, Pos2D.of(10, 11), '');

    CanvasSection R11_11 = of(new String[] {"110", "111", "010"},6, 3, Pos2D.of(11, 11), '');

    CanvasSection R11_12 = of(new String[] {"001", "111", "010"},5, 3, Pos2D.of(12, 11), '');

    CanvasSection R11_13 = of(new String[] {"101", "111", "010"},6, 3, Pos2D.of(13, 11), '');

    CanvasSection R11_14 = of(new String[] {"011", "111", "010"},6, 3, Pos2D.of(14, 11), '');

    CanvasSection R11_15 = of(new String[] {"111", "111", "010"},7, 3, Pos2D.of(15, 11), '');

    CanvasSection R12_00 = of(new String[] {"000", "000", "110"},2, 2, Pos2D.of(0, 12), '');

    CanvasSection R12_01 = of(new String[] {"100", "000", "110"},3, 2, Pos2D.of(1, 12), '');

    CanvasSection R12_02 = of(new String[] {"010", "000", "110"},3, 2, Pos2D.of(2, 12), '');

    CanvasSection R12_03 = of(new String[] {"110", "000", "110"},4, 2, Pos2D.of(3, 12), '');

    CanvasSection R12_04 = of(new String[] {"001", "000", "110"},3, 3, Pos2D.of(4, 12), '');

    CanvasSection R12_05 = of(new String[] {"101", "000", "110"},4, 3, Pos2D.of(5, 12), '');

    CanvasSection R12_06 = of(new String[] {"011", "000", "110"},4, 3, Pos2D.of(6, 12), '');

    CanvasSection R12_07 = of(new String[] {"111", "000", "110"},5, 3, Pos2D.of(7, 12), '');

    CanvasSection R12_08 = of(new String[] {"000", "100", "110"},3, 2, Pos2D.of(8, 12), '');

    CanvasSection R12_09 = of(new String[] {"100", "100", "110"},4, 2, Pos2D.of(9, 12), '');

    CanvasSection R12_10 = of(new String[] {"010", "100", "110"},4, 2, Pos2D.of(10, 12), '');

    CanvasSection R12_11 = of(new String[] {"110", "100", "110"},5, 2, Pos2D.of(11, 12), '');

    CanvasSection R12_12 = of(new String[] {"001", "100", "110"},4, 3, Pos2D.of(12, 12), '');

    CanvasSection R12_13 = of(new String[] {"101", "100", "110"},5, 3, Pos2D.of(13, 12), '');

    CanvasSection R12_14 = of(new String[] {"011", "100", "110"},5, 3, Pos2D.of(14, 12), '');

    CanvasSection R12_15 = of(new String[] {"111", "100", "110"},6, 3, Pos2D.of(15, 12), '');

    CanvasSection R13_00 = of(new String[] {"000", "010", "110"},3, 2, Pos2D.of(0, 13), '');

    CanvasSection R13_01 = of(new String[] {"100", "010", "110"},4, 2, Pos2D.of(1, 13), '');

    CanvasSection R13_02 = of(new String[] {"010", "010", "110"},4, 2, Pos2D.of(2, 13), '');

    CanvasSection R13_03 = of(new String[] {"110", "010", "110"},5, 2, Pos2D.of(3, 13), '');

    CanvasSection R13_04 = of(new String[] {"001", "010", "110"},4, 3, Pos2D.of(4, 13), '');

    CanvasSection R13_05 = of(new String[] {"101", "010", "110"},5, 3, Pos2D.of(5, 13), '');

    CanvasSection R13_06 = of(new String[] {"011", "010", "110"},5, 3, Pos2D.of(6, 13), '');

    CanvasSection R13_07 = of(new String[] {"111", "010", "110"},6, 3, Pos2D.of(7, 13), '');

    CanvasSection R13_08 = of(new String[] {"000", "110", "110"},4, 2, Pos2D.of(8, 13), '');

    CanvasSection R13_09 = of(new String[] {"100", "110", "110"},5, 2, Pos2D.of(9, 13), '');

    CanvasSection R13_10 = of(new String[] {"010", "110", "110"},5, 2, Pos2D.of(10, 13), '');

    CanvasSection R13_11 = of(new String[] {"110", "110", "110"},6, 2, Pos2D.of(11, 13), '');

    CanvasSection R13_12 = of(new String[] {"001", "110", "110"},5, 3, Pos2D.of(12, 13), '');

    CanvasSection R13_13 = of(new String[] {"101", "110", "110"},6, 3, Pos2D.of(13, 13), '');

    CanvasSection R13_14 = of(new String[] {"011", "110", "110"},6, 3, Pos2D.of(14, 13), '');

    CanvasSection R13_15 = of(new String[] {"111", "110", "110"},7, 3, Pos2D.of(15, 13), '');

    CanvasSection R14_00 = of(new String[] {"000", "001", "110"},3, 3, Pos2D.of(0, 14), '');

    CanvasSection R14_01 = of(new String[] {"100", "001", "110"},4, 3, Pos2D.of(1, 14), '');

    CanvasSection R14_02 = of(new String[] {"010", "001", "110"},4, 3, Pos2D.of(2, 14), '');

    CanvasSection R14_03 = of(new String[] {"110", "001", "110"},5, 3, Pos2D.of(3, 14), '');

    CanvasSection R14_04 = of(new String[] {"001", "001", "110"},4, 3, Pos2D.of(4, 14), '');

    CanvasSection R14_05 = of(new String[] {"101", "001", "110"},5, 3, Pos2D.of(5, 14), '');

    CanvasSection R14_06 = of(new String[] {"011", "001", "110"},5, 3, Pos2D.of(6, 14), '');

    CanvasSection R14_07 = of(new String[] {"111", "001", "110"},6, 3, Pos2D.of(7, 14), '');

    CanvasSection R14_08 = of(new String[] {"000", "101", "110"},4, 3, Pos2D.of(8, 14), '');

    CanvasSection R14_09 = of(new String[] {"100", "101", "110"},5, 3, Pos2D.of(9, 14), '');

    CanvasSection R14_10 = of(new String[] {"010", "101", "110"},5, 3, Pos2D.of(10, 14), '');

    CanvasSection R14_11 = of(new String[] {"110", "101", "110"},6, 3, Pos2D.of(11, 14), '');

    CanvasSection R14_12 = of(new String[] {"001", "101", "110"},5, 3, Pos2D.of(12, 14), '');

    CanvasSection R14_13 = of(new String[] {"101", "101", "110"},6, 3, Pos2D.of(13, 14), '');

    CanvasSection R14_14 = of(new String[] {"011", "101", "110"},6, 3, Pos2D.of(14, 14), '');

    CanvasSection R14_15 = of(new String[] {"111", "101", "110"},7, 3, Pos2D.of(15, 14), '');

    CanvasSection R15_00 = of(new String[] {"000", "011", "110"},4, 3, Pos2D.of(0, 15), '');

    CanvasSection R15_01 = of(new String[] {"100", "011", "110"},5, 3, Pos2D.of(1, 15), '');

    CanvasSection R15_02 = of(new String[] {"010", "011", "110"},5, 3, Pos2D.of(2, 15), '');

    CanvasSection R15_03 = of(new String[] {"110", "011", "110"},6, 3, Pos2D.of(3, 15), '');

    CanvasSection R15_04 = of(new String[] {"001", "011", "110"},5, 3, Pos2D.of(4, 15), '');

    CanvasSection R15_05 = of(new String[] {"101", "011", "110"},6, 3, Pos2D.of(5, 15), '');

    CanvasSection R15_06 = of(new String[] {"011", "011", "110"},6, 3, Pos2D.of(6, 15), '');

    CanvasSection R15_07 = of(new String[] {"111", "011", "110"},7, 3, Pos2D.of(7, 15), '');

    CanvasSection R15_08 = of(new String[] {"000", "111", "110"},5, 3, Pos2D.of(8, 15), '');

    CanvasSection R15_09 = of(new String[] {"100", "111", "110"},6, 3, Pos2D.of(9, 15), '');

    CanvasSection R15_10 = of(new String[] {"010", "111", "110"},6, 3, Pos2D.of(10, 15), '');

    CanvasSection R15_11 = of(new String[] {"110", "111", "110"},7, 3, Pos2D.of(11, 15), '');

    CanvasSection R15_12 = of(new String[] {"001", "111", "110"},6, 3, Pos2D.of(12, 15), '');

    CanvasSection R15_13 = of(new String[] {"101", "111", "110"},7, 3, Pos2D.of(13, 15), '');

    CanvasSection R15_14 = of(new String[] {"011", "111", "110"},7, 3, Pos2D.of(14, 15), '');

    CanvasSection R15_15 = of(new String[] {"111", "111", "110"},8, 3, Pos2D.of(15, 15), '');

    CanvasSection R16_00 = of(new String[] {"000", "000", "001"},1, 1, Pos2D.of(0, 16), '');

    CanvasSection R16_01 = of(new String[] {"100", "000", "001"},2, 3, Pos2D.of(1, 16), '');

    CanvasSection R16_02 = of(new String[] {"010", "000", "001"},2, 2, Pos2D.of(2, 16), '');

    CanvasSection R16_03 = of(new String[] {"110", "000", "001"},3, 3, Pos2D.of(3, 16), '');

    CanvasSection R16_04 = of(new String[] {"001", "000", "001"},2, 1, Pos2D.of(4, 16), '');

    CanvasSection R16_05 = of(new String[] {"101", "000", "001"},3, 3, Pos2D.of(5, 16), '');

    CanvasSection R16_06 = of(new String[] {"011", "000", "001"},3, 2, Pos2D.of(6, 16), '');

    CanvasSection R16_07 = of(new String[] {"111", "000", "001"},4, 3, Pos2D.of(7, 16), '');

    CanvasSection R16_08 = of(new String[] {"000", "100", "001"},2, 3, Pos2D.of(8, 16), '');

    CanvasSection R16_09 = of(new String[] {"100", "100", "001"},3, 3, Pos2D.of(9, 16), '');

    CanvasSection R16_10 = of(new String[] {"010", "100", "001"},3, 3, Pos2D.of(10, 16), '');

    CanvasSection R16_11 = of(new String[] {"110", "100", "001"},4, 3, Pos2D.of(11, 16), '');

    CanvasSection R16_12 = of(new String[] {"001", "100", "001"},3, 3, Pos2D.of(12, 16), '');

    CanvasSection R16_13 = of(new String[] {"101", "100", "001"},4, 3, Pos2D.of(13, 16), '');

    CanvasSection R16_14 = of(new String[] {"011", "100", "001"},4, 3, Pos2D.of(14, 16), '');

    CanvasSection R16_15 = of(new String[] {"111", "100", "001"},5, 3, Pos2D.of(15, 16), '');

    CanvasSection R17_00 = of(new String[] {"000", "010", "001"},2, 2, Pos2D.of(0, 17), '');

    CanvasSection R17_01 = of(new String[] {"100", "010", "001"},3, 3, Pos2D.of(1, 17), '');

    CanvasSection R17_02 = of(new String[] {"010", "010", "001"},3, 2, Pos2D.of(2, 17), '');

    CanvasSection R17_03 = of(new String[] {"110", "010", "001"},4, 3, Pos2D.of(3, 17), '');

    CanvasSection R17_04 = of(new String[] {"001", "010", "001"},3, 2, Pos2D.of(4, 17), '');

    CanvasSection R17_05 = of(new String[] {"101", "010", "001"},4, 3, Pos2D.of(5, 17), '');

    CanvasSection R17_06 = of(new String[] {"011", "010", "001"},4, 2, Pos2D.of(6, 17), '');

    CanvasSection R17_07 = of(new String[] {"111", "010", "001"},5, 3, Pos2D.of(7, 17), '');

    CanvasSection R17_08 = of(new String[] {"000", "110", "001"},3, 3, Pos2D.of(8, 17), '');

    CanvasSection R17_09 = of(new String[] {"100", "110", "001"},4, 3, Pos2D.of(9, 17), '');

    CanvasSection R17_10 = of(new String[] {"010", "110", "001"},4, 3, Pos2D.of(10, 17), '');

    CanvasSection R17_11 = of(new String[] {"110", "110", "001"},5, 3, Pos2D.of(11, 17), '');

    CanvasSection R17_12 = of(new String[] {"001", "110", "001"},4, 3, Pos2D.of(12, 17), '');

    CanvasSection R17_13 = of(new String[] {"101", "110", "001"},5, 3, Pos2D.of(13, 17), '');

    CanvasSection R17_14 = of(new String[] {"011", "110", "001"},5, 3, Pos2D.of(14, 17), '');

    CanvasSection R17_15 = of(new String[] {"111", "110", "001"},6, 3, Pos2D.of(15, 17), '');

    CanvasSection R18_00 = of(new String[] {"000", "001", "001"},2, 1, Pos2D.of(0, 18), '');

    CanvasSection R18_01 = of(new String[] {"100", "001", "001"},3, 3, Pos2D.of(1, 18), '');

    CanvasSection R18_02 = of(new String[] {"010", "001", "001"},3, 2, Pos2D.of(2, 18), '');

    CanvasSection R18_03 = of(new String[] {"110", "001", "001"},4, 3, Pos2D.of(3, 18), '');

    CanvasSection R18_04 = of(new String[] {"001", "001", "001"},3, 1, Pos2D.of(4, 18), '');

    CanvasSection R18_05 = of(new String[] {"101", "001", "001"},4, 3, Pos2D.of(5, 18), '');

    CanvasSection R18_06 = of(new String[] {"011", "001", "001"},4, 2, Pos2D.of(6, 18), '');

    CanvasSection R18_07 = of(new String[] {"111", "001", "001"},5, 3, Pos2D.of(7, 18), '');

    CanvasSection R18_08 = of(new String[] {"000", "101", "001"},3, 3, Pos2D.of(8, 18), '');

    CanvasSection R18_09 = of(new String[] {"100", "101", "001"},4, 3, Pos2D.of(9, 18), '');

    CanvasSection R18_10 = of(new String[] {"010", "101", "001"},4, 3, Pos2D.of(10, 18), '');

    CanvasSection R18_11 = of(new String[] {"110", "101", "001"},5, 3, Pos2D.of(11, 18), '');

    CanvasSection R18_12 = of(new String[] {"001", "101", "001"},4, 3, Pos2D.of(12, 18), '');

    CanvasSection R18_13 = of(new String[] {"101", "101", "001"},5, 3, Pos2D.of(13, 18), '');

    CanvasSection R18_14 = of(new String[] {"011", "101", "001"},5, 3, Pos2D.of(14, 18), '');

    CanvasSection R18_15 = of(new String[] {"111", "101", "001"},6, 3, Pos2D.of(15, 18), '');

    CanvasSection R19_00 = of(new String[] {"000", "011", "001"},3, 2, Pos2D.of(0, 19), '');

    CanvasSection R19_01 = of(new String[] {"100", "011", "001"},4, 3, Pos2D.of(1, 19), '');

    CanvasSection R19_02 = of(new String[] {"010", "011", "001"},4, 2, Pos2D.of(2, 19), '');

    CanvasSection R19_03 = of(new String[] {"110", "011", "001"},5, 3, Pos2D.of(3, 19), '');

    CanvasSection R19_04 = of(new String[] {"001", "011", "001"},4, 2, Pos2D.of(4, 19), '');

    CanvasSection R19_05 = of(new String[] {"101", "011", "001"},5, 3, Pos2D.of(5, 19), '');

    CanvasSection R19_06 = of(new String[] {"011", "011", "001"},5, 2, Pos2D.of(6, 19), '');

    CanvasSection R19_07 = of(new String[] {"111", "011", "001"},6, 3, Pos2D.of(7, 19), '');

    CanvasSection R19_08 = of(new String[] {"000", "111", "001"},4, 3, Pos2D.of(8, 19), '');

    CanvasSection R19_09 = of(new String[] {"100", "111", "001"},5, 3, Pos2D.of(9, 19), '');

    CanvasSection R19_10 = of(new String[] {"010", "111", "001"},5, 3, Pos2D.of(10, 19), '');

    CanvasSection R19_11 = of(new String[] {"110", "111", "001"},6, 3, Pos2D.of(11, 19), '');

    CanvasSection R19_12 = of(new String[] {"001", "111", "001"},5, 3, Pos2D.of(12, 19), '');

    CanvasSection R19_13 = of(new String[] {"101", "111", "001"},6, 3, Pos2D.of(13, 19), '');

    CanvasSection R19_14 = of(new String[] {"011", "111", "001"},6, 3, Pos2D.of(14, 19), '');

    CanvasSection R19_15 = of(new String[] {"111", "111", "001"},7, 3, Pos2D.of(15, 19), '');

    CanvasSection R20_00 = of(new String[] {"000", "000", "101"},2, 3, Pos2D.of(0, 20), '');

    CanvasSection R20_01 = of(new String[] {"100", "000", "101"},3, 3, Pos2D.of(1, 20), '');

    CanvasSection R20_02 = of(new String[] {"010", "000", "101"},3, 3, Pos2D.of(2, 20), '');

    CanvasSection R20_03 = of(new String[] {"110", "000", "101"},4, 3, Pos2D.of(3, 20), '');

    CanvasSection R20_04 = of(new String[] {"001", "000", "101"},3, 3, Pos2D.of(4, 20), '');

    CanvasSection R20_05 = of(new String[] {"101", "000", "101"},4, 3, Pos2D.of(5, 20), '');

    CanvasSection R20_06 = of(new String[] {"011", "000", "101"},4, 3, Pos2D.of(6, 20), '');

    CanvasSection R20_07 = of(new String[] {"111", "000", "101"},5, 3, Pos2D.of(7, 20), '');

    CanvasSection R20_08 = of(new String[] {"000", "100", "101"},3, 3, Pos2D.of(8, 20), '');

    CanvasSection R20_09 = of(new String[] {"100", "100", "101"},4, 3, Pos2D.of(9, 20), '');

    CanvasSection R20_10 = of(new String[] {"010", "100", "101"},4, 3, Pos2D.of(10, 20), '');

    CanvasSection R20_11 = of(new String[] {"110", "100", "101"},5, 3, Pos2D.of(11, 20), '');

    CanvasSection R20_12 = of(new String[] {"001", "100", "101"},4, 3, Pos2D.of(12, 20), '');

    CanvasSection R20_13 = of(new String[] {"101", "100", "101"},5, 3, Pos2D.of(13, 20), '');

    CanvasSection R20_14 = of(new String[] {"011", "100", "101"},5, 3, Pos2D.of(14, 20), '');

    CanvasSection R20_15 = of(new String[] {"111", "100", "101"},6, 3, Pos2D.of(15, 20), '');

    CanvasSection R21_00 = of(new String[] {"000", "010", "101"},3, 3, Pos2D.of(0, 21), '');

    CanvasSection R21_01 = of(new String[] {"100", "010", "101"},4, 3, Pos2D.of(1, 21), '');

    CanvasSection R21_02 = of(new String[] {"010", "010", "101"},4, 3, Pos2D.of(2, 21), '');

    CanvasSection R21_03 = of(new String[] {"110", "010", "101"},5, 3, Pos2D.of(3, 21), '');

    CanvasSection R21_04 = of(new String[] {"001", "010", "101"},4, 3, Pos2D.of(4, 21), '');

    CanvasSection R21_05 = of(new String[] {"101", "010", "101"},5, 3, Pos2D.of(5, 21), '');

    CanvasSection R21_06 = of(new String[] {"011", "010", "101"},5, 3, Pos2D.of(6, 21), '');

    CanvasSection R21_07 = of(new String[] {"111", "010", "101"},6, 3, Pos2D.of(7, 21), '');

    CanvasSection R21_08 = of(new String[] {"000", "110", "101"},4, 3, Pos2D.of(8, 21), '');

    CanvasSection R21_09 = of(new String[] {"100", "110", "101"},5, 3, Pos2D.of(9, 21), '');

    CanvasSection R21_10 = of(new String[] {"010", "110", "101"},5, 3, Pos2D.of(10, 21), '');

    CanvasSection R21_11 = of(new String[] {"110", "110", "101"},6, 3, Pos2D.of(11, 21), '');

    CanvasSection R21_12 = of(new String[] {"001", "110", "101"},5, 3, Pos2D.of(12, 21), '');

    CanvasSection R21_13 = of(new String[] {"101", "110", "101"},6, 3, Pos2D.of(13, 21), '');

    CanvasSection R21_14 = of(new String[] {"011", "110", "101"},6, 3, Pos2D.of(14, 21), '');

    CanvasSection R21_15 = of(new String[] {"111", "110", "101"},7, 3, Pos2D.of(15, 21), '');

    CanvasSection R22_00 = of(new String[] {"000", "001", "101"},3, 3, Pos2D.of(0, 22), '');

    CanvasSection R22_01 = of(new String[] {"100", "001", "101"},4, 3, Pos2D.of(1, 22), '');

    CanvasSection R22_02 = of(new String[] {"010", "001", "101"},4, 3, Pos2D.of(2, 22), '');

    CanvasSection R22_03 = of(new String[] {"110", "001", "101"},5, 3, Pos2D.of(3, 22), '');

    CanvasSection R22_04 = of(new String[] {"001", "001", "101"},4, 3, Pos2D.of(4, 22), '');

    CanvasSection R22_05 = of(new String[] {"101", "001", "101"},5, 3, Pos2D.of(5, 22), '');

    CanvasSection R22_06 = of(new String[] {"011", "001", "101"},5, 3, Pos2D.of(6, 22), '');

    CanvasSection R22_07 = of(new String[] {"111", "001", "101"},6, 3, Pos2D.of(7, 22), '');

    CanvasSection R22_08 = of(new String[] {"000", "101", "101"},4, 3, Pos2D.of(8, 22), '');

    CanvasSection R22_09 = of(new String[] {"100", "101", "101"},5, 3, Pos2D.of(9, 22), '');

    CanvasSection R22_10 = of(new String[] {"010", "101", "101"},5, 3, Pos2D.of(10, 22), '');

    CanvasSection R22_11 = of(new String[] {"110", "101", "101"},6, 3, Pos2D.of(11, 22), '');

    CanvasSection R22_12 = of(new String[] {"001", "101", "101"},5, 3, Pos2D.of(12, 22), '');

    CanvasSection R22_13 = of(new String[] {"101", "101", "101"},6, 3, Pos2D.of(13, 22), '');

    CanvasSection R22_14 = of(new String[] {"011", "101", "101"},6, 3, Pos2D.of(14, 22), '');

    CanvasSection R22_15 = of(new String[] {"111", "101", "101"},7, 3, Pos2D.of(15, 22), '');

    CanvasSection R23_00 = of(new String[] {"000", "011", "101"},4, 3, Pos2D.of(0, 23), '');

    CanvasSection R23_01 = of(new String[] {"100", "011", "101"},5, 3, Pos2D.of(1, 23), '');

    CanvasSection R23_02 = of(new String[] {"010", "011", "101"},5, 3, Pos2D.of(2, 23), '');

    CanvasSection R23_03 = of(new String[] {"110", "011", "101"},6, 3, Pos2D.of(3, 23), '');

    CanvasSection R23_04 = of(new String[] {"001", "011", "101"},5, 3, Pos2D.of(4, 23), '');

    CanvasSection R23_05 = of(new String[] {"101", "011", "101"},6, 3, Pos2D.of(5, 23), '');

    CanvasSection R23_06 = of(new String[] {"011", "011", "101"},6, 3, Pos2D.of(6, 23), '');

    CanvasSection R23_07 = of(new String[] {"111", "011", "101"},7, 3, Pos2D.of(7, 23), '');

    CanvasSection R23_08 = of(new String[] {"000", "111", "101"},5, 3, Pos2D.of(8, 23), '');

    CanvasSection R23_09 = of(new String[] {"100", "111", "101"},6, 3, Pos2D.of(9, 23), '');

    CanvasSection R23_10 = of(new String[] {"010", "111", "101"},6, 3, Pos2D.of(10, 23), '');

    CanvasSection R23_11 = of(new String[] {"110", "111", "101"},7, 3, Pos2D.of(11, 23), '');

    CanvasSection R23_12 = of(new String[] {"001", "111", "101"},6, 3, Pos2D.of(12, 23), '');

    CanvasSection R23_13 = of(new String[] {"101", "111", "101"},7, 3, Pos2D.of(13, 23), '');

    CanvasSection R23_14 = of(new String[] {"011", "111", "101"},7, 3, Pos2D.of(14, 23), '');

    CanvasSection R23_15 = of(new String[] {"111", "111", "101"},8, 3, Pos2D.of(15, 23), '');

    CanvasSection R24_00 = of(new String[] {"000", "000", "011"},2, 2, Pos2D.of(0, 24), '');

    CanvasSection R24_01 = of(new String[] {"100", "000", "011"},3, 3, Pos2D.of(1, 24), '');

    CanvasSection R24_02 = of(new String[] {"010", "000", "011"},3, 2, Pos2D.of(2, 24), '');

    CanvasSection R24_03 = of(new String[] {"110", "000", "011"},4, 3, Pos2D.of(3, 24), '');

    CanvasSection R24_04 = of(new String[] {"001", "000", "011"},3, 2, Pos2D.of(4, 24), '');

    CanvasSection R24_05 = of(new String[] {"101", "000", "011"},4, 3, Pos2D.of(5, 24), '');

    CanvasSection R24_06 = of(new String[] {"011", "000", "011"},4, 2, Pos2D.of(6, 24), '');

    CanvasSection R24_07 = of(new String[] {"111", "000", "011"},5, 3, Pos2D.of(7, 24), '');

    CanvasSection R24_08 = of(new String[] {"000", "100", "011"},3, 3, Pos2D.of(8, 24), '');

    CanvasSection R24_09 = of(new String[] {"100", "100", "011"},4, 3, Pos2D.of(9, 24), '');

    CanvasSection R24_10 = of(new String[] {"010", "100", "011"},4, 3, Pos2D.of(10, 24), '');

    CanvasSection R24_11 = of(new String[] {"110", "100", "011"},5, 3, Pos2D.of(11, 24), '');

    CanvasSection R24_12 = of(new String[] {"001", "100", "011"},4, 3, Pos2D.of(12, 24), '');

    CanvasSection R24_13 = of(new String[] {"101", "100", "011"},5, 3, Pos2D.of(13, 24), '');

    CanvasSection R24_14 = of(new String[] {"011", "100", "011"},5, 3, Pos2D.of(14, 24), '');

    CanvasSection R24_15 = of(new String[] {"111", "100", "011"},6, 3, Pos2D.of(15, 24), '');

    CanvasSection R25_00 = of(new String[] {"000", "010", "011"},3, 2, Pos2D.of(0, 25), '');

    CanvasSection R25_01 = of(new String[] {"100", "010", "011"},4, 3, Pos2D.of(1, 25), '');

    CanvasSection R25_02 = of(new String[] {"010", "010", "011"},4, 2, Pos2D.of(2, 25), '');

    CanvasSection R25_03 = of(new String[] {"110", "010", "011"},5, 3, Pos2D.of(3, 25), '');

    CanvasSection R25_04 = of(new String[] {"001", "010", "011"},4, 2, Pos2D.of(4, 25), '');

    CanvasSection R25_05 = of(new String[] {"101", "010", "011"},5, 3, Pos2D.of(5, 25), '');

    CanvasSection R25_06 = of(new String[] {"011", "010", "011"},5, 2, Pos2D.of(6, 25), '');

    CanvasSection R25_07 = of(new String[] {"111", "010", "011"},6, 3, Pos2D.of(7, 25), '');

    CanvasSection R25_08 = of(new String[] {"000", "110", "011"},4, 3, Pos2D.of(8, 25), '');

    CanvasSection R25_09 = of(new String[] {"100", "110", "011"},5, 3, Pos2D.of(9, 25), '');

    CanvasSection R25_10 = of(new String[] {"010", "110", "011"},5, 3, Pos2D.of(10, 25), '');

    CanvasSection R25_11 = of(new String[] {"110", "110", "011"},6, 3, Pos2D.of(11, 25), '');

    CanvasSection R25_12 = of(new String[] {"001", "110", "011"},5, 3, Pos2D.of(12, 25), '');

    CanvasSection R25_13 = of(new String[] {"101", "110", "011"},6, 3, Pos2D.of(13, 25), '');

    CanvasSection R25_14 = of(new String[] {"011", "110", "011"},6, 3, Pos2D.of(14, 25), '');

    CanvasSection R25_15 = of(new String[] {"111", "110", "011"},7, 3, Pos2D.of(15, 25), '');

    CanvasSection R26_00 = of(new String[] {"000", "001", "011"},3, 2, Pos2D.of(0, 26), '');

    CanvasSection R26_01 = of(new String[] {"100", "001", "011"},4, 3, Pos2D.of(1, 26), '');

    CanvasSection R26_02 = of(new String[] {"010", "001", "011"},4, 2, Pos2D.of(2, 26), '');

    CanvasSection R26_03 = of(new String[] {"110", "001", "011"},5, 3, Pos2D.of(3, 26), '');

    CanvasSection R26_04 = of(new String[] {"001", "001", "011"},4, 2, Pos2D.of(4, 26), '');

    CanvasSection R26_05 = of(new String[] {"101", "001", "011"},5, 3, Pos2D.of(5, 26), '');

    CanvasSection R26_06 = of(new String[] {"011", "001", "011"},5, 2, Pos2D.of(6, 26), '');

    CanvasSection R26_07 = of(new String[] {"111", "001", "011"},6, 3, Pos2D.of(7, 26), '');

    CanvasSection R26_08 = of(new String[] {"000", "101", "011"},4, 3, Pos2D.of(8, 26), '');

    CanvasSection R26_09 = of(new String[] {"100", "101", "011"},5, 3, Pos2D.of(9, 26), '');

    CanvasSection R26_10 = of(new String[] {"010", "101", "011"},5, 3, Pos2D.of(10, 26), '');

    CanvasSection R26_11 = of(new String[] {"110", "101", "011"},6, 3, Pos2D.of(11, 26), '');

    CanvasSection R26_12 = of(new String[] {"001", "101", "011"},5, 3, Pos2D.of(12, 26), '');

    CanvasSection R26_13 = of(new String[] {"101", "101", "011"},6, 3, Pos2D.of(13, 26), '');

    CanvasSection R26_14 = of(new String[] {"011", "101", "011"},6, 3, Pos2D.of(14, 26), '');

    CanvasSection R26_15 = of(new String[] {"111", "101", "011"},7, 3, Pos2D.of(15, 26), '');

    CanvasSection R27_00 = of(new String[] {"000", "011", "011"},4, 2, Pos2D.of(0, 27), '');

    CanvasSection R27_01 = of(new String[] {"100", "011", "011"},5, 3, Pos2D.of(1, 27), '');

    CanvasSection R27_02 = of(new String[] {"010", "011", "011"},5, 2, Pos2D.of(2, 27), '');

    CanvasSection R27_03 = of(new String[] {"110", "011", "011"},6, 3, Pos2D.of(3, 27), '');

    CanvasSection R27_04 = of(new String[] {"001", "011", "011"},5, 2, Pos2D.of(4, 27), '');

    CanvasSection R27_05 = of(new String[] {"101", "011", "011"},6, 3, Pos2D.of(5, 27), '');

    CanvasSection R27_06 = of(new String[] {"011", "011", "011"},6, 2, Pos2D.of(6, 27), '');

    CanvasSection R27_07 = of(new String[] {"111", "011", "011"},7, 3, Pos2D.of(7, 27), '');

    CanvasSection R27_08 = of(new String[] {"000", "111", "011"},5, 3, Pos2D.of(8, 27), '');

    CanvasSection R27_09 = of(new String[] {"100", "111", "011"},6, 3, Pos2D.of(9, 27), '');

    CanvasSection R27_10 = of(new String[] {"010", "111", "011"},6, 3, Pos2D.of(10, 27), '');

    CanvasSection R27_11 = of(new String[] {"110", "111", "011"},7, 3, Pos2D.of(11, 27), '');

    CanvasSection R27_12 = of(new String[] {"001", "111", "011"},6, 3, Pos2D.of(12, 27), '');

    CanvasSection R27_13 = of(new String[] {"101", "111", "011"},7, 3, Pos2D.of(13, 27), '');

    CanvasSection R27_14 = of(new String[] {"011", "111", "011"},7, 3, Pos2D.of(14, 27), '');

    CanvasSection R27_15 = of(new String[] {"111", "111", "011"},8, 3, Pos2D.of(15, 27), '');

    CanvasSection R28_00 = of(new String[] {"000", "000", "111"},3, 3, Pos2D.of(0, 28), '');

    CanvasSection R28_01 = of(new String[] {"100", "000", "111"},4, 3, Pos2D.of(1, 28), '');

    CanvasSection R28_02 = of(new String[] {"010", "000", "111"},4, 3, Pos2D.of(2, 28), '');

    CanvasSection R28_03 = of(new String[] {"110", "000", "111"},5, 3, Pos2D.of(3, 28), '');

    CanvasSection R28_04 = of(new String[] {"001", "000", "111"},4, 3, Pos2D.of(4, 28), '');

    CanvasSection R28_05 = of(new String[] {"101", "000", "111"},5, 3, Pos2D.of(5, 28), '');

    CanvasSection R28_06 = of(new String[] {"011", "000", "111"},5, 3, Pos2D.of(6, 28), '');

    CanvasSection R28_07 = of(new String[] {"111", "000", "111"},6, 3, Pos2D.of(7, 28), '');

    CanvasSection R28_08 = of(new String[] {"000", "100", "111"},4, 3, Pos2D.of(8, 28), '');

    CanvasSection R28_09 = of(new String[] {"100", "100", "111"},5, 3, Pos2D.of(9, 28), '');

    CanvasSection R28_10 = of(new String[] {"010", "100", "111"},5, 3, Pos2D.of(10, 28), '');

    CanvasSection R28_11 = of(new String[] {"110", "100", "111"},6, 3, Pos2D.of(11, 28), '');

    CanvasSection R28_12 = of(new String[] {"001", "100", "111"},5, 3, Pos2D.of(12, 28), '');

    CanvasSection R28_13 = of(new String[] {"101", "100", "111"},6, 3, Pos2D.of(13, 28), '');

    CanvasSection R28_14 = of(new String[] {"011", "100", "111"},6, 3, Pos2D.of(14, 28), '');

    CanvasSection R28_15 = of(new String[] {"111", "100", "111"},7, 3, Pos2D.of(15, 28), '');

    CanvasSection R29_00 = of(new String[] {"000", "010", "111"},4, 3, Pos2D.of(0, 29), '');

    CanvasSection R29_01 = of(new String[] {"100", "010", "111"},5, 3, Pos2D.of(1, 29), '');

    CanvasSection R29_02 = of(new String[] {"010", "010", "111"},5, 3, Pos2D.of(2, 29), '');

    CanvasSection R29_03 = of(new String[] {"110", "010", "111"},6, 3, Pos2D.of(3, 29), '');

    CanvasSection R29_04 = of(new String[] {"001", "010", "111"},5, 3, Pos2D.of(4, 29), '');

    CanvasSection R29_05 = of(new String[] {"101", "010", "111"},6, 3, Pos2D.of(5, 29), '');

    CanvasSection R29_06 = of(new String[] {"011", "010", "111"},6, 3, Pos2D.of(6, 29), '');

    CanvasSection R29_07 = of(new String[] {"111", "010", "111"},7, 3, Pos2D.of(7, 29), '');

    CanvasSection R29_08 = of(new String[] {"000", "110", "111"},5, 3, Pos2D.of(8, 29), '');

    CanvasSection R29_09 = of(new String[] {"100", "110", "111"},6, 3, Pos2D.of(9, 29), '');

    CanvasSection R29_10 = of(new String[] {"010", "110", "111"},6, 3, Pos2D.of(10, 29), '');

    CanvasSection R29_11 = of(new String[] {"110", "110", "111"},7, 3, Pos2D.of(11, 29), '');

    CanvasSection R29_12 = of(new String[] {"001", "110", "111"},6, 3, Pos2D.of(12, 29), '');

    CanvasSection R29_13 = of(new String[] {"101", "110", "111"},7, 3, Pos2D.of(13, 29), '');

    CanvasSection R29_14 = of(new String[] {"011", "110", "111"},7, 3, Pos2D.of(14, 29), '');

    CanvasSection R29_15 = of(new String[] {"111", "110", "111"},8, 3, Pos2D.of(15, 29), '');

    CanvasSection R30_00 = of(new String[] {"000", "001", "111"},4, 3, Pos2D.of(0, 30), '');

    CanvasSection R30_01 = of(new String[] {"100", "001", "111"},5, 3, Pos2D.of(1, 30), '');

    CanvasSection R30_02 = of(new String[] {"010", "001", "111"},5, 3, Pos2D.of(2, 30), '');

    CanvasSection R30_03 = of(new String[] {"110", "001", "111"},6, 3, Pos2D.of(3, 30), '');

    CanvasSection R30_04 = of(new String[] {"001", "001", "111"},5, 3, Pos2D.of(4, 30), '');

    CanvasSection R30_05 = of(new String[] {"101", "001", "111"},6, 3, Pos2D.of(5, 30), '');

    CanvasSection R30_06 = of(new String[] {"011", "001", "111"},6, 3, Pos2D.of(6, 30), '');

    CanvasSection R30_07 = of(new String[] {"111", "001", "111"},7, 3, Pos2D.of(7, 30), '');

    CanvasSection R30_08 = of(new String[] {"000", "101", "111"},5, 3, Pos2D.of(8, 30), '');

    CanvasSection R30_09 = of(new String[] {"100", "101", "111"},6, 3, Pos2D.of(9, 30), '');

    CanvasSection R30_10 = of(new String[] {"010", "101", "111"},6, 3, Pos2D.of(10, 30), '');

    CanvasSection R30_11 = of(new String[] {"110", "101", "111"},7, 3, Pos2D.of(11, 30), '');

    CanvasSection R30_12 = of(new String[] {"001", "101", "111"},6, 3, Pos2D.of(12, 30), '');

    CanvasSection R30_13 = of(new String[] {"101", "101", "111"},7, 3, Pos2D.of(13, 30), '');

    CanvasSection R30_14 = of(new String[] {"011", "101", "111"},7, 3, Pos2D.of(14, 30), '');

    CanvasSection R30_15 = of(new String[] {"111", "101", "111"},8, 3, Pos2D.of(15, 30), '');

    CanvasSection R31_00 = of(new String[] {"000", "011", "111"},5, 3, Pos2D.of(0, 31), '');

    CanvasSection R31_01 = of(new String[] {"100", "011", "111"},6, 3, Pos2D.of(1, 31), '');

    CanvasSection R31_02 = of(new String[] {"010", "011", "111"},6, 3, Pos2D.of(2, 31), '');

    CanvasSection R31_03 = of(new String[] {"110", "011", "111"},7, 3, Pos2D.of(3, 31), '');

    CanvasSection R31_04 = of(new String[] {"001", "011", "111"},6, 3, Pos2D.of(4, 31), '');

    CanvasSection R31_05 = of(new String[] {"101", "011", "111"},7, 3, Pos2D.of(5, 31), '');

    CanvasSection R31_06 = of(new String[] {"011", "011", "111"},7, 3, Pos2D.of(6, 31), '');

    CanvasSection R31_07 = of(new String[] {"111", "011", "111"},8, 3, Pos2D.of(7, 31), '');

    CanvasSection R31_08 = of(new String[] {"000", "111", "111"},6, 3, Pos2D.of(8, 31), '');

    CanvasSection R31_09 = of(new String[] {"100", "111", "111"},7, 3, Pos2D.of(9, 31), '');

    CanvasSection R31_10 = of(new String[] {"010", "111", "111"},7, 3, Pos2D.of(10, 31), '');

    CanvasSection R31_11 = of(new String[] {"110", "111", "111"},8, 3, Pos2D.of(11, 31), '');

    CanvasSection R31_12 = of(new String[] {"001", "111", "111"},7, 3, Pos2D.of(12, 31), '');

    CanvasSection R31_13 = of(new String[] {"101", "111", "111"},8, 3, Pos2D.of(13, 31), '');

    CanvasSection R31_14 = of(new String[] {"011", "111", "111"},8, 3, Pos2D.of(14, 31), '');

    CanvasSection R31_15 = of(new String[] {"111", "111", "111"},9, 3, Pos2D.of(15, 31), '');

    String[] pattern();

    int filled();

    int width();

    Pos2D coords();

    char symbol();

    default int empty() {
        return 9 - filled();
    }

    String NAMESPACE = "comet";

    static me.combimagnetron.comet.image.CanvasSection of(String[] pattern, int filled, int width, Pos2D coords, char symbol) {
        return new me.combimagnetron.comet.image.CanvasSection.Impl(pattern, filled, width, coords, symbol);
    }

    record Impl(String[] pattern, int filled, int width, Pos2D coords, char symbol) implements me.combimagnetron.comet.image.CanvasSection {

    }

    static Component optimize(BufferedImage section) {
        LinkedHashMap<Color, ColorGroup> colorGroups = new LinkedHashMap<>();
        for (int y = 0; y < section.getWidth(); y++) {
            for (int x = 0; x < section.getHeight(); x++) {
                int rgb = section.getRGB(x, y);
                Color color = new Color(rgb);
                colorGroups.computeIfAbsent(color, c -> new ColorGroup());
                colorGroups.get(color).add(Pos2D.of(x, y));
            }
        }
        if (colorGroups.size() >= 6) {
            return Component.text("a");
        }
        boolean first = true;
        int z = 0;
        Component component = Component.empty();
        Component finalshift = Component.text("");
        for (Map.Entry<Color, ColorGroup> group : colorGroups.entrySet()) {
            CanvasSection canvasSection = find(group.getValue());
            if (!first) {
                component = component.append(Component.text("4").font(Key.key(NAMESPACE, "patterns_1")));
            }
            first = false;
            component = component.append(Component.text(canvasSection.symbol()).color(TextColor.color(group.getKey().getRGB()))).font(Key.key(NAMESPACE, "patterns_1"));
        }
        return component.append(finalshift);
    }

    static CanvasSection find(ColorGroup group) {
        String[] pattern = new String[]{"000", "000", "000"};
        for (Pos2D coord : group.coords) {
            StringBuilder builder = new StringBuilder(pattern[coord.yi()]);
            builder.setCharAt(coord.xi(), '1');
            pattern[coord.yi()] = builder.toString();
        }
        try {
            return (CanvasSection) Arrays.stream(CanvasSection.class.getDeclaredFields()).filter(f -> {
                try {
                    CanvasSection section = (CanvasSection) f.get(null);
                    if (Arrays.equals(section.pattern(), pattern)) {
                        return true;
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                return false;
            }).findFirst().get().get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    class ColorGroup {
        private final List<Pos2D> coords = new ArrayList<>();

        public void add(Pos2D pos) {
            coords.add(pos);
        }

        public List<Pos2D> coords() {
            return coords;
        }

        public int filled() {
            return coords.size();
        }

    }

}
