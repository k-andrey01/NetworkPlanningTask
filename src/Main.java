import java.util.ArrayList;

public class Main {
    static int n = 9;

    public static void main(String[] args) {
        Way myWay = new Way();

        ArrayList<Way> graph = new ArrayList<>();
        graph.add(new Way(1, 2, 11));
        graph.add(new Way(1, 3, 18));
        graph.add(new Way(1, 4, 6));
        graph.add(new Way(1, 6, 10));
        graph.add(new Way(2, 5, 11));
        graph.add(new Way(2, 6, 15));
        graph.add(new Way(3, 4, 14));
        graph.add(new Way(3, 6, 10));
        graph.add(new Way(4, 7, 19));
        graph.add(new Way(5, 8, 5));
        graph.add(new Way(6, 8, 11));
        graph.add(new Way(6, 9, 12));
        graph.add(new Way(7, 9, 16));
        graph.add(new Way(8, 9, 14));

        System.out.println("=======================================");
        System.out.println("ВХОДЫ И ВЫХОДЫ СИСТЕМЫ");
        ArrayList<Integer> points = getPoints();
        for (Way g : graph) {
            if (points.contains(g.getX2())) {
                points.remove(points.indexOf(g.getX2()));
            }
        }
        System.out.println("Входы системы: " + points);

        points = getPoints();
        for (Way g : graph) {
            if (points.contains(g.getX1())) {
                points.remove(points.indexOf(g.getX1()));
            }
        }
        System.out.println("Выходы системы: " + points);

        System.out.println("\n=======================================");
        System.out.println("РАННИЕ СРОКИ СОВЕРШЕНИЯ СОБЫТИЙ");
        int[] earlyArr = new int[n];
        earlyArr[0] = 0;
        System.out.println("Ранний срок совершения события 1: " + earlyArr[0]);
        for (int i = 1; i < n; i++) {
            int max = Integer.MIN_VALUE;
            if (myWay.getWay(1, i + 1, graph) != null) {
                max = myWay.getWay(1, i + 1, graph).getWeight();
            }
            for (int j = 0; j < i; j++) {
                if (myWay.getWay(j + 1, i + 1, graph) != null && max < earlyArr[j] + myWay.getWay(j + 1, i + 1, graph).getWeight()) {
                    max = earlyArr[j] + myWay.getWay(j + 1, i + 1, graph).getWeight();
                }
            }
            earlyArr[i] = max;
            System.out.println("Ранний срок совершения события " + (i + 1) + ": " + earlyArr[i]);
        }

        System.out.println("\n=======================================");
        System.out.println("ДЛИНА КРИТИЧЕСКОГО ПУТИ");
        System.out.println("Время выполнения проекта (длина критического пути): " + earlyArr[n - 1]);

        System.out.println("\n=======================================");
        System.out.println("ПОЗДНИЕ СРОКИ СОВЕРШЕНИЯ СОБЫТИЙ");
        int[] lateArr = {earlyArr[n - 1], earlyArr[n - 1], earlyArr[n - 1], earlyArr[n - 1], earlyArr[n - 1], earlyArr[n - 1], earlyArr[n - 1], earlyArr[n - 1], earlyArr[n - 1]};
        for (int i = n - 2; i >= 0; i--) {
            int min = Integer.MAX_VALUE;
            if (myWay.getWay(i + 1, n, graph) != null) {
                min = lateArr[i] - myWay.getWay(i + 1, n, graph).getWeight();
            }
            for (int j = n - 2; j > i; j--) {
                if (myWay.getWay(i + 1, j + 1, graph) != null && min > lateArr[i] - myWay.getWay(i + 1, j + 1, graph).getWeight() - (lateArr[n - 1] - lateArr[j])) {
                    min = lateArr[i] - myWay.getWay(i + 1, j + 1, graph).getWeight() - (lateArr[n - 1] - lateArr[j]);
                }
            }
            lateArr[i] = min;
        }
        for (int i = 0; i < n; i++) {
            System.out.println("Поздний срок совершения события " + (i + 1) + ": " + lateArr[i]);
        }

        System.out.println("\n=======================================");
        System.out.println("ПОЛНЫЕ РЕЗЕРВЫ ДЛЯ РАБОТЫ");
        int count = 1;
        int[] fullReserve = new int[graph.size()];
        for (Way w : graph) {
            System.out.print("Полный резерв для работы " + count + ": ");
            int res = lateArr[w.getX2() - 1] - w.getWeight() - earlyArr[w.getX1() - 1];
            fullReserve[count - 1] = res;
            System.out.println(res);
            count++;
        }

        System.out.println("\n=======================================");
        System.out.println("СВОБОДНЫЕ РЕЗЕРВЫ ДЛЯ РАБОТЫ");
        count = 1;
        int[] freeReserve = new int[graph.size()];
        for (Way w : graph) {
            System.out.print("Свободный резерв для работы " + count + ": ");
            int res = earlyArr[w.getX2() - 1] - w.getWeight() - earlyArr[w.getX1() - 1];
            freeReserve[count - 1] = res;
            System.out.println(res);
            count++;
        }

        System.out.println("\n=======================================");
        System.out.println("КРИТИЧЕСКИЕ РАБОТЫ");
        ArrayList<Integer> critWorks = new ArrayList<>();
        System.out.print("Критические работы: ");
        for (int i = 0; i < fullReserve.length; i++) {
            if (fullReserve[i] == 0) {
                critWorks.add(i + 1);
                System.out.print((i + 1) + ", ");
            }
        }

        System.out.println("\n\n=======================================");
        System.out.println("КРИТИЧЕСКИЙ ПУТЬ");
        ArrayList<Integer> critWay = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (lateArr[i] == earlyArr[i])
                critWay.add(i + 1);
        }
        System.out.println("Критический путь: " + critWay);

        System.out.println("\n=======================================");
        System.out.println("ИЗМЕНЕНИЕ ПОЛНОГО РЕЗЕРВА ДЛЯ (6,9)");
        if (freeReserve[10] == 0) {
            System.out.println("Не изменится");
        } else {
            for (Way w : graph) {
                if (w.getX1() == 6 && w.getX2() == 9) {
                    System.out.print("Новый полный резерв для работы " + count + "(6,9) ");
                    int res = lateArr[w.getX2() - 1] + 4 - w.getWeight() - earlyArr[w.getX1() - 1];
                    System.out.println(res);
                    System.out.println("Разница: " + (fullReserve[count - 1] - res));
                }
                count++;
            }
        }

        System.out.println("\n=======================================");
        System.out.println("ИЗМЕНЕНИЕ ВРЕМЕНИ ВЫПОЛНЕНИЯ ПРОЕКТА");
        int res = earlyArr[n - 1];
        if (critWay.contains(6) && critWay.contains(8)) {
            res += 4;
            System.out.println("Старое время выполнения проекта: " + res);
            System.out.println("Новое время выполнения проекта: " + res);
            System.out.println("Разница: " + (earlyArr[n - 1] - res));
        } else {
            System.out.println("Не изменится");
        }
    }

    private static ArrayList<Integer> getPoints() {
        ArrayList<Integer> points = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            points.add(i);
        }

        return points;
    }
}
