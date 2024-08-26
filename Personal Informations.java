import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Person {
    String name, gender, address, phoneNumber, job;
    int age, salary;

    Person(String name, String gender, String address, String phoneNumber, String job, int age, int salary) {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.job = job;
        this.age = age;
        this.salary = salary;
    }
}

public class test {
    private static final String[] SURNAMES = {"김", "이", "박", "최", "정", "강", "조", "윤", "장", "임"};
    private static final String[] NAME_CHARS = {"민", "서", "지", "예", "주", "윤", "지", "도", "하", "유", "진", "선", "우", "현", "영", "미", "성", "준", "원", "혜"};
    private static final String[] CITIES = {"서울", "부산", "인천", "대구", "대전", "광주", "울산", "수원", "창원", "고양"};

    private static final String[] JOBS = {
            "교사", "의사", "간호사", "엔지니어", "프로그래머", "디자이너", "회계사", "변호사", "경찰관", "소방관",
            "요리사", "운전기사", "판매원", "마케터", "기자", "작가", "배우", "가수", "화가", "사진작가",
            "건축가", "인테리어 디자이너", "패션 디자이너", "헤어 디자이너", "메이크업 아티스트", "운동선수", "코치", "트레이너", "영양사", "약사",
            "수의사", "농부", "어부", "목수", "전기기술자", "배관공", "용접공", "기계공", "택시 운전사", "버스 운전사",
            "파일럿", "승무원", "호텔리어", "여행 가이드", "통역사", "번역가", "학원 강사", "과학자", "연구원", "대학교수"
    };

    private static final Map<String, int[]> JOB_SALARY_RANGES = new HashMap<>();
    static {
        JOB_SALARY_RANGES.put("교사", new int[]{3000, 6000});
        JOB_SALARY_RANGES.put("의사", new int[]{6000, 20000});
        JOB_SALARY_RANGES.put("간호사", new int[]{3000, 6000});
        JOB_SALARY_RANGES.put("엔지니어", new int[]{3500, 8000});
        JOB_SALARY_RANGES.put("프로그래머", new int[]{3500, 10000});
        JOB_SALARY_RANGES.put("디자이너", new int[]{2800, 7000});
        JOB_SALARY_RANGES.put("회계사", new int[]{4000, 10000});
        JOB_SALARY_RANGES.put("변호사", new int[]{5000, 15000});
        JOB_SALARY_RANGES.put("경찰관", new int[]{3000, 6000});
        JOB_SALARY_RANGES.put("소방관", new int[]{3000, 6000});
        JOB_SALARY_RANGES.put("요리사", new int[]{2500, 8000});
        JOB_SALARY_RANGES.put("운전기사", new int[]{2500, 4000});
        JOB_SALARY_RANGES.put("판매원", new int[]{2000, 4000});
        JOB_SALARY_RANGES.put("마케터", new int[]{3000, 8000});
        JOB_SALARY_RANGES.put("기자", new int[]{3000, 7000});
        JOB_SALARY_RANGES.put("작가", new int[]{2000, 10000});
        JOB_SALARY_RANGES.put("배우", new int[]{2000, 50000});
        JOB_SALARY_RANGES.put("가수", new int[]{2000, 50000});
        JOB_SALARY_RANGES.put("화가", new int[]{2000, 10000});
        JOB_SALARY_RANGES.put("사진작가", new int[]{2500, 8000});
        JOB_SALARY_RANGES.put("건축가", new int[]{4000, 12000});
        JOB_SALARY_RANGES.put("인테리어 디자이너", new int[]{3000, 8000});
        JOB_SALARY_RANGES.put("패션 디자이너", new int[]{3000, 10000});
        JOB_SALARY_RANGES.put("헤어 디자이너", new int[]{2500, 7000});
        JOB_SALARY_RANGES.put("메이크업 아티스트", new int[]{2500, 8000});
        JOB_SALARY_RANGES.put("운동선수", new int[]{3000, 50000});
        JOB_SALARY_RANGES.put("코치", new int[]{3000, 10000});
        JOB_SALARY_RANGES.put("트레이너", new int[]{2500, 6000});
        JOB_SALARY_RANGES.put("영양사", new int[]{2500, 5000});
        JOB_SALARY_RANGES.put("약사", new int[]{4000, 8000});
        JOB_SALARY_RANGES.put("수의사", new int[]{3500, 10000});
        JOB_SALARY_RANGES.put("농부", new int[]{2000, 5000});
        JOB_SALARY_RANGES.put("어부", new int[]{2000, 6000});
        JOB_SALARY_RANGES.put("목수", new int[]{2500, 6000});
        JOB_SALARY_RANGES.put("전기기술자", new int[]{3000, 7000});
        JOB_SALARY_RANGES.put("배관공", new int[]{2500, 6000});
        JOB_SALARY_RANGES.put("용접공", new int[]{2500, 6000});
        JOB_SALARY_RANGES.put("기계공", new int[]{3000, 7000});
        JOB_SALARY_RANGES.put("택시 운전사", new int[]{2000, 4000});
        JOB_SALARY_RANGES.put("버스 운전사", new int[]{2500, 4500});
        JOB_SALARY_RANGES.put("파일럿", new int[]{6000, 15000});
        JOB_SALARY_RANGES.put("승무원", new int[]{3000, 7000});
        JOB_SALARY_RANGES.put("호텔리어", new int[]{2500, 6000});
        JOB_SALARY_RANGES.put("여행 가이드", new int[]{2000, 5000});
        JOB_SALARY_RANGES.put("통역사", new int[]{3000, 8000});
        JOB_SALARY_RANGES.put("번역가", new int[]{2500, 7000});
        JOB_SALARY_RANGES.put("학원 강사", new int[]{2500, 6000});
        JOB_SALARY_RANGES.put("과학자", new int[]{4000, 12000});
        JOB_SALARY_RANGES.put("연구원", new int[]{3500, 10000});
        JOB_SALARY_RANGES.put("대학교수", new int[]{5000, 15000});
    }

    private static final Random random = new Random();

    public static String generateName() {
        return SURNAMES[random.nextInt(SURNAMES.length)] +
                NAME_CHARS[random.nextInt(NAME_CHARS.length)] +
                NAME_CHARS[random.nextInt(NAME_CHARS.length)];
    }

    public static String generatePhoneNumber() {
        return String.format("010-%04d-%04d", random.nextInt(10000), random.nextInt(10000));
    }

    public static int generateSalary(String job) {
        int[] range = JOB_SALARY_RANGES.getOrDefault(job, new int[]{2000, 5000});
        return random.nextInt(range[1] - range[0] + 1) + range[0];
    }

    public static List<Person> generateKoreanPersons(int count) {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String name = generateName();
            String gender = (i < count / 2) ? "남성" : "여성";
            String address = CITIES[random.nextInt(CITIES.length)];
            String phoneNumber = generatePhoneNumber();
            String job = JOBS[random.nextInt(JOBS.length)];
            int age = random.nextInt(46) + 20; // 20 to 65 years old
            int salary = generateSalary(job);

            persons.add(new Person(name, gender, address, phoneNumber, job, age, salary));
        }
        return persons;
    }

    public static void savePersonsToCSV(List<Person> persons, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("ID,Name,Gender,Age,Address,PhoneNumber,Job,Salary\n");
            for (int i = 0; i < persons.size(); i++) {
                Person p = persons.get(i);
                writer.append(String.format("%d,%s,%s,%d,%s,%s,%s,%d\n",
                        i+1, p.name, p.gender, p.age, p.address, p.phoneNumber, p.job, p.salary));
            }
            System.out.println("CSV 파일이 성공적으로 생성되었습니다: " + fileName);
        } catch (IOException e) {
            System.out.println("CSV 파일 생성 중 오류 발생: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        List<Person> koreanPersons = generateKoreanPersons(1000);
        System.out.println("생성된 1000명의 한국인 정보:");
        for (Person person : koreanPersons) {
            System.out.println(person.name + ", " + person.gender + ", " + person.age + "세, " +
                    person.address + ", " + person.phoneNumber + ", " + person.job + ", 연봉 " + person.salary + "만원");
        }
        System.out.println("총 인원 수: " + koreanPersons.size());

        savePersonsToCSV(koreanPersons, "korean_persons.csv");
    }
}
