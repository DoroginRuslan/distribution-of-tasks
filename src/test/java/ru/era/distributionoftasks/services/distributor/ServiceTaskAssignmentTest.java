package ru.era.distributionoftasks.services.distributor;

import org.junit.jupiter.api.Test;
import ru.era.distributionoftasks.services.distributor.entity.*;

import java.util.*;
import java.util.stream.Collectors;

class ServiceTaskAssignmentTest {
    private String getPriority(List<AgencyPoint> agencyPointList) {
        int high = 0;
        int medium = 0;
        int low = 0;
        for(AgencyPoint agencyPoint : agencyPointList) {
            if(agencyPoint.getTask() == null) continue;
            switch (agencyPoint.getTask().getPriority()) {
                case MAX_PRIORITY -> high++;
                case MEDIUM_PRIORITY -> medium++;
                case LOW_PRIORITY -> low++;
            }
        }
        return "\n\t\tВысокий: " + high +
                "\n\t\tСредний: " + medium +
                "\n\t\tНизкий: " + low;

    }
    @Test
    void checkCalcRoutes() {
        List<AgencyPoint> agencyPointList = getAgencyPointList();
        List<Office> officeList = getOffices();
        AddressTimesMatrix addressTimesMatrix = getTimeMatrixAndFillAddressIds(agencyPointList, officeList);
        ServiceTaskAssignment serviceTaskAssignment = new ServiceTaskAssignment(addressTimesMatrix, agencyPointList, officeList);
        List<Long> nonDistributed = new ArrayList<>();
        List<EmployeeRoutePair> results = serviceTaskAssignment.calcEmployeeRoutes(nonDistributed);
        List<AgencyPoint> nonDistribute = new ArrayList<>(List.copyOf(agencyPointList));
        nonDistribute.removeAll(serviceTaskAssignment.getDistributeAgencyPoints());
        System.out.println(
                "Общее кол-во задач: " + agencyPointList.size() +
                        "\nПриоритет:" + getPriority(agencyPointList) +
                        "\nКол-во распределённых задач: " + serviceTaskAssignment.getDistributeAgencyPoints().size() +
                        "\nПриоритет: " + getPriority(serviceTaskAssignment.getDistributeAgencyPoints()) +
                        "\nКол-во не распределённых задач: " + (agencyPointList.size() - serviceTaskAssignment.getDistributeAgencyPoints().size()) +
                        "\n\tПриоритет: " + getPriority(nonDistribute) +
                        "\nКол-во сотрудников: " + results.size() +
                        "\nКол-во сотрудников без задач: " + results.stream()
                            .map(EmployeeRoutePair::getRoute)
                            .filter(Route::isEmpty)
                            .count()
        );
        for(var result : results) {
            AlgEmployee algEmployee = result.getAlgEmployee();
            Route route = result.getRoute();
            System.out.println(
                    "EmployeeId: " + algEmployee.getDatabaseId() +
                    "\n\tУровень: " + algEmployee.getRang() +
                    "\n\tДлина маршрута: " +
                    "\n\tПрофит: " + route.getProfit() +
                    "\n\tДлительность: " + route.getTime() +
                    "\n\tКол-во задач: " + route.getAgencyPointList().size() +
                    "\n\tУровни задач: " + route.getAgencyPointList().stream()
                            .map(AgencyPoint::getTask)
                            .map(Task::getPriority)
                            .map(Objects::toString)
                            .collect(Collectors.joining(",", "{", "}"))
            );
        }
    }

    private List<AgencyPoint> getAgencyPointList() {
        int overdue = 0;
        return Arrays.asList(
//                new AgencyPoint(1, 0, "вчера", false, 0, 0, 0, overdue),
//                new AgencyPoint(2, 0, "давно", true, 3, 15, 3, overdue),
//                new AgencyPoint(3, 0, "давно", true, 3, 9, 1, overdue),
//                new AgencyPoint(4, 0, "давно", true, 0, 38, 23, overdue),
//                new AgencyPoint(5, 0, "давно", false, 0, 14, 0, overdue),
//                new AgencyPoint(6, 0, "давно", true, 12, 19, 1, overdue),
//                new AgencyPoint(7, 0, "давно", true, 27, 19, 12, overdue),
//                new AgencyPoint(8, 0, "давно", true, 33, 84, 63, overdue),
//                new AgencyPoint(9, 0, "давно", true, 2, 15, 1, overdue),
//                new AgencyPoint(10, 0, "давно", true, 0, 19, 0, overdue),
//                new AgencyPoint(11, 0, "давно", true, 15, 29, 15, overdue),
//                new AgencyPoint(12, 0, "вчера", false, 0, 0, 0, overdue),
//                new AgencyPoint(13, 0, "давно", true, 4, 21, 5, overdue),
//                new AgencyPoint(14, 0, "вчера", false, 0, 5, 0, overdue),
//                new AgencyPoint(15, 0, "давно", true, 7, 14, 3, overdue),
//                new AgencyPoint(16, 0, "вчера", false, 0, 0, 0, overdue),
//                new AgencyPoint(19, 0, "давно", true, 6, 32, 9, overdue),
//                new AgencyPoint(20, 0, "давно", true, 4, 35, 15, overdue)
//
//                ,new AgencyPoint(21, 0, "вчера", false, 0, 6, 0, overdue),
//                new AgencyPoint(22, 0, "давно", true, 6, 18, 6, overdue),
//                new AgencyPoint(23, 0, "давно", true, 0, 15, 5, overdue),
//                new AgencyPoint(24, 0, "давно", true, 2, 96, 20, overdue),
//                new AgencyPoint(25, 0, "вчера", false, 0, 0, 0, overdue),
//                new AgencyPoint(26, 0, "давно", true, 0, 16, 0, overdue),
//                new AgencyPoint(27, 0, "давно", true, 3, 43, 29, overdue),
//                new AgencyPoint(28, 0, "давно", true, 3, 13, 4, overdue),
//                new AgencyPoint(29, 0, "давно", true, 6, 19, 5, overdue),
//                new AgencyPoint(30, 0, "давно", true, 16, 45, 30, overdue),
//                new AgencyPoint(31, 0, "давно", true, 1, 19, 4, overdue),
//                new AgencyPoint(32, 0, "давно", true, 3, 20, 9, overdue),
//                new AgencyPoint(33, 0, "вчера", false, 0, 19, 0, overdue),
//                new AgencyPoint(34, 0, "давно", true, 76, 82, 72, overdue),
//                new AgencyPoint(35, 0, "давно", true, 23, 32, 21, overdue),
//                new AgencyPoint(36, 0, "давно", true, 4, 19, 4, overdue),
//                new AgencyPoint(37, 0, "давно", true, 9, 10, 7, overdue),
//                new AgencyPoint(38, 0, "вчера", false, 0, 13, 0, overdue),
//                new AgencyPoint(39, 0, "вчера", false, 0, 10, 0, overdue)
//
//                ,new AgencyPoint(40, 0,"давно",true,6,30,14, overdue),
//                new AgencyPoint(41, 0,"давно",true,6,65,12, overdue),
//                new AgencyPoint(42, 0,"давно",true,3,20,4, overdue),
//                new AgencyPoint(43, 0,"вчера",false,0,0,0, overdue)
        );
    }

    private List<Office> getOffices() {
        AlgEmployee algEmployee11 = new AlgEmployee(1L, Rang.SENIOR_RANG);
        AlgEmployee algEmployee12 = new AlgEmployee(2L, Rang.MIDDLE_RANG);
        AlgEmployee algEmployee13 = new AlgEmployee(3L, Rang.JUNIOR_RANG);

        AlgEmployee algEmployee21 = new AlgEmployee(4L, Rang.SENIOR_RANG);
        AlgEmployee algEmployee22 = new AlgEmployee(5L, Rang.MIDDLE_RANG);
        AlgEmployee algEmployee23 = new AlgEmployee(6L, Rang.JUNIOR_RANG);

        AlgEmployee algEmployee31 = new AlgEmployee(7L, Rang.MIDDLE_RANG);
        AlgEmployee algEmployee32 = new AlgEmployee(8L, Rang.JUNIOR_RANG);
        AlgEmployee algEmployee33 = new AlgEmployee(9L, Rang.SENIOR_RANG);
        return Arrays.asList(
                new Office(1, Arrays.asList(algEmployee11,algEmployee12,algEmployee13)),
                new Office(2, Arrays.asList(algEmployee21,algEmployee22,algEmployee23)),
                new Office(3, Arrays.asList(
                        algEmployee31
                        ,algEmployee32
//                        ,algEmployee33
                ))
        );
    }

    @SafeVarargs
    private AddressTimesMatrix getTimeMatrixAndFillAddressIds(List<? extends AddressInterface>... addresses) {
        int minTimeMinutes = 10;
        int maxTimeMinutes = 90;
        int countAddresses = 0;
        for(var param : addresses) {
            for(var address : param) {
                address.setAddressIdImpl(countAddresses++);
            }
        }
        // random
        Random random = new Random(12345L);
        int[][] times = new int[countAddresses][countAddresses];
        for(int i = 0; i < times.length; i++) {
            for(int j = 0; j < times[i].length; j++) {
                if(i == j) {
                    times[i][j] = 0;
                } else {
                    times[i][j] = random.nextInt(maxTimeMinutes-minTimeMinutes) + minTimeMinutes;
                }
            }
        }
        // real data
//        int[][] times = getRealData();
        return new AddressTimesMatrix(times);
    }

    int[][] getRealData() {
        int[][] arr = {
                {0, 408, 1505, 549, 1524, 1339, 985, 777, 249, 1279, 1587, 1038, 952, 634, 517, 905, 840, 796, 1012, 917, 917, 778, 978, 481, 954, 476, 1499, 583, 656, 815, 614, 684, 1984, 1114, 623, 636, 574, 629, 702, 851},
                {467, 0, 1187, 190, 1980, 1795, 1002, 459, 716, 961, 1269, 720, 1132, 814, 840, 1085, 522, 588, 694, 934, 934, 460, 660, 272, 1134, 276, 1334, 265, 338, 606, 296, 367, 1666, 954, 305, 318, 215, 311, 1158, 533},
                {1625, 1182, 0, 1076, 2616, 2431, 1137, 910, 1699, 339, 1507, 732, 1992, 1674, 1700, 1945, 808, 992, 997, 1237, 1237, 924, 1111, 1182, 1994, 1179, 1135, 1005, 1082, 1006, 958, 907, 938, 755, 1083, 952, 1074, 946, 2018, 984},
                {604, 153, 1004, 0, 1888, 1704, 886, 276, 748, 778, 1086, 537, 1041, 723, 749, 994, 339, 476, 511, 843, 843, 277, 477, 217, 1043, 184, 1151, 147, 155, 495, 113, 184, 1483, 771, 122, 135, 134, 128, 1067, 350},
                {1579, 1825, 2781, 1940, 0, 727, 2161, 2118, 1528, 2595, 2928, 2379, 1160, 1529, 1171, 1178, 2116, 2058, 2353, 2093, 2093, 2119, 2319, 1758, 1126, 1778, 2176, 1924, 1997, 2074, 1955, 2026, 2602, 2291, 1964, 1977, 1965, 1970, 972, 2192},
                {1398, 1644, 2346, 1759, 698, 0, 1980, 1938, 1347, 2414, 2747, 2198, 912, 1349, 990, 929, 1935, 1877, 2172, 1912, 1912, 1938, 2139, 1577, 877, 1597, 1927, 1744, 1817, 1894, 1774, 1845, 2354, 2110, 1784, 1796, 1784, 1789, 791, 2011},
                {1085, 1062, 1085, 965, 2197, 2012, 0, 1009, 1057, 1130, 1818, 999, 1349, 1032, 1058, 1302, 672, 578, 1209, 433, 433, 1012, 1210, 858, 1352, 878, 679, 865, 1005, 531, 847, 796, 1417, 375, 972, 842, 963, 835, 1375, 1083},
                {923, 520, 908, 414, 2255, 2070, 1056, 0, 1115, 651, 809, 475, 1407, 1090, 1116, 1361, 509, 646, 235, 1081, 1081, 144, 201, 584, 1410, 551, 1321, 492, 302, 665, 390, 339, 1494, 940, 386, 385, 412, 378, 1434, 74},
                {257, 503, 1600, 644, 1552, 1367, 1080, 872, 0, 1374, 1681, 1133, 990, 729, 544, 964, 935, 891, 1107, 1012, 1012, 872, 1073, 576, 992, 571, 1594, 678, 751, 910, 709, 779, 2079, 1209, 718, 730, 668, 724, 730, 946},
                {1286, 843, 333, 737, 2565, 2380, 1127, 571, 1424, 0, 1380, 393, 1717, 1399, 1425, 1670, 580, 717, 658, 1152, 1152, 585, 772, 884, 1719, 859, 1117, 666, 743, 736, 619, 568, 919, 737, 744, 613, 735, 606, 1743, 645},
                {1575, 1172, 1516, 1065, 2907, 2722, 1705, 793, 1767, 1351, 0, 1175, 2059, 1741, 1768, 2012, 1158, 1296, 878, 1731, 1731, 793, 576, 1236, 2061, 1203, 1970, 1156, 954, 1314, 1040, 989, 1998, 1590, 1038, 1034, 1064, 1028, 2085, 717},
                {1105, 662, 673, 556, 2384, 2199, 946, 422, 1244, 417, 1231, 0, 1536, 1218, 1245, 1489, 399, 536, 509, 971, 971, 436, 623, 703, 1538, 678, 1211, 485, 540, 555, 438, 387, 1259, 831, 563, 433, 554, 426, 1562, 496},
                {1044, 1157, 1980, 1139, 1178, 993, 1360, 1317, 989, 1794, 2127, 1578, 0, 523, 663, 156, 1315, 1257, 1552, 1292, 1292, 1318, 1518, 957, 34, 977, 1874, 1123, 1196, 1273, 1154, 1224, 2233, 1490, 1163, 1176, 1164, 1169, 673, 1391},
                {683, 796, 1619, 777, 1583, 1398, 999, 956, 689, 1433, 1765, 1217, 523, 0, 444, 476, 954, 896, 1191, 931, 931, 957, 1157, 596, 525, 616, 1513, 762, 835, 912, 793, 863, 2098, 1129, 802, 815, 802, 808, 722, 1030},
                {562, 808, 1647, 806, 1204, 1019, 1027, 984, 512, 1461, 1794, 1245, 629, 395, 0, 603, 982, 924, 1219, 959, 959, 985, 1185, 624, 631, 644, 1542, 790, 863, 940, 821, 892, 2126, 1157, 830, 843, 831, 836, 382, 1058},
                {997, 1110, 1933, 1092, 1200, 1015, 1313, 1270, 963, 1747, 2080, 1531, 154, 476, 637, 0, 1268, 1210, 1505, 1245, 1245, 1271, 1471, 910, 120, 930, 1828, 1076, 1149, 1226, 1107, 1178, 2412, 1443, 1116, 1129, 1117, 1122, 669, 1344},
                {947, 496, 761, 395, 2132, 1947, 608, 439, 992, 614, 1248, 429, 1284, 966, 993, 1237, 0, 284, 639, 708, 708, 441, 640, 475, 1286, 471, 908, 295, 435, 303, 277, 226, 1240, 528, 402, 271, 393, 264, 1310, 513},
                {969, 616, 1000, 597, 2081, 1896, 518, 638, 941, 814, 1448, 629, 1233, 915, 942, 1186, 335, 0, 838, 469, 469, 641, 839, 469, 1235, 466, 899, 495, 634, 52, 476, 425, 1479, 519, 601, 471, 593, 464, 1259, 712},
                {1051, 648, 944, 542, 2383, 2198, 1167, 269, 1243, 688, 926, 512, 1535, 1218, 1244, 1489, 620, 757, 0, 1192, 1192, 270, 318, 712, 1538, 679, 1432, 632, 430, 776, 516, 465, 1530, 1051, 514, 511, 540, 504, 1562, 191},
                {945, 918, 1142, 899, 2057, 1872, 315, 979, 917, 1154, 1788, 969, 1209, 891, 918, 1162, 662, 472, 1179, 0, 0, 981, 1180, 717, 1211, 738, 829, 835, 957, 425, 817, 766, 1535, 493, 924, 811, 924, 805, 1235, 1053},
                {945, 918, 1142, 899, 2057, 1872, 315, 979, 917, 1154, 1788, 969, 1209, 891, 918, 1162, 662, 472, 1179, 0, 0, 981, 1180, 717, 1211, 738, 829, 835, 957, 425, 817, 766, 1535, 493, 924, 811, 924, 805, 1235, 1053},
                {831, 428, 815, 322, 2163, 1979, 964, 38, 1023, 558, 847, 382, 1316, 998, 1024, 1269, 417, 554, 272, 989, 989, 0, 239, 492, 1318, 459, 1229, 401, 210, 573, 299, 247, 1400, 849, 295, 293, 320, 286, 1342, 112},
                {1027, 624, 1060, 518, 2359, 2174, 1157, 245, 1219, 803, 623, 628, 1511, 1193, 1220, 1464, 610, 748, 330, 1183, 1183, 246, 0, 688, 1513, 655, 1422, 608, 406, 766, 492, 441, 1646, 1042, 490, 487, 516, 480, 1537, 169},
                {652, 201, 1088, 182, 1875, 1690, 820, 361, 735, 862, 1170, 621, 1027, 709, 736, 980, 423, 380, 595, 771, 771, 361, 562, 0, 1029, 171, 1201, 167, 240, 398, 197, 268, 1567, 821, 207, 219, 207, 212, 1053, 435},
                {1033, 1160, 1982, 1141, 1144, 959, 1362, 1319, 983, 1796, 2129, 1580, 34, 525, 657, 121, 1317, 1259, 1554, 1294, 1294, 1320, 1520, 959, 0, 979, 1877, 1125, 1198, 1275, 1156, 1227, 2199, 1492, 1165, 1178, 1166, 1171, 639, 1393},
                {592, 418, 1258, 400, 1704, 1519, 726, 578, 564, 1072, 1388, 839, 856, 539, 565, 809, 593, 535, 813, 658, 658, 579, 779, 218, 858, 0, 1192, 384, 457, 553, 415, 486, 1737, 812, 424, 437, 425, 430, 882, 652},
                {1529, 1345, 1060, 1243, 2171, 1986, 732, 1287, 1501, 1105, 2097, 1277, 1689, 1476, 1502, 1746, 948, 937, 1487, 880, 880, 1290, 1488, 1274, 1654, 1271, 0, 1144, 1283, 890, 1125, 1074, 1392, 519, 1250, 1120, 1241, 1113, 1819, 1361},
                {652, 201, 959, 183, 1914, 1729, 840, 361, 774, 739, 1170, 512, 1066, 749, 775, 1019, 294, 431, 596, 854, 854, 362, 562, 224, 1068, 210, 1105, 0, 240, 449, 199, 148, 1438, 725, 207, 193, 207, 187, 1092, 435},
                {661, 218, 1027, 112, 1953, 1768, 913, 245, 813, 770, 1054, 529, 1105, 788, 814, 1059, 366, 503, 479, 907, 907, 245, 446, 282, 1108, 249, 1178, 190, 0, 522, 140, 199, 1510, 798, 84, 162, 110, 155, 1132, 319},
                {943, 626, 1010, 607, 2055, 1870, 492, 649, 914, 824, 1458, 639, 1207, 889, 915, 1160, 345, 142, 848, 442, 442, 651, 850, 479, 1209, 476, 873, 505, 644, 0, 486, 435, 1489, 493, 611, 481, 603, 474, 1233, 722},
                {713, 269, 909, 163, 1990, 1805, 791, 229, 849, 683, 1039, 442, 1142, 824, 850, 1095, 244, 382, 464, 817, 817, 232, 430, 310, 1144, 286, 1056, 161, 203, 400, 0, 89, 1388, 676, 170, 40, 161, 33, 1168, 303},
                {719, 275, 945, 169, 1995, 1811, 824, 235, 855, 688, 1044, 447, 1148, 830, 856, 1101, 277, 414, 470, 849, 849, 238, 436, 316, 1150, 291, 1089, 167, 209, 433, 51, 0, 1421, 709, 176, 46, 167, 39, 1174, 309},
                {1819, 1368, 808, 1266, 2404, 2219, 1173, 1254, 1867, 684, 1750, 1076, 1922, 1842, 1868, 1939, 971, 1156, 1342, 1287, 1287, 1269, 1455, 1346, 1887, 1343, 1149, 1166, 1306, 1170, 1148, 1097, 0, 768, 1273, 1142, 1264, 1136, 2186, 1328},
                {1170, 943, 710, 841, 2282, 2097, 447, 886, 1141, 755, 1695, 876, 1434, 1116, 1142, 1387, 547, 535, 1085, 561, 561, 888, 1087, 872, 1436, 869, 422, 742, 881, 489, 723, 672, 1042, 0, 848, 718, 840, 711, 1460, 959},
                {693, 250, 1079, 143, 1985, 1800, 961, 347, 845, 853, 1156, 612, 1137, 819, 846, 1090, 414, 551, 581, 939, 939, 347, 548, 314, 1139, 281, 1226, 238, 223, 570, 188, 258, 1558, 846, 0, 210, 142, 203, 1163, 420},
                {777, 333, 936, 227, 2054, 1869, 877, 225, 913, 679, 1035, 438, 1206, 888, 914, 1159, 330, 468, 460, 902, 902, 228, 426, 374, 1208, 349, 1142, 242, 211, 486, 160, 109, 1474, 762, 201, 0, 225, 147, 1232, 299},
                {649, 198, 1049, 45, 1933, 1748, 931, 321, 793, 823, 1130, 582, 1085, 768, 794, 1038, 384, 521, 556, 887, 887, 321, 522, 262, 1088, 229, 1196, 192, 200, 540, 158, 228, 1528, 815, 167, 179, 0, 172, 1111, 395},
                {680, 237, 985, 130, 1957, 1772, 867, 291, 816, 759, 1101, 518, 1109, 791, 818, 1062, 320, 458, 526, 893, 893, 292, 492, 277, 1111, 253, 1132, 145, 170, 476, 94, 165, 1464, 752, 137, 116, 128, 0, 1135, 365},
                {607, 853, 1809, 968, 1068, 883, 1190, 1147, 557, 1624, 1956, 1407, 650, 558, 199, 624, 1144, 1087, 1381, 1121, 1121, 1147, 1348, 786, 653, 806, 1704, 953, 1026, 1103, 984, 1054, 2288, 1319, 993, 1005, 993, 998, 0, 1221},
                {970, 567, 1003, 461, 2302, 2117, 1100, 188, 1162, 746, 746, 571, 1454, 1136, 1163, 1407, 553, 691, 222, 1126, 1126, 189, 137, 631, 1456, 598, 1365, 551, 349, 709, 435, 384, 1589, 985, 433, 430, 459, 423, 1480, 0}
        };
        for(int i = 0 ; i < arr.length; i++) {
            for(int j = 0; j < arr[i].length; j++) {
                arr[i][j] /= 60;
            }
        }
        return arr;
    }
}