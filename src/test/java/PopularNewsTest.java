import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.*;

public class PopularNewsTest{

    public static void main(String[]args){
        System.setProperty("webdriver.chrome.driver", "/home/admin1/Documents/PopulerNews/Driver/chromedriver");
        WebDriver driver = new ChromeDriver();

        List<String> newsHeading = new ArrayList<>();
        List<String> newsPoints = new ArrayList<>();
        Map<String, Integer> newsMap = new HashMap<>();

        driver.get("https://news.ycombinator.com");
        driver.findElement(By.xpath("//a[@class='morelink' and @rel='next']")).click();
        driver.findElement(By.xpath("//a[@class='morelink' and @rel='next']")).click();
        driver.findElement(By.xpath("//a[@class='morelink' and @rel='next']")).click();

        List<WebElement> newsElements = driver.findElements(By.cssSelector("a.storylink"));
        List<WebElement> scorePoints = driver.findElements(By.cssSelector("tr td span.score"));

        for (WebElement webElement : newsElements){
            System.out.println(webElement.getText());
            newsHeading.add(webElement.getText());
        }

        for (WebElement webElement : scorePoints){
            System.out.println(webElement.getText().substring(0, webElement.getText().length() - 7));
            newsPoints.add(webElement.getText().substring(0, webElement.getText().length() - 7));
        }

        for (int i = 0; i < newsHeading.size(); i++){
            System.out.println(newsHeading.get(i));
            System.out.println(Integer.parseInt(newsPoints.get(i)));
            newsMap.put(newsHeading.get(i), Integer.parseInt(newsPoints.get(i)));
        }

        List<String> listOfWords = listOfWords(newsHeading);
        String maxWord = getWordCountInList(listOfWords);
        System.out.println("The word occure maximum times is : " + maxWord);
        String maxNewsHeading = getMostPopularNews(newsMap, maxWord);
        System.out.println("Most popular news is : " + maxNewsHeading);
    }

    //given function for finding word list
    static List<String> listOfWords(List<String> news){
        List<String> listOfWords = new ArrayList<String>();
        for (String s : news) {
            String[] arrOfString = s.split(" ");
            List<String> l1 = Arrays.asList(arrOfString);
            listOfWords.addAll(l1);
        }
        System.out.println(listOfWords);
        return listOfWords;
    }

    //givan function for finding
    static String getWordCountInList(List<String> arr){
        Map<String, Integer> newsMap = new HashMap<>();

        for(int i = 0; i < arr.size(); i++){
            if (newsMap.containsKey(arr.get(i))){
                newsMap.put(arr.get(i), newsMap.get(arr.get(i)) + 1);
            } else{
                newsMap.put(arr.get(i), 1);
            }
        }
        String highestValue = getHighestOccuringWord(newsMap);
        return highestValue;
    }

    static String getHighestOccuringWord(Map<String, Integer> newsMap){
        Set<Map.Entry<String, Integer>> set = newsMap.entrySet();
        String key = "";
        int value = 0;

        for (Map.Entry<String, Integer> me : set){
            if (me.getValue() > value){
                value = me.getValue();
                key = me.getKey();
            }
        }
        System.out.println(key + "--" + value);
        return key;
    }

    static String getMostPopularNews(Map<String, Integer> newsMap, String mostRepetedWord){
        int value = 0;
        String popularWord = " ";

        for (Map.Entry<String, Integer> newNewsMap : newsMap.entrySet()){
            if (newNewsMap.getKey().contains(mostRepetedWord)){

                if (newNewsMap.getValue() > value){
                    value = newNewsMap.getValue();
                    popularWord = newNewsMap.getKey();
                }
            }
        }
        return popularWord;
    }
}
