package netology;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final AtomicInteger counter3 = new AtomicInteger(0);
    private static final AtomicInteger counter4 = new AtomicInteger(0);
    private static final AtomicInteger counter5 = new AtomicInteger(0);

    public static void main(String[] args) {
        String[] texts = new String[100_000];
        Random random = new Random();

        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeThread = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    if (text.length() == 3) {
                        counter3.incrementAndGet();
                    } else if (text.length() == 4) {
                        counter4.incrementAndGet();
                    } else if (text.length() == 5) {
                        counter5.incrementAndGet();
                    }
                }
            }
        });

        Thread sameLetterThread = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text)) {
                    if (text.length() == 3) {
                        counter3.incrementAndGet();
                    } else if (text.length() == 4) {
                        counter4.incrementAndGet();
                    } else if (text.length() == 5) {
                        counter5.incrementAndGet();
                    }
                }
            }
        });

        Thread increasingOrderThread = new Thread(() -> {
            for (String text : texts) {
                if (isIncreasingOrder(text)) {
                    if (text.length() == 3) {
                        counter3.incrementAndGet();
                    } else if (text.length() == 4) {
                        counter4.incrementAndGet();
                    } else if (text.length() == 5) {
                        counter5.incrementAndGet();
                    }
                }
            }
        });

        palindromeThread.start();
        sameLetterThread.start();
        increasingOrderThread.start();

        try {
            palindromeThread.join();
            sameLetterThread.join();
            increasingOrderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Красивых слов с длиной 3: " + counter3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + counter4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + counter5.get() + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        int left = 0;
        int right = text.length() - 1;

        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static boolean isSameLetter(String text) {
        char firstChar = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }

    public static boolean isIncreasingOrder(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) > text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }
}