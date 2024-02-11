package com.hashmac.scholarshiphub.utils;

import com.hashmac.scholarshiphub.dto.Common;
import com.hashmac.scholarshiphub.dto.University;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static List<Common> getAwardingCountries() {
        String baseUrl = "https://flagcdn.com/256x192/";
        List<Common> temp = new ArrayList<>();
        temp.add(new Common("Pakistan", baseUrl + "pk.png"));
        temp.add(new Common("USA", baseUrl + "us.png"));
        temp.add(new Common("Australia", baseUrl + "au.png"));
        temp.add(new Common("Canada", baseUrl + "ca.png"));
        temp.add(new Common("China", baseUrl + "cn.png"));
        temp.add(new Common("Germany", baseUrl + "de.png"));
        temp.add(new Common("India", baseUrl + "in.png"));
        temp.add(new Common("Japan", baseUrl + "jp.png"));
        temp.add(new Common("Netherlands", baseUrl + "nl.png"));
        temp.add(new Common("Norway", baseUrl + "no.png"));
        temp.add(new Common("Russia", baseUrl + "ru.png"));
        temp.add(new Common("Saudi Arabia", baseUrl + "sa.png"));
        temp.add(new Common("South Korea", baseUrl + "kr.png"));
        return temp;
    }

    public static List<Common> getContinents() {
        List<Common> temp = new ArrayList<>();
        temp.add(new Common("Africa", "https://purepng.com/public/uploads/large/map-of-africa-eqe.png"));
        temp.add(new Common("Antarctica", "https://cdn3.iconfinder.com/data/icons/countries-and-continents/512/Antarctica-512.png"));
        temp.add(new Common("Asia", "https://www.pngall.com/wp-content/uploads/5/Asia-PNG-Clipart.png"));
        temp.add(new Common("Australia", "https://www.pngmart.com/files/3/Australia-Map-Transparent-Background.png"));
        temp.add(new Common("Europe", "https://www.pngplay.com/wp-content/uploads/6/Europe-Map-Transparent-PNG.png"));
        temp.add(new Common("North America", "https://www.pngplay.com/wp-content/uploads/7/North-America-Map-PNG-Background.png"));
        temp.add(new Common("South America", "https://cdn1.iconfinder.com/data/icons/world-maps-1/512/South_America-512.png"));
        return temp;
    }

    public static List<Common> getSubjects() {
        List<Common> temp = new ArrayList<>();
        temp.add(new Common("Science", "https://www.pngall.com/wp-content/uploads/10/Science-Vector-PNG.png"));
        temp.add(new Common("Computer Science", "https://www.pngall.com/wp-content/uploads/14/Computing-PNG-Photos.png"));
        return temp;
    }

    public static List<University> getUniversities() {
        List<University> temp = new ArrayList<>();
        temp.add(new University("Pakistan Universities","50+", "https://lums.edu.pk/sites/default/files/styles/popup_gallery/public/2020-05/_1%20Library%20(1)_0.jpg"));
        temp.add(new University("USA Universities", "200+","https://pbs.twimg.com/media/C-RYEnCXsAIoFkw.jpg"));
        temp.add(new University("Australia Universities", "100+","https://assignmentstudio.net/wp-content/uploads/2018/12/https___cdn.evbuc_.com_images_43200365_121703163141_1_original.jpg"));
        return temp;
    }

    public static List<Common> getDegreeLevels() {
        List<Common> temp = new ArrayList<>();
        temp.add(new Common("Undergraduate", "https://www.ubc.ca/_assets/img/okanagan-undergraduates-1920x700.jpg"));
        temp.add(new Common("Postgraduate", "https://cf.ltkcdn.net/college/images/std/202586-634x450-Graduating-class.jpg"));
        temp.add(new Common("PhD", "https://img.freepik.com/premium-photo/teacher-talking-female-high-school-student-sitting-work-bench-using-laptop-design-technology-lesson_625516-2023.jpg"));
        return temp;
    }
}
