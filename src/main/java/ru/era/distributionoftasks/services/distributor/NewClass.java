import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        IntToPair route1 = new IntToPair(60, Arrays.asList(new AgencyPoint(1), new AgencyPoint(2), new AgencyPoint(3)));
        IntToPair route2 = new IntToPair(51, Arrays.asList(new AgencyPoint(4), new AgencyPoint(5), new AgencyPoint(6)));
        IntToPair route3 = new IntToPair(48, Arrays.asList(new AgencyPoint(7), new AgencyPoint(8), new AgencyPoint(9)));
        IntToPair route4 = new IntToPair(36, Arrays.asList(new AgencyPoint(9), new AgencyPoint(11), new AgencyPoint(26)));

        IntToPair route5 = new IntToPair(52, Arrays.asList(new AgencyPoint(13), new AgencyPoint(11), new AgencyPoint(15)));
        IntToPair route6 = new IntToPair(49, Arrays.asList(new AgencyPoint(10), new AgencyPoint(26), new AgencyPoint(18)));
        IntToPair route7 = new IntToPair(43, Arrays.asList(new AgencyPoint(19), new AgencyPoint(20), new AgencyPoint(5)));
        IntToPair route8 = new IntToPair(40, Arrays.asList(new AgencyPoint(11), new AgencyPoint(23), new AgencyPoint(24)));
        IntToPair route9 = new IntToPair(20, Arrays.asList(new AgencyPoint(22), new AgencyPoint(43), new AgencyPoint(18)));

        IntToPair route10 = new IntToPair(61, Arrays.asList(new AgencyPoint(26), new AgencyPoint(1), new AgencyPoint(28)));
        IntToPair route11 = new IntToPair(52, Arrays.asList(new AgencyPoint(4), new AgencyPoint(7), new AgencyPoint(43)));

        List<IntToPair> signorRouteOf1 = new ArrayList<>();
        signorRouteOf1.add(route1);
        signorRouteOf1.add(route2);
        signorRouteOf1.add(route3);
        signorRouteOf1.add(route4);

        List<IntToPair> signorRouteOf2 = new ArrayList<>();
        signorRouteOf2.add(route5);
        signorRouteOf2.add(route6);
        signorRouteOf2.add(route7);
        signorRouteOf2.add(route8);
        signorRouteOf2.add(route9);

        List<IntToPair> signorRouteOf3 = new ArrayList<>();
        signorRouteOf3.add(route10);
        signorRouteOf3.add(route11);

        removeDuplicate1(signorRouteOf1, signorRouteOf2, signorRouteOf3);

        showRoutes(signorRouteOf1, "Of1");
        showRoutes(signorRouteOf2, "Of2");
        showRoutes(signorRouteOf3, "Of3");
    }

    private static void removeDuplicate1(List<IntToPair> office1,
                                         List<IntToPair> office2,
                                         List<IntToPair> office3) {

        Iterator<IntToPair> routesOffice1Iterator = office1.iterator();
        Iterator<IntToPair> routesOffice2Iterator = office2.iterator();
        Iterator<IntToPair> routesOffice3Iterator = office3.iterator();


        while (routesOffice1Iterator.hasNext() || routesOffice2Iterator.hasNext() || routesOffice3Iterator.hasNext()) {
            boolean is = true;
            IntToPair routeOffice1 = routesOffice1Iterator.next();

            for (AgencyPoint agencyPoint : routeOffice1.getRoute()) {
                if (!is) {
                    break;
                }
                routesOffice2Iterator = office2.iterator();

                while (routesOffice2Iterator.hasNext()) {
                    if (!is) {
                        break;
                    }

                    IntToPair routeOffice2 = routesOffice2Iterator.next();
                    routesOffice3Iterator = office3.iterator();

                    while (routesOffice3Iterator.hasNext()) {
                        IntToPair routeOffice3 = routesOffice3Iterator.next();

                        boolean flagDuplicateOf1ToOf2 = routeOffice2.getRoute().contains(agencyPoint);
                        boolean flagDuplicateOf1ToOf3 = routeOffice3.getRoute().contains(agencyPoint);
                        int restTimeTaskOf1 = routeOffice1.getRestTimeTask();
                        int restTimeTaskOf2 = routeOffice2.getRestTimeTask();
                        int restTimeTaskOf3 = routeOffice3.getRestTimeTask();
                        int maxRestTimeTaskOf;
                        int employeesOffice1 = 1;
                        int employeesOffice2 = 1;
                        int employeesOffice3 = 1;
                        int sizeRoutesOf1 = office1.size();
                        int sizeRoutesOf2 = office2.size();
                        int sizeRoutesOf3 = office3.size();

                        if (flagDuplicateOf1ToOf2 && flagDuplicateOf1ToOf3) {
                            maxRestTimeTaskOf = getMaxRestTimeTask(restTimeTaskOf1, restTimeTaskOf2, restTimeTaskOf3);
                            if (employeesOffice1 <= sizeRoutesOf1 || employeesOffice2 <= sizeRoutesOf2 || employeesOffice3 <= sizeRoutesOf3) {
                                if (employeesOffice2 > sizeRoutesOf2 && employeesOffice3 > sizeRoutesOf3) {
                                    routesOffice2Iterator.remove();
                                    routesOffice3Iterator.remove();
                                    break;
                                } else if (employeesOffice1 > sizeRoutesOf1 && employeesOffice3 > sizeRoutesOf3) {
                                    routesOffice1Iterator.remove();
                                    routesOffice3Iterator.remove();
                                    is = false;
                                    break;
                                } else if (employeesOffice1 > sizeRoutesOf1 && employeesOffice2 > sizeRoutesOf2) {
                                    routesOffice1Iterator.remove();
                                    routesOffice2Iterator.remove();
                                    is = false;
                                    break;
                                } else {
                                    routesOffice2Iterator.remove();
                                    routesOffice3Iterator.remove();
                                    break;
                                }
                            } else if (maxRestTimeTaskOf == restTimeTaskOf1) {
                                routesOffice2Iterator.remove();
                                routesOffice3Iterator.remove();
                                break;
                            } else if (maxRestTimeTaskOf == restTimeTaskOf2) {
                                routesOffice1Iterator.remove();
                                routesOffice3Iterator.remove();
                                is = false;
                                break;
                            } else if (maxRestTimeTaskOf == restTimeTaskOf3) {
                                routesOffice1Iterator.remove();
                                routesOffice2Iterator.remove();
                                is = false;
                                break;
                            }
                        } else if (flagDuplicateOf1ToOf2) {
                            maxRestTimeTaskOf = Math.max(restTimeTaskOf1, restTimeTaskOf2);
                            if (maxRestTimeTaskOf != restTimeTaskOf1 && employeesOffice1 <= sizeRoutesOf1) {
                                routesOffice2Iterator.remove();
                                break;
                            } else if (maxRestTimeTaskOf == restTimeTaskOf2 && employeesOffice1 <= sizeRoutesOf1) {
                                routesOffice1Iterator.remove();
                                is = false;
                                break;
                            }
                        } else if (flagDuplicateOf1ToOf3) {
                            maxRestTimeTaskOf = Math.max(restTimeTaskOf1, restTimeTaskOf3);
                            if (maxRestTimeTaskOf == restTimeTaskOf1 && employeesOffice3 < sizeRoutesOf3) {
                                routesOffice2Iterator.remove();
                            } else {
                                routesOffice1Iterator.remove();
                                is = false;
                                break;
                            }
                        }

                    }
                }
            }
        }
    }

   /* private static void removeDuplicate(List<IntToPair> office1,
                                        List<IntToPair> office2,
                                        List<IntToPair> office3) {
        for (IntToPair routeOffice1 : office1) {
            boolean is = true;
            for (AgencyPoint point1 : routeOffice1.getRoute()) {
                if (!is){
                    break;
                }
                for (IntToPair routeOffice2 : office2) {
                    if (!is){
                        break;
                    }
                    *//* for (AgencyPoint point2: roteOffice2.getRoute()) {*//*
                    for (IntToPair routeOffice3 : office3) {
                        boolean flagDuplicateOf1ToOf2 = routeOffice2.getRoute().contains(point1);
                        boolean flagDuplicateOf1ToOf3 = routeOffice3.getRoute().contains(point1);
                        int restTimeTaskOf1 = routeOffice1.getRestTimeTask();
                        int restTimeTaskOf2 = routeOffice2.getRestTimeTask();
                        int restTimeTaskOf3 = routeOffice3.getRestTimeTask();
                        int maxRestTimeTaskOf;
                        int employeesOffice1 = 1;
                        int employeesOffice2 = 1;
                        int employeesOffice3 = 1;
                        int sizeRoutesOf1 = office1.size();
                        int sizeRoutesOf2 = office2.size();
                        int sizeRoutesOf3 = office3.size();

                        if (flagDuplicateOf1ToOf2 && flagDuplicateOf1ToOf3) {
                            maxRestTimeTaskOf = getMaxRestTimeTask(restTimeTaskOf1, restTimeTaskOf2, restTimeTaskOf3);
                            if (employeesOffice1 > sizeRoutesOf1 || employeesOffice2 > sizeRoutesOf2 || employeesOffice3 > sizeRoutesOf3) {
                                if (employeesOffice2 > sizeRoutesOf2 && employeesOffice3 > sizeRoutesOf3) {
                                    office2.remove(routeOffice2);
                                    office3.remove(routeOffice3);
                                } else if (employeesOffice1 > sizeRoutesOf1 && employeesOffice3 > sizeRoutesOf3) {
                                    office1.remove(routeOffice1);
                                    office3.remove(routeOffice3);
                                    is = false;
                                    break;
                                } else {
                                    office1.remove(routeOffice1);
                                    office2.remove(routeOffice2);
                                    is = false;
                                    break;
                                }
                            } else if (maxRestTimeTaskOf == restTimeTaskOf1) {
                                office2.remove(routeOffice2);
                                office3.remove(routeOffice3);
                            } else if (maxRestTimeTaskOf == restTimeTaskOf2) {
                                office1.remove(routeOffice1);
                                office3.remove(routeOffice3);
                                is = false;
                                break;
                            } else if (maxRestTimeTaskOf == restTimeTaskOf3) {
                                office1.remove(routeOffice1);
                                office2.remove(routeOffice2);
                                is = false;
                                break;
                            }
                        } else if (flagDuplicateOf1ToOf2) {
                            maxRestTimeTaskOf = Math.max(restTimeTaskOf1, restTimeTaskOf2);
                            if (maxRestTimeTaskOf == restTimeTaskOf1 && employeesOffice2 < sizeRoutesOf2) {
                                office2.remove(routeOffice2);
                            } else {
                                office1.remove(routeOffice1);
                                is = false;
                                break;
                            }
                        } else if (flagDuplicateOf1ToOf3) {
                            maxRestTimeTaskOf = Math.max(restTimeTaskOf1, restTimeTaskOf3);
                            if (maxRestTimeTaskOf == restTimeTaskOf1 && employeesOffice3 < sizeRoutesOf3) {
                                office3.remove(routeOffice2);
                            } else {
                                office1.remove(routeOffice1);
                                is = false;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }*/

    private static int getMaxRestTimeTask(int restTimeTaskOf1, int restTimeTaskOf2, int restTimeTaskOf3) {
        return Math.max(restTimeTaskOf3, (Math.max(restTimeTaskOf1, restTimeTaskOf2)));
    }

    private static void showRoutes(List<IntToPair> signorRouteOf, String grade) {
        System.out.println(grade);
        for (IntToPair routes :
                signorRouteOf) {
            System.out.println(routes.getRoute());
        }
    }
}
